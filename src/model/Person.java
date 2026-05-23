package model;

// Abstract class - cannot be instantiated directly
public abstract class Person {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String email;
    
    // Constructor
    public Person(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFullName() { return firstName + " " + lastName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // Abstract method - forces child classes to implement
    public abstract String getRole();
    
    @Override
    public String toString() {
        return getFullName() + " (" + getRole() + ")";
    }
}