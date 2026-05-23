package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AdmissionDAO {
    
    public List<Map<String, Object>> getAllAdmissions() {
        List<Map<String, Object>> admissions = new ArrayList<>();
        String sql = "SELECT a.admission_id, p.first_name, p.last_name, r.room_number, " +
                     "a.admission_date, a.discharge_date, a.reason " +
                     "FROM admissions a " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "LEFT JOIN rooms r ON a.room_id = r.room_id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> admission = new HashMap<>();
                admission.put("id", rs.getInt("admission_id"));
                admission.put("patientName", rs.getString("first_name") + " " + rs.getString("last_name"));
                admission.put("roomNumber", rs.getString("room_number"));
                admission.put("admissionDate", rs.getTimestamp("admission_date"));
                admission.put("dischargeDate", rs.getTimestamp("discharge_date"));
                admission.put("reason", rs.getString("reason"));
                admissions.add(admission);
            }
        } catch (SQLException e) {
            System.out.println("Error getting admissions: " + e.getMessage());
        }
        return admissions;
    }
    
    public int getActiveAdmissionCount() {
        String sql = "SELECT COUNT(*) FROM admissions WHERE discharge_date IS NULL";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error counting active admissions: " + e.getMessage());
        }
        return 0;
    }
}