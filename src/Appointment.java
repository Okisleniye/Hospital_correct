public class Appointment {
    private int appointmentId;
    private Patients patient;
    private Doctor doctor;
    private String date;
    private String status;

    public Appointment(int appointmentId, Patients patient, Doctor doctor, String date) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.status = "Scheduled";
    }

    public Appointment() {
        this.appointmentId = 0;
        this.patient = new Patients();
        this.doctor = new Doctor();
        this.date = "TBD";
        this.status = "Pending";
    }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public Patients getPatient() { return patient; }
    public void setPatient(Patients patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void reschedule(String newDate) {
        this.date = newDate;
        this.status = "Rescheduled";
    }

    public void checkUrgency() {
        if (patient.getAge() > 70 || doctor.getSpecialization().equals("Cardiology")) {
            this.status = "High Priority";
        }
    }

    @Override
    public String toString() {
        return "Appt #" + appointmentId + " | " + patient + " with " + doctor + " on " + date + " [" + status + "]";
    }
}