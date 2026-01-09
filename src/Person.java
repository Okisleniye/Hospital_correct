public class Person {
    private int id;
    private String name;
    private String email;

    public Person(int id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
    }

    public Person() {
        this.id = 0;
        this.name = "Unknown";
        this.email = "Unknown";
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id > 0) this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name != null && !name.isEmpty()) this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && email.contains("@")) this.email = email;
    }

    public void displayRole() {
        System.out.println("Role: General Hospital Staff/Person");
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Email: " + email;
    }
}