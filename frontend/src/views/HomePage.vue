<template>
  <div class="home-page">
    <section class="hero-section">
      <div class="hero-content">
        <h1>Bienvenue sur notre site</h1>
      </div>
      <img src="@/assets/images/home-image.jpg" alt="Image d'accueil" class="home-image" />
      <div class="small-images">
        <img src="@/assets/images/home-image - 1.jpg" alt="Petit image 1" class="small-image" />
        <img src="@/assets/images/home-image - 2.jpg" alt="Petit image 2" class="small-image" />
        <img src="@/assets/images/home-image - 3.jpg" alt="Petit image 3" class="small-image" />
        <img src="@/assets/images/home-image - 4.jpg" alt="Petit image 3" class="small-image" />

      </div>
      <p>Nous sommes ravis de vous accueillir sur notre plateforme. Découvrez nos produits et services !</p>
    </section>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
};
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, rgba(248, 248, 248, 0.8), rgba(235, 235, 235, 0.8));
  font-family: 'Roboto', sans-serif;
  overflow: hidden;
}

.hero-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 60px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 24px;
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(15px);
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.hero-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.1), transparent);
  border-radius: 24px;
  z-index: -1;
  transform: scale(1.1);
  transition: transform 0.5s ease;
}

.hero-section:hover::before {
  transform: scale(1);
}

.hero-content {
  position: relative;
  z-index: 2;
  max-width: 80%;
  margin: 0 auto;
}

.hero-section h1 {
  color: #2c3e50;
  font-size: 4rem;
  margin-bottom: 20px;
  font-weight: 700;
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.1);
  animation: fadeIn 2s ease-out;
}

.hero-section p {
  color: #34495e;
  font-size: 1.5rem;
  margin-top: 10px;
  line-height: 1.6;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
  transition: color 0.3s ease;
}

.hero-section .cta-button {
  margin-top: 20px;
  padding: 15px 30px;
  font-size: 1.2rem;
  color: #fff;
  background: #3498db;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.3s ease;
}

.hero-section .cta-button:hover {
  background: #2980b9;
  transform: translateY(-2px);
}

.home-image {
  width: 100%;
  max-width: 1200px;
  height: auto;
  border-radius: 24px;
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.3);
  transition: transform 0.5s ease, box-shadow 0.5s ease;
  animation: slideIn 1.5s ease-out;
}

.home-image:hover {
  transform: scale(1.15);
  box-shadow: 0 24px 48px rgba(0, 0, 0, 0.4);
}

.small-images {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 30px;
}

.small-image {
  width: 80px;
  height: 80px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.small-image:hover {
  transform: scale(1.1);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.3);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
