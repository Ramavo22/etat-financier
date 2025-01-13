<template>
  <div class="bilan">
    <h1 class="title">Etat Financier</h1>

    <!-- Tableau du Bilan -->
    <div class="section">
      <h2><i class="fas fa-balance-scale"></i> Bilan</h2>

      <div v-if="totalActif !== totalPassif" class="error-message">
        <i class="fas fa-warning"></i>
        <span v-if="totalActif > totalPassif">
          Il manque {{ totalActif - totalPassif }} du côté du passif pour équilibrer l'actif et le passif.
        </span>
        <span v-else>
          Il manque {{ totalPassif - totalActif }} du côté de l'actif pour équilibrer l'actif et le
          passif.
        </span>
      </div>


      <div class="table-container">
        <!-- Tableau des Actifs -->
        <table class="table">
          <thead>
            <tr>
              <th>Actif</th>
              <th>Montant (MGA)</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(actif, index) in actifs" :key="'actif-' + index">
              <td>{{ actif.nom }}</td>
              <td>{{ actif.montant }}</td>
            </tr>
            <!-- Ligne des Totaux -->
            <tr class="total-row">
              <td><strong>Total Actif</strong></td>
              <td><strong>{{ totalActif }}</strong></td>
            </tr>
          </tbody>
        </table>

        <!-- Tableau des Passifs -->
        <table class="table">
          <thead>
            <tr>
              <th>Passif</th>
              <th>Montant (MGA)</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(passif, index) in passifs" :key="'passif-' + index">
              <td>{{ passif.nom }}</td>
              <td>{{ (passif.montant) }}</td>
            </tr>
            <!-- Ligne des Totaux -->
            <tr class="total-row">
              <td><strong>Total Passif</strong></td>
              <td><strong>{{ (totalPassif) }}</strong></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="date-selector">
      <label for="startDate">Date de début :</label>
      <input type="date" id="startDate" v-model="startDate" />

      <label for="endDate">Date de fin :</label>
      <input type="date" id="endDate" v-model="endDate" />

      <button @click="fetchResultats">Filtrer</button>
    </div>

    <h2>
      <i class="fas fa-chart-line"></i> Compte de Résultat
      <span class="period">({{ startDate }} à {{ endDate }})</span>
    </h2>

    <!-- Tableau du Compte de Résultat -->
    <div class="section">
      <h2><i class="fas fa-chart-line"></i> Compte de Résultat</h2>
      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>Poste</th>
              <th>Montant (MGA)</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(resultat, index) in resultats" :key="'resultat-' + index">
              <td>{{ resultat.nom }}</td>
              <td>{{ (resultat.montant) }}</td>
            </tr>
            <!-- Ligne des Totaux -->
            <tr class="total-row">
              <td><strong>Total Résultat</strong></td>
              <td><strong>{{ (totalResultat) }}</strong></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Section Analyse Financière -->
    <div v-if="totalActif == totalPassif" class="section">
      <h2><i class="fas fa-search-dollar"></i> Analyse Financière</h2>
      <div class="cards-container">
        <div class="card" v-for="(analysis, index) in financialAnalysis" :key="'analysis-' + index">
          <h3>{{ analysis.name }}</h3>
          <p>{{ analysis.value }} {{ analysis.unit }}</p>
          <p class="interpretation">{{ analysis.interpretation }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Bilan",
    data() {
      return {
        actifs: [

        ],
        passifs: [

        ],
        startDate: "2024-01-01", // Valeur par défaut
        endDate: "2024-12-31",   // Valeur par défaut
        resultats: []
      }
    },
    mounted() {
      this.fetchResultats();
      this.fetchBilan();
    },
    computed: {
      totalActif() {
        return this.actifs.reduce((total, actif) => total + actif.montant, 0);
      },
      totalPassif() {
        return this.passifs.reduce((total, passif) => total + passif.montant, 0);
      },
      totalResultat() {
        return this.resultats.reduce((total, resultat) => total + resultat.montant, 0);
      },
      // Calculs des ratios financiers
      roe() {
        const capitauxPropres = this.passifs.find(passif => passif.nom === "Capitaux propres")?.montant || 0;
        return (this.totalResultat / capitauxPropres) * 100;
      },
      roa() {
        return (this.totalResultat / this.totalActif) * 100;
      },
      margeNette() {
        return (this.totalResultat / this.resultats.find(resultat => resultat.nom === "Chiffre d'affaires")?.montant) * 100;
      },
      ratioLiquiditeGenerale() {
        const liquidites = this.actifs.find(actif => actif.nom === "Liquidités")?.montant || 0;
        const dettesCourantes = this.passifs.find(passif => passif.nom === "Dettes fournisseurs")?.montant || 0;
        return liquidites / dettesCourantes;
      },
      ratioLiquiditeReduite() {
        const liquidites = this.actifs.find(actif => actif.nom === "Liquidités")?.montant || 0;
        const dettesCourantes = this.passifs.find(passif => passif.nom === "Dettes fournisseurs")?.montant || 0;
        return liquidites / (dettesCourantes - this.actifs.find(actif => actif.nom === "Créances clients")?.montant || 0);
      },
      ratioEndettement() {
        return (this.totalPassif / this.totalActif) * 100;
      },
      // Données pour les cards d'analyse financière
      financialAnalysis() {
        return [
          {
            name: "Marge nette (%)",
            value: this.margeNette.toFixed(2),
            unit: "",
            interpretation: "" // Lia complétera cette partie
          },
          {
            name: "ROE (%)",
            value: this.roe.toFixed(2),
            unit: "%",
            interpretation: "" // Lia complétera cette partie
          },
          {
            name: "ROA (%)",
            value: this.roa.toFixed(2),
            unit: "%",
            interpretation: "" // Lia complétera cette partie
          },
          {
            name: "Ratio de liquidité générale",
            value: this.ratioLiquiditeGenerale.toFixed(2),
            unit: "",
            interpretation: "" // Lia complétera cette partie
          },
          {
            name: "Ratio de liquidité réduite",
            value: this.ratioLiquiditeReduite.toFixed(2),
            unit: "",
            interpretation: "" // Lia complétera cette partie
          },
          {
            name: "Ratio d'endettement global (%)",
            value: this.ratioEndettement.toFixed(2),
            unit: "%",
            interpretation: "" // Lia complétera cette partie
          }
        ];
      }
    },
    methods: {
      formatAmount(amount) {
            if (amount === undefined || amount === null || isNaN(amount)) {
                return '0 MGA'; // Valeur par défaut
            }
            return Number(amount).toLocaleString('fr-MG') + ' MGA'

      },
    async fetchBilan() {
        try {
          const response = await fetch('http://localhost:9090/api/bilan');
          if (!response.ok) {
            throw new Error('Erreur lors de la récupération des données');
          }
          const data = await response.json();
          this.actifs = data.actifs;
          this.passifs = data.passifs;
        } catch (error) {
          console.error('Erreur:', error.message);
          alert('Impossible de charger les données du bilan.');
        }
      },
    async fetchResultats() {
        try {
          const url = `http://localhost:9090/api/financial/data?startDate=${this.startDate}&endDate=${this.endDate}`;
          const response = await fetch(url);
          if (!response.ok) {
            throw new Error('Erreur lors de la récupération des données');
          }
          const data = await response.json();
          this.resultats = data;
        } catch (error) {
          console.error('Erreur:', error.message);
          alert('Impossible de charger les données pour la période sélectionnée.');
        }
      },
    async fetchFinancialAnalysis() {
        // const apiKey = 'vmoeNPxG82wXlhm7OHfieIPfSxOYHKpg'; // Remplacer avec votre clé API
        // const apiKey = ''; // Remplacer avec votre clé API
        // const url = 'https://api.ai21.com/studio/v1/chat/completions';
        const url = 'https://api.openai.com/v1/chat/completions';
        const contenu = this.financialAnalysis.map(analysis => {
          return `
                {
                    "${analysis.name}": "Interpretation avec recommandation brève pour la métrique '${analysis.name}' avec une valeur de ${analysis.value} ${analysis.unit}"
                }
            `;
        }).join(',');

        const finalRequest = `
                J'aimerais une interprétation de recommandation très brève pour les métriques suivantes. Répondez uniquement au format JSON, rien que du JSON  avec cle pour linterpretation 'interpretation':
                {
                    ${contenu}
                }
            `;
        const messages = this.financialAnalysis.map(analysis => ({
          role: "user",
          content: finalRequest
        }));

        console.log(messages);

        const response = await fetch(url, {
          method: "POST",
          headers: {
            "Authorization": `Bearer ${apiKey}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            "model": "gpt-4",
            "messages": messages,
            "n": 1,
            "max_tokens": 2048,
            "temperature": 0.7,
            "top_p": 1.0,
          }),
        });

        const data = await response.json();
        // Remplir les interprétations avec les réponses de l'IA
        let interpretation = "";
        interpretation = data.choices[0].message.content;
        const donne = JSON.parse(interpretation);

        this.financialAnalysis.forEach((analysis, index) => {

          // Expressions régulières adaptées pour chaque métrique
          const margeNette = donne["Marge nette (%)"];
          const roe = donne["ROE (%)"];
          const roa = donne["ROA (%)"];
          const ratioLiquiditeGenerale = donne["Ratio de liquidité générale"];
          const ratioLiquiditeReduite = donne["Ratio de liquidité réduite"];
          const ratioEndettementGlobal = donne["Ratio d'endettement global (%)"];

          const cardContainer = document.querySelector(".cards-container");
          var cardElement = cardContainer.querySelector(`.card[data-index="${index}"]`);
          console.log(cardElement);
          if (cardElement) {
            cardElement.querySelector('.interpretation').textContent = donne[analysis.name].interpretation;
          }


          console.log("margenette", margeNette);
          console.log("roe", roe);
          console.log("roa", roa);
          console.log("ratioLiquiditeGenerale", ratioLiquiditeGenerale);
          console.log("ratioLiquiditeReduite", ratioLiquiditeReduite);
          console.log("ratioEndettementGlobal", ratioEndettementGlobal);

          analysis.interpretation = donne[analysis.name] || "Aucune interprétation disponible.";

        });
      },
   
  }
};
</script>
<style scoped>
.bilan {
  padding: 20px;
  font-family: Arial, sans-serif;
  color: #333;
  background-color: #f9f9f9;
}

.title {
  text-align: center;
  color: #4CAF50;
  font-size: 2rem;
}

.section {
  margin-bottom: 30px;
}

.table-container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.table {
  width: 45%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.table th,
.table td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.table th {
  background-color: #4CAF50;
  color: white;
}

.table td {
  color: #555;
}

.table tr:hover {
  background-color: #f1f1f1;
}

.total-row {
  background-color: #e9f7e9;
  font-weight: bold;
}

.error-message {
  background-color: #fc7268;
  color: white;
  padding: 15px;
  border-radius: 8px;
  text-align: center;
  font-weight: bold;
  margin: 20px;
}

/* Cards d'analyse financière */
.cards-container {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  justify-content: center;
}

.card {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.card h3 {
  color: #4CAF50;
  margin-bottom: 10px;
}

.card p {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.interpretation {
  font-size: 0.9rem;
  color: #777;
  margin-top: 10px;
  font-style: italic;
}
</style>
