
INSERT INTO mission (id, name, date_begining, date_ending, state, description)
VALUES (1, 'Rencontre Client', '2022-01-06', '2022-07-12', 0, 'Reunion client a marseille'),
       (2, 'Négociation pour un contrat aux USA ', '2022-01-06', '2022-08-24', 0, 'Signer les contrats des dossiers Dupont');

INSERT INTO expense_bill(id, amount, date, state, user_id)
VALUES (1, 300, '2022-01',0,1),
       (2, 500, '2022-02',0,3),
       (3, 0, '2021-12', 0,2);

INSERT INTO line_bill (id, amount, amount_without_taxes, category, conveyance, country, date, description,
                       fiscal_horsepower, guests_name, hebergement_place, id_expense_bill, state,
                       km, payment_method, registration_number, resto_place, tva, vehicle)
VALUES (1, 500, 488, 0, '', 'France', '2022-12-06', 'Invitation des clients au restaurant', 0, 'jean dubois', '', 1,
        0, 0, '', '', 'Paris', 12, ''),
       (2, 300, 250, 4, '', 'France', '2022-01-06',
        'Billets avion', 0, '', '', 2, 1, 0, 'Carte', '',
        '', 50, '');

INSERT INTO advance (id, amount, date, description, name, state, user_id)
VALUES (1, 1000, '2022-01-15', 'Hebergement sur place', 'Hébergement', 0, 1),
       (2, 300, '2022-01-03', 'billets de train', 'Transport', 1, 3),
       (3, 300, '2022-01-03', 'pour payer les restaurants', 'Nourriture sur place', 2,2);

INSERT INTO user(dtype, id, firstname, mail, name)
VALUES ('User', 1, 'Jeanne', 'jeanne.hottois@gmail.com', 'Hottois'),
       ('User', 2, 'Mathéo', 'matheo.dugue@gmail.com', 'Dugué'),
       ('Manager', 3, 'Léa', 'lea.faivre@gmail.com', 'Faivre'),
       ('Manager', 4, 'Aimé', 'aime.cesaire@gmail.com', 'Césaire');

INSERT INTO line_in_expense_bill(expense_id, line_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO line_for_mission(mission_id, line_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO advance_for_mission(mission_id, advance_id)
VALUES (2, 1),
       (1, 2),
       (2, 3);

INSERT INTO advance_in_expense_bill(expense_id, advance_id)
VALUES (1, 3);