public class Main {
    public static void main(String[] args) {
        System.out.println("=== Connected Hospital System ===");
        System.out.println();

        Patients p1 = new Patients(101, "Amanat Almas", 75, "O+");
        Doctor d1 = new Doctor(501, "Dr. Arman", "Cardiology", 12);

        Appointment a1 = new Appointment(9001, p1, d1, "2025-12-27");

        System.out.println("--- CONNECTION TEST ---");
        System.out.println(a1);
        System.out.println("Accessing Patient name through Appointment: " + a1.getPatient().getFullName());
        System.out.println("Accessing Doctor specialty through Appointment: " + a1.getDoctor().getSpecialization());
        System.out.println();

        System.out.println("--- TESTING CROSS-CLASS LOGIC ---");
        System.out.println("Current Status: " + a1.getStatus());
        a1.checkUrgency();
        System.out.println("Updated Status based on Patient/Doctor data: " + a1.getStatus());
        System.out.println();

        System.out.println("--- UPDATING CONNECTED OBJECTS ---");
        a1.getPatient().setAge(76);
        a1.reschedule("2026-01-05");
        System.out.println(a1);

        System.out.println("\n=== Program Complete ===");
    }
}