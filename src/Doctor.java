public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private int experienceYears;

    public Doctor(int doctorId, String name, String specialization, int experienceYears) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
    }

    public Doctor() {
        this.doctorId = 0;
        this.name = "Unknown";
        this.specialization = "General";
        this.experienceYears = 0;
    }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public boolean isExperienced() {
        return this.experienceYears >= 10;
    }

    public boolean canPerformSurgery() {
        return this.specialization.equalsIgnoreCase("Cardiology") ||
                this.specialization.equalsIgnoreCase("Surgery");
    }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}