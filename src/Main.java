import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        people.add(new Patients(1, "John Smith", "john@email.com", 25, "O+"));
        people.add(new Doctor(101, "Dr. Adams", "adams@hospital.com", "Cardiology", 12));

        boolean running = true;
        while (running) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. View All Registered Personnel");
            System.out.println("4. Demonstrate Polymorphism");
            System.out.println("5. Search and Downcast (Instanceof)"); // New Option
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: "); int pId = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: "); String pName = sc.nextLine();
                    System.out.print("Enter Email: "); String pEmail = sc.nextLine();
                    System.out.print("Enter Age: "); int pAge = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Blood Type: "); String pBlood = sc.nextLine();
                    people.add(new Patients(pId, pName, pEmail, pAge, pBlood));
                    break;
                case 2:
                    System.out.print("Enter ID: "); int dId = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: "); String dName = sc.nextLine();
                    System.out.print("Enter Email: "); String dEmail = sc.nextLine();
                    System.out.print("Enter Specialization: "); String dSpec = sc.nextLine();
                    System.out.print("Enter Years Experience: "); int dExp = sc.nextInt(); sc.nextLine();
                    people.add(new Doctor(dId, dName, dEmail, dSpec, dExp));
                    break;
                case 3:
                    for (Person p : people) { System.out.println(p); }
                    break;
                case 4:
                    for (Person p : people) { p.displayRole(); }
                    break;
                case 5: //Downcasting
                    System.out.print("Enter ID to search details: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;

                    for (Person p : people) {
                        if (p.getId() == searchId) {
                            found = true;
                            System.out.println("Common Info: " + p.getName());

                            // Instanceof check
                            if (p instanceof Doctor) {
                                //  Downcasting
                                Doctor d = (Doctor) p;
                                System.out.println("Specific Info: Specialist in " + d.getSpecialization());
                            }
                            else if (p instanceof Patients) {
                                //  Downcasting
                                Patients pat = (Patients) p;
                                System.out.println("Specific Info: Blood Type " + pat.getBloodType());
                            }
                        }
                    }
                    if (!found) System.out.println("ID not found.");
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}