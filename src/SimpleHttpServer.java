import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.PatientDAO;
import dao.DoctorDAO;
import dao.BillDAO;
import dao.AdmissionDAO;
import dao.PrescriptionDAO;
import model.Patient;
import model.Doctor;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.sql.Date;

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
        System.out.println("\n   PATIENTS:");
        System.out.println("   GET    /api/patients");
        System.out.println("   POST   /api/patients");
        System.out.println("   PUT    /api/patients/{id}");
        System.out.println("   DELETE /api/patients/{id}");
        System.out.println("   GET    /api/patients/count");
        System.out.println("\n   DOCTORS:");
        System.out.println("   GET    /api/doctors");
        System.out.println("   POST   /api/doctors");
        System.out.println("   PUT    /api/doctors/{id}");
        System.out.println("   DELETE /api/doctors/{id}");
        System.out.println("   GET    /api/doctors/count");
        System.out.println("\n   BILLS:");
        System.out.println("   GET    /api/bills");
        System.out.println("   GET    /api/bills/pending/count");
        System.out.println("\n   ADMISSIONS:");
        System.out.println("   GET    /api/admissions");
        System.out.println("   GET    /api/admissions/active/count");
        System.out.println("\n   PRESCRIPTIONS:");
        System.out.println("   GET    /api/prescriptions");
        System.out.println("========================================");
        System.out.println("✅ Ready to accept requests!");
        System.out.println("========================================\n");
    }
    
    // PATIENT HANDLER with CRUD
    static class PatientHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            
            try {
                if (method.equals("GET")) {
                    // GET /api/patients or /api/patients/{id}
                    if (path.equals("/api/patients")) {
                        List<Patient> patients = patientDAO.getAllPatients();
                        sendResponse(exchange, 200, gson.toJson(patients));
                    } else {
                        // Extract ID from path
                        String[] parts = path.split("/");
                        int id = Integer.parseInt(parts[parts.length - 1]);
                        Patient patient = patientDAO.getPatientById(id);
                        if (patient != null) {
                            sendResponse(exchange, 200, gson.toJson(patient));
                        } else {
                            sendResponse(exchange, 404, "{\"error\":\"Patient not found\"}");
                        }
                    }
                } 
                else if (method.equals("POST")) {
                    // POST /api/patients - Create new patient
                    String body = readBody(exchange);
                    JsonObject json = gson.fromJson(body, JsonObject.class);
                    
                    Patient patient = new Patient(
                        json.get("firstName").getAsString(),
                        json.get("lastName").getAsString(),
                        json.has("phone") ? json.get("phone").getAsString() : "",
                        json.has("email") ? json.get("email").getAsString() : "",
                        json.has("dateOfBirth") ? Date.valueOf(json.get("dateOfBirth").getAsString()) : null,
                        json.has("gender") ? json.get("gender").getAsString() : "",
                        json.has("address") ? json.get("address").getAsString() : "",
                        json.has("bloodGroup") ? json.get("bloodGroup").getAsString() : ""
                    );
                    
                    boolean success = patientDAO.addPatient(patient);
                    if (success) {
                        sendResponse(exchange, 201, "{\"message\":\"Patient added successfully\"}");
                    } else {
                        sendResponse(exchange, 500, "{\"error\":\"Failed to add patient\"}");
                    }
                }
                else if (method.equals("PUT")) {
                    // PUT /api/patients/{id} - Update patient
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    String body = readBody(exchange);
                    JsonObject json = gson.fromJson(body, JsonObject.class);
                    
                    Patient patient = patientDAO.getPatientById(id);
                    if (patient != null) {
                        patient.setFirstName(json.get("firstName").getAsString());
                        patient.setLastName(json.get("lastName").getAsString());
                        if (json.has("phone")) patient.setPhone(json.get("phone").getAsString());
                        if (json.has("email")) patient.setEmail(json.get("email").getAsString());
                        if (json.has("address")) patient.setAddress(json.get("address").getAsString());
                        if (json.has("bloodGroup")) patient.setBloodGroup(json.get("bloodGroup").getAsString());
                        
                        boolean success = patientDAO.updatePatient(patient);
                        if (success) {
                            sendResponse(exchange, 200, "{\"message\":\"Patient updated successfully\"}");
                        } else {
                            sendResponse(exchange, 500, "{\"error\":\"Failed to update patient\"}");
                        }
                    } else {
                        sendResponse(exchange, 404, "{\"error\":\"Patient not found\"}");
                    }
                }
                else if (method.equals("DELETE")) {
                    // DELETE /api/patients/{id}
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    
                    boolean success = patientDAO.deletePatient(id);
                    if (success) {
                        sendResponse(exchange, 200, "{\"message\":\"Patient deleted successfully\"}");
                    } else {
                        sendResponse(exchange, 500, "{\"error\":\"Failed to delete patient\"}");
                    }
                }
                else {
                    sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // DOCTOR HANDLER with CRUD
    static class DoctorHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            
            try {
                if (method.equals("GET")) {
                    List<Doctor> doctors = doctorDAO.getAllDoctors();
                    sendResponse(exchange, 200, gson.toJson(doctors));
                }
                else if (method.equals("POST")) {
                    String body = readBody(exchange);
                    JsonObject json = gson.fromJson(body, JsonObject.class);
                    
                    Doctor doctor = new Doctor(
                        json.get("firstName").getAsString(),
                        json.get("lastName").getAsString(),
                        json.has("phone") ? json.get("phone").getAsString() : "",
                        json.has("email") ? json.get("email").getAsString() : "",
                        json.get("specialization").getAsString(),
                        json.get("consultationFee").getAsBigDecimal(),
                        json.get("yearsOfExperience").getAsInt(),
                        json.has("roomNumber") ? json.get("roomNumber").getAsString() : ""
                    );
                    
                    boolean success = doctorDAO.addDoctor(doctor);
                    if (success) {
                        sendResponse(exchange, 201, "{\"message\":\"Doctor added successfully\"}");
                    } else {
                        sendResponse(exchange, 500, "{\"error\":\"Failed to add doctor\"}");
                    }
                }
                else if (method.equals("PUT")) {
                    // PUT implementation for doctors
                    sendResponse(exchange, 501, "{\"message\":\"PUT for doctors coming soon\"}");
                }
                else if (method.equals("DELETE")) {
                    // DELETE implementation for doctors
                    sendResponse(exchange, 501, "{\"message\":\"DELETE for doctors coming soon\"}");
                }
                else {
                    sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // BILL HANDLER
    static class BillHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> bills = billDAO.getAllBills();
                sendResponse(exchange, 200, gson.toJson(bills));
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class PendingBillCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = billDAO.getPendingBillCount();
                sendResponse(exchange, 200, "{\"count\":" + count + "}");
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // ADMISSION HANDLER
    static class AdmissionHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> admissions = admissionDAO.getAllAdmissions();
                sendResponse(exchange, 200, gson.toJson(admissions));
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    static class ActiveAdmissionCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = admissionDAO.getActiveAdmissionCount();
                sendResponse(exchange, 200, "{\"count\":" + count + "}");
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // PRESCRIPTION HANDLER
    static class PrescriptionHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                List<Map<String, Object>> prescriptions = prescriptionDAO.getAllPrescriptions();
                sendResponse(exchange, 200, gson.toJson(prescriptions));
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // PATIENT COUNT HANDLER
    static class PatientCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = patientDAO.getPatientCount();
                sendResponse(exchange, 200, "{\"count\":" + count + "}");
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // DOCTOR COUNT HANDLER
    static class DoctorCountHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                int count = doctorDAO.getDoctorCount();
                sendResponse(exchange, 200, "{\"count\":" + count + "}");
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
    
    // Helper method to read request body
    private static String readBody(HttpExchange exchange) throws Exception {
        InputStream is = exchange.getRequestBody();
        byte[] bytes = is.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    // Helper method to send response
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) {
        try {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
            
            // Handle preflight OPTIONS request
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            
            exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}