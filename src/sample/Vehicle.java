package sample;

public class Vehicle {
    private int id;
    private String name;
    private String brand;
    private String model;
    private String type;
    private int year;
    private int seatsNumber;
    private int doorsNumber;
    private String engine;
    private String transmission;
    private Double fuelConsumption;
    private String color;
    private Double pricePerDay;
    private String availability;

    public Vehicle(int id, String name, String brand, String model, String type, int year, int seatsNumber, int doorsNumber, String engine, String transmission, Double fuelConsumption, String color, Double pricePerDay, String availability) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.year = year;
        this.seatsNumber = seatsNumber;
        this.doorsNumber = doorsNumber;
        this.engine = engine;
        this.transmission = transmission;
        this.fuelConsumption = fuelConsumption;
        this.color = color;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getDoorsNumber() {
        return doorsNumber;
    }

    public void setDoorsNumber(int doorsNumber) {
        this.doorsNumber = doorsNumber;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
