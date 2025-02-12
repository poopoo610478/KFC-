<template>
  <div>
    <!-- 提示訊息 -->
    <div v-if="message" class="message">
      {{ message }}
      
    </div>

    <input v-model.number="coupon.couponId" type="hidden" />
    <form class="coupon-form">
      <!-- ✅ 隱藏的流水號 (確保其值為 long) -->
      <label>優惠券代碼：</label>
      <input v-model="coupon.couponCode" placeholder="請輸入代碼" />

      <label>標題：</label>
      <input v-model="coupon.title" placeholder="請輸入標題" />

      <label>內容：</label>
      <textarea v-model="coupon.content" placeholder="請輸入內容"></textarea>
      <label>價錢：</label>
      <input v-model="coupon.price" type="number" placeholder="請輸入價錢" />

      <label>優惠券圖片：</label>
      <input type="file" @change="handleFileUpload" />
      <div v-if="coupon.pictureUrl">
        <img :src="coupon.pictureUrl" alt="優惠券圖片" style="max-width: 100%; margin-top: 10px;" />
      </div>

      <label>優惠券開始：</label>
      <input v-model="coupon.startTime" type="datetime-local" />

      <label>優惠券結束：</label>
      <input v-model="coupon.endTime" type="datetime-local" />

      <!-- 餐點按鍵與區塊 -->
      <div class="meal-toggle-container">
        <button type="button" class="meal-toggle" @click="toggleMealSection">餐點</button>
        <div v-if="showMealSection" class="meal-input">
          <label>炸雞：</label>
          <input v-model="coupon.friedChicken" type="number" />
          <label>蛋撻：</label>
          <input v-model="coupon.eggTart" type="number" />
          <label>漢堡：</label>
          <input v-model="coupon.hamburger" type="number" />
          <label>雞塊：</label>
          <input v-model="coupon.chickenNuggets" type="number" />
          <label>飲料：</label>
          <input v-model="coupon.drinks" type="number" />
          <label>薯條：</label>
          <input v-model="coupon.fries" type="number" />
          <label>QQ球：</label>
          <input v-model="coupon.QQBall" type="number" />
          <label>雞汁飯:</label>
          <input v-model="coupon.chikenRice" type="number" />
          <label>紙包雞:</label>
          <input v-model="coupon.herbChiken" type="number" />
          <label>雞腿捲:</label>
          <input v-model="coupon.MexicoChiken" type="number" />
          <label>鱈魚圈:</label>
          <input v-model="coupon.FishDonut" type="number" />
          <label>超蝦塊:</label>
          <input v-model="coupon.ShrimpNugget" type="number" />
          <label>早餐:</label>
          <input v-model="coupon.breakfast" type="number" />
        </div>
      </div>
      <div class="restaurant">
        <button type="button" class="exceptrest" @click="toggleMealrestaurant">餐聽排除</button>
        <div v-if="showrestaurant" class="rest-input">
          <label><input type="checkbox" v-model="coupon.TaipeiFuhSingNan" 
            :true-value="false" :false-value="true"/>台北復興南</label>
          <label><input type="checkbox" v-model="coupon.TaipeiKuangFu" 
            :true-value="false" :false-value="true"/>台北光復</label>
         <label><input type="checkbox" v-model="coupon.SinTiamAnKang" 
           :true-value="false" :false-value="true"/>新店安康</label>
          <label><input type="checkbox" v-model="coupon.SinTiamBaLi" 
            :true-value="false" :false-value="true"/>新店八里</label>
          <label><input type="checkbox" v-model="coupon.TaoyuanHighRail" 
            :true-value="false" :false-value="true"/>桃園高鐵</label>
          <label><input type="checkbox" v-model="coupon.YilanCarrefour" 
            :true-value="false" :false-value="true"/>宜蘭家樂福</label>
          <label><input type="checkbox" v-model="coupon.YilanJiaoxi" 
            :true-value="false" :false-value="true"/>宜蘭礁溪</label>
          <label><input type="checkbox" v-model="coupon.TaichungFuhSing" 
            :true-value="false" :false-value="true"/>台中復興</label>
          <label><input type="checkbox" v-model="coupon.PingTungDongGang" 
            :true-value="false" :false-value="true"/>屏東東港</label>
        </div>
      </div>

      <div class="button-group">
        <button type="button" @click="addCoupon">新增</button>
        <button type="button" @click="updateCoupon">修改</button>
        <button type="button" @click="deleteCoupon">刪除</button>
        <button type="button" @click="searchCoupons">查詢</button>
        <button type="button" @click="clearForm">清空</button>
      </div>
    </form>

    <!-- 顯示查詢結果的表格 -->
    <CouponTable :coupons="coupons" @edit="fillForm" />
  </div>
</template>

<script>
import { ref } from "vue";
import { useCouponStore } from "@/stores/couponStores";
import CouponTable from "./CouponTable.vue";

export default {
  components: { CouponTable },
  setup() {
    const message = ref("");
    const showMealSection = ref(false); // 控制餐點區塊顯示
    const showrestaurant = ref(false); // 控制餐廳區塊顯示
    const couponStore = useCouponStore();
    const coupons = ref([]);// ✅ 存放查詢結果 (所有優惠券)
    const coupon = ref({
      couponId: "", // 確保「查詢」後可以填入
      couponCode: "",
      title: "",
      content: "",
      price: 0,
      startTime: "",
      endTime: "",
      picture: null,
      pictureUrl: null, // 預覽圖片
      friedChicken:0,
      eggTart:0,
      chickenNuggets:0,
      hamburger:0,
      drinks:0,
      fries:0,
      QQBall:0,
      chikenRice:0,
      herbChiken:0,
      MexicoChiken:0,
      FishDonut:0,
      ShrimpNugget:0,
      breakfast:0,
      TaipeiFuhSingNan: true, // 台北復興南，默認為 true
      TaipeiKuangFu: true, // 台北光復，默認為 true
      SinTiamAnKang: true, // 新店安康，默認為 true
      SinTiamBaLi: true, // 新店八里，默認為 true
      TaoyuanHighRail: false, // 桃園高鐵，默認為 true
      YilanCarrefour: true, // 宜蘭家樂福，默認為 true
      YilanJiaoxi: true, // 宜蘭礁溪，默認為 true
      TaichungFuhSing: true, // 台中復興，默認為 true
      PingTungDongGang: true, // 屏東東港，默認為 true
    });


    const handleFileUpload = (event) => {
      coupon.value.picture = event.target.files[0]; // 保存圖片檔案
    };
    const toggleMealSection = () => {
      showMealSection.value = !showMealSection.value;
    };
    const toggleMealrestaurant = () => {
      showrestaurant.value = !showrestaurant.value;
    };


    const addCoupon = async () => {
      const formData = new FormData();
      formData.append("couponId", coupon.value.couponId);
      formData.append("couponCode", coupon.value.couponCode);
      formData.append("title", coupon.value.title);
      formData.append("content", coupon.value.content);
      formData.append("price", coupon.value.price);
      formData.append("friedChicken", coupon.value.friedChicken);
      formData.append("eggTart", coupon.value.eggTart);
      formData.append("hamburger", coupon.value.hamburger);
      formData.append("chickenNuggets", coupon.value.chickenNuggets);
      formData.append("drinks", coupon.value.drinks);
      formData.append("fries", coupon.value.fries);
      formData.append("QQBall", coupon.value.QQBall);
      formData.append("chikenRice", coupon.value.chikenRice);
      formData.append("herbChiken", coupon.value.herbChiken);
      formData.append("MexicoChiken", coupon.value.MexicoChiken);
      formData.append("FishDonut", coupon.value.FishDonut);
      formData.append("ShrimpNugget", coupon.value.ShrimpNugget);
      formData.append("breakfast", coupon.value.breakfast);
      formData.append("TaipeiFuhSingNan", coupon.value.TaipeiFuhSingNan);
      formData.append("TaipeiKuangFu", coupon.value.TaipeiKuangFu);
      formData.append("SinTiamAnKang", coupon.value.SinTiamAnKang);
      formData.append("SinTiamBaLi", coupon.value.SinTiamBaLi);
      formData.append("TaoyuanHighRail", coupon.value.TaoyuanHighRail);
      formData.append("YilanCarrefour", coupon.value.YilanCarrefour);
      formData.append("YilanJiaoxi", coupon.value.YilanJiaoxi);
      formData.append("TaichungFuhSing", coupon.value.TaichungFuhSing);
      formData.append("PingTungDongGang", coupon.value.PingTungDongGang);
      formData.append("startTime", coupon.value.startTime);
      formData.append("endTime", coupon.value.endTime);
      if (coupon.value.picture) {
        formData.append("pic", coupon.value.picture);
      }

      try {
        const response = await fetch("http://192.168.50.90:8080/api/coupons/Coupon", {
          method: "POST",
          body: formData,
        });
        if (response.ok) {
          message.value = "新增成功！";
        } else {
          throw new Error("新增失敗！");
        }
      } catch (error) {
        message.value = `錯誤：${error.message}`;
      }
    };

    const updateCoupon = async () => {
      if (!coupon.value.couponId || isNaN(Number(coupon.value.couponId))) {
    message.value = "請先查詢並選擇優惠券！";
    return;
  }
      const formData = new FormData();
      formData.append("couponId", coupon.value.couponId);
      formData.append("couponCode", coupon.value.couponCode);
      formData.append("title", coupon.value.title);
      formData.append("content", coupon.value.content);
      formData.append("price", coupon.value.price);
      formData.append("friedChicken", coupon.value.friedChicken);
      formData.append("eggTart", coupon.value.eggTart);
      formData.append("hamburger", coupon.value.hamburger);
      formData.append("chickenNuggets", coupon.value.chickenNuggets);
      formData.append("drinks", coupon.value.drinks);
      formData.append("fries", coupon.value.fries);
      formData.append("QQBall", coupon.value.QQBall);
      formData.append("chikenRice", coupon.value.chikenRice);
      formData.append("herbChiken", coupon.value.herbChiken);
      formData.append("MexicoChiken", coupon.value.MexicoChiken);
      formData.append("FishDonut", coupon.value.FishDonut);
      formData.append("ShrimpNugget", coupon.value.ShrimpNugget);
      formData.append("breakfast", coupon.value.breakfast);
      formData.append("TaipeiFuhSingNan", coupon.value.TaipeiFuhSingNan);
      formData.append("TaipeiKuangFu", coupon.value.TaipeiKuangFu);
      formData.append("SinTiamAnKang", coupon.value.SinTiamAnKang);
      formData.append("SinTiamBaLi", coupon.value.SinTiamBaLi);
      formData.append("TaoyuanHighRail", coupon.value.TaoyuanHighRail);
      formData.append("YilanCarrefour", coupon.value.YilanCarrefour);
      formData.append("YilanJiaoxi", coupon.value.YilanJiaoxi);
      formData.append("TaichungFuhSing", coupon.value.TaichungFuhSing);
      formData.append("PingTungDongGang", coupon.value.PingTungDongGang);
      formData.append("startTime", coupon.value.startTime);
      formData.append("endTime", coupon.value.endTime);
      if (coupon.value.picture) {
        formData.append("pic", coupon.value.picture);
      }
  try {
    console.log("發送 PUT 請求:", coupon.value);
    
    // 確保 couponId 是 Long 類型
    const id = Number(coupon.value.couponId);

    const response = await fetch(`http://192.168.50.90:8080/api/coupons/update/${id}`, {
      method: "PUT",
      body: formData,
    });

    if (!response.ok) {
      throw new Error(`更新失敗！HTTP 狀態碼：${response.status}`);
    }

    message.value = "修改成功！";
  } catch (error) {
    message.value = `錯誤：${error.message}`;
  }
};

    const deleteCoupon = async () => {
      if (!coupon.value.couponId) {
        message.value = "請選擇要刪除的優惠券！";
        return;
      }
      try {
        const response = await fetch(`http://192.168.50.90:8080/api/coupons/delete/${coupon.value.couponId}`, {
          method: "DELETE",
        });
        if (response.ok) {
          message.value = "刪除成功！";
        } else {
          throw new Error("刪除失敗！");
        }
      } catch (error) {
        message.value = `錯誤：${error.message}`;
      }
    };

    /** ✅ 查詢優惠券 (加入篩選邏輯) */
    const searchCoupons = async () => {
      try {
        const query = {
          title: coupon.value.title,
          couponCode: coupon.value.couponCode, // ✅ 直接帶入篩選條件
        };

        const response = await fetch("http://192.168.50.90:8080/api/coupons/search", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(query),
        });

        if (!response.ok) {
          throw new Error("查詢失敗！");
        }

        const result = await response.json();
        coupons.value = result.filter((c) => c !== null && c !== undefined);

        // ✅ 當有輸入優惠券代碼時，僅保留符合的項目
        if (coupon.value.couponCode) {
          coupons.value = coupons.value.filter(
            (c) => c.couponCode === coupon.value.couponCode
          );
        }

        message.value = `查詢成功，共找到 ${coupons.value.length} 筆資料！`;
      } catch (error) {
        message.value = `錯誤：${error.message}`;
      }
    };
    

    const clearForm = () => {
      coupon.value = {
        couponId: "",
        couponCode: "",
        title: "",
        content: "",
        price:0,
        startTime: "",
        endTime: "",
        picture: null,
        friedChicken:0,
        eggTart:0,
        hamburger:0,
        chickenNuggets:0,
        fries:0,
        QQBall:0,
        drinks:0,
        chikenRice:0,
        herbChiken:0,
        MexicoChiken:0,
        FishDonut:0,
        ShrimpNugget:0,
        breakfast:0,
        TaipeiFuhSingNan: true, // 台北復興南，默認為 true
        TaipeiKuangFu: true, // 台北光復，默認為 true
        SinTiamAnKang: true, // 新店安康，默認為 true
        SinTiamBaLi: true, // 新店八里，默認為 true
        TaoyuanHighRail: false, // 桃園高鐵，默認為 true
        YilanCarrefour: true, // 宜蘭家樂福，默認為 true
        YilanJiaoxi: true, // 宜蘭礁溪，默認為 true
        TaichungFuhSing: true, // 台中復興，默認為 true
        PingTungDongGang: true, // 屏東東港，默認為 true
      };
      message.value = "表單已清空！";
    };

    const fillForm = (selectedCoupon) =>{
      coupon.value.couponId = selectedCoupon.couponId || 0;
      coupon.value.couponCode = selectedCoupon.couponCode || "";
      coupon.value.title = selectedCoupon.title || "";
      coupon.value.content = selectedCoupon.content || "";
      coupon.value.price = selectedCoupon.price || 0;
      coupon.value.friedChicken = selectedCoupon.friedChicken || 0;
      coupon.value.eggTart = selectedCoupon.eggTart || 0;
      coupon.value.hamburger = selectedCoupon.hamburger || 0;
      coupon.value.chickenNuggets = selectedCoupon.chickenNuggets || 0;
      coupon.value.drinks = selectedCoupon.drinks || 0;
      coupon.value.fries = selectedCoupon.fries || 0;
      coupon.value.qqBall = selectedCoupon.qqBall || 0;
      coupon.value.chikenRice = selectedCoupon.chikenRice || 0;
      coupon.value.herbChiken = selectedCoupon.herbChiken || 0;
      coupon.value.MexicoChiken = selectedCoupon.mexicoChiken || 0;
      coupon.value.FishDonut = selectedCoupon.fishDonut || 0;
      coupon.value.ShrimpNugget = selectedCoupon.shrimpNugget || 0;
      coupon.value.breakfast = selectedCoupon.breakfast || 0;
      coupon.value.TaipeiFuhSingNan = selectedCoupon.TaipeiFuhSingnan ?? true;
      coupon.value.TaipeiKuangFu = selectedCoupon.TaipeiKuangfu ?? true;
      coupon.value.SinTiamAnKang = selectedCoupon.SinTiamAnkang ?? true;
      coupon.value.SinTiamBaLi = selectedCoupon.SinTiambaLi ?? true;
      coupon.value.TaoyuanHighRail = selectedCoupon.TaoyuanHighrail ?? false;
      coupon.value.YilanCarrefour = selectedCoupon.Yilancarrefour ?? true;
      coupon.value.YilanJiaoxi = selectedCoupon.Yilanjiaoxi ?? true;
      coupon.value.TaichungFuhSing = selectedCoupon.TaichungFuhsing ?? true;
      coupon.value.PingTungDongGang = selectedCoupon.PingTungDonggang ?? true;
      coupon.value.startTime = selectedCoupon.startTime || "";
      coupon.value.endTime = selectedCoupon.endTime || "";
      coupon.value.pictureUrl = selectedCoupon.picture ? `http://localhost:8080${selectedCoupon.picture}` : null; // 設置圖片URL
    };
    return {
      coupon,
      message,
      addCoupon,
      updateCoupon,
      deleteCoupon,
      searchCoupons,
      clearForm,
      handleFileUpload,
      coupons,
      fillForm,
      showMealSection,
      toggleMealSection,
      showrestaurant,
      toggleMealrestaurant,
    };
  },
};
</script>

<style scoped>
/* 餐點按鍵樣式容器 */
.meal-toggle-container {
  display: flex; /* 使用 Flexbox 讓內容水平排列 */
  align-items: center; /* 讓內容在垂直方向上居中 */
  margin-top: 0px; /* 與上一個區域的間距為 0 */
}

/* 餐廳按鍵樣式容器 */
.restaurant {
  display: flex; /* 使用 Flexbox 讓內容水平排列 */
  align-items: center; /* 讓內容在垂直方向上居中 */
  margin-top: 0px; /* 與上一個區域的間距為 0 */
}

/* 餐點按鍵樣式 */
.meal-toggle {
  background-color: #007bff; /* 按鍵背景顏色為藍色 */
  color: white; /* 按鍵文字顏色為白色 */
  border: none; /* 去除按鍵邊框 */
  border-radius: 4px; /* 添加圓角 */
  padding: 10px 15px; /* 按鍵內部的上下、左右間距 */
  cursor: pointer; /* 滑鼠移到按鍵上變為指針 */
  font-size: 14px; /* 按鍵文字大小 */
}

/* 餐廳按鍵樣式 */
.exceptrest {
  background-color: rgb(200, 11, 11); /* 按鍵背景顏色為紅色 */
  color: yellow; /* 按鍵文字顏色為黃色 */
  border: none; /* 去除按鍵邊框 */
  border-radius: 4px; /* 添加圓角 */
  padding: 10px 15px; /* 按鍵內部的上下、左右間距 */
  cursor: pointer; /* 滑鼠移到按鍵上變為指針 */
  font-size: 14px; /* 按鍵文字大小 */
}

/* 餐點按鍵懸停樣式 */
.meal-toggle:hover {
  background-color: #0056b3; /* 懸停時按鍵背景顏色變深藍 */
}

/* 餐廳按鍵懸停樣式 */
.exceptrest:hover {
  background-color: brown; /* 懸停時按鍵背景顏色變為棕色 */
}

/* 餐點區域容器樣式 */
.meal-input {
  display: flex; /* 使用 Flexbox 讓內容水平排列 */
  align-items: center; /* 垂直方向上居中對齊 */
  gap: 4px; /* 設置文字與輸入框之間的水平間距 */
  flex-wrap: wrap; /* 如果空間不足，自動換行 */
  margin-left: 5px; /* 文字與輸入框之間的間距 */
}

/* 餐點輸入框樣式 */
.meal-input input {
  width: 40px; /* 設置輸入框的固定寬度 */
  text-align: center; /* 讓輸入框內的數字在框內居中顯示 */
}

/* 餐點文字標籤樣式 */
.meal-input label {
  display: flex; /* 讓文字和輸入框水平排列 */
  min-width: 57px; /* 設置文字的最小寬度，防止佔位不一致 */
  padding: 2px; /* 減小文字內部的上下左右間距 */
  
}

/* 餐廳區域容器樣式 */
.rest-input {
  display: flex; /* 使用 Flexbox 讓內容水平排列 */
  gap: 0px; /* 各個餐廳選項之間的水平間距 */
  align-items: center; /* 垂直方向居中對齊 */
  flex-wrap: wrap; /* 如果空間不足，自動換行 */
}

/* 餐廳標籤樣式 */
.rest-input label {
  display: flex; /* 讓勾選框和文字水平排列 */
  align-items: center; /* 垂直方向居中對齊 */
  gap: 5px; /* 勾選框與標籤文字之間的間距 */
  min-width: 93px; /* 每個標籤的最小寬度，確保排列整齊 */
  margin-left: 5px; /* 文字與輸入框之間的間距 */
}

/* 表單容器樣式 */
.coupon-form {
  display: flex; /* 使用 Flexbox 讓內容排列 */
  flex-direction: column; /* 內容垂直排列 */
  gap: 10px; /* 各表單元素之間的間距 */
  max-width: 400px; /* 表單的最大寬度 */
  margin: auto; /* 讓表單在水平居中 */
}

/* 表單標籤文字樣式 */
.coupon-form label {
  font-weight: bold; /* 標籤文字加粗 */
}

/* 按鈕組樣式 */
.button-group {
  display: flex; /* 使用 Flexbox 讓按鈕水平排列 */
  justify-content: space-between; /* 按鈕之間的距離平分 */
}

/* 提示訊息樣式 */
.message {
  position: fixed; /* 固定在螢幕上的特定位置 */
  top: 10px; /* 距離螢幕頂部 10px */
  left: 50%; /* 水平位置在螢幕的正中間 */
  transform: translateX(-50%); /* 將元素向左移動自身寬度的一半，實現水平居中 */
  background-color: #dff0d8; /* 訊息框的背景顏色 */
  border: 1px solid #d6e9c6; /* 設置邊框的顏色和寬度 */
  color: #3c763d; /* 訊息文字的顏色 */
  padding: 10px 20px; /* 設置訊息框內部的上下、左右間距 */
  border-radius: 4px; /* 設置圓角邊框 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 設置陰影效果 */
  z-index: 1000; /* 訊息框的層級 */
  text-align: center; /* 訊息文字居中對齊 */
}
</style>

