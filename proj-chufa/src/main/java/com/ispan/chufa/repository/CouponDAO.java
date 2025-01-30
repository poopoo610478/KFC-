package com.ispan.chufa.repository;

import java.util.List;

import com.ispan.chufa.domain.CouponBean;

public interface CouponDAO {

    public List<CouponBean> findByTitleAndRemaining(String title, String couponCode);
 }