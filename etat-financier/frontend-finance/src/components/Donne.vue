<template>
    <div class="bilan">
        <h1 class="title">Etat Financier</h1>

        <!-- Tableau du Bilan -->
        <div class="section">
            <h2><i class="fas fa-balance-scale"></i> Bilan</h2>

            <div v-if="totalActif !== totalPassif" class="error-message">
                <i class="fas fa-warning"></i>
                <span v-if="totalActif > totalPassif">
                    Il manque {{ formatAmount(totalActif - totalPassif) }} du côté du passif pour équilibrer l'actif et
                    le passif.
                </span>
                <span v-else>
                    Il manque {{ formatAmount(totalPassif - totalActif) }} du côté de l'actif pour équilibrer l'actif et
                    le passif.
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
                            <td>{{ formatAmount(actif.montant) }}</td>
                        </tr>
                        <!-- Ligne des Totaux -->
                        <tr class="total-row">
                            <td><strong>Total Actif</strong></td>
                            <td><strong>{{ formatAmount(totalActif) }}</strong></td>
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
                            <td>{{ formatAmount(passif.montant) }}</td>
                        </tr>
                        <!-- Ligne des Totaux -->
                        <tr class="total-row">
                            <td><strong>Total Passif</strong></td>
                            <td><strong>{{ formatAmount(totalPassif) }}</strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

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
                            <td>{{ formatAmount(resultat.montant) }}</td>
                        </tr>
                        <!-- Ligne des Totaux -->
                        <tr class="total-row">
                            <td><strong>Total Résultat</strong></td>
                            <td><strong>{{ formatAmount(totalResultat) }}</strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div v-if="totalActif == totalPassif" class="section">
            <h2><i class="fas fa-search-dollar"></i> Analyse Financière</h2>
            <div class="cards-container">
                <div class="card" v-for="(analysis, index) in financialAnalysis" :key="'analysis-' + index"
                    :data-index="index">
                    <h3>{{ analysis.name }}</h3>
                    <p>{{ analysis.value }} {{ analysis.unit }}</p>
                    <p class="interpretation">{{ analysis.interpretation }}</p>
                </div>
            </div>
        </div>

    </div>
    <div id="resultats"></div>
</template>

<script>
export default {
    name: "Bilan",
    data() {
        return {
            actifs: [
                { nom: "Immobilisations incorporelles", montant: 1200000 },
                { nom: "Immobilisations corporelles", montant: 3500000 },
                { nom: "Immobilisations mises en concession", montant: 800000 },
                { nom: "Créances clients", montant: 2700000 },
                { nom: "Liquidités", montant: 1800000 },
                { nom: "Autres créances immobilisées", montant: 500000 },
                { nom: "Stocks de produits finis", montant: 450000 },
            ],
            passifs: [
                { nom: "Dettes à long terme", montant: 2200000 },
                { nom: "Dettes fournisseurs", montant: 1700000 },
                { nom: "Emprunts obligataires convertibles", montant: 1300000 },
                { nom: "Capitaux propres", montant: 4200000 },
                { nom: "Provisions pour pensions et obligations similaires", montant: 500000 },
                { nom: "Provisions pour impôts", montant: 700000 },
                { nom: "Autres dettes rattachées à des participations", montant: 350000 },
            ],
            resultats: [
                { nom: "Chiffre d'affaires", montant: 10500000 },
                { nom: "Achats consommés", montant: -4500000 },
                { nom: "Charges d'exploitation", montant: -5500000 },
                { nom: "Charges financières", montant: -80000 },
                { nom: "Subventions d'exploitation", montant: 200000 },
                { nom: "Résultat net", montant: 3000000 },
                { nom: "Autres produits de gestion courante", montant: 150000 },
            ],
        };
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
            return amount.toLocaleString('fr-MG') + ' MGA';
        },
        async fetchFinancialAnalysis() {
            // const apiKey = ''; // Remplacer avec votre clé API
            const apiKey = ''; // Remplacer avec votre clé API
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
        }
    },
    mounted() {
        this.fetchFinancialAnalysis(); // Appel de la fonction lors du montage du composant
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
