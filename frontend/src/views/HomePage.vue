<template>
  <div class="home-page">
    <h1>Bienvenue sur notre site</h1>
    <img src="@/assets/images/home-image.jpg" alt="Image d'accueil" class="home-image" />
    <p>Nous sommes ravis de vous accueillir sur notre plateforme. Découvrez nos produits et services !</p>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
};
</script>

<style scoped>
.home-page {
  text-align: center;
  padding: 20px;
}

.home-image {
  max-width: 100%;
  height: auto;
  margin-top: 20px;
  border-radius: 8px;
}
</style>
