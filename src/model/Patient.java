package model;

import java.sql.Date;

public class Patient extends Person {
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String bloodGroup;
    private Date registrationDate;
    
    // Constructor
    public Patient(String firstName, String lastName, String phone, String email,
                   Date dateOfBirth, String gender, String address, String bloodGroup) {
        super(firstName, lastName, phone, email);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.registrationDate = new Date(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public Date getRegistrationDate() { return registrationDate; }
    
    @Override
    public String getRole() {
        return "Patient";
    }
}