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



INSERT INTO type_compte (nom) VALUES 
('Actif'),
('Passif'),
('Capitaux propres'),
('Produits'),
('Charges');


-- CLASSE Mère
INSERT INTO compte_financier(nom,type_compte) VALUES 
('Actifs courants', 1), -- 1
('Actifs Non courant', 1), -- 2

('Passif courant', 2), -- 3
('Passif Non courant', 2), -- 4

('Capitaux propres', 3); -- 5


-- CLASSE 1 - COMPTES DE CAPITAUX
INSERT INTO compte_financier (nom, type_compte, parent_id) VALUES
('Capital, réserves et assimilés', 3, 5), -- Capitaux propres (parent_id = 5)
('Report à nouveau', 3, 5), -- Capitaux propres (parent_id = 5)
('Résultat de l''exercice', 3, 5), -- Capitaux propres (parent_id = 5)
('Produits et charges différés - hors cycle d''exploitation', 3, 5), -- Capitaux propres (parent_id = 5)
-- Insérer les comptes du passif non courant
('Provisions pour charges - passifs non courants', 2, 4), -- Passif non courant (parent_id = 4)
('Emprunts et dettes assimilés', 2, 4), -- Passif non courant (parent_id = 4)
-- Insérer les comptes du passif courant
('Dettes rattachées à des participations', 2, 3), -- Passif courant (parent_id = 3)
('Comptes de liaison des établissements et sociétés en participation', 2, 3); 

-- Classe 2 - Comptes d'Immobilisations
INSERT INTO compte_financier (nom, type_compte, parent_id) VALUES
('Immobilisations incorporelles', 1, 2), -- Actif non courant -> parent_id 2
('Immobilisations corporelles', 1, 2), -- Actif non courant -> parent_id 2
('Immobilisations mises en concession', 1, 2), -- Actif non courant -> parent_id 2
('Immobilisations en cours', 1, 2), -- Actif non courant -> parent_id 2
('Participations et créances rattachées à des participations', 1, 2), -- Actif non courant -> parent_id 2
('Autres immobilisations financières', 1, 2), -- Actif non courant -> parent_id 2
('Amortissement des immobilisations', 1, 2), -- Actif non courant -> parent_id 2
('Perte de valeur sur immobilisations', 1, 2); -- Actif non courant -> parent_id 2

-- Classe 3 - Comptes Stock
INSERT INTO compte_financier (nom, type_compte, parent_id) VALUES
('Matières premières et fournitures', 1, 1), -- Actif courant -> parent_id 1
('Autres approvisionnements', 1, 1), -- Actif courant -> parent_id 1
('En cours de production de biens', 1, 1), -- Actif courant -> parent_id 1
('En cours de production de services', 1, 1), -- Actif courant -> parent_id 1
('Stocks de produits', 1, 1), -- Actif courant -> parent_id 1
('Stocks de marchandises', 1, 1), -- Actif courant -> parent_id 1
('Stocks à l''extérieur', 1, 1), -- Actif courant -> parent_id 1
('Pertes de valeur sur stocks et en cours', 1, 1); -- Actif courant -> parent_id 1

-- Insérer les comptes de tiers avec les relations parent-enfant
INSERT INTO compte_financier (nom, type_compte, parent_id) VALUES
('Fournisseurs et comptes rattachés', 2, 3), -- Passif courant -> parent_id 3
('Clients et comptes rattachés', 2, 1), -- Actif courant -> parent_id 1
('Personnel et comptes rattachés', 2, 3), -- Passif courant -> parent_id 3
('Organismes sociaux et comptes rattachés', 2, 3), -- Passif courant -> parent_id 3
('Etat, collectivités publiques, organismes internationaux', 2, 3), -- Passif courant -> parent_id 3
('Groupe et Associés Créance', 2, 1), -- Actif courant -> parent_id 1
('Groupe et Associés Dette', 2, 3), -- Passif courant -> parent_id 3
('Débiteurs divers et créditeurs divers', 2, 1), -- Actif courant -> parent_id 1
('Débiteurs divers et créditeurs divers - Passif', 2, 3), -- Passif courant -> parent_id 3
('Comptes transitoires ou d''attente - Actif', 2, 1), -- Actif courant -> parent_id 1
('Comptes transitoires ou d''attente - Passif', 2, 3), -- Passif courant -> parent_id 3
('Charges ou produits constatés d''avance et provisions', 2, 3), -- Passif courant -> parent_id 3
('Pertes de valeur sur comptes de tiers', 2, 1); -- Actif courant -> parent_id 1

-- Insérer les comptes de la classe 5 avec les relations parent-enfant
INSERT INTO compte_financier (nom, type_compte, parent_id) VALUES
('Valeurs mobilières de placement', 1, 1), -- Actif courant -> parent_id 1
('Banques, établissements financiers et assimilés', 1, 1), -- Actif courant -> parent_id 1
('Instruments de trésorerie', 1, 1), -- Actif courant -> parent_id 1
('Caisse', 1, 1), -- Actif courant -> parent_id 1
('Régies d''avances et accréditifs', 1, 1), -- Actif courant -> parent_id 1
('Pertes de valeur sur comptes financiers', 1, 1); -- Actif courant -> parent_id 1


-- Classe 6 - Comptes de Charges (type_compte = 'Charges')
INSERT INTO compte_financier (nom, type_compte) VALUES
('Achats consommés', 5), 
('Services extérieurs', 5), 
('Autres services extérieurs', 5), 
('Impots, taxes et versements assimilés', 5), -- Mère
('Charges de personnel', 5), -- Mère
('Autres charges des activités ordinaires', 5),
('Charges financières', 5),
('Elements extraordinaires (charges)', 5),
('Dotations aux amortissements, provisions, pertes de valeur', 5),
('Impots sur les benefices', 5);

-- Classe 7 - Comptes de Produits (type_compte = 'Produits')
INSERT INTO compte_financier (nom, type_compte) VALUES
('Ventes de produits fabriques, marchandises, prestations', 4),
('Production stockee (ou destockage)', 4),
('Production immobilisee', 4),
('Subventions d''exploitation', 4), 
('Autres produits operationnels', 4), 
('Produits financiers', 4),
('Elements extraordinaires (produits)', 4), 
('Reprises sur provisions et pertes de valeur', 4);
