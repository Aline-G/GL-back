INSERT INTO mission (id,date,description,name)
VALUES (1,'2022-01-06','ramasser tous les sacs','Vider les poubelles'),
       (2,'2022-01-06','Charmer les clients','Charme');

INSERT INTO expense_bill(id,amount,date,description,name,state)
VALUES (1,300,'2022-01', 'Note de frais de Janvier', 'Janvier',0),
       (2,500,'2022-02', 'Note de frais de Février', 'Février',0),
       (3,0,'2021-12', 'Note de frais de Décembre', 'Décembre',0);

INSERT INTO line_bill (id,amount,category,country,date,justificatif,tva,tva_percent,is_validated, id_expense_bill,description,km,resto_place, hebergement_place, vehicle, guests_name)
VALUES (1,500,0,'France','2022-12-06',null,12,2.4,TRUE, 2, 'Invitation des clients au restaurant',0, 'mc DO', '', '','jean dubois'),
       (2,300,3,'France','2022-01-06',null,12,2.4,FALSE, 1,'Achat d un robot netoyant pour vider les poubelles automatiquement', 0,'', '','','');

INSERT INTO advance (id,amount,description,name,state)
VALUES (1,1000,'Pour payer des vacances pour les clients','vacances',0),
       (2,300,'pour payer des femmes de ménage','menage',1);

INSERT INTO line_in_expense_bill(expense_id,line_id)
VALUES (1,2),
       (2,1);

INSERT INTO line_for_mission(mission_id,line_id)
VALUES (1,2),
       (2,1);

INSERT INTO advance_for_mission(mission_id,advance_id)
VALUES (1,2),
       (2,1);



