<script setup>
import { RouterLink, RouterView } from 'vue-router';
import { useRoute } from 'vue-router';
</script>

<template>
  <!-- 根據路由條件渲染 -->
  <header v-if="isHome">
    <div class="wrapper">
      <HelloWorld msg="You did it!" />

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/couponUploadPage/upload">圖片上傳頁面</RouterLink>
      </nav>
    </div>
  </header>

  <!-- 主內容區域 -->
  <main>
    <RouterView />
  </main>
</template>

<script>
import { computed } from 'vue';
export default {
  setup() {
    const route = useRoute();

    // 如果路徑是 "/" 或 "/about"，則顯示導航；否則隱藏
    const isHome = computed(() => route.path === '/' || route.path === '/about');

    return { isHome };
  },
};
</script>

<style scoped>
body {
  background-color: #FBF8F6;
  margin: 0;
  padding: 0;
  min-height: 100vh; /* 確保最少覆蓋整個畫面 */
}
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

main {
  padding: 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
