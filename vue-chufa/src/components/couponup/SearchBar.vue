<template>
    <div class="search-bar">
      <input
        type="text"
        v-model="query"
        placeholder="輸入名稱或任何關鍵字"
        @keyup.enter="handleSearch"
      />
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        query: "", // 綁定輸入框內容
      };
    },
    methods: {
      handleSearch() {
        // 通知父組件觸發查詢
        this.$emit("search", this.query);
      },
    },
  };
  </script>
  
  <style scoped>
  .search-bar {
    width: 100%;
    text-align: center;
  }
  
  .search-bar input {
    width: 80%;
    max-width: 1000px;
    padding: 10px 20px;
    font-size: 18px;
    border: 1px solid #ccc;
    border-radius: 30px;
    outline: none;
    background: rgba(255, 255, 255, 0.8);
  }
  </style>
  