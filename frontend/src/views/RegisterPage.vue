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
      <div class="form-group">
        <label for="type">Type d'utilisateur</label>
        <select v-model="type" id="type" required>
          <option value="">Sélectionner un type</option>
          <option value="CLIENT">Client</option>
          <option value="VENDEUR">Vendeur</option>
        </select>
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
      type: '', // Type d'utilisateur (CLIENT ou VENDEUR)
      errorMessage: '',
    };
  },
  methods: {
    async registerUser() {
      try {
        // Vérification si le type d'utilisateur est sélectionné
        if (!this.type) {
          this.errorMessage = "Veuillez sélectionner un type d'utilisateur.";
          return;
        }

        // Requête d'inscription
        await axios.post('http://localhost:8080/users/register', {
          username: this.username,
          email: this.email,
          phone: this.phone,
          password: this.password,
          type: this.type, // Envoi du type d'utilisateur au backend
        });

        alert('Inscription réussie !');
        this.$router.push('/login');
      } catch (error) {
        this.errorMessage = 'Une erreur s\'est produite lors de l\'inscription.';
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-top: 100px;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

input, select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.btn {
  display: block;
  width: 100%;
  background-color: #ff6f61;
  color: #fff;
  padding: 10px;
  border: none;
  border-radius: 4px;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #ff3d3d;
}

.error {
  color: red;
  text-align: center;
  margin-top: 10px;
}
</style>

  