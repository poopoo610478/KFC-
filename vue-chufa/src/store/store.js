// store.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    buttonText: "新增", // 初始狀態
  },
  mutations: {
    toggleButtonText(state) {
      state.buttonText = state.buttonText === "新增" ? "已更新" : "新增";
    },
  },
  actions: {
    toggleButtonText({ commit }) {
      commit('toggleButtonText');
    },
  },
  getters: {
    buttonText: (state) => state.buttonText,
  },
});
