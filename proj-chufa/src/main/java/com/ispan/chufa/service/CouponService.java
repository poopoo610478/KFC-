package com.ispan.chufa.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.chufa.domain.CouponBean;
import com.ispan.chufa.repository.CouponRepository;
import com.ispan.chufa.repository.MyCouponRepository;

import jakarta.transaction.Transactional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

	public List<CouponBean> getAllCoupons() {
        return couponRepository.findAll(); // 查詢所有優惠券
    }

    @Autowired
    private MyCouponRepository myCouponRepository;

    @Transactional
    public void deleteCoupon(Long couponId) {
        // 取得優惠券資料
        Optional<CouponBean> couponOpt = couponRepository.findById(couponId);
        
        if (couponOpt.isPresent()) {
            CouponBean coupon = couponOpt.get();
            // 先刪除所有與該優惠券相關的使用者優惠券資料
            myCouponRepository.deleteByCouponBean(coupon);

            // 刪除優惠券資料
            couponRepository.deleteById(couponId);
        } else {
            // 異常直接拋出 RuntimeException
            throw new RuntimeException("Coupon with id " + couponId + " not found");
        }
    }
    
 // 新增
  	public CouponBean insert(CouponBean Coupon) {
  		if(Coupon != null) {
  			couponRepository.save(Coupon);
  		}
  		return Coupon;
  	}
  	
  	// 修改
  	public Boolean update(CouponBean Coupon) {
  		if(Coupon != null && couponRepository.existsById(Coupon.getCouponId())) {
  			couponRepository.save(Coupon);
  			return true;
  		}
  		return false;
  	}
  	
  	//刪除
  	public Boolean delete(CouponBean Coupon) {
  		if(Coupon != null && couponRepository.existsById(Coupon.getCouponId())) {
  			couponRepository.deleteById(Coupon.getCouponId());
  			return true;
  		}
  		return false;
  	}
  	
  	//用 id 刪除
  	public Boolean deleteById(Long id) {
  		if(couponRepository.existsById(id)) {
  			couponRepository.deleteById(id);
  			return true;
  		}
  		return false;
  	}
  	
  	
  	// 用 id 查詢
  	public CouponBean selectById(Long id) {
  		if(id != null && couponRepository.existsById(id)) {
  			return couponRepository.findById(id).get();
  		}
  		return null;
  	}
  	
  	// 檢查資料缺失的情況，若有缺失則填入 null (field 都是 wrapper，可 null)
  	String getField(String[] fields, Integer index) {
          return (index != null && index < fields.length) ? fields[index].trim() : null;
      }
  	
  	// 如果有無法轉換成 Integer 的 String 傳入則該欄位存入 null
  	Integer safeParseInteger(String value) {
  	    try {
  	        return value != null && !value.isEmpty() ? Integer.valueOf(value) : null;
  	    } catch (NumberFormatException e) {
  	        return null; // 無法轉換時存入 null
  	    }
  	}
  	
  	// 轉換LocalDate
  	public LocalDateTime safeParseLocalDateTime(String value) {
  	    try {
  	        return value != null && !value.isEmpty() ? LocalDateTime.parse(value) : null; // 直接解析日期（yyyy-MM-dd 格式）
  	    } catch (Exception e) {
  	        return null; // 无法转换时存入 null
  	    }
  	}

  	// 如果有無法轉換成 Integer 的 String 傳入則該欄位存入 null
  	Boolean safeParseBoolean(String value) {
  		if( value == "T") {
  	    	return true;
      	}
  		else{
  	    	return false;
      	}
  	}
  	
  	// 把切割完的 String 轉成 Integer 呼叫 setter 方法，若有無法轉換的字串則跳過
  	CouponBean mapLineToBean(String line, Map<String, Integer> fieldIndex, String fileName) {
          try {
              String[] fields = line.split(",");
              CouponBean Coupon = new CouponBean();

           // 映射欄位值
              Coupon.setTimesOfUse(safeParseInteger(getField(fields, fieldIndex.get("TimesOfUse"))));
              Coupon.setCouponCode(getField(fields, fieldIndex.get("couponCode")));
              Coupon.setTitle(getField(fields, fieldIndex.get("title")));
              Coupon.setContent(getField(fields, fieldIndex.get("content")));
              Coupon.setState(safeParseBoolean(getField(fields, fieldIndex.get("state"))));
              Coupon.setPicture(getField(fields, fieldIndex.get("picture")));
              Coupon.setStartTime(safeParseLocalDateTime(getField(fields, fieldIndex.get("startTime"))));
              Coupon.setEndTime(safeParseLocalDateTime(getField(fields, fieldIndex.get("endTime"))));
//              bean.setsTime(getField(fields, fieldIndex.get("sTime")));
//              bean.seteTime(getField(fields, fieldIndex.get("eTime")));
      

              // 填入自動生成系統資訊
              return Coupon;
          } catch (Exception e) {
              // 忽略有問題的資料
          	return null;
          }
      }
  	
  	 public void processAndSaveCsv(Path filePath, String fileName) throws IOException {
  	        // 讀取所有行
  	        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
  	        if (lines.isEmpty()) {
  	            throw new IllegalArgumentException("不可傳入空檔案");
  	        }
  	        // 第一行
  	        String[] fieldTypes = lines.get(0).split(",");
  	        // Map("name",1) 對第一行建立索引。
  	        Map<String,Integer> fieldIndex = new HashMap<>();
  	        for(int i =0; i<fieldTypes.length;i++) {
  	        	fieldIndex.put(fieldTypes[i],i);
  	        }
  	        
  	        List<CouponBean> beanList = 
  	        		lines.stream()
  	        		.skip(1)
  	        		.map(line -> mapLineToBean(line, fieldIndex, fileName))
  	        		.filter(Objects::nonNull)
  	        		.collect(Collectors.toList());
   
  	        couponRepository.saveAll(beanList);	   
  	    }
  	 
  // 新增根據標題和剩餘數量條件查詢的方法
  	public List<CouponBean> findCouponsByTitleAndRemaining(String title, String couponCode) {
  	    if (title == null && couponCode == null) {
  	        return couponRepository.findAll();
  	    }
  	    return couponRepository.findByTitleAndRemaining(title, couponCode);
  	}
  	/**
     * 生成唯一優惠券代碼
     * @return 唯一優惠券代碼
     */
    public String generateUniqueCode() {
        return "COUPON-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
