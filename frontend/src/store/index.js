import { createStore } from 'vuex';

const store = createStore({
  state: {
    isLoggedIn: !!localStorage.getItem('user'),
    user: JSON.parse(localStorage.getItem('user')) || {
      id: null,
      role: ''
    },
    orders: [],
    images: []  // Ajouter une propriété pour les images
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
    setOrders(state, orders) {
      state.orders = orders;
    },
    setImages(state, images) {
      state.images = images;  // Ajouter une mutation pour les images
    },
    addImage(state, image) {
      state.images.push(image);  // Ajouter une image au tableau
    },
    removeImage(state, imageUrl) {
      state.images = state.images.filter(img => img.url !== imageUrl);  // Supprimer une image
    }
  },
  actions: {
    login({ commit }, user) {
      commit('setUser', user);
    },
    logout({ commit }) {
      commit('logout');
    },
    delete({ commit }, orderId) {
      commit('deleteOrder', orderId);
    },
    setImages({ commit }, images) {
      commit('setImages', images);  // Action pour définir les images
    },
    addImage({ commit }, image) {
      commit('addImage', image);  // Action pour ajouter une image
    },
    removeImage({ commit }, imageUrl) {
      commit('removeImage', imageUrl);  // Action pour supprimer une image
    }
  }
});

export default store;
