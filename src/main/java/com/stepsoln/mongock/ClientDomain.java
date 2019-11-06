package com.stepsoln.mongock;

public class ClientDomain {

    private final String name;

    private final String surname;

    public ClientDomain(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
