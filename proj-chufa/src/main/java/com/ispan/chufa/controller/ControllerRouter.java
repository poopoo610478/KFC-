package com.ispan.chufa.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.chufa.domain.CouponBean;
import com.ispan.chufa.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class ControllerRouter {

    private static final String UPLOAD_DIR = "C:\\JavaFramework\\sts-workspace\\CouponKFC\\ispan_chufa\\proj-chufa\\src\\main\\resources\\static\\UploadImages";

    @Autowired
    private CouponService couponService;

    // @GetMapping("/all")
    // public ResponseEntity<?> getAllCoupons() {
    //     List<CouponBean> coupons = couponService.getAllCoupons();
    //     return ResponseEntity.ok(coupons);
    // }

    // @GetMapping("/id/{id}")
    // public ResponseEntity<?> getCouponById(@PathVariable Long id) {
    //     CouponBean coupon = couponService.selectById(id);
    //     if (coupon == null) {
    //         return ResponseEntity.status(404).body("找不到優惠券！");
    //     }
    //     return ResponseEntity.ok(coupon);
    // }


    @PostMapping("/Coupon")
    @ResponseBody
    public ResponseEntity<?> addCoupon(
            @ModelAttribute CouponBean coupon, // 接收其他表單欄位
            @RequestParam(value = "pic", required = false) MultipartFile picture) { // 單獨處理圖片文件
        try {
            // 確保圖片目錄存在
            File uploadDirFile = new File(UPLOAD_DIR);
            if (!uploadDirFile.exists()) {
                boolean dirCreated = uploadDirFile.mkdirs();
                System.out.println("圖片目錄創建結果：" + dirCreated);
            }

            // 處理圖片文件
            if (picture != null && !picture.isEmpty()) {
                String originalFilename = picture.getOriginalFilename();
                System.out.println("接收到的圖片名稱：" + originalFilename);

                Path filePath = Paths.get(UPLOAD_DIR, originalFilename);
                Files.write(filePath, picture.getBytes());
                System.out.println("圖片成功保存到：" + filePath.toString());

                // 設定圖片路徑到 coupon 的 picture 屬性
                coupon.setPicture("/UploadImages/" + originalFilename);
            } else {
                System.out.println("未上傳圖片或圖片為空");
            }

//            // 自動生成 couponCode
//            if (coupon.getCouponCode() == null || coupon.getCouponCode().isEmpty()) {
//                coupon.setCouponCode(couponService.generateUniqueCode());
//            }


            // 根據時間設定優惠券的有效狀態
            LocalDateTime now = LocalDateTime.now();
            if (coupon.getStartTime() != null && coupon.getEndTime() != null) {
                boolean isValid = now.isAfter(coupon.getStartTime()) && now.isBefore(coupon.getEndTime());
                coupon.setState(isValid);
            }

            // 儲存到資料庫
            couponService.insert(coupon);
            System.out.println("新增成功，資料已保存至資料庫");

            return ResponseEntity.ok("新增成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("圖片儲存失敗：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("新增失敗：" + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCoupon(
            @PathVariable Long id,
            @ModelAttribute CouponBean coupon,
            @RequestParam(value = "pic", required = false) MultipartFile picture) {
        try {
            CouponBean existingCoupon = couponService.selectById(id);
            if (existingCoupon == null) {
                return ResponseEntity.status(404).body("找不到優惠券！");
            }

            // 更新圖片
            if (picture != null && !picture.isEmpty()) {
                String originalFilename = picture.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, originalFilename);
                Files.write(filePath, picture.getBytes());

                // 刪除舊圖片
                if (existingCoupon.getPicture() != null) {
                    Path oldPath = Paths.get(UPLOAD_DIR, existingCoupon.getPicture().substring("/UploadImages/".length()));
                    if (Files.exists(oldPath)) {
                        Files.delete(oldPath);
                    }
                }

                existingCoupon.setPicture("/UploadImages/" + originalFilename);
            }

            // **只更新有變動的欄位**
            if (coupon.getTitle() != null) existingCoupon.setTitle(coupon.getTitle());
            if (coupon.getContent() != null) existingCoupon.setContent(coupon.getContent());
            if (coupon.getPrice() != null) existingCoupon.setPrice(coupon.getPrice());
            if (coupon.getStartTime() != null) existingCoupon.setStartTime(coupon.getStartTime());
            if (coupon.getEndTime() != null) existingCoupon.setEndTime(coupon.getEndTime());

            // **更新餐點資訊**
            if (coupon.getFriedChicken() != null) existingCoupon.setFriedChicken(coupon.getFriedChicken());
            if (coupon.getEggTart() != null) existingCoupon.setEggTart(coupon.getEggTart());
            if (coupon.getHamburger() != null) existingCoupon.setHamburger(coupon.getHamburger());
            if (coupon.getChickenNuggets() != null) existingCoupon.setChickenNuggets(coupon.getChickenNuggets());
            if (coupon.getDrinks() != null) existingCoupon.setDrinks(coupon.getDrinks());
            if (coupon.getFries() != null) existingCoupon.setFries(coupon.getFries());
            if (coupon.getQQBall() != null) existingCoupon.setQQBall(coupon.getQQBall());
            if (coupon.getChikenRice() != null) existingCoupon.setChikenRice(coupon.getChikenRice());
            if (coupon.getHerbChiken() != null) existingCoupon.setHerbChiken(coupon.getHerbChiken());
            if (coupon.getMexicoChiken() != null) existingCoupon.setMexicoChiken(coupon.getMexicoChiken());
            if (coupon.getFishDonut() != null) existingCoupon.setFishDonut(coupon.getFishDonut());
            if (coupon.getShrimpNugget() != null) existingCoupon.setShrimpNugget(coupon.getShrimpNugget());

            // **更新餐廳排除資訊**
            if (coupon.getTaipeiFuhSingNan() != null) existingCoupon.setTaipeiFuhSingNan(coupon.getTaipeiFuhSingNan());
            if (coupon.getTaipeiKuangFu() != null) existingCoupon.setTaipeiKuangFu(coupon.getTaipeiKuangFu());
            if (coupon.getSinTiamAnKang() != null) existingCoupon.setSinTiamAnKang(coupon.getSinTiamAnKang());
            if (coupon.getSinTiamBaLi() != null) existingCoupon.setSinTiamBaLi(coupon.getSinTiamBaLi());
            if (coupon.getTaoyuanHighRail() != null) existingCoupon.setTaoyuanHighRail(coupon.getTaoyuanHighRail());
            if (coupon.getYilanCarrefour() != null) existingCoupon.setYilanCarrefour(coupon.getYilanCarrefour());
            if (coupon.getYilanJiaoxi() != null) existingCoupon.setYilanJiaoxi(coupon.getYilanJiaoxi());
            if (coupon.getTaichungFuhSing() != null) existingCoupon.setTaichungFuhSing(coupon.getTaichungFuhSing());
            if (coupon.getPingTungDongGang() != null) existingCoupon.setPingTungDongGang(coupon.getPingTungDongGang());

            couponService.update(existingCoupon);
            return ResponseEntity.ok("修改成功！");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("圖片儲存失敗：" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("修改失敗：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        try {
            couponService.deleteById(id);
            return ResponseEntity.ok("刪除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("刪除失敗：" + e.getMessage());
        }
    }
    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchCoupons(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "couponCode", required = false) String couponCode) {
        try {
            // 調用 Service 方法進行查詢
            List<CouponBean> coupons = couponService.findCouponsByTitleAndRemaining(title, couponCode);

            if (coupons.isEmpty()) {
                return ResponseEntity.ok("未找到符合條件的優惠券！");
            }
            return ResponseEntity.ok(coupons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("查詢失敗：" + e.getMessage());
        }
    }
    // 新增獲取所有圖片的 API
    @GetMapping("/images/all")
@ResponseBody
public ResponseEntity<?> fetchAllImages() {
    try {
        // 從資料庫獲取所有圖片資料
        List<CouponBean> coupons = couponService.getAllCoupons();

        // 獲取靜態目錄中所有圖片檔案
        File directory = new File("C:/JavaFramework/sts-workspace/CouponKFC/ispan_chufa/proj-chufa/src/main/resources/static/UploadImages");
        if (!directory.exists() || !directory.isDirectory()) {
            return ResponseEntity.badRequest().body("圖片目錄不存在！");
        }

        String[] fileNames = directory.list((dir, name) -> {
            String lowerCaseName = name.toLowerCase();
            return lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")
                    || lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".gif");
        });

        if (fileNames == null) {
            fileNames = new String[0];
        }

        // 資料庫資料轉換為 Map 結構，鍵為純檔案名稱（去除路徑）
        Map<String, CouponBean> databaseImageMap = coupons.stream()
                .collect(Collectors.toMap(
                        coupon -> {
                            // 提取圖片檔案名稱部分（去掉路徑）
                            String picture = coupon.getPicture();
                            if (picture != null && picture.contains("/")) {
                                return picture.substring(picture.lastIndexOf("/") + 1); // 只取檔案名
                            }
                            return picture;
                        },
                        coupon -> coupon,
                        (oldValue, newValue) -> oldValue // 如果有重複，保留舊值
                ));

        // 合併資料庫資料與檔案資料
        List<Map<String, Object>> images = Arrays.stream(fileNames).map(filename -> {
            Map<String, Object> imageDetails = new HashMap<>();
            imageDetails.put("filename", filename); // 圖片名稱

            // 從資料庫中查詢匹配的資料（如果存在）
            CouponBean matchingCoupon = databaseImageMap.get(filename);

            // 如果資料庫有對應記錄，添加 friedChicken 和 fries 值；否則默認為 0
            imageDetails.put("friedChicken", matchingCoupon != null ? matchingCoupon.getFriedChicken() : 0);
            imageDetails.put("fries", matchingCoupon != null ? matchingCoupon.getFries() : 0); // 新增薯條屬性
            imageDetails.put("eggTart", matchingCoupon != null ? matchingCoupon.getEggTart() : 0); // 新增薯條屬性
            imageDetails.put("hamburger", matchingCoupon != null ? matchingCoupon.getHamburger() : 0); // 新增薯條屬性
            imageDetails.put("chickenNuggets", matchingCoupon != null ? matchingCoupon.getChickenNuggets() : 0); // 新增薯條屬性
            imageDetails.put("drinks", matchingCoupon != null ? matchingCoupon.getDrinks() : 0); // 新增薯條屬性
            imageDetails.put("QQBall", matchingCoupon != null ? matchingCoupon.getQQBall() : 0); // 新增薯條屬性
            imageDetails.put("chikenRice", matchingCoupon != null ? matchingCoupon.getChikenRice() : 0); // 新增薯條屬性
            imageDetails.put("herbChiken", matchingCoupon != null ? matchingCoupon.getHerbChiken() : 0); // 新增薯條屬性
            imageDetails.put("MexicoChiken", matchingCoupon != null ? matchingCoupon.getMexicoChiken() : 0); // 新增薯條屬性
            imageDetails.put("FishDonut", matchingCoupon != null ? matchingCoupon.getFishDonut() : 0); // 新增薯條屬性
            imageDetails.put("ShrimpNugget", matchingCoupon != null ? matchingCoupon.getShrimpNugget() : 0); // 新增薯條屬性

            return imageDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images); // 返回圖片信息
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("獲取圖片失敗：" + e.getMessage());
    }
}

    
}
