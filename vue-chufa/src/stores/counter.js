const routes = [
  {
    path: "/originalRoute", // 假設已有路由
    name: "original",
    component: () => import("../views/coupons/OriginalPage.vue"),
  },
];

export default { routes };
