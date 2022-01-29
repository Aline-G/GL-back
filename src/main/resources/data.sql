INSERT INTO mission (id,name,date_begining,date_ending,state,description)
VALUES (1,'Vider les poubelles','2022-01-06','2022-07-12',0,'ramasser tous les sacs'),
       (2,'Charme','2022-01-06','2022-08-24',0,'Charmer les clients');

INSERT INTO expense_bill(id,amount,date,description,name,state)
VALUES (1,300,'2022-01', 'Note de frais de Janvier', 'Janvier',0),
       (2,500,'2022-02', 'Note de frais de Février', 'Février',0),
       (3,0,'2021-12', 'Note de frais de Décembre', 'Décembre',0);

INSERT INTO line_bill (id,amount,amount_without_taxes,category,conveyance,country,date,description, fiscal_horsepower,guests_name,hebergement_place,id_expense_bill, is_validated, justificatif,km ,payment_method, registration_number,resto_place,tva,vehicle)
VALUES (1,500, 488,0,'','France','2022-12-06','Invitation des clients au restaurant',0,'jean dubois','',1,TRUE,null,0,'','','Paris', 12, ''),
       (2,300,250,4,'','France','2022-01-06','Achat d un robot netoyant pour vider les poubelles automatiquement',0,'','',2,FALSE ,null,0,'Carte','','', 50, '');

INSERT INTO advance (id,amount,date,description,name,state)
VALUES (1,1000,'2022-01-15','Pour payer des vacances pour les clients','vacances',0),
       (2,300,'2022-01-03','pour payer des femmes de ménage','menage',1);

INSERT INTO line_in_expense_bill(expense_id,line_id)
VALUES (1,2),
       (2,1);

INSERT INTO line_for_mission(mission_id,line_id)
VALUES (1,2),
       (2,1);

INSERT INTO advance_for_mission(mission_id,advance_id)
VALUES (1,2),
       (2,1);



