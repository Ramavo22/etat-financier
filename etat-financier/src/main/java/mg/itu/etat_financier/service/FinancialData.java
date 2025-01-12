package mg.itu.etat_financier.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialData {

    private TransactionService transactionService;

    public FinancialData(TransactionService transactionService) {
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
    public JSONObject getFinancialDataAsJSON(LocalDate startDate, LocalDate endDate) {
        JSONObject json = new JSONObject();
        json.put("Chiffre_d_affaires", getChiffreAffaires(startDate, endDate));
        json.put("Production_stockee", getProductionStockee(startDate, endDate));
        json.put("Production_immobilisee", getProductionImmobilisee(startDate, endDate));
        json.put("Production_de_l_exercice", getProductionExercice(startDate, endDate));
        json.put("Achats_consommes", getAchatsConsommes(startDate, endDate));
        json.put("Services_exterieurs", getServicesExterieurs(startDate, endDate));
        json.put("Consommation_de_l_exercice", getConsommationExercice(startDate, endDate));
        json.put("Valeur_ajoutee_exploitation", getValeurAjouteeExploitation(startDate, endDate));
        json.put("Charges_de_personnel", getChargesPersonnel(startDate, endDate));
        json.put("Impots_taxes", getImpotsTaxes(startDate, endDate));
        json.put("Excedent_brut_exploitation", getExcedentBrutExploitation(startDate, endDate));
        json.put("Autres_produits_operationnels", getAutresProduitsOper(startDate, endDate));
        json.put("Autres_charges_operationnelles", getAutresChargesOper(startDate, endDate));
        json.put("Dotations_aux_amortissements", getDotationsAmortissements(startDate, endDate));
        json.put("Reprise_sur_provisions", getRepriseProvisions(startDate, endDate));
        json.put("Resultat_operationnel", getResultatOperationnel(startDate, endDate));
        json.put("Produits_financiers", getProduitsFinanciers(startDate, endDate));
        json.put("Charges_financieres", getChargesFinancieres(startDate, endDate));
        json.put("Resultat_financier", getResultatFinancier(startDate, endDate));
        json.put("Resultat_avant_impots", getResultatAvantImpots(startDate, endDate));
        json.put("Impots_exigibles", getImpotsExigibles(startDate, endDate));
        json.put("Impots_differes", getImpotsDifferes(startDate, endDate));
        json.put("Total_des_produits_activites_ordinaire", getTotalProduitsActivitesOrdinaires(startDate, endDate));
        json.put("Total_des_charges_activites_ordinaire", getTotalChargesActivitesOrdinaires(startDate, endDate));
        json.put("Resultat_net_des_activites_ordinaire", getResultatNetActivitesOrdinaires(startDate, endDate));
        json.put("Elements_extraordinaires_produits", getElementsExtraordinairesProduits(startDate, endDate));
        json.put("Elements_extraordinaires_charges", getElementsExtraordinairesCharges(startDate, endDate));
        json.put("Resultat_extraordinaire", getResultatExtraordinaire(startDate, endDate));
        json.put("Resultat_net_de_l_exercice", getResultatNetExercice(startDate, endDate));
        json.put("Part_dans_les_resultats_nets_des_societes_mises_en_equivalence", getPartSociétésMisesEquivalence(startDate, endDate));
        json.put("Resultat_net_de_l_ensemble_consolide", getResultatNetEnsembleConsolide(startDate, endDate));

        return json;
    }
}
