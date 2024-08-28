<template>
  <footer class="footer">
    <div class="container">
      <p>&copy; 2024 Mon Application. Tous droits réservés.</p>
      <p>
        <router-link></router-link>
      </p>
    </div>
  </footer>
</template>


<script>
export default {
  name: 'AppFooter', // Nom multi-mots
  // Votre code ici
}
</script>
<style scoped>
.footer {
  background-color: #333;
  color: #fff;
  padding: 20px 0;
  text-align: center;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

footer p {
  margin: 5px 0;
}

footer a {
  color: #ffd700;
  text-decoration: none;
  font-weight: bold;
}

footer a:hover {
  text-decoration: underline;
}
</style>
