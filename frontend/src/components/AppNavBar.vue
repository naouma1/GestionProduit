<template>
  <header>
    <nav>
      <ul>
        <li><router-link to="/">Accueil</router-link></li>
        <li v-if="isLoggedIn"><router-link to="/produit">Produits</router-link></li>
        <li v-if="isLoggedIn"><router-link to="/commande">Mes Commandes</router-link></li>
        <li v-if="isLoggedIn"><router-link to="/logout">Déconnexion</router-link></li>
        <li v-if="!isLoggedIn"><router-link to="/login">Connexion</router-link></li>
        <li v-if="!isLoggedIn"><router-link to="/register">Inscription</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  computed: {
    // Vérifie si l'utilisateur est connecté en regardant le localStorage
    isLoggedIn() {
      return !!localStorage.getItem('user'); 
    }
  },
  methods: {
    // Méthode pour déconnecter l'utilisateur
    logout() {
      localStorage.removeItem('user'); // Supprime les informations de connexion
      this.$router.push('/login'); // Redirige vers la page de connexion
    }
  }
}
</script>

<style scoped>
header {
  background: linear-gradient(145deg, #6d4c41, #8d6e63); /* Dégradé bois */
  color: #fff;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

nav ul {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
  align-items: center; /* Alignement vertical au centre */
  justify-content: center; /* Centre les éléments horizontalement */
}

nav li {
  margin: 0 20px; /* Espacement horizontal entre les éléments */
}

nav a, button {
  color: #fff; /* Couleur du texte des liens et boutons */
  text-decoration: none;
  font-weight: bold;
  padding: 10px 20px;
  border-radius: 5px;
  transition: color 0.3s ease, background-color 0.3s ease;
}

nav a:hover, button:hover {
  color: #6d4c41; /* Couleur bois foncé pour le texte au survol */
  background-color: #d2b48c; /* Couleur bois clair pour le fond au survol */
}

button {
  background-color: transparent;
  border: 1px solid #fff; /* Bordure blanche pour le bouton */
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #d2b48c; /* Couleur bois clair pour le bouton au survol */
  color: #6d4c41; /* Couleur bois foncé pour le texte du bouton au survol */
}
</style>
