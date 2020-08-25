package sample;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLFile {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Reservation>reservations=new ArrayList<>();

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public XMLFile() {
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void writeVehicles(File file)  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException err) {
            err.printStackTrace();
        }

        Element root = document.createElement("vehicles");
        document.appendChild(root);
        for (Vehicle vehicle : vehicles) {
            Element eVehicle = document.createElement("vehicle");
            if (vehicle.getAvailability().equals("DA")) {
                Attr availabilityAttr = document.createAttribute("availability");
                availabilityAttr.setValue("true");
                eVehicle.setAttributeNode(availabilityAttr);
            }

            Element nameVehicle = document.createElement("name");
            nameVehicle.appendChild(document.createTextNode(vehicle.getName()));
            eVehicle.appendChild(nameVehicle);

            Element brandVehicle = document.createElement("brand");
            brandVehicle.appendChild(document.createTextNode(vehicle.getBrand()));
            eVehicle.appendChild(brandVehicle);

            Element modelVehicle = document.createElement("model");
            modelVehicle.appendChild(document.createTextNode(vehicle.getModel()));
            eVehicle.appendChild(modelVehicle);

            Element typeVehicle = document.createElement("type");
            typeVehicle.appendChild(document.createTextNode(vehicle.getType()));
            eVehicle.appendChild(typeVehicle);

            Element yearVehicle = document.createElement("year");
            yearVehicle.appendChild(document.createTextNode(Integer.toString(vehicle.getYear())));
            eVehicle.appendChild(yearVehicle);

            Element seatsNumber = document.createElement("seatsNumber");
            seatsNumber.appendChild(document.createTextNode(Integer.toString(vehicle.getSeatsNumber())));
            eVehicle.appendChild(seatsNumber);

            Element doorsNumber = document.createElement("doorsNumber");
            doorsNumber.appendChild(document.createTextNode(Integer.toString(vehicle.getDoorsNumber())));
            eVehicle.appendChild(doorsNumber);

            Element engine= document.createElement("engine");
            engine.appendChild(document.createTextNode(vehicle.getEngine()));
            eVehicle.appendChild(engine);

            Element transmission= document.createElement("transmission");
            transmission.appendChild(document.createTextNode(vehicle.getTransmission()));
            eVehicle.appendChild(transmission);

            Element fuelConsumption= document.createElement("fuelConsumption");
            fuelConsumption.appendChild(document.createTextNode(Double.toString(vehicle.getFuelConsumption())));
            eVehicle.appendChild(fuelConsumption);

            Element color= document.createElement("color");
            color.appendChild(document.createTextNode(vehicle.getColor()));
            eVehicle.appendChild(color);

            Element pricePerDay= document.createElement("pricePerDay");
            pricePerDay.appendChild(document.createTextNode(Double.toString(vehicle.getPricePerDay())));
            eVehicle.appendChild(pricePerDay);
            root.appendChild(eVehicle);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch(TransformerException err) {
            err.printStackTrace();
        }
    }

    public void writeClients(File file)  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException err) {
            err.printStackTrace();
        }

        Element root = document.createElement("clients");
        document.appendChild(root);

        for (Client client : clients) {
            Element eClient = document.createElement("client");

            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(client.getFirstName()));
            eClient.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(client.getLastName()));
            eClient.appendChild(lastName);
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(client.getEmail()));
            eClient.appendChild(email);
            Element username = document.createElement("username");
            username.appendChild(document.createTextNode(client.getUsername()));
            eClient.appendChild(username);
            Element address = document.createElement("address");
            address.appendChild(document.createTextNode(client.getAddress()));
            eClient.appendChild(address);
            Element telephone = document.createElement("telephone");
            telephone.appendChild(document.createTextNode(client.getTelephone()));
            eClient.appendChild(telephone);
            /*ArrayList<Reservation>clientReservations=new ArrayList<>();
            for(Reservation r: listReservations){
                if(r.getClient().getUsername().equals(client.getUsername()))
                    clientReservations.add(r);
            }*/
            for (Reservation reservation : reservations) {
                if (client.getUsername().equals(reservation.getClient().getUsername())) {
                    Element eReservation = document.createElement("reservation");
                    if (reservation.getCard()!=null) {
                        Attr onlineAttr = document.createAttribute("payingOnline");
                        onlineAttr.setValue("true");
                        eReservation.setAttributeNode(onlineAttr);
                    }

                    Element vehicleName = document.createElement("vehicleName");
                    vehicleName.appendChild(document.createTextNode(reservation.getVehicle().getName()));
                    eReservation.appendChild(vehicleName);

                    Element pickupDate = document.createElement("pickupDate");
                    pickupDate.appendChild(document.createTextNode(reservation.getPickUpDate().getDayOfMonth()+"/"+reservation.getPickUpDate().getMonth()+"/"+reservation.getPickUpDate().getYear()));
                    eReservation.appendChild(pickupDate);

                    Element returnDate = document.createElement("returnDate");
                    returnDate.appendChild(document.createTextNode(reservation.getReturnDate().getDayOfMonth()+"/"+reservation.getReturnDate().getMonth()+"/"+reservation.getReturnDate().getYear()));
                    eReservation.appendChild(returnDate);

                    Element pickupTime = document.createElement("pickupTime");
                    pickupTime.appendChild(document.createTextNode(reservation.getPickupTime()));
                    eReservation.appendChild(pickupTime);

                    Element returnTime = document.createElement("returnTime");
                    returnTime.appendChild(document.createTextNode(reservation.getReturnTime()));
                    eReservation.appendChild(returnTime);

                    eClient.appendChild(eReservation);
                }
            }

            root.appendChild(eClient);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch(TransformerException err) {
            err.printStackTrace();
        }
    }

    public void writeReservations(File file)  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException err) {
            err.printStackTrace();
        }

        Element root = document.createElement("reservations");
        document.appendChild(root);

        for (Reservation reservation : reservations) {
            Element eReservation = document.createElement("reservation");
            if (reservation.getCard()!=null) {
                Attr onlineAttr = document.createAttribute("payingOnline");
                onlineAttr.setValue("true");
                eReservation.setAttributeNode(onlineAttr);
            }
            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(reservation.getClient().getFirstName()));
            eReservation.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(reservation.getClient().getLastName()));
            eReservation.appendChild(lastName);
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(reservation.getClient().getEmail()));
            eReservation.appendChild(email);
            Element username = document.createElement("username");
            username.appendChild(document.createTextNode(reservation.getClient().getUsername()));
            eReservation.appendChild(username);
            Element address = document.createElement("address");
            address.appendChild(document.createTextNode(reservation.getClient().getAddress()));
            eReservation.appendChild(address);
            Element telephone = document.createElement("telephone");
            telephone.appendChild(document.createTextNode(reservation.getClient().getTelephone()));
            eReservation.appendChild(telephone);
            /*ArrayList<Reservation>clientReservations=new ArrayList<>();
            for(Reservation r: listReservations){
                if(r.getClient().getUsername().equals(client.getUsername()))
                    clientReservations.add(r);
            }*/
            Element vehicleName = document.createElement("vehicleName");
            vehicleName.appendChild(document.createTextNode(reservation.getVehicle().getName()));
            eReservation.appendChild(vehicleName);

            Element pickupDate = document.createElement("pickupDate");
            pickupDate.appendChild(document.createTextNode(reservation.getPickUpDate().getDayOfMonth()+"/"+reservation.getPickUpDate().getMonth()+"/"+reservation.getPickUpDate().getYear()));
            eReservation.appendChild(pickupDate);

            Element returnDate = document.createElement("returnDate");
            returnDate.appendChild(document.createTextNode(reservation.getReturnDate().getDayOfMonth()+"/"+reservation.getReturnDate().getMonth()+"/"+reservation.getReturnDate().getYear()));
            eReservation.appendChild(returnDate);

            Element pickupTime = document.createElement("pickupTime");
            pickupTime.appendChild(document.createTextNode(reservation.getPickupTime()));
            eReservation.appendChild(pickupTime);

            Element returnTime = document.createElement("returnTime");
            returnTime.appendChild(document.createTextNode(reservation.getReturnTime()));
            eReservation.appendChild(returnTime);
            root.appendChild(eReservation);
        }


        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch(TransformerException err) {
            err.printStackTrace();
        }
    }
}
