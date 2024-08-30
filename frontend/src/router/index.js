import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import LoginPage from '@/views/LoginPage.vue';
import RegisterPage from '@/views/RegisterPage.vue';
import ProduitPage from '@/views/ProduitPage.vue';
import LogoutPage from '@/views/LogoutPage.vue';
import CommandePage from '@/views/CommandePage.vue';


const routes = [
  { path: '/', component: HomePage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/produit', component: ProduitPage },
  { path: '/commande', component: CommandePage},
  { path: '/logout', component: LogoutPage },

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
