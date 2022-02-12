SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE advance_in_expense_bill;
TRUNCATE advance_for_mission;
TRUNCATE line_for_mission;
TRUNCATE line_in_expense_bill;
TRUNCATE advance;
TRUNCATE line_bill;
TRUNCATE expense_bill;
TRUNCATE user;
TRUNCATE team;
TRUNCATE mission;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO mission (id, name, date_begining, date_ending, state, description)
VALUES (1, 'Vider les poubelles', '2022-01-06', '2022-07-12', 0, 'ramasser tous les sacs'),
       (2, 'Nouveau client', '2022-01-06', '2022-08-24', 0, 'Signer les contrats des dossiers Dupont');

INSERT INTO team(id, name)
VALUES (1, 'direction'),
       (2, 'accouting'),
       (3, 'r&d'),
       (4, 'makerting');

INSERT INTO user(id, firstname, mail, name, work_team_id)
VALUES (1, 'nono', 'nono.bobo@pouet.xxx', 'bobo', 1),
       (2, 'jojo', 'jojo.lastico@pouet.xxx', 'lastico', 1),
       (3, 'john', 'john.fitz.gerald.ken.eddy@gmail.com', 'fitz gerlad ken eddy', 2),
       (4, 'm-c', 'mch@eco.fr', 'h', 2);

INSERT INTO expense_bill(id, amount, date, state, manager_id, user_id)
VALUES (1, 300, '2022-01', 0, 1, 1),
       (2, 500, '2022-02', 0, 1, 3),
       (3, 0, '2021-12', 0, 2, 2);

INSERT INTO line_bill (id, amount, amount_without_taxes, category, conveyance, country, date, description,
                       fiscal_horsepower, guests_name, hebergement_place, id_expense_bill,
                       km, payment_method, registration_number, resto_place, tva, vehicle, state, supporting_documents)
VALUES (1, 500, 488, 0, '', 'France', '2022-12-06', 'Invitation des clients au restaurant', 0, 'jean dubois', '', 1,
        0, '', '', 'Paris', 12, '', 0, ''),
       (2, 300, 250, 4, '', 'France', '2022-01-06',
        'Achat d un robot netoyant pour vider les poubelles automatiquement', 0, 'jojo', '', 2, 0, 'Carte', '',
        '', 50, '', 0, '');

INSERT INTO advance (id, amount, date, description, name, state, user_id)
VALUES (1, 1000, '2022-01-15', 'Pour payer des vacances pour les clients', 'vacances', 0, 1),
       (2, 300, '2022-01-03', 'pour payer des femmes de ménage', 'menage', 1, 3),
       (3, 300, '2022-01-03', 'pour payer des services', 'menage', 2, 2);

INSERT INTO line_in_expense_bill(expense_id, line_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO line_for_mission(mission_id, line_id)
VALUES (1, 2),
       (2, 1);

INSERT INTO advance_for_mission(mission_id, advance_id)
VALUES (1, 2),
       (2, 1),
       (1, 3);

INSERT INTO advance_in_expense_bill(expense_id, advance_id)
VALUES (1, 3);