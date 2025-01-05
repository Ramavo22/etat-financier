package mg.itu.etat_financier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalyseService {

    private final JdbcTemplate template;

    public AnalyseService(JdbcTemplate template) {
        this.template = template;
    }


    public Double margeNette(){
        String sql = """
            WITH Revenu AS (
                SELECT SUM(montant) AS total_revenu
                FROM transaction_financiere
                WHERE compte_financier_id IN (
                    SELECT id FROM compte_financier WHERE type_compte = (
                        SELECT id FROM type_compte WHERE nom = 'Produits'
                    )
                )
            ),
            Charges AS (
                SELECT SUM(montant) AS total_charges
                FROM transaction_financiere
                WHERE compte_financier_id IN (
                    SELECT id FROM compte_financier WHERE type_compte = (
                        SELECT id FROM type_compte WHERE nom = 'Charges'
                    )
                )
            )
            SELECT
                (Revenu.total_revenu - Charges.total_charges) / Revenu.total_revenu AS marge_nette
            FROM
                Revenu, Charges
        """;

        return template.queryForObject(sql, Double.class);
    }

    public Double retourActif(){
        String sql = """
            WITH ResultatNet AS (
                SELECT
                    (SUM(CASE WHEN tc.nom = 'Produits' THEN tf.montant ELSE 0 END) -
                     SUM(CASE WHEN tc.nom = 'Charges' THEN tf.montant ELSE 0 END)) AS resultat_net
                FROM transaction_financiere tf
                JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                JOIN type_compte tc ON cf.type_compte = tc.id
            ),
            TotalActif AS (
                SELECT SUM(tf.montant) AS total_actif
                FROM transaction_financiere tf
                JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                JOIN type_compte tc ON cf.type_compte = tc.id
                WHERE tc.nom = 'Actif'
            )
            SELECT
                (ResultatNet.resultat_net / TotalActif.total_actif) * 100 AS roa
            FROM
                ResultatNet, TotalActif
            """;

        return template.queryForObject(sql, Double.class);
    }


    public Double retourCapitauxPropre(){
        String sql = """
                WITH ResultatNet AS (
                    SELECT
                        (SUM(CASE WHEN tc.nom = 'Produits' THEN tf.montant ELSE 0 END) -
                         SUM(CASE WHEN tc.nom = 'Charges' THEN tf.montant ELSE 0 END)) AS resultat_net
                    FROM transaction_financiere tf
                    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                    JOIN type_compte tc ON cf.type_compte = tc.id
                ),
                CapitauxPropres AS (
                    SELECT SUM(tf.montant) AS total_capitaux_propres
                    FROM transaction_financiere tf
                    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                    JOIN type_compte tc ON cf.type_compte = tc.id
                    WHERE tc.nom = 'Capitaux propres'
                )
                SELECT
                    (ResultatNet.resultat_net / CapitauxPropres.total_capitaux_propres) * 100 AS roe
                FROM
                    ResultatNet, CapitauxPropres
                """;
        return template.queryForObject(sql, Double.class);
    }

    public Double ratioLiquiditeGeneral() {
        String sql = """
            WITH ActifCourant AS (
                SELECT SUM(tf.montant) AS total_actif_courant
                FROM transaction_financiere tf
                JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                WHERE cf.parent_id = (
                    SELECT id FROM compte_financier WHERE nom = 'Actifs courants'
                )
            ),
            PassifCourant AS (
                SELECT SUM(tf.montant) AS total_passif_courant
                FROM transaction_financiere tf
                JOIN compte_financier cf ON tf.compte_financier_id = cf.id
                WHERE cf.parent_id = (
                    SELECT id FROM compte_financier WHERE nom = 'Passif courant'
                )
            )
            SELECT
                (ActifCourant.total_actif_courant / PassifCourant.total_passif_courant) AS ratio_liquidite_generale
            FROM
                ActifCourant, PassifCourant
        """;

        return template.queryForObject(sql, Double.class);
    }

    public Double ratioLiquiditeReduite() {
        String sql = """
        WITH ActifCourantSansStock AS (
            SELECT SUM(tf.montant) AS total_actif_courant_sans_stock
            FROM transaction_financiere tf
            JOIN compte_financier cf ON tf.compte_financier_id = cf.id
            WHERE cf.parent_id = (
                SELECT id FROM compte_financier WHERE nom = 'Actifs courants'
            )
            AND cf.nom != 'Stock'
        ),
        PassifCourant AS (
            SELECT SUM(tf.montant) AS total_passif_courant
            FROM transaction_financiere tf
            JOIN compte_financier cf ON tf.compte_financier_id = cf.id
            WHERE cf.parent_id = (
                SELECT id FROM compte_financier WHERE nom = 'Passif courant'
            )
        )
        SELECT 
            (CASE 
                WHEN PassifCourant.total_passif_courant = 0 THEN NULL
                ELSE (ActifCourantSansStock.total_actif_courant_sans_stock / PassifCourant.total_passif_courant)
            END) AS ratio_liquidite_reduite
        FROM 
            ActifCourantSansStock, PassifCourant
    """;

        return template.queryForObject(sql, Double.class);
    }

    public Double ratioEndettementGlobal() {
        String sql = """
        WITH Dettes AS (
            SELECT SUM(tf.montant) AS total_dettes
            FROM transaction_financiere tf
            JOIN compte_financier cf ON tf.compte_financier_id = cf.id
            WHERE cf.parent_id IN (
                SELECT id FROM compte_financier WHERE nom IN ('Passif courant', 'Passif Non courant')
            )
        ),
        Actifs AS (
            SELECT SUM(tf.montant) AS total_actifs
            FROM transaction_financiere tf
            JOIN compte_financier cf ON tf.compte_financier_id = cf.id
            WHERE cf.parent_id IN (
                SELECT id FROM compte_financier WHERE nom IN ('Actifs courants', 'Actifs Non courant')
            )
        )
        SELECT 
            (CASE 
                WHEN Actifs.total_actifs = 0 THEN NULL
                ELSE (Dettes.total_dettes / Actifs.total_actifs) * 100
            END) AS ratio_endettement_global
        FROM 
            Dettes, Actifs
    """;

        return template.queryForObject(sql, Double.class);
    }





}
