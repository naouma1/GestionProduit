<template>
  <div id="app">
    <AppHeader />
    <main>
      <router-view></router-view>
    </main>
    <AppFooter />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';

export default {
  components: {
    AppHeader,
    AppFooter
  }
}
</script>

<style>
/* Ajouter des styles globaux si nécessaire */
main {
  padding: 20px;
  min-height: 80vh; /* Ajuster la hauteur pour que le footer reste en bas */
}
</style>
