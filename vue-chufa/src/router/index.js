import { createRouter, createWebHistory } from 'vue-router'
import coupons from "./coupons";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...coupons, // 展開純數組
  ],
});

export default router;
