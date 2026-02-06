package database;

import model.Doctor;
import model.Nurse;
import model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public boolean insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO staff (staff_id, name, salary, experience_years, staff_type, specialization) " +
                "VALUES (?, ?, ?, ?, 'DOCTOR', ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctor.getStaffId());
            statement.setString(2, doctor.getName());
            statement.setDouble(3, doctor.getSalary());
            statement.setInt(4, doctor.getExperienceYears());
            statement.setString(5, doctor.getSpecialization());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public boolean insertNurse(Nurse nurse) {
        String sql = "INSERT INTO staff (staff_id, name, salary, experience_years, staff_type, patients_assigned) " +
                "VALUES (?, ?, ?, ?, 'NURSE', ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nurse.getStaffId());
            statement.setString(2, nurse.getName());
            statement.setDouble(3, nurse.getSalary());
            statement.setInt(4, nurse.getExperienceYears());
            statement.setInt(5, nurse.getPatientsAssigned()); // Fixed index 5

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + staffList.size() + " staff from database");

        } catch (SQLException e) {
            System.out.println("❌ Select all staff failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);

                resultSet.close();
                statement.close();

                if (staff != null) {
                    System.out.println("✅ Found staff with ID: " + staffId);
                }

                return staff;
            }

            System.out.println("⚠️ No staff found with ID: " + staffId);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return null;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'DOCTOR' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return doctors;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Doctor) {
                    doctors.add((Doctor) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + doctors.size() + " doctors");

        } catch (SQLException e) {
            System.out.println("❌ Select doctors failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return doctors;
    }

    public List<Nurse> getAllNurse() {
        List<Nurse> nurses = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'NURSE' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return nurses;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Nurse) {
                    nurses.add((Nurse) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + nurses.size() + " nurses");

        } catch (SQLException e) {
            System.out.println("❌ Select nurses failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return nurses   ;
    }

    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experience_years = ?, specialization = ? " +
                "WHERE staff_id = ? AND staff_type = 'DOCTOR'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());
            statement.setInt(5, doctor.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Doctor updated: " + doctor.getName());
                return true;
            } else {
                System.out.println("⚠️ No doctor found with ID: " + doctor.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Doctor failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean updateNurse(Nurse nurse) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experience_years = ?, patients_assigned = ? " +
                "WHERE staff_id = ? AND staff_type = 'NURSE'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nurse.getName());
            statement.setDouble(2, nurse.getSalary());
            statement.setInt(3, nurse.getExperienceYears());
            statement.setInt(4, nurse.getPatientsAssigned());
            statement.setInt(5, nurse.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Nurse updated: " + nurse.getName());
                return true;
            } else {
                System.out.println("⚠️ No nurse found with ID: " + nurse.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Nurse failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println("✅ Staff deleted (ID: " + staffId + ")");
                return true;
            } else {
                System.out.println("⚠️ No staff found with ID: " + staffId);
            }

        } catch (SQLException e) {
            System.out.println("❌ Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public List<Staff> searchByName(String name) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");  // % = wildcard

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff matching '" + name + "'");

        } catch (SQLException e) {
            System.out.println("❌ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<Staff> searchBySalaryRange(double minSalary, double maxSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary BETWEEN ? AND ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);
            statement.setDouble(2, maxSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff in salary range " +
                    minSalary + " - " + maxSalary);

        } catch (SQLException e) {
            System.out.println("❌ Search by salary failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<Staff> searchByMinSalary(double minSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary >= ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff earning >= " + minSalary);

        } catch (SQLException e) {
            System.out.println("❌ Search by min salary failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    private Staff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        int staffId = resultSet.getInt("staff_id");
        String name = resultSet.getString("name");
        double salary = resultSet.getDouble("salary");
        int experienceYears = resultSet.getInt("experience_years");
        String staffType = resultSet.getString("staff_type");

        Staff staff = null;

        if ("DOCTOR".equals(staffType)) {
            String specialization = resultSet.getString("specialization");
            staff = new Doctor(staffId, name, salary, experienceYears, specialization);

        } else if ("NURSE".equals(staffType)) {
            int patientsAssigned = resultSet.getInt("patients_assigned");
            staff = new Nurse(staffId, name, salary, experienceYears, patientsAssigned);
        }

        return staff;
    }

    public void displayAllStaff() {
        List<Staff> staffList = getAllStaff();

        System.out.println("\n========================================");
        System.out.println("   ALL STAFF FROM DATABASE");
        System.out.println("========================================");

        if (staffList.isEmpty()) {
            System.out.println("No staff members in database.");
        } else {
            for (int i = 0; i < staffList.size(); i++) {
                Staff s = staffList.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
        }

        System.out.println("========================================\n");
    }

    public void demonstratePolymorphism() {
        List<Staff> staffList = getAllStaff();

        System.out.println("\n========================================");
        System.out.println("  POLYMORPHISM: Staff from Database");
        System.out.println("========================================");

        if (staffList.isEmpty()) {
            System.out.println("No staff to demonstrate.");
        } else {
            for (Staff s : staffList) {
                s.work();  // Polymorphic call!
            }
        }

        System.out.println("========================================\n");
    }
}