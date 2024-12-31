INSERT INTO type_compte (nom) VALUES 
('Actif'),
('Passif'),
('Capitaux propres'),
('Produits'),
('Charges');



INSERT INTO compte_financier (nom, type_compte,parent_id) VALUES 
('Actifs courants', 1, NULL), -- 1
('Actifs Non courant', 1, NULL), -- 2

('Passif courant', 2, NULL), -- 3
('Passif Non courant', 2, NULL), -- 4

('Capitaux propres', 3, NULL), -- 5

('Produits d''exploitation', 4, NULL), -- 6
('Produits financiers', 4, NULL), -- 7
('Produits exceptionnels', 4, NULL), -- 8

('Charges d''exploitation', 5, NULL), -- 9
('Charges financières', 5, NULL), -- 10
('Charges exceptionnelles', 5, NULL), -- 11

-- Actifs courants
('Stock', 1, 1), -- 12
('Creances clients', 1, 1), -- 13
('Tresorerie', 1, 1), -- 14

-- Actifs non courants
('Immobilisations corporelles', 1, 2), -- 15
('Immobilisations incorporelles', 1, 2), -- 16
('Immobilisations financieres', 1, 2), -- 17
('Investissement immobiliers', 1, 2), -- 18

-- Passif courant
('Dettes fournisseurs', 2, 3), -- 19
('Charge a payer', 2, 3), -- 20
('Emprunt à court terme', 2, 3), -- 21

-- Passif non courant
('Emprunts et dettes a long terme', 2, 4), -- 22
('provisions à long terme', 2, 4), -- 23
('Passif d''impot différé', 2, 4), -- 24

-- produits d'exploitation
('Chiffre d''affaire', 4, 6), -- 25

-- produits financiers
('Interets reçus', 4, 7), -- 26

-- charges d'exploitation
('Achats consommés', 5, 9), -- 27
('Charge de personnels', 5, 10), -- 28
('Dotations aux amortissements', 5, 11), -- 29
('Dotations aux provisions', 5, 11), -- 30
('Charges externes', 5, 11), -- 31

-- charges financières
('Interets sur emprunts', 5, 12), -- 32
('Perte de change', 5, 12), -- 33
('Perte de valeur sur actifs financiers', 5, 12), -- 34

-- charge exceptionnelles
('Perte non récurrente', 5, 13); -- 35


-- Stock
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(12, 50000.00, 'Achat de médicaments et produits pharmaceutiques', '2024-01-15'),
(12, 7000.00, 'Approvisionnement en matériel médical', '2024-02-10'),
(12, 43000.00, 'Sortie de stock suite à des ventes', '2024-12-31');

-- Créances clients
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(13, 15000.00, 'Créances générées par des ventes à crédit', '2024-04-10'),
(13, 5000.00, 'Règlement d''un client pour une facture', '2024-05-15');

-- Trésorerie
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(14, 20000.00, 'Encaissement des ventes', '2024-03-15'),
(14, 13000.00, 'Paiement de fournisseurs et charges sociales', '2024-06-10');

-- Immobilisations corporelles
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(15, 100000.00, 'Achat d''équipements médicaux', '2024-01-10'),
(15, 20000.00, 'Amortissement des équipements médicaux', '2024-12-31');

-- Immobilisations incorporelles
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(16, 30000.00, 'Acquisition de logiciels de gestion', '2024-03-15'),
(16, 5000.00, 'Amortissement des logiciels de gestion', '2024-12-31');

-- Dettes fournisseurs
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(19, 8000.00, 'Facture fournisseur pour des médicaments', '2024-01-20'),
(19, 5000.00, 'Règlement partiel à un fournisseur', '2024-02-15');

-- Emprunts et dettes à long terme
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(22, 50000.00, 'Emprunt bancaire à long terme contracté', '2024-02-01'),
(22, 10000.00, 'Remboursement partiel de l''emprunt', '2024-06-30'),
(22, 90000.00, 'Emprunt à un particulier','2024-07-01');


-- Provisions à long terme
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(23, 20000.00, 'Provision pour litiges à long terme', '2024-04-20'),
(23, 5000.00, 'Annulation partielle de la provision', '2024-09-15');


INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(5, 100000.00, 'Apport initial des propriétaires', '2024-01-01'),
(5, 20000.00, 'Bénéfice net de l''année réinvesti', '2024-12-31');



-- Charges d'exploitation : Achats consommés
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(27, 15000.00, 'Achats de médicaments', '2024-02-25'),
(27, 7000.00, 'Achats de matériel médical', '2024-04-10');

-- Charges d'exploitation : Autres charges (exemple : charges sociales)
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(28, 3000.00, 'Paiement des charges sociales', '2024-06-10');


-- Produits : Chiffre d'affaires
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction)
VALUES
(25, 50000.00, 'Ventes de médicaments et produits pharmaceutiques', '2024-03-30'),
(25, 20000.00, 'Ventes de matériel médical', '2024-06-15');



