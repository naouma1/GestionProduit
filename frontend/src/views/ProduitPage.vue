<template>
  <div class="produit-page">
    <h1>Liste des Produits</h1>
    <div v-if="products.length">
      <div v-for="product in products" :key="product.id" class="product-item">
        <h2>{{ product.name }}</h2>
        <p><strong>Prix :</strong> {{ product.price }} €</p>
        <p><strong>Description :</strong> {{ product.description }}</p>
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
        <div v-if="product.imageUrl">
          <img :src="'http://localhost:8080/images/' + product.imageFileName" alt="Image du produit" class="product-image"/>

        </div>
        <button 
          @click="addOrder(product.id)" 
          :disabled="!product.selectedQuantity || product.selectedQuantity < 1 || product.selectedQuantity > product.quantity"
          :class="{ 'out-of-stock': product.quantity <= 0 }"
        >
          {{ product.quantity > 0 ? 'Commander' : 'Stock Épuisé' }}
        </button>
      </div>
    </div>
    <div v-else>
      <p>Aucun produit disponible pour le moment.</p>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import { ref } from 'vue';

export default {
  name: 'ProduitPage',
  setup() {
    const products = ref([]);
    const userId = ref(null);

    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/products/getall');
        products.value = response.data.map(product => ({
          ...product,
          selectedQuantity: 0
        }));
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
        window.location.href = '/commande'
      } catch (error) {
        console.error('Erreur lors de l\'ajout de la commande:', error);
        alert('Erreur lors de l\'ajout de la commande : ' + (error.response?.data || error.message));
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
      addOrder,
      validateQuantity
    };
  },
};
</script>

<style scoped>
.produit-page {
  padding: 20px;
}

.product-item {
  border: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 10px;
}

h1 {
  margin-bottom: 20px;
}

h2 {
  color: #333;
}

.product-image {
  max-width: 200px;
  height: auto;
  display: block;
  margin-top: 10px;
}

.placeholder {
  color: red; 
  font-weight: bold;
}

button {
  margin-top: 10px;
  padding: 5px 10px;
}

button.out-of-stock {
  background-color: #ddd;
  color: #999;
  cursor: not-allowed;
}

.quantity-input {
  margin-left: 10px;
  width: 80px;
  padding: 5px;
}
</style>
