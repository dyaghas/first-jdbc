package com.hashpack;

class Car {

    private String brand;
    private String color;
    private String fuel;
    private int year;
    private String[] brands = new String[]{"bmw", "Chevrolet", "ford", "honda", "hyundai", "porsche"};

    public Car(String b, String c, String f, int y) {
        setBrand(b);
        setColor(c);
        setFuel(f);
        setYear(y);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String b) {
        b = b.toLowerCase();
        //verify if the specified brand is present
        for(int i = 0; i < brands.length; i++) {
            if(b.equals(brands[i])) {
                this.brand = b;
                break;
            }
        }
        if(this.brand == null) {
            System.out.println("invalid car brand");
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        switch(fuel) {
            case "gasoline":
            case "eletric":
            case "diesel":
            case "hybrid":
                this.fuel = fuel;
                break;
            default:
                System.out.println("Invalid car fuel type");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if(year >= 1886 && year <= 2023){
            this.year = year;
        } else {
            System.out.println("invalid car model date");
        }
    }
}
