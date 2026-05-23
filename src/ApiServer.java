import com.google.gson.Gson;
import dao.PatientDAO;
import dao.DoctorDAO;
import model.Patient;
import model.Doctor;
import static spark.Spark.*;

public class ApiServer {
    private static Gson gson = new Gson();
    private static PatientDAO patientDAO = new PatientDAO();
    private static DoctorDAO doctorDAO = new DoctorDAO();
    
    public static void main(String[] args) {
        // Configure CORS (allows frontend to call backend)
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
        
        port(8080);
        System.out.println("🚀 Server started at http://localhost:8080");
        
        // PATIENT ENDPOINTS
        get("/api/patients", (req, res) -> patientDAO.getAllPatients(), gson::toJson);
        get("/api/patients/count", (req, res) -> patientDAO.getPatientCount(), gson::toJson);
        get("/api/patients/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            return patientDAO.getPatientById(id);
        }, gson::toJson);
        
        // DOCTOR ENDPOINTS
        get("/api/doctors", (req, res) -> doctorDAO.getAllDoctors(), gson::toJson);
        get("/api/doctors/count", (req, res) -> doctorDAO.getDoctorCount(), gson::toJson);
        
        System.out.println("✅ API Endpoints ready:");
        System.out.println("   GET  http://localhost:8080/api/patients");
        System.out.println("   GET  http://localhost:8080/api/doctors");
    }
}