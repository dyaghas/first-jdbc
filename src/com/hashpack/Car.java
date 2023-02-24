package com.hashpack;

class Car {

    private int id;
    private String brand;
    private String color;
    private int year;
    private String[] brands = new String[]{"bmw", "chevrolet", "ford", "honda", "hyundai", "porsche"};

    public Car(int id, String b, String c, int y) {
        setId(id);
        setBrand(b);
        setColor(c);
        setYear(y);
    }

    public String getCarParameters() {
        String carInstance = "ID: " + id + "\n" +
                "Brand:" + brand + "\n" +
                "Color: " + color + "\n" +
                "Model year: " + year + "\n";
        return carInstance;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
