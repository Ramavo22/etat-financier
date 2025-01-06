<template>
  <div class="home-page">
    <!-- Section Introduction -->
    <section class="intro">
      <h1>Bienvenue dans votre application d'Analyse Comptable</h1>
      <p>
        Simplifiez la gestion de vos finances et de vos transactions comptables. Suivez vos opérations, générez des rapports financiers détaillés, et obtenez des vues claires de vos résultats.
      </p>
    </section>

    <!-- Section Fonctionnalités -->
    <h2>Nos Fonctionnalités</h2>
    <section class="features">
      <div class="feature-item">
        <i class="fas fa-money-bill-wave"></i>
        <h3>Ajouter des Transactions</h3>
        <p>
          Enregistrez vos transactions comptables en toute simplicité, qu'elles soient des débits ou des crédits. Suivez l'historique de vos opérations.
        </p>
      </div>
      <div class="feature-item">
        <i class="fas fa-user"></i>
        <h3>Gestion des Comptes</h3>
        <p>
          Gérez vos comptes bancaires, vos comptes clients et vos comptes fournisseurs. Visualisez l'état de vos finances en un clin d'œil.
        </p>
      </div>
      <div class="feature-item">
        <i class="fas fa-chart-line"></i>
        <h3>Génération de Rapports Financiers</h3>
        <p>
          Créez des rapports détaillés pour une lecture financière complète. Suivez les revenus, les dépenses, les marges bénéficiaires, et plus encore.
        </p>
      </div>
    </section>

    <!-- Section d'Appel à l'Action -->
    <section class="cta">
      <h2>Commencez à Gérer vos Finances !</h2>
      <p>
        Cliquez sur le bouton ci-dessous pour commencer à enregistrer vos transactions ou explorer vos rapports financiers.
      </p>
      <router-link to="/form-transaction" class="cta-button">
        <i class="fas fa-plus-circle"></i> Ajouter une Transaction
      </router-link>
      <router-link to="/bilan" class="cta-button">
        <i class="fas fa-chart-line"></i> Explorer vos rapports financiers
      </router-link>
    </section>
  </div>
</template>

<script>
export default {
  name: 'Home',
};
</script>

<style scoped>
.home-page {
  font-family: 'Arial', sans-serif;
  background-color: #f5f8f5; /* Fond clair */
  color: #333;
  padding: 60px 20px;
  text-align: center;
}

.intro {
  background-color: #ffffff;
  padding: 40px;
  margin-bottom: 50px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.intro:hover {
  transform: translateY(-5px); /* Effet de levée pour l'intro */
}

.intro h1 {
  color: #4CAF50; /* Vert principal */
  font-size: 2.4rem;
  margin-bottom: 20px;
}

.intro p {
  font-size: 1.2rem;
  color: #555;
  margin-bottom: 20px;
  line-height: 1.6;
}

.section-title {
  color: #4CAF50;
  font-size: 2rem;
  margin-bottom: 30px;
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 1px;
}

.cta-button {
  display: inline-block;
  padding: 14px 30px;
  background-color: #4CAF50;
  color: white;
  font-size: 1.2rem;
  text-decoration: none;
  font-weight: bold;
  border-radius: 8px;
  margin: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.cta-button:hover {
  background-color: #45a049;
  transform: scale(1.05); /* Légère échelle au survol */
}

.cta-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 40px;
  padding: 0 20px;
  margin-bottom: 60px;
}

.feature-item {
  background-color: #ffffff;
  padding: 35px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature-item:hover {
  transform: translateY(-10px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
}

.feature-item h3 {
  color: #4CAF50;
  font-size: 1.5rem;
  margin-bottom: 15px;
}

.feature-item p {
  color: #777;
  font-size: 1.1rem;
  line-height: 1.6;
}

.feature-item .feature-icon {
  font-size: 3rem;
  color: #4CAF50;
  margin-bottom: 15px;
}

.cta {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  margin-top: 60px;
}

.cta h2 {
  color: #4CAF50;
  font-size: 2.4rem;
  margin-bottom: 25px;
}

.cta p {
  font-size: 1.2rem;
  margin-bottom: 25px;
  color: #555;
  line-height: 1.6;
}
</style>
