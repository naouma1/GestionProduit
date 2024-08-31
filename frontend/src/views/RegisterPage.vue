<template>
  <div class="register">
    <h1>Inscription</h1>
    <form @submit.prevent="registerUser">
      <div class="form-group">
        <label for="username">Nom d'utilisateur</label>
        <input v-model="username" type="text" id="username" required />
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" id="email" required />
      </div>
      <div class="form-group">
        <label for="phone">Téléphone</label>
        <input v-model="phone" type="tel" id="phone" required />
      </div>
      <div class="form-group">
        <label for="password">Mot de passe</label>
        <input v-model="password" type="password" id="password" required />
      </div>
      <button type="submit" class="btn">S'inscrire</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      phone: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    async registerUser() {
      try {
        await axios.post('http://localhost:8080/users/register', {
          username: this.username,
          email: this.email,
          phone: this.phone,
          password: this.password,
        });

        alert('Inscription réussie !');
        this.$router.push('/login');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          this.errorMessage = error.response.data.message;
        } else {
          this.errorMessage = 'Une erreur s\'est produite lors de l\'inscription.';
        }
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
.register {
  max-width: 500px;
  margin: auto;
  padding: 20px;
  background: #f4f1f0;
  border-radius: 12px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  margin-top: 100px;
  position: relative;
  overflow: hidden;
}

.register::before {
  content: "";
  position: absolute;
  top: -60px;
  left: -60px;
  width: 220px;
  height: 220px;
  background: radial-gradient(circle, #8d6e63, transparent);
  border-radius: 50%;
  opacity: 0.15;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  color: #6d4c41;
  font-family: 'Roboto', sans-serif;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #6d4c41;
}

input {
  width: 100%;
  padding: 14px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s, box-shadow 0.3s;
}

input:focus {
  border-color: #8d6e63;
  box-shadow: 0 0 8px rgba(141, 110, 99, 0.3);
  outline: none;
}

.btn {
  display: block;
  width: 100%;
  background: #6d4c41;
  color: #fff;
  padding: 14px;
  border: none;
  border-radius: 8px;
  font-size: 1.1em;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.btn:hover {
  background: #4e3b2e;
  transform: scale(1.05);
}

.error {
  color: #e74c3c;
  text-align: center;
  margin-top: 15px;
  font-size: 0.9em;
}
</style>
