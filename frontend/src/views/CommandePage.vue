<template>
  <div class="commande-page">
    <h1>Liste des Produits</h1>
    <div v-if="orders.length">
      <div v-for="order in orders" :key="order.id" class="order-item">
        <p><strong>ID :</strong> {{ order.id }}</p>
        <p><strong>Nom :</strong> {{ order.product.name ? order.product.name : 'Inconnu' }}</p>
        <p><strong>Prix :</strong> {{ formatPrice(order.product.price) }}</p>
        <p><strong>Description :</strong> {{ order.product.description ? order.product.description : 'Non spécifié' }}</p>
        <p><strong>Quantité :</strong> {{ order.product.quantity !== undefined && order.product.quantity !== null ? order.product.quantity : '0' }}</p>
        <button @click="deleteOrder(order.id)">Supprimer</button>
      </div>
    </div>
    <div v-else>
      <p>Aucune commande disponible pour le moment.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CommandePage',
  data() {
    return {
      orders: [],
      userId: null,
    };
  },
  created() {
    this.userId = this.$store.state.user.id || null;

    if (this.userId) {
      this.fetchOrders();
    } else {
      alert("Veuillez vous connecter pour voir vos commandes.");
    }
  },
  methods: {
    async fetchOrders() {
      try {
        const response = await axios.get(`http://localhost:8080/orders/user/${this.userId}`);
        this.orders = response.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des commandes:', error);
      }
    },
    async deleteOrder(orderId) {
      try {
        await axios.delete(`http://localhost:8080/orders/delete/${orderId}`);
        await this.fetchOrders(); // Rafraîchit la liste des commandes
      } catch (error) {
        console.error('Erreur lors de la suppression de la commande:', error.response ? error.response.data : error.message);
      }
    },
    formatPrice(price) {
      return price !== undefined && price !== null ? price.toFixed(2) + ' €' : 'N/A';
    }
  },
};
</script>

<style scoped>
.commande-page {
  margin: 20px;
}

.order-item {
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.order-item p {
  margin: 5px 0;
}
</style>
