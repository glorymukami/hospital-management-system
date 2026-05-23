import dao.PatientDAO;
import dao.DoctorDAO;
import model.Patient;
import model.Doctor;
import util.DBConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("=========================================\n");
        
        // Test database connection
        try {
            DBConnection.getConnection();
            System.out.println("✅ Database connected!\n");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            return;
        }
        
        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        
        // Show patient count
        int patientCount = patientDAO.getPatientCount();
        System.out.println("📊 TOTAL PATIENTS: " + patientCount);
        
        // Show doctor count
        int doctorCount = doctorDAO.getDoctorCount();
        System.out.println("📊 TOTAL DOCTORS: " + doctorCount + "\n");
        
        // List all patients
        System.out.println("📋 PATIENT LIST:");
        System.out.println("----------------------------------------");
        List<Patient> patients = patientDAO.getAllPatients();
        
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            for (Patient p : patients) {
                System.out.println("ID: " + p.getId() + " | " + p.getFullName() + " | " + p.getPhone() + " | Blood: " + p.getBloodGroup());
            }
        }
        
        // List all doctors
        System.out.println("\n👨‍⚕️ DOCTOR LIST:");
        System.out.println("----------------------------------------");
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            for (Doctor d : doctors) {
                System.out.println("ID: " + d.getId() + " | Dr. " + d.getFullName() + " | " + d.getSpecialization() + " | Fee: $" + d.getConsultationFee());
            }
        }
        
        System.out.println("\n=========================================");
        System.out.println("✅ SYSTEM READY!");
        System.out.println("Total Patients: " + patientCount + " | Total Doctors: " + doctorCount);
        System.out.println("=========================================");
    }
}