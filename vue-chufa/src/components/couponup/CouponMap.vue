<template>
    <div class="coupon-map-container">
    <div class="info-box">
      <div class="header">
        <span class="title">最近的肯德基</span>
        <button class="map-button" @click="openGoogleMaps">地圖</button>
      </div>
      <div class="details">
        <p><span class="store-info">店名：KFC 桃園大竹店</span> | 
          <span class="store-info">地址：桃園市蘆竹區大竹路428-1號</span></p>
      </div>
    </div>
    <a href="https://www.kfcclub.com.tw/Coupon" target="_blank" class="action-button">
          現在就行動
        </a>
  </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  
  // 📌 假資料：手動建立肯德基店家資訊
  const kfcStores = [
    { name: "KFC 台北復興店", address: "台北市復興南路 100 號", lat: 25.0403, lng: 121.5436 },
    { name: "KFC 台北信義店", address: "台北市信義區松高路 200 號", lat: 25.0330, lng: 121.5654 },
    { name: "KFC 高雄中正店", address: "高雄市中正路 300 號", lat: 22.6306, lng: 120.3030 },
    { name: "KFC 台中逢甲店", address: "台中市西屯區福星路 88 號", lat: 24.1788, lng: 120.6462 },
    { name: "KFC 桃園中壢店", address: "桃園市中壢區中山路 50 號", lat: 24.9575, lng: 121.2247 },
    { name: "KFC 桃園大竹店", address: "桃園市蘆竹區大竹路428-1號", lat: 25.0211, lng: 121.2646 },
  ];
  
  export default {
    setup() {
      const userLocation = ref(null); // 📌 使用者當前位置
      const nearestKfc = ref(null); // 📌 最近的肯德基店家
  
      // 📌 計算兩點距離（Haversine 公式）
      const getDistance = (lat1, lng1, lat2, lng2) => {
        const toRad = (value) => (value * Math.PI) / 180;
        const R = 6371; // 地球半徑 (KM)
        const dLat = toRad(lat2 - lat1);
        const dLng = toRad(lng2 - lng1);
        const a =
          Math.sin(dLat / 2) * Math.sin(dLat / 2) +
          Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
          Math.sin(dLng / 2) * Math.sin(dLng / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
      };
  
      // 📌 取得使用者位置
      const getUserLocation = () => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              userLocation.value = {
                lat: position.coords.latitude,
                lng: position.coords.longitude,
              };
              findNearestKfc();
            },
            (error) => {
              console.error("無法獲取位置：", error);
            }
          );
        } else {
          console.error("瀏覽器不支援定位");
        }
      };
  
      // 📌 找出最近的肯德基
      const findNearestKfc = () => {
        if (!userLocation.value) return;
  
        let minDistance = Infinity;
        let closestStore = null;
  
        kfcStores.forEach((store) => {
          const distance = getDistance(
            userLocation.value.lat,
            userLocation.value.lng,
            store.lat,
            store.lng
          );
  
          if (distance < minDistance) {
            minDistance = distance;
            closestStore = store;
          }
        });
  
        nearestKfc.value = closestStore;
      };
  
      // 📌 點擊按鈕開啟 Google Maps
      const openGoogleMaps = () => {
        if (nearestKfc.value) {
          const { lat, lng } = nearestKfc.value;
          window.open(`https://www.google.com/maps?q=${lat},${lng}`, "_blank");
        }
      };
  
      onMounted(getUserLocation);
  
      return { nearestKfc, openGoogleMaps };
    },
  };
  </script>
  
  <style scoped>
  .coupon-map-container {
  position: absolute; /* 固定位置 */
  top: 5px; /* 與頂部的距離 */
  min-width: 300px; /* 限制寬度，避免影響其他內容 */
  max-height: 95px;
  background: white;
  padding: 3px;
  border: 2px solid rgb(172, 172, 172);
  border-radius: 8px;
  z-index: 1000; /* 確保不會被其他內容蓋住 */
  margin: auto; /* 讓表單在水平居中 */
}
.header {
  display: flex;
  justify-content: start;
  align-items: center;
  
}
/* 📌 最近的肯德基（紅色 + 粗體） */
.title {
  font-size: 22px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  color: #E4002B;
}


.map-button {
  background-color: red;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  margin-left: 5px;
}

.map-button:hover {
  background-color: #cc0000;
}
.map-button:active {
  background-color: #f3a3a3;
}
/* 📌 店名 & 地址（紅色 + 加粗） */
.store-info {
  font-size: 15px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  color: #E4002B;
}
/* 讓店名與地址同行 */
.details p {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
}
/* 📌 「現在就行動」按鈕 */
.action-button {
  background-color: #E4002B;
  color: white;
  font-size: 20px;
  font-weight: 900;
  font-family: 'Arial Black', 'Noto Sans TC', sans-serif;
  padding: 18px 36px;
  border-radius: 10px;
  text-decoration: none;
  text-align: center;
  display: inline-block;
  justify-content: space-between;
  transition: background 0.2s ease-in-out;
  position: absolute;
  right: -200px; /* ✅ 框框右側間距 */
  top: -3px; /* ✅ 框框右側間距 */
}

.action-button:hover {
  background-color: #cc0000;
}
.action-button:active {
  background-color: #f3a3a3;
}
  </style>
  