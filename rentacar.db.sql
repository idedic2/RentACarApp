BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "client" (
	"id"	INTEGER,
	"address"	TEXT,
	"telephone"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "user"("id")
);
CREATE TABLE IF NOT EXISTS "admin" (
	"id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "user"("id")
);
CREATE TABLE IF NOT EXISTS "reservation" (
	"id"	INTEGER,
	"vehicle_id"	INTEGER,
	"client_id"	INTEGER,
	"pickup_date"	TEXT,
	"return_date"	TEXT,
	"pickup_time"	TEXT,
	"return_time"	TEXT,
	"card_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("vehicle_id") REFERENCES "vehicle"("id"),
	FOREIGN KEY("client_id") REFERENCES "client"("id"),
	FOREIGN KEY("card_id") REFERENCES "card"("id")
);
CREATE TABLE IF NOT EXISTS "user" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "card" (
	"id"	INTEGER,
	"card_number"	TEXT,
	"code"	TEXT,
	"expiration_date"	TEXT,
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
INSERT INTO "user" ("id","first_name","last_name","email","username","password") VALUES (1,'Irma','Dedic','idedic2@etf.unsa.ba','idedic2','password'),
 (2,'Alem','Zekić','azekic1@gmail.com','azekic1','password'),
 (3,'Maja','Dedić','maja97@gmail.com','maja','password');
INSERT INTO "vehicle" ("id","name","brand","model","type","year","seats_number","doors_number","engine","transmission","fuel_consumption","color","price_per_day","availability") VALUES (2,'GOLF 6','VOLKSWAGEN','GOLF 6 PLUS','Putnicki automobil',2019,5,5,'Dizel','Manuelni',20.0,'Bijela',50.0,'NE'),
 (3,'AUDI A6','AUDI','AUDI A6 S6','Putnicki automobil',2025,5,5,'Dizel','Automatik',20.0,'Bijela',150.0,'NE'),
 (4,'BMW 640d','BMW','BMW 640d Coupe','Luksuzni automobil',2011,5,5,'Benzin','Automatik',20.0,'Plava',100.0,'NE'),
 (5,'OPEL VIVARO','OPEL','OPEL VIVARO SELECTION','Transportno vozilo',2018,8,5,'Dizel','Manuelni',91.0,'Siva metalik',140.0,'NE'),
 (6,'SEAT ARONA','SEAT','SEAT ARONA AUTOMATIC','Putnicki automobil',2018,5,5,'Benzin','Automatik',71.0,'Crvena',85.0,'NE');
COMMIT;
