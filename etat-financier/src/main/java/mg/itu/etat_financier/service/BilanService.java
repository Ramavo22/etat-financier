package mg.itu.etat_financier.service;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BilanService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JSONObject getBilanJSON() {
    JSONObject bilan = new JSONObject();

    // ACTIFS NON COURANTS
    JSONObject actifsNonCourants = new JSONObject();
    actifsNonCourants.put("Ecart d'acquisition", 0);
    actifsNonCourants.put("Immobilisations incorporelles", safeGetValue(() -> getImmIncorporelles()));
    actifsNonCourants.put("Immobilisations corporelles", safeGetValue(() -> getImmCorporelles()));
    actifsNonCourants.put("Immobilisations en cours", safeGetValue(() -> getImmEnCours()));
    actifsNonCourants.put("Immobilisations financières", safeGetValue(() -> getImmFinancieres()));
    actifsNonCourants.put("Titres mis en équivalence", safeGetValue(() -> getTitresEquivalence()));
    actifsNonCourants.put("Autres participations et créances rattachées", safeGetValue(() -> getAutresParticipations()));
    actifsNonCourants.put("Autres titres immobilisés", safeGetValue(() -> getAutresTitresImmobilises()));
    actifsNonCourants.put("Prêts et autres immobilisations financières", safeGetValue(() -> getPretsImmobilisations()));
    actifsNonCourants.put("TOTAL ACTIFS NON COURANTS", safeGetValue(() -> getActifsNonCourants()));

    // ACTIFS COURANTS
    JSONObject actifsCourants = new JSONObject();
    actifsCourants.put("Stocks et en cours", safeGetValue(() -> getStocksEtEnCours()));
    actifsCourants.put("Créances et emplois assimilés", safeGetValue(() -> getCreancesEtEmplois()));
    actifsCourants.put("Clients et autres débiteurs", safeGetValue(() -> getClientsEtAutresDebiteurs()));
    actifsCourants.put("Impôts", safeGetValue(() -> getImpots()));
    actifsCourants.put("Autres créances et actifs assimilés", safeGetValue(() -> getAutresCreances()));
    actifsCourants.put("Trésorerie et équivalents de trésorerie", safeGetValue(() -> getTresorerieEtEquivalents()));
    actifsCourants.put("Placements et autres équivalents de trésorerie", safeGetValue(() -> getPlacementsEquivalents()));
    actifsCourants.put("Trésorerie (fonds en caisse et dépôts à vue)", safeGetValue(() -> getTresorerieCaisseDepots()));
    actifsCourants.put("TOTAL ACTIFS COURANTS", safeGetValue(() -> getTotalActifsCourants()));

    // TOTAL DES ACTIFS
    bilan.put("TOTAL DES ACTIFS", safeGetValue(() -> getTotalActifs()));

    // CAPITAUX PROPRES
    JSONObject capitauxPropres = new JSONObject();
    capitauxPropres.put("Capital émis", safeGetValue(() -> getCapitalEmis()));
    capitauxPropres.put("Primes et réserves consolidées", safeGetValue(() -> getPrimesEtReserves()));
    capitauxPropres.put("Ecarts d'évaluation", safeGetValue(() -> getEcartsEvaluation()));
    capitauxPropres.put("Ecart d'équivalence", safeGetValue(() -> getEcartsEquivalence()));
    capitauxPropres.put("Résultat net - part du groupe", safeGetValue(() -> getResultatNetPartDuGroupe()));
    capitauxPropres.put("Autres capitaux propres - report à nouveau", safeGetValue(() -> getAutresCapitauxPropres()));
    capitauxPropres.put("Part de la société consolidante", safeGetValue(() -> getPartSocieteConsolidante()));
    capitauxPropres.put("Part des minoritaires", safeGetValue(() -> getPartDesMinoritaires()));
    capitauxPropres.put("TOTAL I", safeGetValue(() -> getTotalCapitauxPropres()));

    // PASSIFS NON-COURANTS
    JSONObject passifsNonCourants = new JSONObject();
    passifsNonCourants.put("Produits différés : subventions d'investissement", safeGetValue(() -> getSubventionsInvestissement()));
    passifsNonCourants.put("Impôts différés", safeGetValue(() -> getImpotsDifferes()));
    passifsNonCourants.put("Emprunts et dettes financières", safeGetValue(() -> getEmpruntsDettesFinancieres()));
    passifsNonCourants.put("Provisions et produits constatés d'avance", safeGetValue(() -> getProvisionsProduits()));
    passifsNonCourants.put("TOTAL PASSIFS NON COURANTS II", safeGetValue(() -> getTotalPassifsNonCourants()));

    // PASSIFS COURANTS
    JSONObject passifsCourants = new JSONObject();
    passifsCourants.put("Dettes à court terme - partie à court terme de dettes à long terme", safeGetValue(() -> getDettesCourtTerme()));
    passifsCourants.put("Fournisseurs et comptes rattachés", safeGetValue(() -> getFournisseursEtComptes()));
    passifsCourants.put("Provisions et produits constatés d'avance - passifs courants", safeGetValue(() -> getProvisionsProduitsCourants()));
    passifsCourants.put("Autres dettes", safeGetValue(() -> getAutresDettes()));
    passifsCourants.put("Comptes de trésorerie (découverts bancaires)", safeGetValue(() -> getComptesTresorerie()));
    passifsCourants.put("TOTAL PASSIFS COURANTS", safeGetValue(() -> getTotalPassifsCourants()));

    // TOTAL DES PASSIFS
    bilan.put("TOTAL DES PASSIFS", safeGetValue(() -> getTotalPassifs()));

    // Ajouter les objets JSON dans le bilan final
    bilan.put("ACTIFS NON COURANTS", actifsNonCourants);
    bilan.put("ACTIFS COURANTS", actifsCourants);
    bilan.put("CAPITAUX PROPRES", capitauxPropres);
    bilan.put("PASSIFS NON COURANTS", passifsNonCourants);
    bilan.put("PASSIFS COURANTS", passifsCourants);

    return bilan;
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
        return getTotalActifsNonCourants() + getTotalActifsCourants();
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
            SELECT SUM(t.montant) AS total_subventions_investissement
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
            SELECT SUM(t.montant) AS total_provisions_prod_concustes
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Provisions et produits constatés d\'avance') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // TOTAL PASSIFS NON-COURANTS II
    public Double getTotalPassifsNonCourants() {
        String sql = """
            SELECT SUM(t.montant) AS total_provisions_prod_concustes
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Passif Non courant') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Dettes à court terme
    public Double getDettesCourtTerme() {
        String sql = """
            SELECT SUM(t.montant) AS total_dettes_court_terme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Dettes à court terme') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Fournisseurs et comptes rattachés
    public Double getFournisseursEtComptes() {
        String sql = """
            SELECT SUM(t.montant) AS total_dettes_court_terme
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Fournisseurs et comptes rattachés') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Provisions et produits constatés d'avance - passifs courants
    public Double getProvisionsProduitsCourants() {
        String sql = """
            SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Provisions et produits constatés d''avance - passifs courants') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Comptes de trésorerie (découverts bancaires)
    public Double getComptesTresorerie() {
        String sql = """
            SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Tresorerie') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // TOTAL PASSIFS COURANTS
    public Double getTotalPassifsCourants() {
        String sql = """
            SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
            FROM transaction_financiere t
            JOIN get_enfants_recursive('Passif courant') e
                ON t.compte_financier_id = e.id;
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
}
