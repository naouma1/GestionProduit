<template>
  <div class="commande-page">
    <h1>Liste des Commandes</h1>

    <!-- Section de tri -->
    <div class="sorting">
      <label for="sort">Trier par :</label>
      <select id="sort" v-model="sortBy" @change="sortOrders">
        <option value="date">Date</option>
        <option value="price">Prix</option>
        <option value="name">Nom du produit</option>
      </select>
    </div>

    <!-- Affichage des commandes -->
    <div v-if="paginatedOrders.length" class="orders-grid">
      <div v-for="order in paginatedOrders" :key="order.id" class="order-card">
        <img :src="getImageSrc(order.product.imageFileName)" alt="Produit" class="product-image"/>
        <div class="order-details">
          <h2 class="product-name">{{ order.product.name || 'Produit Inconnu' }}</h2>
          <p><strong>ID :</strong> {{ order.id }}</p>
          <p><strong>Prix :</strong> {{ formatPrice(order.product.price) }}</p>
          <p><strong>Quantité :</strong> {{ order.quantity || '0' }}</p>
          <p><strong>Date de Commande :</strong> {{ formatDate(order.dateCommande) }}</p>
        </div>
        <button @click="deleteOrder(order.id)" class="btn btn-danger">Supprimer</button>
      </div>
    </div>

    <!-- Pagination -->
    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">Précédent</button>
      <span>Page {{ currentPage }} sur {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Suivant</button>
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
      currentPage: 1,
      itemsPerPage: 6,
      sortBy: 'date',
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
  computed: {
    totalPages() {
      return Math.ceil(this.orders.length / this.itemsPerPage);
    },
    paginatedOrders() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.orders.slice(start, end);
    },
  },
  methods: {
    async fetchOrders() {
      try {
        const response = await axios.get(`http://localhost:8080/orders/user/${this.userId}`);
        this.orders = response.data;
        this.sortOrders(); // Trier initialement les commandes après la récupération
      } catch (error) {
        console.error('Erreur lors de la récupération des commandes:', error);
      }
    },
    async deleteOrder(orderId) {
      try {
        await axios.delete(`http://localhost:8080/orders/delete/${orderId}`);
        await this.fetchOrders(); // Rafraîchir la liste après suppression
      } catch (error) {
        console.error('Erreur lors de la suppression de la commande:', error.response ? error.response.data : error.message);
      }
    },
    sortOrders() {
      switch (this.sortBy) {
        case 'date':
          this.orders.sort((a, b) => new Date(b.dateCommande) - new Date(a.dateCommande));
          break;
        case 'price':
          this.orders.sort((a, b) => (b.product.price || 0) - (a.product.price || 0));
          break;
        case 'name':
          this.orders.sort((a, b) => a.product.name.localeCompare(b.product.name));
          break;
      }
    },
    formatPrice(price) {
      return price !== undefined && price !== null ? price.toFixed(2) + ' €' : 'N/A';
    },
    formatDate(date) {
      if (!date) return 'Non spécifié';
      try {
        const dateObj = new Date(date);
        const dateOptions = { year: 'numeric', month: 'long', day: 'numeric' };
        return dateObj.toLocaleDateString('fr-FR', dateOptions);
      } catch (error) {
        console.error('Erreur lors du formatage de la date:', error);
        return 'Date invalide';
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    getImageSrc(imageFileName) {
      return imageFileName ? `http://localhost:8080/images/${imageFileName}` : '/path/to/default-image.jpg';
    },
  },
};
</script>

<style scoped>
.commande-page {
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
}

.sorting {
  margin-bottom: 20px;
}

.orders-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.order-card {
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.product-image {
  max-width: 80%;
  height: auto;
  border-radius: 5px;
  margin-bottom: 15px;
}

.order-details {
  text-align: left;
  width: 100%;
  margin-bottom: 15px;
}

.product-name {
  font-size: 1.2em;
  margin-bottom: 10px;
  color: #2c3e50;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn-danger {
  background: linear-gradient(145deg, #8d6e63, #6d4c41); /* Dégradé bois */
  color: white;
}

.btn-danger:hover {
  background: linear-gradient(145deg, #7b5e57, #5a3d32); /* Bois foncé */
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.pagination button {
  padding: 8px 12px;
  border: none;
  border-radius: 5px;
  background: linear-gradient(145deg, #8d6e63, #6d4c41); /* Dégradé bois */
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.pagination button:disabled {
  background: linear-gradient(145deg, #d3b89f, #b28b6a); /* Bois clair pour désactivé */
  cursor: not-allowed;
}
</style>

