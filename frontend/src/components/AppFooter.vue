<template>
  <footer class="footer">
    <div class="container">
      <p>&copy; 2024 Mon Application. Tous droits réservés.</p>
      <p>
        <router-link to="/contact" class="footer-link">Contact</router-link> |
        <router-link to="/about" class="footer-link">À propos</router-link>
      </p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
}
</script>

<style scoped>
.footer {
  background: linear-gradient(135deg, #6d4c41, #8d6e63); /* Dégradé de couleur bois */
  color: #fff;
  padding: 20px 0;
  text-align: center;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.2); /* Ombre portée pour un effet de profondeur */
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

footer p {
  margin: 10px 0;
}

.footer-link {
  color: #ffd700; /* Couleur dorée pour les liens */
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease, text-decoration 0.3s ease;
}

.footer-link:hover {
  color: #fff; /* Changer la couleur des liens au survol */
  text-decoration: underline;
}
</style>
