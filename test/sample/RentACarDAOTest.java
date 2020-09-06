package sample;

import net.sf.jasperreports.components.barbecue.BarcodeProviders;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RentACarDAOTest {

    @Test
    void getVehicles() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        ArrayList<Vehicle> vehicles = dao.getVehicles();
        assertEquals(6, vehicles.size());
    }

    @Test
    void addVehicle() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        File folder = new File("resources/images");
        File[] listOfFiles = folder.listFiles();
        String url = "";
        //System.out.println(listOfFiles.length);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].toURI().toString().contains("audiA6"))
                url = listOfFiles[i].toURI().toString();
            break;
        }
        Vehicle vehicle = new Vehicle();
        try {
            vehicle = new Vehicle(-5, "Audi A6", "Audi", "A6 Quattro", "Luksuzni automobil", 2019, 5, 5, "Automatik", "Benzin", 12.5, "Plava", 140.0, "DA", url);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle = new Vehicle(0, "Audi A6", "Audi", "A6 Quattro", "Luksuzni automobil", LocalDate.now().getYear()+5, 5, 5, "Automatik", "Benzin", 12.5, "Plava", 140.0, "DA", url);
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Godina proizvodnje ne smije biti iz budućnosti", e.getMessage());
        }
        try {
            vehicle = new Vehicle(0, "Audi A6", "Audi", "A6 Quattro", "Luksuzni automobil", 2019, 5, 5, "Automatik", "Benzin", 12.5, "Plava", 140.0, "DA", url);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addVehicle(vehicle);
        Vehicle newVehicle = dao.getVehiclePerId(6);
        assertEquals(7, dao.getVehicles().size());
        assertEquals("Audi A6", newVehicle.getName());
    }


    @Test
    void editVehicle() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Vehicle vehicle = dao.getVehiclePerId(2);
        assertEquals("Ford Custom", vehicle.getName());
        vehicle.setName("Ford Custom 2.0");
        dao.editVehicle(vehicle);
        assertEquals("Ford Custom 2.0", vehicle.getName());
    }
    @Test
    void editVehicle2() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Vehicle vehicle = dao.getVehiclePerId(2);
        try {
            vehicle.setId(-6);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setYear(-2019);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setFuelConsumption(-10.5);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setPricePerDay(-100.5);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setSeatsNumber(-5);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setDoorsNumber(-5);
        } catch (NegativeNumberException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            vehicle.setYear(LocalDate.now().getYear()+5);
        } catch (IllegalArgumentException | NegativeNumberException e) {
            assertEquals("Godina proizvodnje ne smije biti iz budućnosti", e.getMessage());
        }
        assertEquals("Ford Custom", vehicle.getName());
        vehicle.setName("Ford Custom 2.0");
        dao.editVehicle(vehicle);
        assertEquals("Ford Custom 2.0", vehicle.getName());
    }

    @Test
    void deleteVehicle() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        File folder = new File("resources/images");
        File[] listOfFiles = folder.listFiles();
        String url = "";
        //System.out.println(listOfFiles.length);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].toURI().toString().contains("audiA6"))
                url = listOfFiles[i].toURI().toString();
            break;
        }
        Vehicle vehicle = null;
        try {
            vehicle = new Vehicle(0, "Audi A6", "Audi", "A6 Quattro", "Luksuzni automobil", 2019, 5, 5, "Automatik", "Benzin", 12.5, "Plava", 140.0, "DA", url);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addVehicle(vehicle);
        Vehicle newVehicle = dao.getVehiclePerId(6);
        dao.deleteVehicle(newVehicle);
        assertEquals(6, dao.getVehicles().size());
    }

    @Test
    void getVehiclesPerType() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        ArrayList<Vehicle> vehicles = dao.getVehiclesPerType("Luksuzni automobil");
        assertEquals(2, vehicles.size());
        assertEquals("Audi A6 Quattro", vehicles.get(0).getName());
        assertEquals("Mercedes Eclass", vehicles.get(1).getName());
    }

    @Test
    void getVehiclePerId() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Vehicle vehicle = dao.getVehiclePerId(2);
        assertEquals("Ford Custom", vehicle.getName());
    }

    @Test
    void deleteEmployee() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getEmployees().size());
        Employee employee = new Employee();
        employee = dao.getEmployeePerUsername("zaposlenik");
        dao.deleteEmployee(employee);
        assertEquals(0, dao.getEmployees().size());
    }

    @Test
    void getEmployees() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getEmployees().size());
    }

    @Test
    void addEmployee() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getEmployees().size());
        Employee employee = null;
        try {
            employee = new Employee(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "no");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addEmployee(employee);
        assertEquals(2, dao.getEmployees().size());
    }

    @Test
    void editEmployee() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Employee employee = dao.getEmployeePerUsername("zaposlenik");
        assertEquals("Zaposlenik", employee.getFirstName());
        employee.setFirstName("Employee");
        dao.editEmployee(employee);
        assertEquals("Employee", employee.getFirstName());
    }

    @Test
    void deleteClient() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getClients().size());
        Client client = dao.getClientPerUsername("klijent");
        dao.deleteClient(client);
        assertEquals(0, dao.getClients().size());
    }

    @Test
    void getClients() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getClients().size());
    }

    @Test
    void addClient() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getClients().size());
        Client client = new Client();
        try {
            client = new Client(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "Adresa", "024524567");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addClient(client);
        assertEquals(2, dao.getClients().size());
    }

    @Test
    void editClient() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client = dao.getClientPerUsername("klijent");
        assertEquals("Klijent", client.getFirstName());
        client.setFirstName("Client");
        client.setAddress("nova");
        client.setTelephone("033243556");
        dao.editClient(client);
        assertEquals("Client", client.getFirstName());
    }

    @Test
    void getClientPerId() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client = null;
        try {
            client = new Client(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "Adresa", "024524567");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addClient(client);
        ArrayList<Client> clients = dao.getClients();
        Client client1 = clients.get(1);
        Client client2 = dao.getClientPerId(client1.getId());
        assertEquals("Novi", client2.getFirstName());
    }

    @Test
    void getUserPerUsername() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client = null;
        try {
            client = new Client(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "Adresa", "024524567");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addClient(client);
        assertTrue(dao.getUserPerUsername("novi"));
    }

    @Test
    void doesExistUser() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client = null;
        try {
            client = new Client(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "Adresa", "024524567");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addClient(client);
        assertTrue(dao.doesExistUser("novi", "password", "Klijent"));
    }

    @Test
    void doesExistUser1() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Employee employee = null;
        try {
            employee = new Employee(0, "Novi", "Prezimenković", "novi@gmail.com", "novi", "password", "no");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addEmployee(employee);
        assertTrue(dao.doesExistUser("novi", "password", "Zaposlenik"));
        assertTrue(dao.doesExistUser("idedic2", "password", "Admin"));
    }

    @Test
    void addReservation() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Vehicle vehicle = dao.getVehiclePerId(2);
        assertEquals("Ford Custom", vehicle.getName());
        Client client = dao.getClientPerUsername("klijent");
        Card card = null;
        try {
            card = new Card(0, "1234123412341234", "123", LocalDate.of(2022, 10, 31));
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addCard(card);
        Card card1 = dao.getCard("1234123412341234");
        Reservation reservation = null;
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 20), "10:00", "10:00", card1);
        } catch (NegativeNumberException | InvalidTimeFormatException e) {
            e.printStackTrace();
        }
        dao.addReservation(reservation);
        assertEquals(1, dao.getReservations().size());
    }
    @Test
    void addReservation2() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Vehicle vehicle = dao.getVehiclePerId(2);
        assertEquals("Ford Custom", vehicle.getName());
        Client client = dao.getClientPerUsername("klijent");
        Reservation reservation = new Reservation();
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 20), "greška", "10:00", null);
        } catch (DateTimeException | NegativeNumberException | InvalidTimeFormatException e) {
            assertEquals("Nevalidno vrijeme preuzimanja vozila", e.getMessage());
        }
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 20), "10:00", "greška", null);
        } catch (DateTimeException | NegativeNumberException | InvalidTimeFormatException e) {
            assertEquals("Nevalidno vrijeme vraćanja vozila", e.getMessage());
        }
        try {
            reservation = new Reservation(-6, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 20), "10:00", "10:00", null);
        } catch (DateTimeException | NegativeNumberException | InvalidTimeFormatException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 9, 10), "10:00", "10:00", null);
        } catch (DateTimeException | NegativeNumberException | InvalidTimeFormatException e) {
            assertEquals("Nevalidan format datuma", e.getMessage());
        }
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 5, 10), "10:00", "10:00", null);
        } catch (DateTimeException | InvalidTimeFormatException | NegativeNumberException e) {
            assertEquals("Nevalidan format datuma", e.getMessage());
        }
        try {
            reservation = new Reservation(0, vehicle, client, LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 20), "10:00", "10:00", null);
        } catch (DateTimeException | InvalidTimeFormatException | NegativeNumberException e) {
            e.printStackTrace();
        }
        dao.addReservation(reservation);
        assertEquals(1, dao.getReservations().size());
    }
    @Test
    void deleteCard() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Card card = new Card();
        try {
            card = new Card(-2, "1234123412341234", "123", LocalDate.of(2022, 10, 31));
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
        }
        try {
            card = new Card(0, "123412341", "123", LocalDate.of(2022, 10, 31));
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan broj kartice", e.getMessage());
        }
        try {
            card = new Card(0, "1234123412341234", "12", LocalDate.of(2022, 10, 31));
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan kod", e.getMessage());
        }
        try {
            card = new Card(0, "1234123412341234", "123", LocalDate.of(2019, 10, 31));
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan datum isteka kartice", e.getMessage());
        }
        try {
            card = new Card(0, "1234123412341234", "123", LocalDate.of(2023, 10, 31));
        } catch (NegativeNumberException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        dao.addCard(card);
        Card card1 = dao.getCard("1234123412341234");
       Card card2=dao.getCardPerId(card1.getId());
       assertEquals(card1.getId(), card2.getId());
       dao.deleteCard(card);
       assertNull(dao.getCard("1234123412341234"));
    }
    @Test
    void deleteCard2() {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        Card card = new Card();
        try {
          card.setId(-3);
        } catch (NegativeNumberException | IllegalArgumentException e) {
            assertEquals("Unijeli ste negativan broj", e.getMessage());
            try {
                card.setId(0);
            } catch (NegativeNumberException ex) {
                ex.printStackTrace();
            }
        }
        try {
           card.setCardNumber("123412341234");
        } catch (IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan broj kartice", e.getMessage());
            card.setCardNumber("1234123412341234");
        }
        try {
            card.setCode("123456");
        } catch (IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan kod", e.getMessage());
            card.setCode("123");
        }
        try {
            card.setExpirationDate(LocalDate.of(2020, 5, 31));
        } catch (IllegalArgumentException e) {
            assertEquals("Unijeli ste nevalidan datum isteka kartice", e.getMessage());
            card.setExpirationDate(LocalDate.of(2024, 5, 31));
        }
        dao.addCard(card);
        Card card1 = dao.getCard("1234123412341234");
        Card card2=dao.getCardPerId(card1.getId());
        assertEquals(card1.getId(), card2.getId());
        dao.deleteCard(card);
        assertNull(dao.getCard("1234123412341234"));
    }
}