<template>
  <div>
    <h1>Liste des Produits</h1>
    <div v-if="products.length === 0">Aucun produit trouvé.</div>
    <div v-else>
      <!-- Liste des produits -->
      <ul>
        <li v-for="product in products" :key="product.id">
          <h2>{{ product.name }}</h2>
          <p>Prix: {{ product.price }} €</p>
          <p>Quantité en stock: {{ product.quantity }}</p>
          <p>Description: {{ product.description }}</p>
          <img v-if="product.imageUrl" :src="product.imageUrl" alt="Image du produit" />

          <!-- Formulaire de commande pour chaque produit -->
          <div>
            <input
              type="number"
              v-model.number="product.orderQuantity"
              :max="product.quantity"
              min="1"
              placeholder="Quantité"
            />
            <button @click="orderProduct(product)">Commander</button>
          </div>
          <hr />
        </li>
      </ul>
    </div>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { mapState } from 'vuex'; // Importez mapState si vous utilisez Vuex

export default {
  name: 'ProduitPage',
  data() {
    return {
      products: [], // Tableau pour stocker les produits
      errorMessage: '' // Message d'erreur à afficher
    };
  },
  computed: {
    ...mapState(['isLoggedIn', 'user']) // Utilisez mapState pour accéder à l'état
  },
  created() {
    this.fetchProducts(); // Appel à la méthode pour récupérer les produits lorsque le composant est créé
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await axios.get('http://localhost:8080/products/getall'); // URL de l'API pour récupérer tous les produits
        this.products = response.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des produits:', error);
        this.errorMessage = "Erreur lors de la récupération des produits.";
      }
    },
    async orderProduct(product) {
      // Vérifiez si l'utilisateur est connecté et est un client
      if (!this.isLoggedIn || this.user.role !== 'client') {
        this.errorMessage = "Vous devez être connecté en tant que client pour passer une commande.";
        return;
      }

      const orderData = {
        productId: product.id,
        quantity: product.orderQuantity,
        userId: this.user.id
      };

      try {
        await axios.post('http://localhost:8080/orders/create', orderData);
        alert("Commande passée avec succès !");
        // Réinitialiser la quantité commandée
        product.orderQuantity = 1;
      } catch (error) {
        console.error('Erreur lors de la commande:', error);
        this.errorMessage = "Erreur lors de la création de la commande.";
      }
    }
  }
};
</script>

<style scoped>
/* Ajoutez vos styles ici */
h1 {
  text-align: center;
}
img {
  max-width: 200px;
  height: auto;
}
.error {
  color: red;
}
</style>
