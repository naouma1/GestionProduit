<template>
    <div class="login-container">
      <h1>Connexion</h1>
      <form @submit.prevent="login">
        <div class="form-group">
          <label for="username">Nom d'utilisateur</label>
          <input
            type="text"
            id="username"
            v-model="username"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Mot de passe</label>
          <input
            type="password"
            id="password"
            v-model="password"
            required
          />
        </div>
        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
        <button type="submit" class="btn">Se connecter</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref } from 'vue';
  
  export default {
    name: 'LoginPage',
    setup() {
      const username = ref('');
      const password = ref('');
      const errorMessage = ref('');
  
      const login = async () => {
        try {
          const response = await axios.post('http://localhost:8080/users/login', {
            username: username.value,
            password: password.value,
          });
  
          if (response.data.status === 'success') {
            // Handle successful login (e.g., save token, redirect user)
            localStorage.setItem('user', JSON.stringify(response.data.user));
            // Redirection vers une autre page, par exemple, la page d'accueil
            window.location.href = '/produit';
          } else {
            errorMessage.value = response.data.message;
          }
        } catch (error) {
          errorMessage.value = 'Erreur lors de la connexion. Veuillez réessayer.';
        }
      };
  
      return {
        username,
        password,
        errorMessage,
        login,
      };
    },
  };
  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
  }
  
  input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
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
  