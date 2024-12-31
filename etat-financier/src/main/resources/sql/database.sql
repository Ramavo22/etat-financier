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
