import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import dao.PatientDAO;
import dao.DoctorDAO;
import dao.BillDAO;
import dao.AdmissionDAO;
import dao.PrescriptionDAO;
import model.Patient;
import model.Doctor;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class SimpleHttpServer {
    private static Gson gson = new Gson();
    private static PatientDAO patientDAO = new PatientDAO();
    private static DoctorDAO doctorDAO = new DoctorDAO();
    private static BillDAO billDAO = new BillDAO();
    private static AdmissionDAO admissionDAO = new AdmissionDAO();
    private static PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Patient endpoints
        server.createContext("/api/patients", new PatientHandler());
        server.createContext("/api/patients/count", new PatientCountHandler());
        
        // Doctor endpoints
        server.createContext("/api/doctors", new DoctorHandler());
        server.createContext("/api/doctors/count", new DoctorCountHandler());
        
        // Bill endpoints
        server.createContext("/api/bills", new BillHandler());
        server.createContext("/api/bills/pending/count", new PendingBillCountHandler());
        
        // Admission endpoints
        server.createContext("/api/admissions", new AdmissionHandler());
        server.createContext("/api/admissions/active/count", new ActiveAdmissionCountHandler());
        
        // Prescription endpoints
        server.createContext("/api/prescriptions", new PrescriptionHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("========================================");
        System.out.println("🚀 SERVER STARTED SUCCESSFULLY!");
        System.out.println("========================================");
        System.out.println("📡 Server running at: http://localhost:8080");
        System.out.println("\n📋 Available API Endpoints:");
        System.out.println("   GET http://localhost:8080/api/patients");
        System.out.println("   GET http://localhost:8080/api/patients/count");
        System.out.println("   GET http://localhost:8080/api/doctors");
        System.out.println("   GET http://localhost:8080/api/doctors/count");
        System.out.println("   GET http://localhost:8080/api/bills");
        System.out.println("   GET http://localhost:8080/api/bills/pending/count");
        System.out.println("   GET http://localhost:8080/api/admissions");
        System.out.println("   GET http://localhost:8080/api/admissions/active/count");
        System.out.println("   GET http://localhost:8080/api/prescriptions");
        System.out.println("========================================");
        System.out.println("✅ Ready to accept requests!");
        System.out.println("========================================\n");
    }
    
    // PATIENT HANDLERS
    static class PatientHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Patient> patients = patientDAO.getAllPatients();
                String response = gson.toJson(patients);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class PatientCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = patientDAO.getPatientCount();
                String response = "{\"count\":" + count + "}";
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // DOCTOR HANDLERS
    static class DoctorHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Doctor> doctors = doctorDAO.getAllDoctors();
                String response = gson.toJson(doctors);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class DoctorCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = doctorDAO.getDoctorCount();
                String response = "{\"count\":" + count + "}";
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // BILL HANDLERS
    static class BillHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> bills = billDAO.getAllBills();
                String response = gson.toJson(bills);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class PendingBillCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = billDAO.getPendingBillCount();
                String response = "{\"count\":" + count + "}";
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // ADMISSION HANDLERS
    static class AdmissionHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> admissions = admissionDAO.getAllAdmissions();
                String response = gson.toJson(admissions);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class ActiveAdmissionCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = admissionDAO.getActiveAdmissionCount();
                String response = "{\"count\":" + count + "}";
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // PRESCRIPTION HANDLERS
    static class PrescriptionHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> prescriptions = prescriptionDAO.getAllPrescriptions();
                String response = gson.toJson(prescriptions);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // HELPER METHOD TO SEND RESPONSE
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) {
        try {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
            exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}