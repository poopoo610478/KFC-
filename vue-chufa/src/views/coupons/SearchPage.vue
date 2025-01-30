<template>
    <div class="search-page">
      <div class="content">
        <h1>我的優惠券</h1>
        <SearchBar @search="handleSearch" />
        <p v-if="showOk" class="ok-text">OK</p>
      </div>
    </div>
  </template>
  
  <script>
  import SearchBar from "@/components/couponup/SearchBar.vue";
  import { useCouponStore } from "@/stores/couponStores";
  import { ref } from "vue";
  
  export default {
    components: { SearchBar },
    setup() {
      const couponStore = useCouponStore();
      const showOk = ref(false); // 控制 OK 顯示的狀態
  
      const handleSearch = (query) => {
        couponStore.setLastQuery(query); // 保存查詢字串
        showOk.value = true; // 顯示 OK
        setTimeout(() => {
          showOk.value = false; // 3 秒後自動隱藏 OK
        }, 3000);
      };
  
      return {
        showOk,
        handleSearch,
      };
    },
  };
  </script>
  
  <style>
/* 設置背景圖片長寬比不變並覆蓋整個網頁 */
.search-page {
  width: 115%;
  height: 100vh;
  background: url("/images/1120.jpg") no-repeat center;
  background-size: cover; /* 確保長寬比不變並覆蓋全屏 */
  display: flex;
  justify-content: center;

  
}

/* 白色容器設置為上下自適應，高度充滿 */
.content {
  background: rgba(255, 255, 255, 0.9); /* 半透明背景 */
  max-width: 1200px; /* 最大寬度 1200px */
  min-width: 800px; /* 最小寬度 1200px */
  width: 85%; /* 適應小螢幕 */
  
  text-align: center;
  display: flex;
  flex-direction: column; /* 縱向排列 */
  justify-content: center; /* 垂直居中 */
  z-index: 1; /* 讓容器層級高於背景圖片 */
  align-items: center; /* 水平居中 */
}

h1 {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
}

.ok-text {
  margin-top: 0px; /* 控制 OK 的顯示位置 */
  font-size: 0px;
  color: #333;
  font-weight: bold;
}
</style>
  