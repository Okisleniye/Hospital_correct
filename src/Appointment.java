public class Appointment {
    private int appointmentId;
    private String patientName;
    private String doctorName;
    private String date;

    public Appointment(int appointmentId, String patientName, String doctorName, String date) {
        setAppointmentId(appointmentId);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
    }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) {
        if (appointmentId > 0) this.appointmentId = appointmentId;
    }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "Appt ID: " + appointmentId + " | Patient: " + patientName + " | Doctor: " + doctorName + " | Date: " + date;
    }
}