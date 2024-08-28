import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false,
    user: {
      id: null,
      role: '' // Assurez-vous que le rôle de l'utilisateur est stocké ici
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.isLoggedIn = true;
    },
    logout(state) {
      state.user = { id: null, role: '' };
      state.isLoggedIn = false;
    }
  },
  actions: {
    login({ commit }, user) {
      commit('setUser', user);
    },
    logout({ commit }) {
      commit('logout');
    }
  }
});
