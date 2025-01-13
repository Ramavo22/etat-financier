package mg.itu.etat_financier.service;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialData {

    private TransactionFinanciereService transactionService;

    public FinancialData(TransactionFinanciereService transactionService) {
        this.transactionService = transactionService;
    }

    // Fonction utilitaire pour obtenir la somme avec un filtre de date
    private double getSumByCategoryAndDateRange(String category, LocalDate startDate, LocalDate endDate) {
        return transactionService.getSumByCategoryAndDateRange(category, startDate, endDate);
    }

    // Chiffre d'affaires
    public double getChiffreAffaires(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Chiffre d'affaires", startDate, endDate);
    }

    // Production stockée
    public double getProductionStockee(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Production stockée", startDate, endDate);
    }

    // Production immobilisée
    public double getProductionImmobilisee(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Production immobilisée", startDate, endDate);
    }

    // I- Production de l'exercice
    public double getProductionExercice(LocalDate startDate, LocalDate endDate) {
        return getChiffreAffaires(startDate, endDate) + getProductionStockee(startDate, endDate) + getProductionImmobilisee(startDate, endDate);
    }

    // Achats consommés
    public double getAchatsConsommes(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Achats consommés", startDate, endDate);
    }

    // Services extérieurs et autres consommations
    public double getServicesExterieurs(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Services extérieurs et autres consommations", startDate, endDate);
    }

    // II- Consommation de l'exercice
    public double getConsommationExercice(LocalDate startDate, LocalDate endDate) {
        return getAchatsConsommes(startDate, endDate) + getServicesExterieurs(startDate, endDate);
    }

    // III- VALEUR AJOUTEE D'EXPLOITATION (I - II)
    public double getValeurAjouteeExploitation(LocalDate startDate, LocalDate endDate) {
        return getProductionExercice(startDate, endDate) - getConsommationExercice(startDate, endDate);
    }

    // Charges de personnel
    public double getChargesPersonnel(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Charges de personnel", startDate, endDate);
    }

    // Impôts, taxes et versements assimilés
    public double getImpotsTaxes(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Impôts, taxes et versements assimilés", startDate, endDate);
    }

    // IV- EXCEDENT BRUT D'EXPLOITATION
    public double getExcedentBrutExploitation(LocalDate startDate, LocalDate endDate) {
        return getValeurAjouteeExploitation(startDate, endDate) - getChargesPersonnel(startDate, endDate) - getImpotsTaxes(startDate, endDate);
    }

    // Autres produits opérationnels
    public double getAutresProduitsOper(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Autres produits opérationnels", startDate, endDate);
    }

    // Autres charges opérationnelles
    public double getAutresChargesOper(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Autres charges opérationnelles", startDate, endDate);
    }

    // Dotations aux amortissements, aux provisions et pertes de valeur
    public double getDotationsAmortissements(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Dotations aux amortissements, aux provisions et pertes de valeur", startDate, endDate);
    }

    // Reprise sur provisions et pertes de valeurs
    public double getRepriseProvisions(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Reprise sur provisions et pertes de valeurs", startDate, endDate);
    }

    // V- RESULTAT OPERATIONNEL
    public double getResultatOperationnel(LocalDate startDate, LocalDate endDate) {
        return getExcedentBrutExploitation(startDate, endDate) + getAutresProduitsOper(startDate, endDate) 
               - getAutresChargesOper(startDate, endDate) - getDotationsAmortissements(startDate, endDate)
               + getRepriseProvisions(startDate, endDate);
    }

    // Produits financiers
    public double getProduitsFinanciers(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Produits financiers", startDate, endDate);
    }

    // Charges financières
    public double getChargesFinancieres(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Charges financières", startDate, endDate);
    }

    // VI- RESULTAT FINANCIER
    public double getResultatFinancier(LocalDate startDate, LocalDate endDate) {
        return getProduitsFinanciers(startDate, endDate) - getChargesFinancieres(startDate, endDate);
    }

    // VII- RESULTAT AVANT IMPOTS (V + VI)
    public double getResultatAvantImpots(LocalDate startDate, LocalDate endDate) {
        return getResultatOperationnel(startDate, endDate) + getResultatFinancier(startDate, endDate);
    }

    // Impôts exigibles sur résultats
    public double getImpotsExigibles(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Impôts exigibles sur résultats", startDate, endDate);
    }

    // Impôts différés (Variations)
    public double getImpotsDifferes(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Impôts différés (Variations)", startDate, endDate);
    }

    // TOTAL DES PRODUITS DES ACTIVITES ORDINAIRES
    public double getTotalProduitsActivitesOrdinaires(LocalDate startDate, LocalDate endDate) {
        return getResultatAvantImpots(startDate, endDate) + getImpotsExigibles(startDate, endDate) 
               + getImpotsDifferes(startDate, endDate);
    }

    // TOTAL DES CHARGES DES ACTIVITES ORDINAIRES
    public double getTotalChargesActivitesOrdinaires(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("TOTAL DES CHARGES DES ACTIVITES ORDINAIRES", startDate, endDate);
    }

    // VIII- RESULTAT NET DES ACTIVITES ORDINAIRES
    public double getResultatNetActivitesOrdinaires(LocalDate startDate, LocalDate endDate) {
        return getTotalProduitsActivitesOrdinaires(startDate, endDate) - getTotalChargesActivitesOrdinaires(startDate, endDate);
    }

    // Eléments extraordinaires (produits)
    public double getElementsExtraordinairesProduits(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Eléments extraordinaires (produits)", startDate, endDate);
    }

    // Eléments extraordinaires (charges)
    public double getElementsExtraordinairesCharges(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Eléments extraordinaires (charges)", startDate, endDate);
    }

    // IX- RESULTAT EXTRAORDINAIRE
    public double getResultatExtraordinaire(LocalDate startDate, LocalDate endDate) {
        return getElementsExtraordinairesProduits(startDate, endDate) - getElementsExtraordinairesCharges(startDate, endDate);
    }

    // X- RESULTAT NET DE L'EXERCICE
    public double getResultatNetExercice(LocalDate startDate, LocalDate endDate) {
        return getResultatNetActivitesOrdinaires(startDate, endDate) + getResultatExtraordinaire(startDate, endDate);
    }

    // Part dans les résultats nets des sociétés mises en équivalence
    public double getPartSociétésMisesEquivalence(LocalDate startDate, LocalDate endDate) {
        return getSumByCategoryAndDateRange("Part dans les résultats nets des sociétés mises en équivalence", startDate, endDate);
    }

    // XI- RESULTAT NET DE L'ENSEMBLE CONSOLIDE
    public double getResultatNetEnsembleConsolide(LocalDate startDate, LocalDate endDate) {
        return getResultatNetExercice(startDate, endDate) + getPartSociétésMisesEquivalence(startDate, endDate);
    }

   // Fonction pour retourner tout en JSON
    public JSONArray getFinancialDataAsJSON(LocalDate startDate, LocalDate endDate) {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(createJsonEntry("Chiffre d'affaires", getChiffreAffaires(startDate, endDate)));
        jsonArray.put(createJsonEntry("Production stockée", getProductionStockee(startDate, endDate)));
        jsonArray.put(createJsonEntry("Production immobilisée", getProductionImmobilisee(startDate, endDate)));
        jsonArray.put(createJsonEntry("Production de l'exercice", getProductionExercice(startDate, endDate)));
        jsonArray.put(createJsonEntry("Achats consommés", getAchatsConsommes(startDate, endDate)));
        jsonArray.put(createJsonEntry("Services extérieurs", getServicesExterieurs(startDate, endDate)));
        jsonArray.put(createJsonEntry("Consommation de l'exercice", getConsommationExercice(startDate, endDate)));
        jsonArray.put(createJsonEntry("Valeur ajoutée d'exploitation", getValeurAjouteeExploitation(startDate, endDate)));
        jsonArray.put(createJsonEntry("Charges de personnel", getChargesPersonnel(startDate, endDate)));
        jsonArray.put(createJsonEntry("Impôts et taxes", getImpotsTaxes(startDate, endDate)));
        jsonArray.put(createJsonEntry("Excédent brut d'exploitation", getExcedentBrutExploitation(startDate, endDate)));
        jsonArray.put(createJsonEntry("Autres produits opérationnels", getAutresProduitsOper(startDate, endDate)));
        jsonArray.put(createJsonEntry("Autres charges opérationnelles", getAutresChargesOper(startDate, endDate)));
        jsonArray.put(createJsonEntry("Dotations aux amortissements", getDotationsAmortissements(startDate, endDate)));
        jsonArray.put(createJsonEntry("Reprises sur provisions", getRepriseProvisions(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat opérationnel", getResultatOperationnel(startDate, endDate)));
        jsonArray.put(createJsonEntry("Produits financiers", getProduitsFinanciers(startDate, endDate)));
        jsonArray.put(createJsonEntry("Charges financières", getChargesFinancieres(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat financier", getResultatFinancier(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat avant impôts", getResultatAvantImpots(startDate, endDate)));
        jsonArray.put(createJsonEntry("Impôts exigibles", getImpotsExigibles(startDate, endDate)));
        jsonArray.put(createJsonEntry("Impôts différés", getImpotsDifferes(startDate, endDate)));
        jsonArray.put(createJsonEntry("Total des produits des activités ordinaires", getTotalProduitsActivitesOrdinaires(startDate, endDate)));
        jsonArray.put(createJsonEntry("Total des charges des activités ordinaires", getTotalChargesActivitesOrdinaires(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat net des activités ordinaires", getResultatNetActivitesOrdinaires(startDate, endDate)));
        jsonArray.put(createJsonEntry("Éléments extraordinaires - produits", getElementsExtraordinairesProduits(startDate, endDate)));
        jsonArray.put(createJsonEntry("Éléments extraordinaires - charges", getElementsExtraordinairesCharges(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat extraordinaire", getResultatExtraordinaire(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat net de l'exercice", getResultatNetExercice(startDate, endDate)));
        jsonArray.put(createJsonEntry("Part dans les résultats nets des sociétés mises en équivalence", getPartSociétésMisesEquivalence(startDate, endDate)));
        jsonArray.put(createJsonEntry("Résultat net de l'ensemble consolidé", getResultatNetEnsembleConsolide(startDate, endDate)));

        return jsonArray;
    }

    // Méthode utilitaire pour créer une entrée JSON avec un nom et un montant
    private JSONObject createJsonEntry(String nom, Object montant) {
        JSONObject entry = new JSONObject();
        entry.put("nom", nom);
        entry.put("montant", montant);
        return entry;
    }

}
