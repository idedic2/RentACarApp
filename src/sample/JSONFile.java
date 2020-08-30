package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class JSONFile {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Reservation>reservations=new ArrayList<>();
    private ArrayList<Employee>employees=new ArrayList<>();
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public JSONFile() {
    }

    public void setVehicles(ArrayList<Vehicle> drzave) {
        this.vehicles = drzave;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void writeVehicles(File file)  {
        JSONArray jvehicles = new JSONArray();
        for(Vehicle vehicle: vehicles) {
            JSONObject jvehicle = new JSONObject();
            jvehicle.put("name", vehicle.getName());
            jvehicle.put("brand", vehicle.getBrand());
            jvehicle.put("model", vehicle.getModel());
            jvehicle.put("type", vehicle.getType());
            jvehicle.put("year", vehicle.getYear());
            jvehicle.put("seatsNumber", vehicle.getSeatsNumber());
            jvehicle.put("doorsNumber", vehicle.getDoorsNumber());
            jvehicle.put("engine", vehicle.getEngine());
            jvehicle.put("transmission", vehicle.getTransmission());
            jvehicle.put("fuelConsumption", vehicle.getFuelConsumption());
            jvehicle.put("color", vehicle.getColor());
            jvehicle.put("pricePerDay", vehicle.getPricePerDay());
            jvehicle.put("availability", vehicle.getAvailability());
            jvehicle.put("image", vehicle.getImage());
            jvehicles.put(jvehicle);
        }
        try {
            Files.write(file.toPath(), Arrays.asList(jvehicles.toString()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }
    }

    public void writeClients(File file)  {
        JSONArray jclients = new JSONArray();
        for(Client client : clients) {
            JSONObject jclient = new JSONObject();
            jclient.put("firstName", client.getFirstName());
            jclient.put("lastName", client.getLastName());
            jclient.put("email", client.getEmail());
            jclient.put("username", client.getUsername());
            jclient.put("address", client.getAddress());
            jclient.put("telephone", client.getTelephone());
            JSONArray jreservations = new JSONArray();
            for (Reservation reservation: reservations) {
                if (reservation.getClient().getId()== client.getId()) {
                    JSONObject jreservation = new JSONObject();
                    jreservation.put("vehicleName", reservation.getVehicle().getName());
                    jreservation.put("pickupDate", reservation.getPickUpDate());
                    jreservation.put("returnDate", reservation.getReturnDate());
                    jreservation.put("pickupTime", reservation.getPickupTime());
                    jreservation.put("returnTime", reservation.getReturnTime());
                    if (reservation.getCard()!=null)
                        jreservation.put("payingOnline", true);
                    jreservations.put(jreservation);
                }
            }
            jclient.put("reservations", jreservations);
            jclients.put(jclient);
        }
        try {
            Files.write(file.toPath(), Arrays.asList(jclients.toString()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }
    }

    public void writeReservations(File file)  {
        JSONArray jreservations = new JSONArray();
        for(Reservation reservation : reservations) {
            JSONObject jreservation = new JSONObject();
            jreservation.put("nameVehicle", reservation.getVehicle().getName());
            jreservation.put("modelVehicle", reservation.getVehicle().getModel());
            jreservation.put("firstName", reservation.getClient().getFirstName());
            jreservation.put("lastName", reservation.getClient().getLastName());
            jreservation.put("username", reservation.getClient().getUsername());
            jreservation.put("addresss", reservation.getClient().getAddress());
            jreservation.put("telephone", reservation.getClient().getTelephone());
            jreservation.put("pickupDate", reservation.getPickUpDate());
            jreservation.put("returnDate", reservation.getReturnDate());
            jreservation.put("pickupTime", reservation.getPickupTime());
            jreservation.put("returnTime", reservation.getReturnTime());
            if (reservation.getCard()!=null)
                jreservation.put("payingOnline", true);

            jreservations.put(jreservation);
        }
        try {
            Files.write(file.toPath(), Arrays.asList(jreservations.toString()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }
    }

    public void writeEmployees(File file)  {
        JSONArray jemployees = new JSONArray();
        for(Employee employee : employees) {
            JSONObject jemployee = new JSONObject();
            jemployee.put("firstName", employee.getFirstName());
            jemployee.put("lastName", employee.getLastName());
            jemployee.put("email", employee.getEmail());
            jemployee.put("username", employee.getUsername());
            jemployees.put(jemployee);
        }
        try {
            Files.write(file.toPath(), Arrays.asList(jemployees.toString()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }
    }
}
