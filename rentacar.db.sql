BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "card" (
	"id"	INTEGER,
	"card_number"	TEXT,
	"code"	TEXT,
	"expiration_date"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "reservation" (
	"id"	INTEGER,
	"vehicle_id"	INTEGER,
	"user_id"	INTEGER,
	"pickup_date"	TEXT,
	"return_date"	TEXT,
	"pickup_time"	TEXT,
	"return_time"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("vehicle_id") REFERENCES "vehicle"("id"),
	FOREIGN KEY("user_id") REFERENCES "user"("id")
);
CREATE TABLE IF NOT EXISTS "client" (
	"id"	INTEGER,
	"address"	TEXT,
	"telephone"	TEXT,
	"id_card"	INTEGER,
	FOREIGN KEY("id_card") REFERENCES "card"("id")
);
CREATE TABLE IF NOT EXISTS "admin" (
	"id"	INTEGER,
	FOREIGN KEY("id") REFERENCES "user"("id")
);
CREATE TABLE IF NOT EXISTS "user" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"type_user"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "vehicle" (
	"id"	INTEGER,
	"name"	TEXT,
	"brand"	TEXT,
	"model"	TEXT,
	"type"	TEXT,
	"year"	INTEGER,
	"seats_number"	INTEGER,
	"doors_number"	INTEGER,
	"engine"	TEXT,
	"transmission"	TEXT,
	"fuel_consumption"	REAL,
	"color"	TEXT,
	"price_per_day"	REAL,
	"availability"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "card" ("id","card_number","code","expiration_date") VALUES (1,'343796895302403','2403','10/2020');
INSERT INTO "reservation" ("id","vehicle_id","user_id","pickup_date","return_date","pickup_time","return_time") VALUES (1,3,1,'15/10/2020','20/10/2020','10:10','15:10');
INSERT INTO "client" ("id","address","telephone","id_card") VALUES (1,'Paromlinska 12','062345997',1);
INSERT INTO "admin" ("id") VALUES (1);
INSERT INTO "user" ("id","first_name","last_name","email","username","password","type_user") VALUES (1,'Irma','Dedic','idedic2@etf.unsa.ba','idedic2','password',1),
 (2,'Alem','Zekić','azekic1@gmail.com','azekic1','password',2),
 (3,'Maja','Dedić','maja97@gmail.com','maja','password',2);
INSERT INTO "vehicle" ("id","name","brand","model","type","year","seats_number","doors_number","engine","transmission","fuel_consumption","color","price_per_day","availability") VALUES (2,'GOLF 6','VOLKSWAGEN','GOLF 6 PLUS',NULL,2019,5,5,'Dizel','Manuelni',20.0,'Bijela',50.0,'DA'),
 (3,'AUDI A6','AUDI','AUDI A6 S6',NULL,2017,5,5,'Dizel','Automatik',20.0,'Bijela',140.0,'DA'),
 (4,'BMW 640d','BMW','BMW 640d Coupe','Luksuzni automobil',2013,5,5,'Benzin','Automatik',20.0,'Crna',100.0,'NE'),
 (5,'OPEL VIVARO','OPEL','OPEL VIVARO SELECTION','Transportno vozilo',2018,8,5,'Dizel','Manuelni',91.0,'Siva metalik',140.0,'DA'),
 (6,'SEAT ARONA','SEAT','SEAT ARONA AUTOMATIC','Putnicki automobil',2018,5,5,'Benzin','Automatik',71.0,'Crvena',85.0,'DA');
COMMIT;
