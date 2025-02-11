import { defineStore } from "pinia";

export const useCouponStore = defineStore("couponStore", {
  state: () => ({
    coupons: [], // 儲存優惠券資料
    editingCoupon: null, // 當前正在編輯的優惠券
    images: [], // 儲存圖片路徑
  }),
  actions: {
    // 獲取所有優惠券
    async fetchCoupons() {
      try {
        const response = await fetch("/coupons/all");
        if (response.ok) {
          this.coupons = await response.json();
        } else {
          console.error("Failed to fetch coupons: HTTP status", response.status);
        }
      } catch (error) {
        console.error("Failed to fetch coupons:", error);
      }
    },
    // 新增優惠券
    async addCoupon(coupon) {
      try {
        const formData = new FormData();
        for (const key in coupon) {
          formData.append(key, coupon[key]);
        }
        const response = await fetch("http://192.168.23.88:8080/api/coupons/Coupon", {
          method: "POST",
          body: formData,
        });
        if (response.ok) {
          console.log("Coupon added successfully!");
        } else {
          console.error("Failed to add coupon: HTTP status", response.status);
        }
      } catch (error) {
        console.error("Failed to add coupon:", error);
      }
    },
    // 更新優惠券
    async updateCoupon(coupon) {
      try {
        const formData = new FormData();
        for (const key in coupon) {
          formData.append(key, coupon[key]);
        }
        const response = await fetch(
          `http://192.168.23.88:8080/api/coupons/update/${coupon.value.couponId}`,
          {
            method: "PUT",
            body: formData,
          }
        );
        if (response.ok) {
          console.log("Coupon updated successfully!");
        } else {
          console.error("Failed to update coupon: HTTP status", response.status);
        }
      } catch (error) {
        console.error("Failed to update coupon:", error);
      }
    },
    // 刪除優惠券
    async deleteCoupon(id) {
      try {
        const response = await fetch(
          `http://192.168.23.88:8080/api/coupons/delete/${id}`,
          {
            method: "DELETE",
          }
        );
        if (response.ok) {
          console.log("Coupon deleted successfully!");
        } else {
          console.error("Failed to delete coupon: HTTP status", response.status);
        }
      } catch (error) {
        console.error("Failed to delete coupon:", error);
      }
    },
//查詢優惠券
    async searchCoupons(param) {
      try {
        const response = await fetch("http://192.168.23.88:8080/api/coupons/search", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            // 傳遞查詢參數，例如標題和優惠券代碼
            title: "",
            couponCode: "",
          }),
        });

        if (!response.ok) {
          throw new Error("查詢失敗！");
        }

        const data = await response.json();
        this.coupons = data; // 更新查詢結果
        return true;
      } catch (error) {
        console.error("查詢錯誤：", error);
        return false;
      }
    },
    
    // 設置當前編輯中的優惠券
    setEditingCoupon(coupon) {
      this.editingCoupon = coupon;
    },
    // 清空編輯中的優惠券
    clearEditingCoupon() {
      this.editingCoupon = null;
    },
    // 獲取圖片路徑
    async fetchAllImages() {
      try {
        const response = await fetch("http://192.168.23.88:8080/api/coupons/images/all", {
          method: "GET", // 確保是 GET 方法
        });
        if (response.ok) {
          this.images = await response.json(); // 更新圖片列表
          console.log("Images fetched from API:", this.images); // 調試用
        } else {
          console.error("Failed to fetch images: HTTP status", response.status);
        }
      } catch (error) {
        console.error("Failed to fetch images:", error);
      }
    },
  },
});
