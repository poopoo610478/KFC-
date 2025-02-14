<template>
  <div class="container">
    <!-- 顯示當前時間 -->
    <!-- 篩選按鈕 -->

    <button @click="toggleFriedChickenFilter" :class="['filter-btn', { 'active-btn': isFriedChickenActive }]">炸雞</button>
    <button @click="toggleFriesFilter" :class="['filter-btn', { 'active-btn': isFriesActive }]">薯條</button>
    <button @click="toggleEggTartFilter" :class="['filter-btn', { 'active-btn': isEggTartActive }]">蛋撻</button>
    <button @click="toggleHamburgerFilter" :class="['filter-btn', { 'active-btn': isHamburgerActive }]">漢堡</button>
    <button @click="toggleChickenNuggetsFilter" :class="['filter-btn', { 'active-btn': isChickenNuggetsActive }]">雞塊</button>
    <button @click="toggleDrinksFilter" :class="['filter-btn', { 'active-btn': isDrinksActive }]">飲料</button>
    <button @click="toggleQQBallFilter" :class="['filter-btn', { 'active-btn': isQQBallActive }]">QQ球</button>
    <button @click="toggleChikenRiceFilter" :class="['filter-btn', { 'active-btn': isChikenRiceActive }]">雞汁飯</button>
    <button @click="toggleHerbChikenFilter" :class="['filter-btn', { 'active-btn': isHerbChikenActive }]">紙包雞</button>
    <button @click="toggleMexicoChikenFilter" :class="['filter-btn', { 'active-btn': isMexicoChikenActive }]">雞腿捲</button>
    <button @click="toggleFishDonutFilter" :class="['filter-btn', { 'active-btn': isFishDonutActive }]">鱈魚圈</button>
    <button @click="toggleShrimpNuggetFilter" :class="['filter-btn', { 'active-btn': isShrimpNuggetActive }]">超蝦塊</button>
    <button @click="toggleBreakfastFilter" :class="['filter-btn', { 'active-btn': isBreakfastActive }]">吃早餐</button>
<br>
    <!-- 第二排篩選按鈕 -->
   <button @click="toggleNoFriedChickenFilter" :class="['exclude-btn', { 'passive-btn': isFriedChickenPassive }]">不要炸雞</button>
   <button @click="toggleNoFriesFilter" :class="['exclude-btn', { 'passive-btn': isFriesPassive }]">不要薯條</button>
   <button @click="toggleNoEggTartFilter" :class="['exclude-btn', { 'passive-btn': isEggTartPassive }]">不要蛋撻</button>
   <button @click="toggleNoHamburgerFilter" :class="['exclude-btn', { 'passive-btn': isHamburgerPassive }]">不要漢堡</button>
   <button @click="toggleNoChickenNuggetsFilter" :class="['exclude-btn', { 'passive-btn': isChickenNuggetsPassive }]">不要雞塊</button>
   <button @click="toggleNoDrinksFilter" :class="['exclude-btn', { 'passive-btn': isDrinksPassive }]">不要飲料</button>
   <button @click="toggleNoQQBallFilter" :class="['exclude-btn', { 'passive-btn': isQQBallPassive }]">不要QQ球</button>
   <button @click="toggleNoChikenRiceFilter" :class="['exclude-btn', { 'passive-btn': isChikenRicePassive }]">不要雞汁飯</button>
   <button @click="toggleNoHerbChikenFilter" :class="['exclude-btn', { 'passive-btn': isHerbChikenPassive }]">不要紙包雞</button>
   <button @click="toggleNoMexicoChikenFilter" :class="['exclude-btn', { 'passive-btn': isMexicoChikenPassive }]">不要雞腿捲</button>
   <button @click="toggleNoFishDonutFilter" :class="['exclude-btn', { 'passive-btn': isFishDonutPassive }]">不要鱈魚圈</button>
   <button @click="toggleNoShrimpNuggetFilter" :class="['exclude-btn', { 'passive-btn': isShrimpNuggetPassive }]">不要超蝦塊</button>
   <button @click="toggleNoBreakfastFilter" :class="['exclude-btn', { 'passive-btn': isBreakfastPassive }]">排除早餐</button>
   <!-- 排序選單 -->
   <div class="sort-controls">
      <label>排序依據：</label>
      <select v-model="sortBy" @change="sortImages" class="sort-select">
        <option value="price">價錢 </option>
        <option value="endTime">期限 </option>
        <option value="friedChicken">炸雞 </option>
        <option value="fries">薯條 </option>
        <option value="eggTart">蛋撻 </option>
        <option value="hamburger">漢堡 </option>
        <option value="chickenNuggets">雞塊 </option>
        <option value="drinks">飲料 </option>
      </select>

      <label>排序方式：</label>
      <select v-model="sortOrder" @change="sortImages" class="sort-select">
        <option value="asc">升順 </option>
        <option value="desc">降順 </option>
      </select>
    </div>
<!-- ✅ 隱藏的按鈕 (每 30 秒自動點擊) -->
<button ref="autoFilterBtn" @click="updateFilteredImages" style="display: none;"></button>
    <!-- 圖片顯示 -->
    <div v-if="filteredImages.length > 0" class="image-grid">
      <div v-for="(image, index) in filteredImages" :key="index" class="image-item">
        <!-- 拼接圖片 URL -->
        <img :src="`${BASE_API}/UploadImages/${image.filename}`" :alt="`圖片 ${index + 1}`" />
      </div>
    </div>
    <div v-else>
      <p>目前沒有圖片。</p>
    </div>
  </div>
</template>

<script>
import { useCouponStore } from "@/stores/couponStores";
import { onMounted, ref } from "vue";

export default {
  name: "CouponImageUploader",
  setup() {
    const couponStore = useCouponStore(); //重點2
    const BASE_API = "https://tiny-poems-boil.loca.lt"
    
    const isFriedChickenPassive = ref(false);
    const isFriesPassive = ref(false);
    const isEggTartPassive = ref(false);
    const isHamburgerPassive = ref(false);
    const isChickenNuggetsPassive = ref(false);
    const isDrinksPassive = ref(false);
    const isQQBallPassive = ref(false);
    const isChikenRicePassive = ref(false);
    const isHerbChikenPassive = ref(false);
    const isMexicoChikenPassive = ref(false);
    const isFishDonutPassive = ref(false);
    const isShrimpNuggetPassive = ref(false);
    const isBreakfastPassive = ref(false);

    const isFriedChickenActive = ref(false);
    const isFriesActive = ref(false);
    const isEggTartActive = ref(false);
    const isHamburgerActive = ref(false);
    const isChickenNuggetsActive = ref(false);
    const isDrinksActive = ref(false);
    const isQQBallActive = ref(false);
    const isChikenRiceActive = ref(false);
    const isHerbChikenActive = ref(false);
    const isMexicoChikenActive = ref(false);
    const isFishDonutActive = ref(false);
    const isShrimpNuggetActive = ref(false);
    const isBreakfastActive = ref(false);


    const hideFriedChickenZero = ref(false); // 是否隱藏 friedChicken == 0 的圖片
    const hideFriesZero = ref(false); // 是否隱藏 fries == 0 的圖片
    const hideEggTartZero = ref(false);
    const hideHamburgerZero = ref(false);
    const hideChickenNuggetsZero = ref(false);
    const hideDrinksZero = ref(false);
    const hideQQBallZero = ref(false);
    const hideChikenRiceZero = ref(false);
    const hideHerbChikenZero = ref(false);
    const hideMexicoChikenZero = ref(false);
    const hideFishDonutZero = ref(false);
    const hideShrimpNuggetZero = ref(false);
    const hideBreakfastZero = ref(false);

    const showOnlyNoFriedChicken = ref(false); // 不要炸雞按鈕邏輯
    const showOnlyNoFries = ref(false);
    const showOnlyNoEggTart = ref(false);
    const showOnlyNoHamburger = ref(false);
    const showOnlyNoChickenNuggets = ref(false);
    const showOnlyNoDrinks = ref(false);
    const showOnlyNoQQBall = ref(false);
    const showOnlyNoChikenRice = ref(false);
    const showOnlyNoHerbChiken = ref(false);
    const showOnlyNoMexicoChiken = ref(false);
    const showOnlyNoFishDonut = ref(false);
    const showOnlyNoShrimpNugget = ref(false);
    const showOnlyNoBreakfast = ref(false);



    const filteredImages = ref([]);
    const autoFilterBtn = ref(null); // ✅ 隱藏按鈕的引用
    const currentTime = ref(new Date()); // ✅ 當前時間

    const sortBy = ref("price"); // 預設排序依據為價錢
    const sortOrder = ref("desc"); // 預設排序方式為降順
    
    const fetchImages = async () => {
      await couponStore.fetchAllImages(); // 從後端獲取圖片列表
      filteredImages.value = couponStore.images; // 初始載入所有圖片
       updateFilteredImages();
       sortImages();
    };

    const sortImages = () => {
      filteredImages.value.sort((a, b) => {
        let valA = a[sortBy.value];
        let valB = b[sortBy.value];

        if (sortBy.value === "endTime") {
          valA = new Date(valA);
          valB = new Date(valB);
        }

        if (sortOrder.value === "asc") {
          return valA - valB;
        } else {
          return valB - valA;
        }
      });
    };

    // ✅ 轉換 MSSQL datetime2(6) 格式為 Date 物件
    const parseMSSQLDateTime = (dateTimeStr) => {
      if (!dateTimeStr) return null;
      console.log(`📦 原始 EndTime: ${dateTimeStr}`); // ✅ 確認時間格式
      return new Date(dateTimeStr);
    };

    const updateFilteredImages = () => {
      // 根據篩選條件更新圖片列表         //重點3
      console.log("✅ updateFilteredImages 正在執行"); // 加入測試輸出

      filteredImages.value = couponStore.images.filter((image) => {
        if (image.endTime) {
          const endTime = parseMSSQLDateTime(image.endTime);
          console.log(`🕒 圖片: ${image.filename}, EndTime: ${endTime}, Now: ${currentTime.value}`);

          if (endTime && endTime < currentTime.value) {
            console.log(`❌ 圖片過期：${image.filename}`);
            return false; // ✅ 隱藏過期圖片
          }
        } else {
          console.warn(`⚠️ 圖片 ${image.filename} 沒有 endTime 資料`);
        }


        if (hideFriedChickenZero.value && image.friedChicken === 0) {
          return false; // 隱藏炸雞數量為 0 的圖片
        }

        if (hideFriesZero.value && image.fries === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideEggTartZero.value && image.eggTart === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideHamburgerZero.value && image.hamburger === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideChickenNuggetsZero.value && image.chickenNuggets === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideDrinksZero.value && image.drinks === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideQQBallZero.value && image.QQBall === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideChikenRiceZero.value && image.chikenRice === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideHerbChikenZero.value && image.herbChiken === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideMexicoChikenZero.value && image.MexicoChiken === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideFishDonutZero.value && image.FishDonut === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideShrimpNuggetZero.value && image.ShrimpNugget === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }
        if (hideBreakfastZero.value && image.breakfast === 0) {
          return false; // 隱藏薯條數量為 0 的圖片
        }


        if (showOnlyNoFriedChicken.value && image.friedChicken !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoFries.value && image.fries !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoEggTart.value && image.eggTart !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoHamburger.value && image.hamburger !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoChickenNuggets.value && image.chickenNuggets !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoDrinks.value && image.drinks !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoQQBall.value && image.QQBall !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoChikenRice.value && image.chikenRice !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoHerbChiken.value && image.herbChiken !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoMexicoChiken.value && image.MexicoChiken !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoFishDonut.value && image.FishDonut !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoShrimpNugget.value && image.ShrimpNugget !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }
        if (showOnlyNoBreakfast.value && image.breakfast !== 0) {
          return false === 0; // 不要炸雞篩選條件
        }

        return true; // 顯示其他圖片
      });
    };
    

    const toggleFriedChickenFilter = () => {    //重點4
      hideFriedChickenZero.value = !hideFriedChickenZero.value;
      isFriedChickenActive.value = !isFriedChickenActive.value;
      updateFilteredImages();
    };

    const toggleFriesFilter = () => {
      hideFriesZero.value = !hideFriesZero.value; // 切換薯條篩選條件
      isFriesActive.value = !isFriesActive.value;
      updateFilteredImages();
    };
    const toggleEggTartFilter = () => {
      hideEggTartZero.value = !hideEggTartZero.value; // 切換薯條篩選條件
      isEggTartActive.value = !isEggTartActive.value;
      updateFilteredImages();
    };
    const toggleHamburgerFilter = () => {
      hideHamburgerZero.value = !hideHamburgerZero.value; // 切換薯條篩選條件
      isHamburgerActive.value = !isHamburgerActive.value;
      updateFilteredImages();
    };
    const toggleChickenNuggetsFilter = () => {
      hideChickenNuggetsZero.value = !hideChickenNuggetsZero.value; // 切換薯條篩選條件
      isChickenNuggetsActive.value = !isChickenNuggetsActive.value;
      updateFilteredImages();
    };
    const toggleDrinksFilter = () => {
      hideDrinksZero.value = !hideDrinksZero.value; // 切換薯條篩選條件
      isDrinksActive.value = !isDrinksActive.value;
      updateFilteredImages();
    };
    const toggleQQBallFilter = () => {
      hideQQBallZero.value = !hideQQBallZero.value; // 切換薯條篩選條件
      isQQBallActive.value = !isQQBallActive.value;
      updateFilteredImages();
    };
    const toggleChikenRiceFilter = () => {
      hideChikenRiceZero.value = !hideChikenRiceZero.value; // 切換薯條篩選條件
      isChikenRiceActive.value = !isChikenRiceActive.value;
      updateFilteredImages();
    };
    const toggleHerbChikenFilter = () => {
      hideHerbChikenZero.value = !hideHerbChikenZero.value; // 切換薯條篩選條件
      isHerbChikenActive.value = !isHerbChikenActive.value;
      updateFilteredImages();
    };
    const toggleMexicoChikenFilter = () => {
      hideMexicoChikenZero.value = !hideMexicoChikenZero.value; // 切換薯條篩選條件
      isMexicoChikenActive.value = !isMexicoChikenActive.value;
      updateFilteredImages();
    };
    const toggleFishDonutFilter = () => {
      hideFishDonutZero.value = !hideFishDonutZero.value; // 切換薯條篩選條件
      isFishDonutActive.value = !isFishDonutActive.value;
      updateFilteredImages();
    };
    const toggleShrimpNuggetFilter = () => {
      hideShrimpNuggetZero.value = !hideShrimpNuggetZero.value; // 切換薯條篩選條件
      isShrimpNuggetActive.value = !isShrimpNuggetActive.value;
      updateFilteredImages();
    };
    const toggleBreakfastFilter = () => {
      hideBreakfastZero.value = !hideBreakfastZero.value; // 切換薯條篩選條件
      isBreakfastActive.value = !isBreakfastActive.value;
      updateFilteredImages();
    };


    const toggleNoFriedChickenFilter = () => {
      showOnlyNoFriedChicken.value = !showOnlyNoFriedChicken.value;
      isFriedChickenPassive.value = !isFriedChickenPassive.value;
      updateFilteredImages();
    };
    const toggleNoFriesFilter = () => {
      showOnlyNoFries.value = !showOnlyNoFries.value;
      isFriesPassive.value = !isFriesPassive.value;
      updateFilteredImages();
    };
    const toggleNoEggTartFilter = () => {
      showOnlyNoEggTart.value = !showOnlyNoEggTart.value;
      isEggTartPassive.value = !isEggTartPassive.value;
      updateFilteredImages();
    };
    const toggleNoHamburgerFilter = () => {
      showOnlyNoHamburger.value = !showOnlyNoHamburger.value;
      isHamburgerPassive.value = !isHamburgerPassive.value;
      updateFilteredImages();
    };
    const toggleNoChickenNuggetsFilter = () => {
      showOnlyNoChickenNuggets.value = !showOnlyNoChickenNuggets.value;
      isChickenNuggetsPassive.value = !isChickenNuggetsPassive.value;
      updateFilteredImages();
    };
    const toggleNoDrinksFilter = () => {
      showOnlyNoDrinks.value = !showOnlyNoDrinks.value;
      isDrinksPassive.value = !isDrinksPassive.value;
      updateFilteredImages();
    };
    const toggleNoQQBallFilter = () => {
      showOnlyNoQQBall.value = !showOnlyNoQQBall.value;
      isQQBallPassive.value = !isQQBallPassive.value;
      updateFilteredImages();
    };
    const toggleNoChikenRiceFilter = () => {
      showOnlyNoChikenRice.value = !showOnlyNoChikenRice.value;
      isChikenRicePassive.value = !isChikenRicePassive.value;
      updateFilteredImages();
    };
    const toggleNoHerbChikenFilter = () => {
      showOnlyNoHerbChiken.value = !showOnlyNoHerbChiken.value;
      isHerbChikenPassive.value = !isHerbChikenPassive.value;
      updateFilteredImages();
    };
    const toggleNoMexicoChikenFilter = () => {
      showOnlyNoMexicoChiken.value = !showOnlyNoMexicoChiken.value;
      isMexicoChikenPassive.value = !isMexicoChikenPassive.value;
      updateFilteredImages();
    };
    const toggleNoFishDonutFilter = () => {
      showOnlyNoFishDonut.value = !showOnlyNoFishDonut.value;
      isFishDonutPassive.value = !isFishDonutPassive.value;
      updateFilteredImages();
    };
    const toggleNoShrimpNuggetFilter = () => {
      showOnlyNoShrimpNugget.value = !showOnlyNoShrimpNugget.value;
      isShrimpNuggetPassive.value = !isShrimpNuggetPassive.value;
      updateFilteredImages();
    };
    const toggleNoBreakfastFilter = () => {
      showOnlyNoBreakfast.value = !showOnlyNoBreakfast.value;
      isBreakfastPassive.value = !isBreakfastPassive.value;
      updateFilteredImages();
    };
    
    /** ✅ 初始化並設定 30 秒自動更新圖片 */
    // ✅ 當頁面掛載時執行一次，並每 3 秒自動更新
    onMounted(async () => {
      await fetchImages(); // 先取得所有圖片
      updateFilteredImages(); // 初次篩選
    });
    return {
      filteredImages,
      BASE_API,

      autoFilterBtn,
      updateFilteredImages,
      currentTime,
      sortBy,
      sortOrder,
      sortImages,

      isFriedChickenPassive,
      isFriesPassive,
      isEggTartPassive,
      isHamburgerPassive,
      isChickenNuggetsPassive,
      isDrinksPassive,
      isQQBallPassive,
      isChikenRicePassive,
      isHerbChikenPassive,
      isMexicoChikenPassive,
      isFishDonutPassive,
      isShrimpNuggetPassive,
      isBreakfastPassive,

      isFriedChickenActive, // ✅ 3. 在模板中綁定狀態
      isFriesActive,
      isEggTartActive,
      isHamburgerActive,
      isChickenNuggetsActive,
      isDrinksActive,
      isQQBallActive,
      isChikenRiceActive,
      isHerbChikenActive,
      isMexicoChikenActive,
      isFishDonutActive,
      isShrimpNuggetActive,
      isBreakfastActive,


      toggleFriedChickenFilter,       //重點五
      toggleFriesFilter, // 將新函數暴露給模板
      toggleEggTartFilter,
      toggleHamburgerFilter,
      toggleChickenNuggetsFilter,
      toggleDrinksFilter,
      toggleQQBallFilter,
      toggleChikenRiceFilter,
      toggleHerbChikenFilter,
      toggleMexicoChikenFilter,
      toggleFishDonutFilter,
      toggleShrimpNuggetFilter,
      toggleBreakfastFilter,

      toggleNoFriedChickenFilter, // 將新按鈕函數暴露到模板
      toggleNoFriesFilter,
      toggleNoEggTartFilter,
      toggleNoHamburgerFilter,
      toggleNoChickenNuggetsFilter,
      toggleNoDrinksFilter,
      toggleNoQQBallFilter,
      toggleNoChikenRiceFilter,
      toggleNoHerbChikenFilter,
      toggleNoMexicoChikenFilter,
      toggleNoFishDonutFilter,
      toggleNoShrimpNuggetFilter,
      toggleNoBreakfastFilter,
    };
  },
};
</script>

<style scoped>
/* 手機版適應 */
@media (max-width: 768px) {
  .container {
    z-index: 1000;
    max-width: 100vw; /* 限制最大寬度 */
  overflow-x: hidden;
  margin-left: unset !important;
  margin-right: unset !important;
  margin-top: unset !important; /* 或 margin-top: 40px; */
  max-width: 90vw !important; /* 避免超過畫面 */
}


  .filter-group, .exclude-group {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
    justify-content: space-between;
  }

  .filter-btn, .exclude-btn {
    width: 48%;
    padding: 10px;
    font-size: 14px;
    border-radius: 5px;

  }

  .image-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 0px; /* 📌 讓圖片間隔變小 */
    justify-content: center;
    width: 100%; /* 🔥 讓圖片區塊與紅色框框對齊 */
    min-width: unset !important;
  }

  .image-item {
    width: 100%; /* 🔥 兩張一排 */
    max-width: 150px; /* 📌 限制最大寬度 */
  }

  .image-item img {
    width: 100%;
    height: auto;
    border-radius: 8px;
    max-width: unset !important;
  }

  .sort-controls {
    color: #000 !important; /* 強制字體顏色為純黑 */
    font-weight: bold !important; /* 讓字體變粗 */
    opacity: 1 !important; /* 移除透明度 */
  }
}

.container {
  margin-top: 20px; /* 或 margin-top: 40px; */
  align-items: center; /* 水平置中 */
  margin-left: 25px;
  margin-right: 15px;
}

.passive-btn {
  background-color: blueviolet !important; /* ✅ 4. 啟動時變色 */
  color: white ;
  font-weight: bold;
}



.active-btn {
  background-color: rgb(193, 81, 81) !important; /* ✅ 4. 啟動時變色 */
  color: white !important;
  font-weight: bold;
}

.image-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
  margin-top: 20px;
  min-width: 650px;
}

.image-item img {
  max-width: 150px;
  height: auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

button.exclude-btn{
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #a086cc; /* 淡紫色 */
  color: white;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.exclude-btn:hover {
  background-color: #d1c4e9; /* 深紫色 */
}


button.filter-btn {
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #ff9800;
  color: white;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-btn:hover{
  background-color: #4caf50;
}

.sort-select {
  padding: 8px;
  background-color: #E4002B; /* ✅ 設定指定顏色 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 5px;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  max-width: 100px;
  max-height: 34px;
}

.sort-select:hover {
  background-color: #C30024; /* 深一點的紅色以增強互動效果 */
}
.sort-controls label,
.sort-controls select option {
  font-weight: bold; /* 讓文字變粗 */
}
</style>
