package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionDAO {
    
    public List<Map<String, Object>> getAllPrescriptions() {
        List<Map<String, Object>> prescriptions = new ArrayList<>();
        String sql = "SELECT pr.prescription_id, p.first_name as p_first, p.last_name as p_last, " +
                     "d.first_name as d_first, d.last_name as d_last, m.medicine_name, " +
                     "pr.dosage, pr.duration_days, pr.prescribed_date " +
                     "FROM prescriptions pr " +
                     "JOIN patients p ON pr.patient_id = p.patient_id " +
                     "JOIN doctors d ON pr.doctor_id = d.doctor_id " +
                     "JOIN medicines m ON pr.medicine_id = m.medicine_id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> prescription = new HashMap<>();
                prescription.put("id", rs.getInt("prescription_id"));
                prescription.put("patientName", rs.getString("p_first") + " " + rs.getString("p_last"));
                prescription.put("doctorName", "Dr. " + rs.getString("d_first") + " " + rs.getString("d_last"));
                prescription.put("medicineName", rs.getString("medicine_name"));
                prescription.put("dosage", rs.getString("dosage"));
                prescription.put("duration", rs.getInt("duration_days"));
                prescription.put("prescribedDate", rs.getDate("prescribed_date"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            System.out.println("Error getting prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }
}