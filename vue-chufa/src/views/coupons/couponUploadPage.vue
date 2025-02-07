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
/* 手機版排版 */
@media (max-width: 768px) {
  .coupon-upload-page {
    padding: 10px;
    background-color: white;
    width: 100% !important; /* 🔥 修正寬度適應手機 */
    min-width: 350px !important; /* ❌ 移除 min-width: 900px 避免手機顯示異常 */
    max-width: 100vw !important; /* 避免超出畫面 */
    margin-left: auto !important; /* 📌 置中 */
    overflow-x: hidden !important;
    margin-left: -30px !important;
    margin-right: 50px !important;
    margin-top: 110px !important; /* 依照你的需求調整數值 */
    max-width: 90vw !important; /* 避免超過畫面 */
  }
}


.coupon-upload-page {
  background-color: white;
  margin-top: 45px; /* 或 margin-top: 40px; */
  margin-left: 5px;
  margin-right: 5px;
  padding-top: 20px; /* 或 margin-top: 40px; */
  min-height: 100vh; /* 確保覆蓋整個畫面 */
  
  min-width: 900px;
  margin-left: 15%;
  border: 2px solid #E4002B;
  border-radius: 8px;
}
.main-container {
  position: relative; /* 確保內部元素正確排列 */
  display: flex;
  flex-direction: column; /* 讓地圖和優惠券篩選區塊縱向排列 */
  align-items: center; /* 水平置中 */
}
</style>
