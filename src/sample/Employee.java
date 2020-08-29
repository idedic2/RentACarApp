package sample;

public class Employee extends User{
    private String admin;
    public Employee(int id, String firstName, String lastName, String email, String username, String password, String admin) throws NegativeNumberException {
        super(id, firstName, lastName, email, username, password);
        this.admin=admin;
    }

    public Employee() {
        super();
    }

    public String getAdmin() {
        return admin;
    }
}
