const routes = [
  {
    path: "/originalRoute", // 假設已有路由
    name: "original",
    component: () => import("../views/coupons/OriginalPage.vue"),
  },
  {
    path: "/search",
    name: "search",
    component: () => import("../views/coupons/SearchPage.vue"), // 新增搜索頁面的路徑
  },
];

export default { routes };
