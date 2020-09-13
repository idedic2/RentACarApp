package ba.unsa.etf.rpr;

public class Client extends User{
    private String address;
    private String telephone;
//iz klijenta obrisati karitcu, i kartica ce bti u rez,
    public Client(int id, String firstName, String lastName, String email, String username, String password, String address, String telephone) throws NegativeNumberException {
        super(id, firstName, lastName, email, username, password);
        this.address = address;
        this.telephone = telephone;

    }
    @Override
    public String toString(){
        return super.getFirstName()+" "+super.getLastName()+": "+super.getUsername()+", "+getAddress();
    }

    public Client() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
