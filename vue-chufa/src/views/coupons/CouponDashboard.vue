<template>
  <div class="dashboard">
    <CouponForm @refresh="fetchCoupons" />
    <CouponTable :coupons="coupons" @edit="editCoupon" />
  </div>
</template>

<script>
import { useCouponStore } from "@/stores/couponStores";
import { computed } from "vue";
import CouponForm from "@/components/couponup/CouponForm.vue";
import CouponTable from "@/components/couponup/CouponTable.vue";

export default {
  name: "CouponDashboard",
  components: { CouponForm, CouponTable },
  setup() {
    const couponStore = useCouponStore();

    const coupons = computed(() => couponStore.coupons);
    const fetchCoupons = couponStore.fetchCoupons;

    const searchCoupons = async () => {
      await couponStore.fetchCoupons(); // 執行查詢，並刷新資料表格
    };

    const editCoupon = (coupon) => {
      couponStore.setEditingCoupon(coupon); // 點擊標題時，填入上方表格
    };

    fetchCoupons(); // 預先載入所有資料
    return { coupons, fetchCoupons, searchCoupons, editCoupon };
  },
};
</script>
<style scoped>
.dashboard{
  margin: auto; /* 讓表單在水平居中 */
  margin-left: 8%;
}

</style>