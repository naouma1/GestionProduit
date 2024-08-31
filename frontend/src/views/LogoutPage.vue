<template>
  <div class="logout-container">
    <h1>Déconnexion</h1>
    <form @submit.prevent="logout">
      <button type="submit" class="btn">Se déconnecter</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';

export default {
  name: 'LogoutPage',
  setup() {
    const errorMessage = ref('');

    const logout = async () => {
      try {
        const response = await axios.post('http://localhost:8080/users/logout');

        if (response.status === 200) {
          // Handle successful logout
          localStorage.removeItem('user');
          // Redirect to login page
          window.location.href = '/login';
        } else {
          errorMessage.value = 'Erreur lors de la déconnexion. Veuillez réessayer.';
        }
      } catch (error) {
        errorMessage.value = 'Erreur lors de la déconnexion. Veuillez réessayer.';
      }
    };

    return {
      errorMessage,
      logout,
    };
  },
};
</script>

<style scoped>
.logout-container {
  max-width: 400px;
  margin: 50px auto; /* Espacement autour du conteneur */
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: linear-gradient(145deg, #6d4c41, #8d6e63); /* Dégradé bois */
  color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* Ombre portée pour un effet de profondeur */
}

.logout-container h1 {
  text-align: center;
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #d2b48c; /* Couleur bois clair */
  color: #333;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.btn:hover {
  background-color: #bcae9b; /* Couleur bois clair pour le survol */
  color: #6d4c41; /* Couleur bois foncé */
}

.error {
  color: #f8d7da; /* Couleur rouge clair pour les erreurs */
  margin-top: 10px;
  text-align: center;
}
</style>
