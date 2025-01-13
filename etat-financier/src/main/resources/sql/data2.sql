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

('Capitaux propres', 3), -- 5
('Stock', 1); -- 1


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
('Stocks de produits', 1, 67), -- Actif courant -> parent_id 1
('Stocks de marchandises', 1, 67), -- Actif courant -> parent_id 1
('Stocks à l''extérieur', 1, 67), -- Actif courant -> parent_id 1
('Pertes de valeur sur stocks et en cours', 1, 67); -- Actif courant -> parent_id 1

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



-- Transaction 1
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(1, 1000000.00, 'Achat de matières premières', '2025-01-01'); -- Matières premières et fournitures

-- Transaction 2
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(2, 5000000.00, 'Acquisition d''immobilisations corporelles', '2025-01-02'); -- Immobilisations corporelles

-- Transaction 3
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(3, 2000000.00, 'Emprunt contracté', '2025-01-03'); -- Emprunts et dettes assimilés

-- Transaction 4
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(4, 1500000.00, 'Frais d''études de marché', '2025-01-04'); -- Services extérieurs

-- Transaction 5
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(5, 3000000.00, 'Achats de marchandises', '2025-01-05'); -- Achats consommés

-- Transaction 6
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(6, 1200000.00, 'Vente de produits fabriqués', '2025-01-06'); -- Ventes de produits fabriqués, marchandises, prestations

-- Transaction 7
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(7, 800000.00, 'Vente de produits stockés', '2025-01-07'); -- Production stockée (ou destockage)

-- Transaction 8
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(8, 2500000.00, 'Subvention d''exploitation reçue', '2025-01-08'); -- Subventions d''exploitation

-- Transaction 9
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(9, 1500000.00, 'Réglement des charges de personnel', '2025-01-09'); -- Charges de personnel

-- Transaction 10
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(10, 1000000.00, 'Amortissement des immobilisations', '2025-01-10'); -- Dotations aux amortissements, provisions, pertes de valeur

-- Transaction 11
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(11, 200000.00, 'Vente de marchandises', '2025-01-11'); -- Autres produits opérationnels

-- Transaction 12
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(12, 1300000.00, 'Pertes sur stocks', '2025-01-12'); -- Pertes de valeur sur stocks et en cours

-- Transaction 13
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(13, 500000.00, 'Réglement des dettes fournisseurs', '2025-01-13'); -- Fournisseurs et comptes rattachés

-- Transaction 14
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(14, 600000.00, 'Réglement des comptes de liaison des établissements', '2025-01-14'); -- Comptes de liaison des établissements et sociétés en participation

-- Transaction 15
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(15, 700000.00, 'Réglement des dettes rattachées à des participations', '2025-01-15'); -- Dettes rattachées à des participations

-- Transaction 16
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(16, 300000.00, 'Avances et accréditifs reçus', '2025-01-16'); -- Régies d''avances et accréditifs

-- Transaction 17
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(17, 950000.00, 'Versement de produits financiers', '2025-01-17'); -- Produits financiers

-- Transaction 18
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(18, 1200000.00, 'Frais financiers payés', '2025-01-18'); -- Charges financières

-- Transaction 19
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(19, 1000000.00, 'Vente d''immobilisations corporelles', '2025-01-19'); -- Immobilisations corporelles

-- Transaction 20
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(20, 800000.00, 'Vente d''immobilisations incorporelles', '2025-01-20'); -- Immobilisations incorporelles

-- Transaction 21
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(21, 400000.00, 'Perte de valeur sur une immobilisation', '2025-01-21'); -- Perte de valeur sur immobilisations

-- Transaction 22
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(22, 500000.00, 'Achat d''autres immobilisations financières', '2025-01-22'); -- Autres immobilisations financières

-- Transaction 23
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(23, 300000.00, 'Vente de titres de participation', '2025-01-23'); -- Participations et créances rattachées à des participations

-- Transaction 24
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(24, 1200000.00, 'Réglement des dettes rattachées à des participations', '2025-01-24'); -- Emprunts et dettes assimilés

-- Transaction 25
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(25, 800000.00, 'Achat de matières premières', '2025-01-25'); -- Matières premières et fournitures

-- Transaction 26
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(26, 950000.00, 'Vente de produits fabriqués', '2025-01-26'); -- Ventes de produits fabriqués, marchandises, prestations

-- Transaction 27
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(27, 200000.00, 'Vente de produits finis', '2025-01-27'); -- Production immobilisée

-- Transaction 28
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(28, 1500000.00, 'Réglement des dettes fournisseurs', '2025-01-28'); -- Fournisseurs et comptes rattachés

-- Transaction 29
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(29, 700000.00, 'Caisse reçue', '2025-01-29'); -- Caisse

-- Transaction 30
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(30, 1200000.00, 'Charges exceptionnelles', '2025-01-30'); -- Elements extraordinaires (charges)

-- Transaction 31
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(31, 800000.00, 'Vente de stocks de produits', '2025-02-01'); -- Stocks de produits

-- Transaction 32
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(32, 600000.00, 'Production de biens destinés à la vente', '2025-02-02'); -- En cours de production de biens

-- Transaction 33
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(33, 500000.00, 'Services rendus', '2025-02-03'); -- En cours de production de services

-- Transaction 34
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(34, 900000.00, 'Subventions d''exploitation', '2025-02-04'); -- Subventions d''exploitation

-- Transaction 35
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(35, 1000000.00, 'Vente de produits immobilisés', '2025-02-05'); -- Production immobilisée

-- Transaction 36
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(36, 800000.00, 'Achat de services extérieurs', '2025-02-06'); -- Services extérieurs

-- Transaction 37
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(37, 1100000.00, 'Rémunération du personnel', '2025-02-07'); -- Charges de personnel

-- Transaction 38
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(38, 500000.00, 'Vente de titres de participation', '2025-02-08'); -- Valeurs mobilières de placement

-- Transaction 39
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(39, 700000.00, 'Pertes de valeur sur immobilisations', '2025-02-09'); -- Perte de valeur sur immobilisations

-- Transaction 40
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(40, 600000.00, 'Dépréciation des créances', '2025-02-10'); -- Débiteurs divers et créditeurs divers

-- Transaction 41
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(41, 1200000.00, 'Perte sur créances irrécouvrables', '2025-02-11'); -- Pertes de valeur sur comptes de tiers

-- Transaction 42
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(42, 800000.00, 'Réglement des comptes clients', '2025-02-12'); -- Clients et comptes rattachés

-- Transaction 43
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(43, 1000000.00, 'Réglement des dettes fiscales', '2025-02-13'); -- Etat, collectivités publiques, organismes internationaux

-- Transaction 44
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(44, 1200000.00, 'Acquisition d''immobilisations en cours', '2025-02-14'); -- Immobilisations en cours

-- Transaction 45
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(45, 1500000.00, 'Vente de produits extraordinaires', '2025-02-15'); -- Elements extraordinaires (produits)

-- Transaction 46
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(46, 400000.00, 'Production de biens et services', '2025-02-16'); -- Production immobilisée

-- Transaction 47
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(47, 800000.00, 'Charges exceptionnelles sur produits', '2025-02-17'); -- Elements extraordinaires (charges)

-- Transaction 48
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(48, 600000.00, 'Retrait des produits financiers', '2025-02-18'); -- Produits financiers

-- Transaction 49
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(49, 1000000.00, 'Réglement des dettes passées', '2025-02-19'); -- Dettes rattachées à des participations

-- Transaction 50
INSERT INTO transaction_financiere (compte_financier_id, montant, description, date_transaction) VALUES
(50, 800000.00, 'Vente de produits commerciaux', '2025-02-20'); -- Autres services extérieurs
