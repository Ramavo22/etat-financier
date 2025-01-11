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
