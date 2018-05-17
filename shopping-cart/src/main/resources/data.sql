insert into ProductCategory
values
(1,'Paracetamol'),
(2,'Food'),
(3,'Insurance'),
(4,'Electronics');


insert into Product
(PRODUCTID, PRODUCTNAME,PERUNITQTY,PRICE,CATEGORYID)
values
(1,'Paracetamol XYZ 20 Tablets',20,7.00,select CATEGORYID from ProductCategory where categoryName='Paracetamol'),
(2,'Paracetamol XYZ 10 Tablets',10,4.00,select CATEGORYID from ProductCategory where categoryName='Paracetamol'),
(3,'Pork Sausages',1,6.00,select CATEGORYID from ProductCategory where categoryName='Food'),
(4,'Baked Beans',1,1.30,select CATEGORYID from ProductCategory where categoryName='Food'),
(5,'6 eggs pack',6,3.00,select CATEGORYID from ProductCategory where categoryName='Food'),
(6,'12 eggs pack',12,5.40,select CATEGORYID from ProductCategory where categoryName='Food'),
(7,'Loaf of Bread',1,1.40,select CATEGORYID from ProductCategory where categoryName='Food'),
(8,'Microwave Oven',1,57.30,select CATEGORYID from ProductCategory where categoryName='Electronics'),
(9,'TV 25 inch XamZung',1,180.00,select CATEGORYID from ProductCategory where categoryName='Electronics'),
(10,'Insurance per Electric Appliance',1,120.00,select CATEGORYID from ProductCategory where categoryName='Insurance');