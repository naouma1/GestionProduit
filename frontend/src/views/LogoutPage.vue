<template>
    <div class="logout-container">
      <h1>Déconnexion</h1>
      <form @submit.prevent="logout">
        <button type="submit" class="btn">Se déconnecter</button>
      </form>
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
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .btn {
    padding: 10px 15px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }
  
  .btn:hover {
    background-color: #0056b3;
  }
  
  .error {
    color: red;
    margin-top: 10px;
  }
  </style>