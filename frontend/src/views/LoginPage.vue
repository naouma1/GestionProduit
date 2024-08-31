<template>
  <div class="login-container">
    <div class="login-card">
      <h1>Connexion</h1>
      <form @submit.prevent="login">
        <div class="form-group">
          <label for="username">Nom d'utilisateur</label>
          <input
            type="text"
            id="username"
            v-model="username"
            placeholder="Entrez votre nom d'utilisateur"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Mot de passe</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Entrez votre mot de passe"
            required
          />
        </div>
        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
        <button type="submit" class="btn">Se connecter</button>
      </form>
    </div>
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
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.05));
  font-family: 'Roboto', sans-serif;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  padding: 30px;
  text-align: center;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.login-card:hover {
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
  transform: translateY(-5px);
}

.login-card h1 {
  margin-bottom: 20px;
  color: #333;
  font-size: 2rem;
  font-weight: 600;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  color: #555;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input:focus {
  border-color: #007bff;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
  outline: none;
}

.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  background: linear-gradient(145deg, #8d6e63, #6d4c41); /* Dégradé bois */
  color: white;
  font-size: 1.1rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn:hover {
  background: linear-gradient(145deg, #7b5e57, #5a3d32); /* Dégradé bois foncé */
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
}

.error {
  color: red;
  margin-top: 10px;
  font-size: 0.9rem;
}
</style>

