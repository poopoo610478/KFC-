const routes = [
    {
      path: '/couponUploadPage/upload', // 定義圖片上傳頁面的路由
      name: 'upload',
      component: () => import('../views/coupons/couponUploadPage.vue'), // 動態加載組件
    },
  {
    path: '/coupons',
    name: 'CouponDashboard',
    component: () => import('@/views/coupons/CouponDashboard.vue'),
  },
];
export default routes; // 直接導出數組
  