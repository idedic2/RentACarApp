BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "employee" (
	"id"	INTEGER,
	"admin"	TEXT,
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
CREATE TABLE IF NOT EXISTS "client" (
	"id"	INTEGER,
	"address"	TEXT,
	"telephone"	TEXT,
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
INSERT INTO "employee" ("id","admin") VALUES (1,'yes'),
 (2,'no');
INSERT INTO "vehicle" ("id","name","brand","model","type","year","seats_number","doors_number","engine","transmission","fuel_consumption","color","price_per_day","availability","image") VALUES (0,'Audi A6 Quattro','Audi','A6 Quattro','Luksuzni automobil',2018,5,5,'Dizel','Automatik',20.0,'Plava',140.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/audiA6Vehicle.png'),
 (1,'Mercedes Eclass','Mercedes','Eclass','Luksuzni automobil',2019,5,5,'Dizel','Automatik',30.0,'Crna',140.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/mercedesEclassVehicle.png'),
 (2,'Ford Custom','Ford','Custom 2.0','Transportno vozilo',2017,8,4,'Benzin','Automatik',30.0,'Siva',120.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/fordCustomVehicle.png'),
 (3,'Škoda Fabia','Škoda','Fabia 1.2','Putnicki automobil',2017,5,5,'Benzin','Automatik',12.0,'Plava',50.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/skodaFabiaVehicle.png'),
 (4,'Opel Vivaro','Opel','Vivaro 1.6','Transportno vozilo',2019,8,4,'Dizel','Manuelni',30.0,'Siva',120.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/opelVivaroVehicle.png'),
 (5,'Seat Leon','Seat','Leon 1.2 Turbo','Putnicki automobil',2018,5,5,'Dizel','Manuelni',10.0,'Bijela',80.0,'DA','file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/seatLeonVehicle.png');
INSERT INTO "client" ("id","address","telephone") VALUES (3,'Klijentova adresa','062333457');
INSERT INTO "user" ("id","first_name","last_name","email","username","password") VALUES (1,'Irma','Dedic','idedic2@etf.unsa.ba','idedic2','password'),
 (2,'Zaposlenik','Zaposlenikovic','zaposlenik@gmail.com','zaposlenik','password'),
 (3,'Klijent','Klijentijevic','klijent@gmail.com','klijent','password');
COMMIT;
