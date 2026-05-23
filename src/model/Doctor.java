package model;

import java.math.BigDecimal;

public class Doctor extends Person {
    private String specialization;
    private BigDecimal consultationFee;
    private int yearsOfExperience;
    private String roomNumber;
    
    // Constructor
    public Doctor(String firstName, String lastName, String phone, String email,
                  String specialization, BigDecimal consultationFee, 
                  int yearsOfExperience, String roomNumber) {
        super(firstName, lastName, phone, email);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = yearsOfExperience;
        this.roomNumber = roomNumber;
    }
    
    // Getters and Setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
    
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    @Override
    public String getRole() {
        return "Doctor";
    }
}