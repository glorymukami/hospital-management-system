package dao;

import model.Doctor;
import util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    
    // CREATE - Add a new doctor
    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (first_name, last_name, specialization, phone, email, consultation_fee, years_of_experience, room_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, doctor.getFirstName());
            pstmt.setString(2, doctor.getLastName());
            pstmt.setString(3, doctor.getSpecialization());
            pstmt.setString(4, doctor.getPhone());
            pstmt.setString(5, doctor.getEmail());
            pstmt.setBigDecimal(6, doctor.getConsultationFee());
            pstmt.setInt(7, doctor.getYearsOfExperience());
            pstmt.setString(8, doctor.getRoomNumber());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding doctor: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Get all doctors
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("specialization"),
                    rs.getBigDecimal("consultation_fee"),
                    rs.getInt("years_of_experience"),
                    rs.getString("room_number")
                );
                doctor.setId(rs.getInt("doctor_id"));
                doctors.add(doctor);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting doctors: " + e.getMessage());
        }
        
        return doctors;
    }
    
    // READ - Get doctor by ID
    public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("specialization"),
                    rs.getBigDecimal("consultation_fee"),
                    rs.getInt("years_of_experience"),
                    rs.getString("room_number")
                );
                doctor.setId(rs.getInt("doctor_id"));
                return doctor;
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting doctor: " + e.getMessage());
        }
        
        return null;
    }
    
    // GET - Doctor count
    public int getDoctorCount() {
        String sql = "SELECT COUNT(*) FROM doctors";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("Error counting doctors: " + e.getMessage());
        }
        
        return 0;
    }
}