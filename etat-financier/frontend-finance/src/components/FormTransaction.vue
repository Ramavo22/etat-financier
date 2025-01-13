<template>
  <div class="app-container">
    <div class="sidebar">
      <h2>Menu</h2>
      <ul class="main-menu">
        <li class="main-item">
          <a href="#" @click="toggleBilanMenu">Bilan</a>
          <ul v-if="isBilanMenuOpen" class="dropdown">
            <li>
              <a href="#" @click="toggleActifMenu">Actif</a>
              <ul v-if="isActifMenuOpen" class="sub-dropdown">
                <li><a href="#" @click="loadComptes('actifCourant')">Actif Courant</a></li>
                <li><a href="#" @click="loadComptes('actifNonCourant')">Actif Non Courant</a></li>
              </ul>
            </li>
            <li>
              <a href="#" @click="togglePassifMenu">Passif et Capitaux Propres</a>
              <ul v-if="isPassifMenuOpen" class="sub-dropdown">
                <li><a href="#" @click="loadComptes('passifCourant')">Passif Courant</a></li>
                <li><a href="#" @click="loadComptes('passifNonCourant')">Passif Non Courant</a></li>
                <li><a href="#" @click="loadComptes('capitauxPropres')">Capitaux Propres</a></li>
              </ul>
            </li>
          </ul>
        </li>
        <li class="main-item">
          <a href="#" @click="toggleCompteResutlat" >Ajout Compte de Résultat</a>
          <ul v-if="isCompteResultat" class="sub-dropdown">
            <li><a href="#" @click="loadComptes('charges')">Charges</a></li>
            <li><a href="#" @click="loadComptes('produits')">Produits</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <div class="content">
      <div v-if="activeForm" class="form-transaction">
        <h2>{{ activeFormTitle }}</h2>
        <form @submit.prevent="handleSubmit" class="transaction-form">
          <div class="form-group">
            <label for="compte">Compte :</label>
            <select v-model="form.compte" id="compte" required>
              <option disabled value="">Sélectionnez un compte</option>
              <option v-for="(compte, index) in comptes" :key="index" :value="compte">
                {{ compte.nom }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label for="montant">Montant :</label>
            <input
                type="number"
                id="montant"
                v-model="form.montant"
                required
                min="0"
                step="0.01"
                placeholder="Entrez un montant"
            />
          </div>

          <div class="form-group">
            <label for="date">Date :</label>
            <input
                type="date"
                id="date"
                v-model="form.date"
                required
            />
          </div>
          <div class="form-group">
            <label for="desc">Description :</label>
            <input
                type="text"
                id="desc"
                v-model="form.description"
                required
            />
          </div>

          <button type="submit" class="submit-btn">Soumettre</button>
        </form>

        <div v-if="formSubmitted" class="submitted-data">
          <h3>Transaction Soumise :</h3>
          <pre>{{ form }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      data: {
        actifCourant: [],
        actifNonCourant: [],
        passifCourant: [],
        passifNonCourant: [],
        capitauxPropres: [],
        charges:[],
        produits:[]
      },
      form: {
        compte: '',
        montant: 0,
        date: '',
        estDebit: false,
        description:''
      },
      comptes: [],
      formSubmitted: false,

      activeForm: null,  // variable pour suivre le formulaire actif
      activeFormTitle: '',  // titre dynamique du formulaire

      // État des menus déroulants
      isBilanMenuOpen: false,
      isActifMenuOpen: false,
      isPassifMenuOpen: false,
      isCompteResultat: false
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get("http://localhost:9090/menuData");
        if (response.data.status === "success") {
          this.data.actifCourant = response.data.datas.actifCourant;
          this.data.actifNonCourant = response.data.datas.actifNonCourant;
          this.data.passifCourant = response.data.datas.passifCourant;
          this.data.passifNonCourant = response.data.datas.passifNonCourant;
          this.data.capitauxPropres = response.data.datas.capitauxPropre;
          this.data.charges = response.data.datas.charges;
          this.data.produits = response.data.datas.produits;
        } else {
          console.error("Erreur lors du chargement des données :", response.data.error);
        }
      } catch (error) {
        console.error("Erreur réseau :", error);
      }
    },
    loadComptes(selectedType) {
      this.comptes = this.data[selectedType].map(item => ({ id: item.id, nom: item.nom }));
      this.activeForm = selectedType; // met à jour le formulaire actif
      this.activeFormTitle = this.getFormTitle(selectedType); // met à jour le titre du formulaire
    },
    async handleSubmit() {
      try {
        // Préparer les données du formulaire
        const formData = {
          compteId: this.form.compte.id,
          montant: this.form.montant,
          date: this.form.date,
          estDebit: this.form.estDebit,
          description:this.form.description
        };

        console.log(formData);

        //Effectuer une requête POST
        const response = await axios.post("http://localhost:9090/transaction", formData);

        if (response.data.status === "success") {
          // Gestion de la réponse réussie
          this.formSubmitted = true;
          alert(response.data.messages);
        } else {
          // Gérer les erreurs de réponse
          alert(response.data.error);
          console.error("Erreur lors de la soumission :", response.data);
        }
      } catch (error) {
        // Gérer les erreurs réseau ou serveur
        console.error("Erreur réseau :", error);
      }
    },
    toggleBilanMenu() {
      this.isBilanMenuOpen = !this.isBilanMenuOpen;
    },
    toggleActifMenu() {
      this.isActifMenuOpen = !this.isActifMenuOpen;
    },
    togglePassifMenu() {
      this.isPassifMenuOpen = !this.isPassifMenuOpen;
    },
    toggleCompteResutlat(){
      this.isCompteResultat = !this.isPassifMenuOpen;
    },
    getFormTitle(type) {
      // Retourne le titre approprié pour le formulaire en fonction du type
      const titles = {
        actifCourant: 'Formulaire - Actif Courant',
        actifNonCourant: 'Formulaire - Actif Non Courant',
        passifCourant: 'Formulaire - Passif Courant',
        passifNonCourant: 'Formulaire - Passif Non Courant',
        capitauxPropres: 'Formulaire - Capitaux Propres',
        charges: 'Formulaire - Charges',
        produits: 'Formulaire - Produit'
      };
      return titles[type] || '';
    }
  },
  async mounted() {
    await this.fetchData();
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 250px;
  background-color: #333;
  color: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100%;
}

.sidebar h2 {
  text-align: center;
  color: #fff;
  margin-bottom: 30px;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar ul li {
  margin-bottom: 20px;
}

.sidebar ul li a {
  color: white;
  text-decoration: none;
  font-size: 1.1rem;
  display: block;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.sidebar ul li a:hover {
  background-color: #575757;
}

/* Animation pour les sous-menus */
.sub-dropdown {
  padding-left: 20px;
  list-style-type: none;
  overflow: hidden;
  max-height: 0;
  transition: max-height 0.3s ease-out; /* Transition fluide */
}

.sub-dropdown li {
  margin-bottom: 10px;
}

.sub-dropdown li a {
  font-size: 1rem;
}

.sidebar ul li:hover .sub-dropdown {
  max-height: 500px; /* Assure que le sous-menu se développe */
}

.sidebar ul li .sub-dropdown {
  transition: max-height 0.3s ease-out;
}

.content {
  margin-left: 250px;
  padding: 20px;
  width: 100%;
}

.form-transaction {
  max-width: 600px;
  margin: 30px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #4CAF50;
  margin-bottom: 20px;
}

.transaction-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  margin-bottom: 5px;
}

input, select {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

input[type="number"], input[type="date"] {
  font-size: 1rem;
}

.submit-btn {
  padding: 12px 20px;
  background-color: #4CAF50;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover {
  background-color: #45a049;
}

.submitted-data {
  margin-top: 30px;
  padding: 10px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
}

pre {
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
}
</style>





