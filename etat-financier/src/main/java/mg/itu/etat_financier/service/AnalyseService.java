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
    WITH total_produit AS (
        SELECT SUM(tf.montant) AS valeur
        FROM transaction_financiere tf
                 JOIN compte_financier cf ON cf.id = tf.compte_financier_id
        WHERE cf.type_compte = 4
    ),
         total_charge AS (
             SELECT SUM(tf.montant) AS valeur
             FROM transaction_financiere tf
                      JOIN compte_financier cf ON cf.id = tf.compte_financier_id
             WHERE cf.type_compte = 5
         )
    SELECT
        CASE
            WHEN total_produit.valeur = 0 OR total_produit.valeur IS NULL THEN NULL
            ELSE ((total_produit.valeur - total_charge.valeur) / total_produit.valeur) * 100
            END AS margeNette
    FROM
        total_produit, total_charge
    """;
        return template.queryForObject(sql, Double.class);
    }

    public Double retourActif(){
        String sql = """
        WITH resultatNet AS (
           SELECT SUM(tf.montant) AS valeur
           FROM transaction_financiere tf
           WHERE tf.compte_financier_id = 8
             AND EXTRACT(YEAR FROM tf.date_transaction) = 2024
       ),
       total_actif AS (
           SELECT SUM(tf2.montant) AS valeur
           FROM transaction_financiere tf2
           JOIN public.compte_financier cf ON cf.id = tf2.compte_financier_id
           WHERE cf.type_compte = 1
       )
       SELECT
           CASE
               WHEN total_actif.valeur = 0 THEN NULL -- Éviter la division par zéro
               ELSE resultatNet.valeur / total_actif.valeur
           END AS ROA
       FROM resultatNet, total_actif
            """;

        return template.queryForObject(sql, Double.class);
    }


    public Double retourCapitauxPropre(){
        String sql = """
        WITH resultatNet AS (
            SELECT SUM(tf.montant) AS valeur
            FROM transaction_financiere tf
            WHERE tf.compte_financier_id = 8
              AND EXTRACT(YEAR FROM tf.date_transaction) = 2024
        ),
        capitauxPropre AS (
            SELECT SUM(tf.montant) AS valeur
            FROM transaction_financiere tf
            JOIN public.compte_financier cf ON cf.id = tf.compte_financier_id
            WHERE cf.type_compte = 3
        )
        SELECT
            CASE
                WHEN capitauxPropre.valeur = 0 THEN NULL -- Éviter la division par zéro
                ELSE (resultatNet.valeur / capitauxPropre.valeur) * 100
            END AS ROE
        FROM resultatNet, capitauxPropre;
                """;
        return template.queryForObject(sql, Double.class);
    }

    public Double ratioLiquiditeGeneral() {
        String sql = """
        WITH actifCourant AS (
            SELECT SUM(tf.montant) AS valeur
            FROM transaction_financiere tf
            JOIN public.compte_financier cf ON cf.id = tf.compte_financier_id
            WHERE cf.parent_id = 1
        ),
        passifCourant AS (
            SELECT SUM(tf.montant) AS valeur
            FROM transaction_financiere tf
            JOIN public.compte_financier cf ON cf.id = tf.compte_financier_id
            WHERE cf.parent_id = 3
        )
        SELECT
            CASE
                WHEN passifCourant.valeur = 0 THEN NULL -- Éviter division par zéro
                ELSE (actifCourant.valeur / passifCourant.valeur)
            END AS rlg
        FROM actifCourant, passifCourant;
        """;

        return template.queryForObject(sql, Double.class);
    }

    public Double ratioLiquiditeReduite() {
        String sql = """
        WITH actifCourant AS (
            SELECT SUM(tf.montant) AS valeur
            FROM transaction_financiere tf
                     JOIN public.compte_financier cf ON cf.id = tf.compte_financier_id
            WHERE cf.parent_id = 1
              AND cf.id NOT IN (22, 23, 24, 25, 26, 27, 28, 29) -- Exclusion des IDs liés aux stocks
        ),
             passifCourant AS (
                 SELECT SUM(tf.montant) AS valeur
                 FROM transaction_financiere tf
                          JOIN public.compte_financier cf ON cf.id = tf.compte_financier_id
                 WHERE cf.parent_id = 3
             )
        SELECT
            CASE
                WHEN passifCourant.valeur = 0 THEN NULL -- Éviter division par zéro
                ELSE (actifCourant.valeur / passifCourant.valeur) -- Calcul du RLG
                END AS rlg
        FROM actifCourant, passifCourant;
    """;

        return template.queryForObject(sql, Double.class);
    }

    public Double ratioEndettementGlobal() {
        String sql = """
        WITH dette AS (
          SELECT tf.montant as valeur FROM transaction_financiere tf
          JOIN public.compte_financier cf on cf.id = tf.compte_financier_id
          WHERE cf.type_compte = 2
          AND tf.compte_financier_id IN (10, 11, 12, 13, 14, 30, 36)
      ),
           total_actif AS (
               SELECT SUM(tf2.montant) AS valeur
               FROM transaction_financiere tf2
                        JOIN public.compte_financier cf ON cf.id = tf2.compte_financier_id
               WHERE cf.type_compte = 1
           )
      SELECT (dette.valeur/total_actif.valeur) * 100 as dette_global
      FROM dette,total_actif
    """;
        return template.queryForObject(sql, Double.class);
    }





}
