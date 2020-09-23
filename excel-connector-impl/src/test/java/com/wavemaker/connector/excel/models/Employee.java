package com.wavemaker.connector.excel.models;

public class Employee{
    int sno;
    String name;
    String emailAddress;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Employee(int sno, String name, String emailaddress) {
        this.sno = sno;
        this.name = name;
        this.emailAddress = emailaddress;
    }

    public Employee(){}
}