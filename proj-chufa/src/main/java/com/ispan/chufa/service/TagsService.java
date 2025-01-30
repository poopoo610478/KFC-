package com.ispan.chufa.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.chufa.domain.TagsBean;
import com.ispan.chufa.repository.TagsRepository;

@Service
public class TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    // 刪除標籤
    public Boolean deleteTags(Long id) {
        if (id != null) {
            if (tagsRepository.existsById(id)) {
                tagsRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    // 用ID查詢標籤
    public TagsBean findById(Long id) {
        if (id != null) {
            Optional<TagsBean> optional = tagsRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 更新標籤
    public TagsBean updateTags(Long id, String tagName) {
        TagsBean bean = findById(id);
        if (bean != null) {
            bean.setTagUpdatedAt(LocalDateTime.now());// 標籤系統_更新時間 set現在時間
            bean.setTagName(tagName);// 標籤系統_標籤名稱 RequestBody獲取
            return tagsRepository.save(bean);
        } else {
            return null;
        }
    }

    // 更新標籤狀態
    public TagsBean updateTagsState(Long id, String tagState) {
        TagsBean bean = findById(id);
        if (bean != null) {
            bean.setTagUpdatedAt(LocalDateTime.now());// 標籤系統_更新時間 set現在時間
            bean.setTagState(tagState);// 標籤系統_標籤名稱 RequestBody獲取
            return tagsRepository.save(bean);
        } else {
            return null;
        }
    }
}
