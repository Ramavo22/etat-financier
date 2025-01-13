package mg.itu.etat_financier.service;


import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BilanService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


   /*public JSONObject getBilanJSON() {
    JSONObject bilan = new JSONObject();

    // ACTIFS NON COURANTS
    JSONArray actifsNonCourants = new JSONArray();
    actifsNonCourants.put(createJsonEntry("Ecart d'acquisition", 0));
    actifsNonCourants.put(createJsonEntry("Immobilisations incorporelles", safeGetValue(() -> getImmIncorporelles())));
    actifsNonCourants.put(createJsonEntry("Immobilisations corporelles", safeGetValue(() -> getImmCorporelles())));
    actifsNonCourants.put(createJsonEntry("Immobilisations en cours", safeGetValue(() -> getImmEnCours())));
    actifsNonCourants.put(createJsonEntry("Immobilisations financières", safeGetValue(() -> getImmFinancieres())));
    actifsNonCourants.put(createJsonEntry("Titres mis en équivalence", safeGetValue(() -> getTitresEquivalence())));
    actifsNonCourants.put(createJsonEntry("Autres participations et créances rattachées", safeGetValue(() -> getAutresParticipations())));
    actifsNonCourants.put(createJsonEntry("Autres titres immobilisés", safeGetValue(() -> getAutresTitresImmobilises())));
    actifsNonCourants.put(createJsonEntry("Prêts et autres immobilisations financières", safeGetValue(() -> getPretsImmobilisations())));
    actifsNonCourants.put(createJsonEntry("TOTAL ACTIFS NON COURANTS", safeGetValue(() -> getActifsNonCourants())));

    // ACTIFS COURANTS
    JSONArray actifsCourants = new JSONArray();
    actifsCourants.put(createJsonEntry("Stocks et en cours", safeGetValue(() -> getStocksEtEnCours())));
    actifsCourants.put(createJsonEntry("Créances et emplois assimilés", safeGetValue(() -> getCreancesEtEmplois())));
    actifsCourants.put(createJsonEntry("Clients et autres débiteurs", safeGetValue(() -> getClientsEtAutresDebiteurs())));
    actifsCourants.put(createJsonEntry("Impôts", safeGetValue(() -> getImpots())));
    actifsCourants.put(createJsonEntry("Autres créances et actifs assimilés", safeGetValue(() -> getAutresCreances())));
    actifsCourants.put(createJsonEntry("Trésorerie et équivalents de trésorerie", safeGetValue(() -> getTresorerieEtEquivalents())));
    actifsCourants.put(createJsonEntry("Placements et autres équivalents de trésorerie", safeGetValue(() -> getPlacementsEquivalents())));
    actifsCourants.put(createJsonEntry("Trésorerie (fonds en caisse et dépôts à vue)", safeGetValue(() -> getTresorerieCaisseDepots())));
    actifsCourants.put(createJsonEntry("TOTAL ACTIFS COURANTS", safeGetValue(() -> getTotalActifsCourants())));

    // TOTAL DES ACTIFS
    bilan.put("TOTAL DES ACTIFS", safeGetValue(() -> getTotalActifs()));

    // CAPITAUX PROPRES
    JSONArray capitauxPropres = new JSONArray();
    capitauxPropres.put(createJsonEntry("Capital émis", safeGetValue(() -> getCapitalEmis())));
    capitauxPropres.put(createJsonEntry("Primes et réserves consolidées", safeGetValue(() -> getPrimesEtReserves())));
    capitauxPropres.put(createJsonEntry("Ecarts d'évaluation", safeGetValue(() -> getEcartsEvaluation())));
    capitauxPropres.put(createJsonEntry("Ecart d'équivalence", safeGetValue(() -> getEcartsEquivalence())));
    capitauxPropres.put(createJsonEntry("Résultat net - part du groupe", safeGetValue(() -> getResultatNetPartDuGroupe())));
    capitauxPropres.put(createJsonEntry("Autres capitaux propres - report à nouveau", safeGetValue(() -> getAutresCapitauxPropres())));
    capitauxPropres.put(createJsonEntry("Part de la société consolidante", safeGetValue(() -> getPartSocieteConsolidante())));
    capitauxPropres.put(createJsonEntry("Part des minoritaires", safeGetValue(() -> getPartDesMinoritaires())));
    capitauxPropres.put(createJsonEntry("TOTAL I", safeGetValue(() -> getTotalCapitauxPropres())));

    // PASSIFS NON-COURANTS
    JSONArray passifsNonCourants = new JSONArray();
    passifsNonCourants.put(createJsonEntry("Produits différés : subventions d'investissement", safeGetValue(() -> getSubventionsInvestissement())));
    passifsNonCourants.put(createJsonEntry("Impôts différés", safeGetValue(() -> getImpotsDifferes())));
    passifsNonCourants.put(createJsonEntry("Emprunts et dettes financières", safeGetValue(() -> getEmpruntsDettesFinancieres())));
    passifsNonCourants.put(createJsonEntry("Provisions et produits constatés d'avance", safeGetValue(() -> getProvisionsProduits())));
    passifsNonCourants.put(createJsonEntry("TOTAL PASSIFS NON COURANTS II", safeGetValue(() -> getTotalPassifsNonCourants())));

    // PASSIFS COURANTS
    JSONArray passifsCourants = new JSONArray();
    passifsCourants.put(createJsonEntry("Dettes à court terme - partie à court terme de dettes à long terme", safeGetValue(() -> getDettesCourtTerme())));
    passifsCourants.put(createJsonEntry("Fournisseurs et comptes rattachés", safeGetValue(() -> getFournisseursEtComptes())));
    passifsCourants.put(createJsonEntry("Provisions et produits constatés d'avance - passifs courants", safeGetValue(() -> getProvisionsProduitsCourants())));
    passifsCourants.put(createJsonEntry("Autres dettes", safeGetValue(() -> getAutresDettes())));
    passifsCourants.put(createJsonEntry("Comptes de trésorerie (découverts bancaires)", safeGetValue(() -> getComptesTresorerie())));
    passifsCourants.put(createJsonEntry("TOTAL PASSIFS COURANTS", safeGetValue(() -> getTotalPassifsCourants())));

    // TOTAL DES PASSIFS
    bilan.put("TOTAL DES PASSIFS", safeGetValue(() -> getTotalPassifs()));

    // Ajouter les tableaux JSON dans le bilan final
    bilan.put("ACTIFS NON COURANTS", actifsNonCourants);
    bilan.put("ACTIFS COURANTS", actifsCourants);
    bilan.put("CAPITAUX PROPRES", capitauxPropres);
    bilan.put("PASSIFS NON COURANTS", passifsNonCourants);
    bilan.put("PASSIFS COURANTS", passifsCourants);

    return bilan;
}*/
public JSONObject getBilanJSON() {
    JSONObject bilan = new JSONObject();

    // ACTIFS
    JSONArray actifs = new JSONArray();
    // actifs.put(new JSONObject().put("nom", "Ecart d'acquisition").put("montant", 0));
    actifs.put(new JSONObject().put("nom", "Immobilisations incorporelles").put("montant", safeGetValue(() -> getImmIncorporelles())));
    actifs.put(new JSONObject().put("nom", "Immobilisations corporelles").put("montant", safeGetValue(() -> getImmCorporelles())));
    actifs.put(new JSONObject().put("nom", "Immobilisations en cours").put("montant", safeGetValue(() -> getImmEnCours())));
    actifs.put(new JSONObject().put("nom", "Immobilisations financières").put("montant", safeGetValue(() -> getImmFinancieres())));
    // actifs.put(new JSONObject().put("nom", "Titres mis en équivalence").put("montant", 0));
    // actifs.put(new JSONObject().put("nom", "Autres participations et créances rattachées").put("montant", 0));
    // actifs.put(new JSONObject().put("nom", "Autres titres immobilisés").put("montant", 0));
    // actifs.put(new JSONObject().put("nom", "Prêts et autres immobilisations financières").put("montant", 0));
    actifs.put(new JSONObject().put("nom", "Stocks et en cours").put("montant", safeGetValue(() -> getStocksEtEnCours())));
    actifs.put(new JSONObject().put("nom", "Créances et emplois assimilés").put("montant", safeGetValue(() -> getCreancesEtEmplois())));
    // actifs.put(new JSONObject().put("nom", "Clients et autres débiteurs").put("montant", safeGetValue(() -> getClientsEtAutresDebiteurs())));
    // actifs.put(new JSONObject().put("nom", "Impôts").put("montant", safeGetValue(() -> getImpots())));
    // actifs.put(new JSONObject().put("nom", "Autres créances et actifs assimilés").put("montant", safeGetValue(() -> getAutresCreances())));
    actifs.put(new JSONObject().put("nom", "Trésorerie et équivalents de trésorerie").put("montant", safeGetValue(() -> getTresorerieEtEquivalents())));
    // actifs.put(new JSONObject().put("nom", "Placements et autres équivalents de trésorerie").put("montant", safeGetValue(() -> getPlacementsEquivalents())));
    // actifs.put(new JSONObject().put("nom", "Trésorerie (fonds en caisse et dépôts à vue)").put("montant", safeGetValue(() -> getTresorerieCaisseDepots())));
    actifs.put(new JSONObject().put("nom", "TOTAL ACTIFS NON COURANTS").put("montant", safeGetValue(() -> getActifsNonCourants())));
    actifs.put(new JSONObject().put("nom", "TOTAL ACTIFS COURANTS").put("montant", safeGetValue(() -> getTotalActifsCourants())));

    // PASSIFS
    JSONArray passifs = new JSONArray();
    passifs.put(new JSONObject().put("nom", "Produits différés : subventions d'investissement").put("montant", safeGetValue(() -> getSubventionsInvestissement())));
    // passifs.put(new JSONObject().put("nom", "Impôts différés").put("montant", safeGetValue(() -> getImpotsDifferes())));
    passifs.put(new JSONObject().put("nom", "Emprunts et dettes financières").put("montant", safeGetValue(() -> getEmpruntsDettesFinancieres())));
    passifs.put(new JSONObject().put("nom", "Provisions et produits constatés d'avance").put("montant", safeGetValue(() -> getProvisionsProduits())));
    passifs.put(new JSONObject().put("nom", "Dettes à court terme - partie à court terme de dettes à long terme").put("montant", safeGetValue(() -> getDettesCourtTerme())));
    passifs.put(new JSONObject().put("nom", "Fournisseurs et comptes rattachés").put("montant", safeGetValue(() -> getFournisseursEtComptes())));
    passifs.put(new JSONObject().put("nom", "Provisions et produits constatés d'avance - passifs courants").put("montant", safeGetValue(() -> getProvisionsProduitsCourants())));
    // passifs.put(new JSONObject().put("nom", "Autres dettes").put("montant", safeGetValue(() -> getAutresDxettes())));
    passifs.put(new JSONObject().put("nom", "Comptes de trésorerie (découverts bancaires)").put("montant", safeGetValue(() -> getComptesTresorerie())));
    passifs.put(new JSONObject().put("nom", "TOTAL PASSIFS NON COURANTS II").put("montant", safeGetValue(() -> getTotalPassifsNonCourants())));
    passifs.put(new JSONObject().put("nom", "TOTAL PASSIFS COURANTS").put("montant", safeGetValue(() -> getTotalPassifsCourants())));


    // Structure finale
    bilan.put("actifs", actifs);
    bilan.put("passifs", passifs);

    return bilan;
}


private JSONObject createJsonEntry(String nom, Object montant) {
    JSONObject entry = new JSONObject();
    entry.put("nom", nom);
    entry.put("montant", montant);
    return entry;
}


// Helper function to safely execute method and return 0 in case of error
private Double safeGetValue(Supplier<Double> supplier) {
    try {
        return supplier.get();
    } catch (Exception e) {
        return 0.0;
    }
}

    public Double getTotalActifs() {
        return getActifsNonCourants() + getTotalActifsCourants();
    }
    public Double getTotalPassifs() {
        return getTotalPassifsNonCourants() + getTotalPassifsCourants();
    }
    // Immobilisations incorporelles
    public Double getImmIncorporelles() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Immobilisations incorporelles') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Immobilisations corporelles
    public Double getImmCorporelles() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Immobilisations corporelles') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Immobilisations en cours
    public Double getImmEnCours() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Immobilisations en cours') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Immobilisations financières
    public Double getImmFinancieres() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Autres immobilisations financières') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Actifs Non courants
    public Double getActifsNonCourants() {
        String sql = """
            SELECT SUM(t.montant) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Actifs Non courant') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Stocks et en cours
    public Double getStocksEtEnCours() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Stock') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Créances et emplois assimilés
    public Double getCreancesEtEmplois() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Creances clients') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Trésorerie et équivalents de trésorerie
    public Double getTresorerieEtEquivalents() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Tresorerie') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Actifs Courants : Total des actifs courants
    public Double getTotalActifsCourants() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Actifs courants') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }


    // Capitaux propres : Capital émis
    public Double getCapitalEmis() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Capital, réserves et assimilés') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Primes et réserves consolidées
    public Double getPrimesEtReserves() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme_primes_et_reserves
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Report à nouveau') e ON t.compte_financier_id = e.id
            UNION ALL
            SELECT COALESCE(SUM(t.montant), 0)
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Résultat de l''exercice') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Écarts d'évaluation
    public Double getEcartsEvaluation() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme_ecarts_evaluation
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Ecarts d\'évaluation') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Écart d'équivalence
    public Double getEcartsEquivalence() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS somme_ecart_equivalence
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Ecart d\'équivalence') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Résultat net - part du groupe
    public Double getResultatNetPartDuGroupe() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Résultat de l''exercice') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Autres capitaux propres - report à nouveau
    public Double getAutresCapitauxPropres() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Report à nouveau') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // TOTAL I : Capitaux propres
    public Double getTotalCapitauxPropres() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
            FROM transaction_financiere t
            JOIN get_enfants_recursive('capitaux propres') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Subventions d'investissement
    public Double getSubventionsInvestissement() {
        String sql = """
            SELECT COALESCE(SUM(t.montant) AS total_subventions_investissement
            FROM transaction_financiere t
            JOIN compte_financier c ON t.compte_financier_id = c.id
            WHERE c.nom = 'Subventions d''investissement';
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Emprunts et dettes financières
    public Double getEmpruntsDettesFinancieres() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Emprunts et dettes assimilés') e ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Provisions et produits constatés d'avance
    public Double getProvisionsProduits() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS total_provisions_prod_concustes
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Provisions et produits constatés d\'avance') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // TOTAL PASSIFS NON-COURANTS II
    public Double getTotalPassifsNonCourants() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS total_provisions_prod_concustes
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Passif Non courant') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Dettes à court terme
    public Double getDettesCourtTerme() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0) AS total_dettes_court_terme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Dettes à court terme') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Fournisseurs et comptes rattachés
    public Double getFournisseursEtComptes() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0)  AS total_dettes_court_terme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Fournisseurs et comptes rattachés') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Provisions et produits constatés d'avance - passifs courants
    public Double getProvisionsProduitsCourants() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0)  AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Provisions et produits constatés d''avance - passifs courants') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Comptes de trésorerie (découverts bancaires)
    public Double getComptesTresorerie() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0)  AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Tresorerie') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // TOTAL PASSIFS COURANTS
    public Double getTotalPassifsCourants() {
        String sql = """
            SELECT COALESCE(SUM(t.montant), 0)  AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Passif courant') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
}
