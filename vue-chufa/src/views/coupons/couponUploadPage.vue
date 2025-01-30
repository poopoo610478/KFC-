<template>
  <div class="coupon-upload-page">
    <CouponImageUploader />
  </div>
</template>

<script>
import CouponImageUploader from "@/components/couponup/couponImageUploader.vue";

export default {
  name: "CouponUploadPage",
  components: {
    CouponImageUploader,
  },
};
</script>

<style scoped>
.coupon-upload-page {
  padding: 20px;
}
</style>
