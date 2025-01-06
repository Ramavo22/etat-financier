<template>
  <div class="form-transaction">
    <h2>Formulaire de Transaction</h2>
    <form @submit.prevent="handleSubmit" class="transaction-form">
      
      <div class="form-group">
        <label for="compte">Compte :</label>
        <select v-model="form.compte" id="compte" required>
          <option disabled value="">Sélectionnez un compte</option>
          <option v-for="compte in comptes" :key="compte.id" :value="compte.id">
            {{ compte.nom }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="montant">Montant :</label>
        <input
          type="number"
          id="montant"
          v-model.number="form.montant"
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

      <button type="submit" class="submit-btn">Soumettre</button>
    </form>

    <div v-if="formSubmitted" class="submitted-data">
      <h3>Transaction Soumise :</h3>
      <pre>{{ form }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        compte: '',
        montant: 0,
        date: '',  // Ajouter la date au modèle de données
        estDebit: false,
      },
      comptes: [
        { id: 1, nom: 'Compte A' },
        { id: 2, nom: 'Compte B' },
        { id: 3, nom: 'Compte C' },
      ],
      formSubmitted: false,
    };
  },
  methods: {
    handleSubmit() {
      this.formSubmitted = true;
      console.log('Formulaire soumis:', this.form);
    },
  },
};
</script>

<style scoped>
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

.radio-buttons {
  display: flex;
  justify-content: space-between;
}

.radio-buttons label {
  display: flex;
  align-items: center;
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
