package dao;

import model.Patient;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    
    // CREATE - Add a new patient
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone, email, address, blood_group) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, patient.getDateOfBirth());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getPhone());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getBloodGroup());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Get all patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("blood_group")
                );
                patient.setId(rs.getInt("patient_id"));
                patients.add(patient);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting patients: " + e.getMessage());
        }
        
        return patients;
    }
    
    // READ - Get patient by ID
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("blood_group")
                );
                patient.setId(rs.getInt("patient_id"));
                return patient;
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting patient: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update patient information
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET first_name=?, last_name=?, phone=?, email=?, address=?, blood_group=? WHERE patient_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setString(3, patient.getPhone());
            pstmt.setString(4, patient.getEmail());
            pstmt.setString(5, patient.getAddress());
            pstmt.setString(6, patient.getBloodGroup());
            pstmt.setInt(7, patient.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Remove a patient
    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
            return false;
        }
    }
    
    // COUNT - Get total number of patients
    public int getPatientCount() {
        String sql = "SELECT COUNT(*) FROM patients";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("Error counting patients: " + e.getMessage());
        }
        
        return 0;
    }
}