BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "card" (
	"id"	INTEGER,
	"card_number"	TEXT,
	"code"	TEXT,
	"expiration_date"	TEXT,
	PRIMARY KEY("id")
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
CREATE TABLE IF NOT EXISTS "client" (
	"id"	INTEGER,
	"address"	TEXT,
	"telephone"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "user"("id")
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
	"image"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "employee" (
	"id"	INTEGER,
	"admin"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "user"("id")
);
INSERT INTO "card" ("id","card_number","code","expiration_date") VALUES (0,'1234567789534555','1234','JANUARY/2022'),
 (1,'1234567891234566','342','JANUARY/2022'),
 (2,'1234567891234567','123','31/JANUARY/2021'),
 (3,'1234567123456784','2222','31/1/2023');
INSERT INTO "user" ("id","first_name","last_name","email","username","password") VALUES (1,'Irma','Dedic','idedic2@etf.unsa.ba','idedic2','password'),
 (2,'Zaposlenik','Zaposlenikovic','zaposlenik@gmail.com','zaposlenik','password'),
 (3,'Klijent','Klijentijevic','klijent@gmail.com','klijent','password');
INSERT INTO "client" ("id","address","telephone") VALUES (3,'Klijentova adresa','062333457');
INSERT INTO "employee" ("id","admin") VALUES (1,'yes'),
 (2,'no');
COMMIT;