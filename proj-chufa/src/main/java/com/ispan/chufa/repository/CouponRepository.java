package com.ispan.chufa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.chufa.domain.CouponBean;

@Repository
public interface CouponRepository extends JpaRepository<CouponBean, Long>, CouponDAO {
    // 根據標題和剩餘數量查詢
    List<CouponBean> findByTitleAndRemaining(String title, String couponCode);

    // 根據優惠券代碼檢查是否存在
    boolean existsByCouponCode(String couponCode);
}
