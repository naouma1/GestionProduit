import { createStore } from 'vuex';

const store = createStore({
  state: {
    isLoggedIn: !!localStorage.getItem('user'),
    user: JSON.parse(localStorage.getItem('user')) || {
      id: null,
      role: ''
    },
    orders: [],
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.isLoggedIn = true;
      localStorage.setItem('user', JSON.stringify(user));
    },
    logout(state) {
      state.user = { id: null, role: '' };
      state.isLoggedIn = false;
      localStorage.removeItem('user');
    },
    deleteOrder(state, orderId) {
      state.orders = state.orders.filter(order => order.id !== orderId);
    },
    setOrders(state, orders){
      state.orders = orders;

    }
  },
  actions: {
    login({ commit }, user) {
      commit('setUser', user);
    },
    logout({ commit }) {
      commit('logout');
    },
    delete({ commit }, orderId){
      commit ('deleteOrder',orderId)
    }
  }
});
  

export default store;