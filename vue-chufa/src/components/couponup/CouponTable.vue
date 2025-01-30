<template>
  <table>
    <thead>
      <tr>
        <th>標題</th>
        <th>內容</th>
        <th>狀態</th>
        <th>優惠券圖片</th>
        <th>價錢</th>
        <th>優惠券開始</th>
        <th>優惠券結束</th>
        <th>優惠券代碼</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="coupon in coupons" :key="coupon.couponId">
        <!-- 標題設為超連結 -->
        <td>
          <a href="#" @click.prevent="$emit('edit', coupon)">
            {{ coupon.title }}
          </a>
        </td>
        <td>{{ coupon.content }}</td>
        <td>{{ coupon.state ? "有效" : "無效" }}</td>
        <td>
          <a :href="'http://localhost:8080' + coupon.picture" target="_blank">
            查看圖片
          </a>
        </td>
        <td>{{ coupon.price }}</td>
        <td>{{ coupon.startTime }}</td>
        <td>{{ coupon.endTime }}</td>
        <td>{{ coupon.couponCode }}</td>
      </tr>

    </tbody>
  </table>
</template>

<script>
export default {
  props: ["coupons"],
};
</script>
<style scoped>
table {
  width: 100%;
  border-collapse: collapse; /* 移除多餘的邊框間距 */
  margin-top: 20px;
}

thead {
  background-color: #f5f5f5; /* 添加背景色，區分表頭 */
}

th,
td {
  border: 1px solid #ddd; /* 添加邊框 */
  padding: 10px; /* 增加內邊距，讓文字不緊貼邊框 */
  text-align: center; /* 居中對齊 */
  min-width: 125px;
}

th {
  font-weight: bold;
  text-transform: uppercase; /* 文字轉成大寫，提升視覺效果 */
}

tr:nth-child(even) {
  background-color: #f9f9f9; /* 奇偶行使用不同背景色 */
}

tr:hover {
  background-color: #f1f1f1; /* 滑過時高亮行 */
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline; /* 滑過時顯示下劃線 */
}
</style>

