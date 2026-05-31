package com.kane.solutions.model;

public class Applicant {
    private String name;
    private Integer age;
    private String address;
    private String email;

    public Applicant(String name, Integer age, String address, String email) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
    }

    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
}
