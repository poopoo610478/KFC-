package com.ispan.chufa.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ispan.chufa.domain.CouponBean;
import com.ispan.chufa.repository.CouponRepository;


@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;
       
    // 新增優惠券
    @PostMapping
    public ResponseEntity<CouponBean> createCoupon(@RequestBody CouponBean couponBean) {
        CouponBean savedCoupon = couponRepository.save(couponBean);
        return ResponseEntity.ok(savedCoupon);
    }

    // 查詢所有優惠券
    @GetMapping
    public ResponseEntity<List<CouponBean>> getAllCoupons() {
        List<CouponBean> coupons = couponRepository.findAll();
        return ResponseEntity.ok(coupons);
    }

    // // 查詢特定優惠券
    // @GetMapping("/{id}")
    // public ResponseEntity<CouponBean> getCouponById(@PathVariable Long id) {
    //     Optional<CouponBean> coupon = couponRepository.findById(id);
    //     return coupon.map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // // 修改優惠券
    // @PutMapping("/{id}")
    // public ResponseEntity<CouponBean> updateCoupon(@PathVariable Long id, @RequestBody CouponBean updatedCoupon) {
    //     return couponRepository.findById(id).map(coupon -> {
    //         coupon.setCouponCode(updatedCoupon.getCouponCode());
    //         coupon.setTimesOfUse(updatedCoupon.getTimesOfUse());
    //         coupon.setTitle(updatedCoupon.getTitle());
    //         coupon.setContent(updatedCoupon.getContent());
    //         coupon.setState(updatedCoupon.getState());
    //         coupon.setPicture(updatedCoupon.getPicture());
    //         coupon.setStartTime(updatedCoupon.getStartTime());
    //         coupon.setEndTime(updatedCoupon.getEndTime());
    //         CouponBean savedCoupon = couponRepository.save(coupon);
    //         return ResponseEntity.ok(savedCoupon);
    //     }).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // 刪除優惠券
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Map<String, String>> deleteCoupon(@PathVariable Long id) {
    //     try {
    //         couponService.deleteCoupon(id);
            
    //         // 成功刪除後回傳 JSON 格式訊息
    //         Map<String, String> response = new HashMap<>();
    //         response.put("message", "Coupon with id " + id + " has been successfully deleted.");
            
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    //     } catch (RuntimeException ex) {
    //         Map<String, String> errorResponse = new HashMap<>();
    //         errorResponse.put("message", "Coupon with id " + id + " not found.");
            
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    //     }
    // }
}

