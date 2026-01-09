public class Patients extends Person {
    private int age;
    private String bloodType;

    public Patients(int id, String name, String email, int age, String bloodType) {
        super(id, name, email);
        setAge(age);
        this.bloodType = bloodType;
    }

    public Patients() {
        super();
        this.age = 0;
        this.bloodType = "Unknown";
    }

    public int getAge() { return age; }
    public void setAge(int age) {
        if (age >= 0) this.age = age;
    }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    @Override
    public void displayRole() {
        System.out.println("Role: Patient (Under Medical Care)");
    }

    @Override
    public String toString() {
        return "Patient -> " + super.toString() + " | Age: " + age + " | Blood: " + bloodType;
    }
}