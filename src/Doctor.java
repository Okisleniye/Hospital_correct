public class Doctor extends Person {
    private String specialization;
    private int experienceYears;

    public Doctor(int id, String name, String email, String specialization, int experienceYears) {
        super(id, name, email);
        this.specialization = specialization;
        setExperienceYears(experienceYears);
    }

    public Doctor() {
        super();
        this.specialization = "General";
        this.experienceYears = 0;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) {
        if (experienceYears >= 0) this.experienceYears = experienceYears;
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Doctor (" + specialization + ")");
    }

    @Override
    public String toString() {
        return "Doctor -> " + super.toString() + " | Spec: " + specialization + " | Exp: " + experienceYears + " years";
    }
}