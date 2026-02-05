package database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StaffDAO - Week 8 Enhanced
 * Complete CRUD operations + Advanced Search
 * - CREATE (INSERT) ✓
 * - READ (SELECT) ✓
 * - UPDATE ✓ NEW!
 * - DELETE ✓ NEW!
 * - SEARCH by name ✓ NEW!
 * - SEARCH by salary range ✓ NEW!
 */
public class StaffDAO {

    // ========================================
    // CREATE - INSERT OPERATIONS (Week 7)
    // ========================================

    /**
     * INSERT Chef into database
     */
    public boolean insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO staff (int staffId, String name, double salary, int experienceYears, String specialization) " +
                "VALUES (?, ?, ?, 'Doctor', ?, NULL)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, doctor.getName());
            statement.setDouble(2, doctor.getSalary());
            statement.setInt(3, doctor.getExperienceYears());
            statement.setString(4, doctor.getSpecialization());
            statement.setInt(5, doctor.getStaffId());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Doctor inserted: " + doctor.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Doctor failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    /**
     * INSERT Waiter into database
     */
    public boolean insertNurse(Nurse nurse) {
        String sql = "INSERT INTO staff (int staffId, String name, double salary, int experienceYears, int patientsAssigned) " +
                "VALUES (?, ?, ?, 'NURSE', NULL, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nurse.getName());
            statement.setDouble(2, nurse.getSalary());
            statement.setInt(3, nurse.getExperienceYears());
            statement.setInt(4, nurse.getPatientsAssigned());
            statement.setInt(4, nurse.getStaffId());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Nurse inserted: " + nurse.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Nurse failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    // ========================================
    // READ - SELECT OPERATIONS (Week 7)
    // ========================================

    /**
     * SELECT ALL staff members
     * @return List of Staff (Chef and Waiter objects)
     */
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

    /**
     * SELECT staff by ID
     */
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

    /**
     * SELECT all Chefs
     */
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

    /**
     * SELECT all Waiters
     */
    public List<Nurse> getAllNurse() {
        List<Nurse> nurses = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'WAITER' ORDER BY staff_id";

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

    // ========================================
    // WEEK 8: UPDATE OPERATION
    // ========================================

    /**
     * UPDATE Chef in database
     * @param chef Chef object with updated data
     * @return true if successful
     */

    // CHEF from DB
    // CHEF set change
    // update chef
    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experienceYears = ?, specialization = ? " +
                "WHERE staffId = ? AND staff_type = 'DOCTOR'";

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

    /**
     * UPDATE Waiter in database
     * @param waiter Waiter object with updated data
     * @return true if successful
     */
    public boolean updateNurse(Nurse nurse) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experience_years = ?, patientsAssigned = ? " +
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

    // ========================================
    // WEEK 8: DELETE OPERATION
    // ========================================

    /**
     * DELETE staff by ID
     * @param staffId ID of staff to delete
     * @return true if successful
     */
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

    // ========================================
    // WEEK 8: SEARCH BY NAME
    // ========================================

    /**
     * SEARCH staff by name (partial match, case-insensitive)
     * Example: searchByName("mur") finds "Murat", "Murray", etc.
     * @param name Name or partial name to search
     * @return List of matching staff
     */
    public List<Staff> searchByName(String name) {
        List<Staff> staffList = new ArrayList<>();

        // ILIKE for case-insensitive search, % for partial match
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

    // ========================================
    // WEEK 8: SEARCH BY SALARY RANGE
    // ========================================

    /**
     * SEARCH staff by salary range
     * @param minSalary Minimum salary (inclusive)
     * @param maxSalary Maximum salary (inclusive)
     * @return List of staff in salary range
     */
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

    /**
     * SEARCH staff with minimum salary
     * @param minSalary Minimum salary
     * @return List of staff earning at least minSalary
     */
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

    // ========================================
    // HELPER METHOD
    // ========================================

    /**
     * Extract Staff object from ResultSet
     * Creates Chef or Waiter based on staff_type
     */
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

    // ========================================
    // DISPLAY METHODS
    // ========================================

    /**
     * Display all staff in console
     */
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

    /**
     * Demonstrate polymorphism with database data
     */
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