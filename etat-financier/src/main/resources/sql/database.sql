-- Création de la base de données et connexion
CREATE DATABASE finance_reader;
\c finance_reader;


-- Table pour les types de compte
CREATE TABLE type_compte (
    id SERIAL PRIMARY KEY,            -- Identifiant unique pour chaque type de compte
    nom VARCHAR(50) UNIQUE NOT NULL   -- Nom du type de compte (ex : "Actif", "Passif")
);

-- Table pour les comptes financiers
CREATE TABLE compte_financier (
    id SERIAL PRIMARY KEY,            -- Identifiant unique du compte
    nom VARCHAR(255) NOT NULL,        -- Nom du compte (ex : "Actifs courants", "Capitaux propres")
    type_compte INT REFERENCES type_compte(id),  -- Référence vers la table des types de comptes
    parent_id INT,                    -- Peut référencer un compte parent (pour structurer les hiérarchies)
    FOREIGN KEY (parent_id) REFERENCES compte_financier(id) ON DELETE CASCADE
);

-- Table pour les transactions financières
CREATE TABLE transaction_financiere (
    id SERIAL PRIMARY KEY,                        -- Identifiant unique de la transaction
    compte_financier_id INT NOT NULL,             -- Référence au compte financier
    montant DECIMAL(15, 2) NOT NULL CHECK (montant >= 0), -- Montant de la transaction (doit être positif)      -- Indique si la transaction est un débit ou crédit
    description TEXT,                             -- Description de la transaction
    date_transaction DATE NOT NULL,          -- Date et heure de la transaction
    FOREIGN KEY (compte_financier_id) REFERENCES compte_financier(id) 
);


CREATE OR REPLACE FUNCTION get_enfants_recursive(nom_rubrique VARCHAR)
RETURNS TABLE(id INT, nom VARCHAR, type_compte INT, parent_id INT) AS
$$
BEGIN
    RETURN QUERY
    WITH RECURSIVE enfants AS (
        SELECT c.id, c.nom, c.type_compte, c.parent_id
        FROM compte_financier c
        WHERE c.nom = nom_rubrique
        
        UNION ALL
        
        SELECT c.id, c.nom, c.type_compte, c.parent_id
        FROM compte_financier c
        INNER JOIN enfants e ON c.parent_id = e.id
    )
    SELECT e.id, e.nom, e.type_compte, e.parent_id
    FROM enfants e;
END;
$$ LANGUAGE plpgsql;


SELECT * FROM get_enfants_recursive('Actifs courants');


. Actifs Non Courants :
Immobilisations incorporelles : Somme des comptes associés.

SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations incorporelles') e
    ON t.compte_financier_id = e.id;

Immobilisations corporelles : Somme des comptes associés.
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations corporelles') e
    ON t.compte_financier_id = e.id;

Immobilisations en cours : Somme des comptes associés.
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations en cours') e
    ON t.compte_financier_id = e.id;

Immobilisations financières : Somme des comptes associés.
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Autres immobilisations financières') e
    ON t.compte_financier_id = e.id;

SELECT SUM(t.montant) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Actifs Non courant') e
    ON t.compte_financier_id = e.id;

2. Actifs Courants :
Stocks et en cours : Somme des comptes associés (stocks, matières premières, produits, etc.).
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Stock') e
    ON t.compte_financier_id = e.id;

Créances et emplois assimilés : Somme des comptes de créances (clients, autres débiteurs).
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Creances clients') e
    ON t.compte_financier_id = e.id;

Trésorerie et équivalents de trésorerie : Somme des comptes de trésorerie (banques, caisse).
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Tresorerie') e
    ON t.compte_financier_id = e.id;

SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Actifs courants') e
    ON t.compte_financier_id = e.id;


/*CAPITAUX PROPRES
Capital émis
Peut être calculé si vous avez un compte spécifique pour le capital émis dans compte_financier. Par exemple, 'Capital, réserves et assimilés' pourrait être utilisé.*/
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Capital, réserves et assimilés') e
    ON t.compte_financier_id = e.id;

/*Primes et réserves consolidées
Vous pouvez calculer les réserves si elles sont associées à des comptes comme 'Report à nouveau' ou 'Résultat de l'exercice'.*/
SELECT COALESCE(SUM(t.montant), 0) AS somme_primes_et_reserves
FROM transaction_financiere t
JOIN get_enfants_recursive('Report à nouveau') e ON t.compte_financier_id = e.id
UNION ALL
SELECT COALESCE(SUM(t.montant), 0)
FROM transaction_financiere t
JOIN get_enfants_recursive('Résultat de l''exercice') e ON t.compte_financier_id = e.id;


-- Écarts d'évaluation
-- Non mentionnés explicitement dans les données fournies.
SELECT COALESCE(SUM(t.montant), 0) AS somme_ecarts_evaluation
FROM transaction_financiere t
JOIN get_enfants_recursive('Ecarts d\'évaluation') e ON t.compte_financier_id = e.id;


--Écart d'équivalence
--Non spécifié dans les données.
SELECT COALESCE(SUM(t.montant), 0) AS somme_ecart_equivalence
FROM transaction_financiere t
JOIN get_enfants_recursive('Ecart d\'équivalence') e ON t.compte_financier_id = e.id;


--Résultat net - part du groupe
--Peut être obtenu si le compte 'Résultat de l'exercice' est utilisé.
SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
FROM transaction_financiere t
JOIN get_enfants_recursive('Résultat de l\'exercice') e ON t.compte_financier_id = e.id;


--Autres capitaux propres - report à nouveau
--Peut être calculé via le compte 'Report à nouveau'.
SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
FROM transaction_financiere t
JOIN get_enfants_recursive('Report à nouveau') e ON t.compte_financier_id = e.id;


--TOTAL I
--Somme de tous les sous-comptes liés aux capitaux propres.
SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
FROM transaction_financiere t
JOIN get_enfants_recursive('capitaux propres') e ON t.compte_financier_id = e.id;

--PASSIFS NON-COURANTS
-- Produits différés : subventions d'investissement
-- Non spécifié dans les données fournies.
SELECT 
    SUM(t.montant) AS total_subventions_investissement
FROM 
    transaction_financiere t
JOIN 
    compte_financier c ON t.compte_financier_id = c.id
WHERE 
    c.nom = 'Subventions d''investissement';


-- Emprunts et dettes financières
-- Peut être obtenu via le compte 'Emprunts et dettes assimilés'.
SELECT COALESCE(SUM(t.montant), 0) AS resultat_net_part_du_groupe
FROM transaction_financiere t
JOIN get_enfants_recursive('Emprunts et dettes assimilés') e ON t.compte_financier_id = e.id;

-- Provisions et produits constatés d'avance
-- Vous pouvez utiliser un compte correspondant si spécifié.
SELECT SUM(t.montant) AS total_provisions_prod_concustes
FROM transaction_financiere t
JOIN get_enfants_recursive('Provisions et produits constatés d\'avance') e
    ON t.compte_financier_id = e.id;



-- TOTAL PASSIFS NON-COURANTS II
-- Somme de tous les sous-comptes liés aux passifs non-courants.

SELECT SUM(t.montant) AS total_provisions_prod_concustes
FROM transaction_financiere t
JOIN get_enfants_recursive('Passif Non courant') e
    ON t.compte_financier_id = e.id;


-- PASSIFS COURANTS
-- Dettes à court terme - partie à court terme de dettes à long terme
-- Non spécifié explicitement.
SELECT SUM(t.montant) AS total_dettes_court_terme
FROM transaction_financiere t
JOIN get_enfants_recursive('Dettes à court terme') e
    ON t.compte_financier_id = e.id;


-- Fournisseurs et comptes rattachés
-- Peut être calculé via le compte 'Fournisseurs et comptes rattachés'.
SELECT SUM(t.montant) AS total_dettes_court_terme
FROM transaction_financiere t
JOIN get_enfants_recursive('Fournisseurs et comptes rattachés') e
    ON t.compte_financier_id = e.id;

-- Provisions et produits constatés d'avance - passifs courants
-- Peut être calculé si un compte similaire est défini.
SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
FROM transaction_financiere t
JOIN get_enfants_recursive('Provisions et produits constatés d''avance - passifs courants') e
    ON t.compte_financier_id = e.id;

-- Comptes de trésorerie (découverts bancaires)
-- Peut être partiellement couvert si des comptes de trésorerie sont définis.
SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
FROM transaction_financiere t
JOIN get_enfants_recursive('Tresorerie') e
    ON t.compte_financier_id = e.id;

-- TOTAL PASSIFS COURANTS
-- Somme des sous-comptes liés aux passifs courants.
SELECT SUM(t.montant) AS total_provisions_produits_constates_avance
FROM transaction_financiere t
JOIN get_enfants_recursive('Passif courant') e
    ON t.compte_financier_id = e.id;

