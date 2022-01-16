INSERT INTO mission (id,date,description,name)
VALUES (1,'2022-01-06','ramasser tous les sacs','Vider les poubelles'),
       (2,'2022-01-06','Charmer les clients','Charme');

INSERT INTO expense_bill(id,amount,date,description,name,state)
VALUES (1,0,'2022-01-06', 'Note de frais de Janvier', 'Janvier',1),
       (2,0,'2022-02-06', 'Note de frais de Février', 'Février',0),
       (3,0,'2021-12-06', 'Note de frais de Décembre', 'Décembre',2);

INSERT INTO line_bill (id,amount,country,date,justificatif,tva,tva_percent,is_validated, id_expense_bill,description)
VALUES (1,500, 'France','2022-12-06',null,12,2.4,TRUE, 2, 'Invitation des clients au restaurant'),
       (2,300, 'France','2022-01-06',null,12,2.4,FALSE, 1,'Achat d un robot netoyant pour vider les poubelles automatiquement');


INSERT INTO line_in_expense_bill(expense_id,line_id)
VALUES (1,2),
       (2,1);

INSERT INTO line_for_mission(mission_id,line_id)
VALUES (1,2),
       (2,1);


