package com.ispan.chufa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.chufa.domain.PlaceBean;
import com.ispan.chufa.domain.PostBean;
import com.ispan.chufa.dto.InteractionDTO;
import com.ispan.chufa.dto.PostDTO;
import com.ispan.chufa.dto.PostResponse;
import com.ispan.chufa.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    // @JsonView(Views.Public.class)
    public PostResponse find(@RequestBody String json) {
        PostResponse responseBean = new PostResponse();

        List<PostDTO> posts = postService.findPostsByCriteria(json);
        // 用PostDTO因為改傳MemberDTO只回傳部分資料
        if (posts != null && !posts.isEmpty()) {
            responseBean.setPostdto(posts);
        } else {
            responseBean.setPostdto(new ArrayList<>());
        }

        return responseBean;
    }

    @PostMapping("/listall")
    public String postMethodName(@RequestBody String entity) {
        

        return entity;
    }

    @PostMapping("/insertinteraction")
    public InteractionDTO insertaction(@RequestBody String json) {
        InteractionDTO response = postService.performaction(json);
        try {
            // 檢查是否有缺少必要的參數
            if (json == null || !json.contains("userid") || !json.contains("interactiontype")
                    || !json.contains("postid")) {
                response.setSuccess(false);
                response.setMessage("缺少必要的參數");
                return response;
            }

            if (response == null) {
                response = new InteractionDTO(); // 如果 response 為 null重新初始化一個新的 InteractionDTO
                response.setSuccess(false);
                response.setMessage("操作失敗：參數無效或用戶不存在");
            } else {
                response.setMessage("操作成功");
                response.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("操作失敗：" + e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    // 創建 Post 並關聯多個 Place
    @PostMapping("/create")
    public ResponseEntity<PostBean> createPostWithPlaces(@RequestBody PostBean post) {
        // 假設前端會提供一個 post 和一個包含 Place ID 的 list
        Set<Long> placeIds = post.getPlaces().stream()
                .map(PlaceBean::getPlaceId)
                .collect(Collectors.toSet());
        PostBean createdPost = postService.createPostWithPlaces(post, placeIds);
        return ResponseEntity.ok(createdPost);
    }

    // 查詢指定 ID 的 Post 和其關聯的 Places
    @GetMapping("/{id}")
    public ResponseEntity<PostBean> getPostById(@PathVariable Long id) {
        PostBean post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

}
