<template>
  
  <div class="coupon-upload-page">
    <CouponImageUploader />
    <CouponMap /> <!-- 插入 CouponMap.vue，顯示最近的肯德基 -->
  </div>
</template>

<script>
import CouponImageUploader from "@/components/couponup/couponImageUploader.vue";
import CouponMap from "@/components/couponup/CouponMap.vue"; // 📌 引入元件
export default {
  name: "CouponUploadPage",
  components: {
    CouponImageUploader,
    CouponMap, // 📌 註冊元件
  },
};
</script>

<style scoped>

.coupon-upload-page {
  background-color: white;
  margin-top: 40px; /* 或 margin-top: 40px; */
  margin-left: 5px;
  margin-right: 5px;
  padding-top: 20px; /* 或 margin-top: 40px; */
  min-height: 100vh; /* 確保覆蓋整個畫面 */
  align-items: center; /* 水平置中 */
  min-width: 900px;
  margin-left: 15%;
}
.main-container {
  position: relative; /* 確保內部元素正確排列 */
  display: flex;
  flex-direction: column; /* 讓地圖和優惠券篩選區塊縱向排列 */
  align-items: center; /* 水平置中 */
}
</style>
