package menu;

import database.StaffDAO;
import model.Doctor;
import model.Nurse;
import model.Staff;

import java.util.List;
import java.util.Scanner;

/**
 * RestaurantMenu - Week 8
 * FULLY DATABASE-DRIVEN - No ArrayLists!
 * All data comes from PostgreSQL database
 */
public class HospitalMenu implements Menu {
    private Scanner scanner;
    private StaffDAO staffDAO;

    public HospitalMenu() {
        this.scanner = new Scanner(System.in);
        this.staffDAO = new StaffDAO();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  RESTAURANT MANAGEMENT SYSTEM v2.0    ║");
        System.out.println("║  Week 8: Fully Database-Driven 🗄️     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("✅ All data is stored in PostgreSQL");
        System.out.println("✅ No in-memory ArrayLists");
        System.out.println("✅ Complete CRUD operations");
    }

    @Override
    public void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         MAIN MENU - Week 8            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("┌─ STAFF MANAGEMENT ─────────────────────┐");
        System.out.println("│ 1. Add Doctors                            │");
        System.out.println("│ 2. Add Nurse                          │");
        System.out.println("│ 3. View All Staff                      │");
        System.out.println("│ 4. View Doctors Only                     │");
        System.out.println("│ 5. View Nurses Only                   │");
        System.out.println("│ 6. Update Staff                        │");
        System.out.println("│ 7. Delete Staff                        │");
        System.out.println("├─ SEARCH & FILTER ──────────────────────┤");
        System.out.println("│ 8. Search by Name                      │");
        System.out.println("│ 9. Search by Salary Range              │");
        System.out.println("│10. High-Paid Staff (Salary >= X)       │");
        System.out.println("├─ DEMO & OTHER ─────────────────────────┤");
        System.out.println("│11. Polymorphism Demo                   │");
        System.out.println("│ 0. Exit                                │");
        System.out.println("└────────────────────────────────────────┘");
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("\n👉 Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        addNurse();
                        break;
                    case 3:
                        viewAllStaff();
                        break;
                    case 4:
                        viewDoctors();
                        break;
                    case 5:
                        viewNurses();
                        break;
                    case 6:
                        updateStaff();
                        break;
                    case 7:
                        deleteStaff();
                        break;
                    case 8:
                        searchByName();
                        break;
                    case 9:
                        searchBySalaryRange();
                        break;
                    case 10:
                        searchHighPaidStaff();
                        break;
                    case 11:
                        demonstratePolymorphism();
                        break;
                    case 0:
                        running = false;
                        System.out.println("\n╔════════════════════════════════════════╗");
                        System.out.println("║  Thank you for using our system!      ║");
                        System.out.println("║  Goodbye! 👋                          ║");
                        System.out.println("╚════════════════════════════════════════╝");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please select 0-11.");
                }

                if (choice != 0) {
                    pressEnterToContinue();
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("❌ Error: Please enter a valid number!");
                scanner.nextLine();
                pressEnterToContinue();
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                scanner.nextLine();
                pressEnterToContinue();
            }
        }

        scanner.close();
    }

    // ========================================
    // CREATE OPERATIONS
    // ========================================

    private void addDoctor() {
        try {
            System.out.println("\n┌─ ADD DOCTOR ─────────────────────────────┐");

            System.out.print("│ Enter Doctor ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("│ Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("│ Enter Salary (KZT): ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("│ Enter Experience (years): ");
            int experience = scanner.nextInt();
            scanner.nextLine();

            System.out.print("│ Enter Specialization: ");
            String specialization = scanner.nextLine();

            System.out.println("└────────────────────────────────────────┘");

            Doctor doctor = new Doctor(id, name, salary, experience, specialization);
            staffDAO.insertDoctor(doctor );

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid input type!");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
        }
    }

    private void addNurse() {
        try {
            System.out.println("\n┌─ ADD NURSE ───────────────────────────┐");

            System.out.print("│ Enter Nurse ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("│ Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("│ Enter Salary (KZT): ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("│ Enter Experience (years): ");
            int experience = scanner.nextInt();
            scanner.nextLine();

            System.out.print("│ Enter Patients Assigned: ");
            int patientsAssigned = scanner.nextInt();
            scanner.nextLine();

            System.out.println("└────────────────────────────────────────┘");

            Nurse nurse = new Nurse(id, name, salary, experience, patientsAssigned );
            staffDAO.insertNurse(nurse );

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid input type!");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
        }
    }

    // ========================================
    // READ OPERATIONS
    // ========================================

    private void viewAllStaff() {
        staffDAO.displayAllStaff();
    }

    private void viewDoctors() {
        List<Doctor> doctors = staffDAO.getAllDoctors();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         DOCTORS ONLY                    ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (doctors.isEmpty()) {
            System.out.println("📭 No doctors in database.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor =doctors.get(i);
                System.out.println((i + 1) + ". " + doctor.toString());
                System.out.println("   Specialization: " + doctor.getSpecialization());
                if (doctor.isSeniorDoctor()) {
                    System.out.println("   ⭐ SENIOR DOCTOR (10+ years)");
                }
                System.out.println();
            }
            System.out.println("Total Doctors: " + doctors.size());
        }
    }

    private void viewNurses() {
        List<Nurse> nurses = staffDAO.getAllNurse();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        NURSES ONLY                   ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (nurses.isEmpty()) {
            System.out.println("📭 No nurses in database.");
        } else {
            for (int i = 0; i < nurses.size(); i++) {
                Nurse nurse = nurses.get(i);
                System.out.println((i + 1) + ". " + nurse.toString());
                System.out.println("   Patients Assigned: " + nurse.getPatientsAssigned());
                if (nurse.isHeadNurse()) {
                    System.out.println("   ⭐ HEAD NURSE (6+ years)");
                }
                System.out.println();
            }
            System.out.println("Total Nurses: " + nurses.size());
        }
    }

    // ========================================
    // UPDATE OPERATION (Week 8)
    // ========================================

    private void updateStaff() {
        System.out.println("\n┌─ UPDATE STAFF ─────────────────────────┐");
        System.out.print("│ Enter Staff ID to update: ");

        try {
            int staffId = scanner.nextInt();
            scanner.nextLine();

            // First, get existing staff from database
            Staff existingStaff = staffDAO.getStaffById(staffId);

            if (existingStaff == null) {
                System.out.println("❌ No staff found with ID: " + staffId);
                return;
            }

            // Display current info
            System.out.println("│ Current Info:");
            System.out.println("│ " + existingStaff.toString());
            System.out.println("└────────────────────────────────────────┘");

            // Get new values
            System.out.println("\n┌─ ENTER NEW VALUES ─────────────────────┐");
            System.out.println("│ (Press Enter to keep current value)   │");

            System.out.print("│ New Name [" + existingStaff.getName() + "]: ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = existingStaff.getName();
            }

            System.out.print("│ New Salary [" + existingStaff.getSalary() + "]: ");
            String salaryInput = scanner.nextLine();
            double newSalary = salaryInput.trim().isEmpty() ?
                    existingStaff.getSalary() : Double.parseDouble(salaryInput);

            System.out.print("│ New Experience [" + existingStaff.getExperienceYears() + "]: ");
            String expInput = scanner.nextLine();
            int newExperience = expInput.trim().isEmpty() ?
                    existingStaff.getExperienceYears() : Integer.parseInt(expInput);

            // Update based on type
            if (existingStaff instanceof Doctor) {
                Doctor doctor = (Doctor) existingStaff;
                System.out.print("│ New Specialization [" + doctor.getSpecialization() + "]: ");
                String newSpec = scanner.nextLine();
                if (newSpec.trim().isEmpty()) {
                    newSpec = doctor.getSpecialization();
                }

                Doctor updatedDoctor = new Doctor(staffId, newName, newSalary, newExperience, newSpec);
                staffDAO.updateDoctor(updatedDoctor);

            } else if (existingStaff instanceof Nurse) {
                Nurse nurse = (Nurse) existingStaff;
                System.out.print("│ New Patients Assigned [" + nurse.getPatientsAssigned() + "]: ");
                String patientsInput = scanner.nextLine();
                int newPatients = patientsInput.trim().isEmpty() ?
                        nurse.getPatientsAssigned() : Integer.parseInt(patientsInput );

                Nurse updatedNurse = new Nurse(staffId, newName, newSalary, newExperience, newPatients);
                staffDAO.updateNurse(updatedNurse);
            }

            System.out.println("└────────────────────────────────────────┘");

        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid number format!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
        }
    }

    // ========================================
    // DELETE OPERATION (Week 8)
    // ========================================

    private void deleteStaff() {
        System.out.println("\n┌─ DELETE STAFF ─────────────────────────┐");
        System.out.print("│ Enter Staff ID to delete: ");

        try {
            int staffId = scanner.nextInt();
            scanner.nextLine();

            // First, show who will be deleted
            Staff staff = staffDAO.getStaffById(staffId);

            if (staff == null) {
                System.out.println("❌ No staff found with ID: " + staffId);
                return;
            }

            System.out.println("│ Staff to delete:");
            System.out.println("│ " + staff.toString());
            System.out.println("└────────────────────────────────────────┘");

            System.out.print("⚠️  Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                staffDAO.deleteStaff(staffId);
            } else {
                System.out.println("❌ Deletion cancelled.");
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid input!");
            scanner.nextLine();
        }
    }

    // ========================================
    // SEARCH OPERATIONS (Week 8)
    // ========================================

    private void searchByName() {
        System.out.println("\n┌─ SEARCH BY NAME ───────────────────────┐");
        System.out.print("│ Enter name to search: ");
        String name = scanner.nextLine();
        System.out.println("└────────────────────────────────────────┘");

        List<Staff> results = staffDAO.searchByName(name);

        displaySearchResults(results, "Search: '" + name + "'");
    }

    private void searchBySalaryRange() {
        try {
            System.out.println("\n┌─ SEARCH BY SALARY RANGE ───────────────┐");
            System.out.print("│ Enter minimum salary: ");
            double minSalary = scanner.nextDouble();

            System.out.print("│ Enter maximum salary: ");
            double maxSalary = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("└────────────────────────────────────────┘");

            List<Staff> results = staffDAO.searchBySalaryRange(minSalary, maxSalary);

            displaySearchResults(results, "Salary: " + minSalary + " - " + maxSalary + " KZT");

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid number!");
            scanner.nextLine();
        }
    }

    private void searchHighPaidStaff() {
        try {
            System.out.println("\n┌─ HIGH-PAID STAFF ──────────────────────┐");
            System.out.print("│ Enter minimum salary: ");
            double minSalary = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("└────────────────────────────────────────┘");

            List<Staff> results = staffDAO.searchByMinSalary(minSalary);

            displaySearchResults(results, "Salary >= " + minSalary + " KZT");

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid number!");
            scanner.nextLine();
        }
    }

    private void displaySearchResults(List<Staff> results, String criteria) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         SEARCH RESULTS                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Criteria: " + criteria);
        System.out.println("─────────────────────────────────────────");

        if (results.isEmpty()) {
            System.out.println("📭 No staff found matching criteria.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                Staff s = results.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
            System.out.println("─────────────────────────────────────────");
            System.out.println("Total Results: " + results.size());
        }
    }

    // ========================================
    // POLYMORPHISM DEMO
    // ========================================

    private void demonstratePolymorphism() {
        staffDAO.demonstratePolymorphism();
    }

    // ========================================
    // HELPER METHOD
    // ========================================

    private void pressEnterToContinue() {
        System.out.println("\n[Press Enter to continue...]");
        scanner.nextLine();
    }
}