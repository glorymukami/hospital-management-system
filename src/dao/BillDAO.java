package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BillDAO {
    
    // Get all bills with patient names
    public List<Map<String, Object>> getAllBills() {
        List<Map<String, Object>> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, p.first_name, p.last_name, b.total_amount, " +
                     "b.paid_amount, b.payment_status, b.payment_date " +
                     "FROM bills b JOIN patients p ON b.patient_id = p.patient_id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> bill = new HashMap<>();
                bill.put("id", rs.getInt("bill_id"));
                bill.put("patientName", rs.getString("first_name") + " " + rs.getString("last_name"));
                bill.put("totalAmount", rs.getBigDecimal("total_amount"));
                bill.put("paidAmount", rs.getBigDecimal("paid_amount"));
                bill.put("status", rs.getString("payment_status"));
                bill.put("paymentDate", rs.getDate("payment_date"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            System.out.println("Error getting bills: " + e.getMessage());
        }
        return bills;
    }
    
    public int getPendingBillCount() {
        String sql = "SELECT COUNT(*) FROM bills WHERE payment_status != 'Paid'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error counting pending bills: " + e.getMessage());
        }
        return 0;
    }
}