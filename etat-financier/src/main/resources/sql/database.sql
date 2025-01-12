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
JOIN get_enfants_recursive('Immobilisations incorporelles') e
    ON t.compte_financier_id = e.id;

Créances et emplois assimilés : Somme des comptes de créances (clients, autres débiteurs).
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations incorporelles') e
    ON t.compte_financier_id = e.id;

Trésorerie et équivalents de trésorerie : Somme des comptes de trésorerie (banques, caisse).
SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations incorporelles') e
    ON t.compte_financier_id = e.id;

SELECT COALESCE(SUM(t.montant), 0) AS somme
FROM transaction_financiere t
JOIN get_enfants_recursive('Immobilisations incorporelles') e
    ON t.compte_financier_id = e.id;


