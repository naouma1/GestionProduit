<template>
  <div class="produit-page">
    <h1>Liste des Produits</h1>

    <!-- Section de tri -->
    <div class="filters">
      <label for="sort">Trier par :</label>
      <select id="sort" v-model="sortBy" @change="sortProducts">
        <option value="name">Nom</option>
        <option value="price">Prix</option>
        <option value="quantity">Quantité</option>
      </select>
    </div>

    <!-- Affichage des produits -->
    <div v-if="products.length" class="products-list">
      <div v-for="product in paginatedProducts" :key="product.id" class="product-item">
        <div class="product-info">
          <img :src="'http://localhost:8080/images/' + product.imageFileName" alt="Image du produit" class="product-image" v-if="product.imageFileName"/>
          <div class="product-details">
            <h2>{{ product.name }}</h2>
            <p><strong>{{ formatPrice(product.price) }} DH</strong></p>
            <p>{{ product.description }}</p>
            <p>
              <strong>Quantité en stock :</strong>
              <span v-if="product.quantity > 0">
                {{ product.quantity }}
                <input
                  v-model.number="product.selectedQuantity"
                  type="number"
                  min="1"
                  :max="product.quantity"
                  placeholder="Quantité"
                  class="quantity-input"
                  @input="validateQuantity(product)"
                />
              </span>
              <span v-else class="placeholder">Stock épuisé</span>
            </p>
          </div>
        </div>
        <div class="product-actions">
          <button 
            @click="addOrder(product.id)" 
            :disabled="!product.selectedQuantity || product.selectedQuantity < 1 || product.selectedQuantity > product.quantity"
            :class="{ 'out-of-stock': product.quantity <= 0 }"
          >
            {{ product.quantity > 0 ? 'Commander' : 'Stock Épuisé' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">Précédent</button>
      <span>Page {{ currentPage }} sur {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Suivant</button>
    </div>

    <div v-else>
      <p>Aucun produit disponible pour le moment.</p>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import { ref, computed } from 'vue';

export default {
  name: 'ProduitPage',
  setup() {
    const products = ref([]);
    const userId = ref(null);
    const sortBy = ref('name');
    const currentPage = ref(1);
    const itemsPerPage = 9;

    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/products/getall');
        products.value = response.data.map(product => ({
          ...product,
          selectedQuantity: 0
        }));
        sortProducts(); // Trier initialement après récupération des produits
      } catch (error) {
        console.error('Erreur lors de la récupération des produits:', error);
      }
    };

    const validateQuantity = (product) => {
      if (product.selectedQuantity > product.quantity) {
        product.selectedQuantity = product.quantity;
      }
      if (product.selectedQuantity < 1) {
        product.selectedQuantity = 1;
      }
    };

    const addOrder = async (productId) => {
      if (!userId.value) {
        alert('Vous devez être connecté pour passer une commande.');
        return;
      }
      const product = products.value.find(p => p.id === productId);
      if (!product || product.selectedQuantity < 1 || product.selectedQuantity > product.quantity) {
        alert('Quantité invalide.');
        return;
      }
      try {
        await axios.post('http://localhost:8080/orders/create', {
          productId: productId,
          quantity: product.selectedQuantity,
          userId: userId.value,
        });
        window.location.href = '/commande';
      } catch (error) {
        console.error('Erreur lors de l\'ajout de la commande:', error);
        alert('Erreur lors de l\'ajout de la commande : ' + (error.response?.data || error.message));
      }
    };

    const sortProducts = () => {
      switch (sortBy.value) {
        case 'name':
          products.value.sort((a, b) => a.name.localeCompare(b.name));
          break;
        case 'price':
          products.value.sort((a, b) => a.price - b.price);
          break;
        case 'quantity':
          products.value.sort((a, b) => b.quantity - a.quantity);
          break;
      }
      currentPage.value = 1; // Réinitialiser la page actuelle après le tri
    };

    const paginatedProducts = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage;
      const end = start + itemsPerPage;
      return products.value.slice(start, end);
    });

    const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage));

    const prevPage = () => {
      if (currentPage.value > 1) {
        currentPage.value--;
      }
    };

    const nextPage = () => {
      if (currentPage.value < totalPages.value) {
        currentPage.value++;
      }
    };

    const initialize = () => {
      const storedUser = JSON.parse(localStorage.getItem('user'));
      if (storedUser) {
        userId.value = storedUser.id;
      }
      fetchProducts();
    };

    initialize();

    return {
      products,
      userId,
      sortBy,
      addOrder,
      validateQuantity,
      sortProducts,
      paginatedProducts,
      totalPages,
      currentPage,
      prevPage,
      nextPage,
      formatPrice: (price) => price.toFixed(2),
    };
  },
};
</script>

<style scoped>
.produit-page {
  padding: 20px;
}

.filters {
  margin-bottom: 20px;
}

.filters label {
  margin-right: 10px;
}

.filters select {
  padding: 5px;
  font-size: 16px;
}

.products-list {
  display: flex;
  flex-wrap: wrap;
  gap: 30px;
}

.product-item {
  border: 1px solid #ddd;
  padding: 20px;
  margin-bottom: 30px;
  border-radius: 10px;
  background-color: #f9f9f9;
  width: calc(33.333% - 30px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.product-info {
  display: flex;
  align-items: center;
}

.product-image {
  max-width: 200px;
  height: auto;
  border-radius: 8px;
  margin-right: 20px;
}

.product-details {
  flex: 1;
}

.product-actions {
  margin-top: 15px;
}

button {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  background: linear-gradient(145deg, #8d6e63, #6d4c41);
  color: white;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

button:hover {
  background: linear-gradient(145deg, #7b5e57, #5a3d32);
  transform: scale(1.05);
}

button.out-of-stock {
  background: linear-gradient(145deg, #d3b89f, #b28b6a);
  color: #fff;
  cursor: not-allowed;
}

.quantity-input {
  margin-left: 10px;
  width: 100px;
  padding: 8px;
}

.pagination {
  margin-top: 30px;
  text-align: center;
}

.pagination button {
  margin: 0 10px;
  padding: 8px 15px;
  background: linear-gradient(145deg, #8d6e63, #6d4c41);
  color: white;
}

.pagination button:hover {
  background: linear-gradient(145deg, #7b5e57, #5a3d32);
}
</style>




