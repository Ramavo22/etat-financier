
-- 1 Marge nette
-- Calcul du revenu total
WITH Revenu AS (
    SELECT SUM(montant) AS total_revenu
    FROM transaction_financiere
    WHERE compte_financier_id IN (
        SELECT id FROM compte_financier WHERE type_compte = (
            SELECT id FROM type_compte WHERE nom = 'Produits'
        )
    )
),
-- Calcul des charges totales
Charges AS (
    SELECT SUM(montant) AS total_charges
    FROM transaction_financiere
    WHERE compte_financier_id IN (
        SELECT id FROM compte_financier WHERE type_compte = (
            SELECT id FROM type_compte WHERE nom = 'Charges'
        )
    )
)
-- Calcul de la marge nette
SELECT 
    (Revenu.total_revenu - Charges.total_charges) / Revenu.total_revenu AS marge_nette
FROM 
    Revenu, Charges;

----------------------------------------------------------------------------
-- 2 ROA
-- Calcul du résultat net
WITH ResultatNet AS (
    SELECT 
        (SUM(CASE WHEN tc.nom = 'Produits' THEN tf.montant ELSE 0 END) - 
         SUM(CASE WHEN tc.nom = 'Charges' THEN tf.montant ELSE 0 END)) AS resultat_net
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    JOIN type_compte tc ON cf.type_compte = tc.id
),

-- Calcul du total des actifs
TotalActif AS (
    SELECT SUM(tf.montant) AS total_actif
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    JOIN type_compte tc ON cf.type_compte = tc.id
    WHERE tc.nom = 'Actif'
)

-- Calcul du ROA
SELECT 
    (ResultatNet.resultat_net / TotalActif.total_actif) * 100 AS roa
FROM 
    ResultatNet, TotalActif;

----------------------------------------------------------------------------
-- 3 ROE
-- Calcul du résultat net
WITH ResultatNet AS (
    SELECT 
        (SUM(CASE WHEN tc.nom = 'Produits' THEN tf.montant ELSE 0 END) - 
         SUM(CASE WHEN tc.nom = 'Charges' THEN tf.montant ELSE 0 END)) AS resultat_net
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    JOIN type_compte tc ON cf.type_compte = tc.id
),

-- Calcul du total des capitaux propres
CapitauxPropres AS (
    SELECT SUM(tf.montant) AS total_capitaux_propres
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    JOIN type_compte tc ON cf.type_compte = tc.id
    WHERE tc.nom = 'Capitaux propres'
)

-- Calcul du ROE
SELECT 
    (ResultatNet.resultat_net / CapitauxPropres.total_capitaux_propres) * 100 AS roe
FROM 
    ResultatNet, CapitauxPropres;

----------------------------------------------------------------------------
-- 4 Ratio de Liquidité Générale
-- Calcul du total des actifs courants
WITH ActifCourant AS (
    SELECT SUM(tf.montant) AS total_actif_courant
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id = (
        SELECT id FROM compte_financier WHERE nom = 'Actifs courants'
    )
),

-- Calcul du total des passifs courants
PassifCourant AS (
    SELECT SUM(tf.montant) AS total_passif_courant
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id = (
        SELECT id FROM compte_financier WHERE nom = 'Passif courant'
    )
)

-- Calcul du Ratio de Liquidité Générale
SELECT 
    (ActifCourant.total_actif_courant / PassifCourant.total_passif_courant) AS ratio_liquidite_generale
FROM 
    ActifCourant, PassifCourant;

----------------------------------------------------------------------------
-- 5 Ratio de Liquidité Réduite
-- Calcul du total des actifs courants sans stock
WITH ActifCourantSansStock AS (
    SELECT SUM(tf.montant) AS total_actif_courant_sans_stock
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id = (
        SELECT id FROM compte_financier WHERE nom = 'Actifs courants'
    )
    AND cf.nom != 'Stock'
),

-- Calcul du total des passifs courants
PassifCourant AS (
    SELECT SUM(tf.montant) AS total_passif_courant
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id = (
        SELECT id FROM compte_financier WHERE nom = 'Passif courant'
    )
)

-- Calcul du Ratio de Liquidité Réduite
SELECT 
    (ActifCourantSansStock.total_actif_courant_sans_stock / PassifCourant.total_passif_courant) AS ratio_liquidite_reduite
FROM 
    ActifCourantSansStock, PassifCourant;

----------------------------------------------------------------------------
-- 6 Ratio d'Endettement Global
-- Calcul du total des dettes
WITH Dettes AS (
    SELECT SUM(tf.montant) AS total_dettes
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id IN (
        SELECT id FROM compte_financier WHERE nom IN ('Passif courant', 'Passif Non courant')
    )
),

-- Calcul du total des actifs
Actifs AS (
    SELECT SUM(tf.montant) AS total_actifs
    FROM transaction_financiere tf
    JOIN compte_financier cf ON tf.compte_financier_id = cf.id
    WHERE cf.parent_id IN (
        SELECT id FROM compte_financier WHERE nom IN ('Actifs courants', 'Actifs Non courant')
    )
)

-- Calcul du Ratio d'Endettement Global
SELECT 
    (Dettes.total_dettes / Actifs.total_actifs) * 100 AS ratio_endettement_global
FROM 
    Dettes, Actifs;
