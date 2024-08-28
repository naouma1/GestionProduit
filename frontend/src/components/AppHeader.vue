<template>
    <header>
      <nav>
        <ul>
          <li><router-link to="/">Accueil</router-link></li>
          <li><router-link to="/produit">Produits</router-link></li>
          <li v-if="!isLoggedIn"><router-link to="/login">Connexion</router-link></li>
          <li v-if="!isLoggedIn"><router-link to="/register">Inscription</router-link></li>
          <!-- Afficher le lien de déconnexion uniquement si l'utilisateur est connecté -->
          <li v-if="isLoggedIn"><router-link to="/logout">Déconnexion</router-link></li>
          <li v-if="isLoggedIn"><router-link to="/commande">Mes Commandes</router-link></li>
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
    background: #333;
    color: #fff;
    padding: 10px;
  }
  
  nav ul {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  
  nav li {
    margin-right: 15px;
  }
  
  nav a {
    color: #fff;
    text-decoration: none;
  }
  </style>
  