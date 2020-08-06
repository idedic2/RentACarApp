package sample;

public class Client extends User{
    private String address;
    private String telephone;
    private Card card;

    public Client(int id, String firstName, String lastName, String email, String username, String password, String address, String telephone, Card card) {
        super(id, firstName, lastName, email, username, password);
        this.address = address;
        this.telephone = telephone;
        this.card = card;
    }

    public Client(int id, String firstName, String lastName, String email, String username, String password, String address, String telephone) {
        super(id, firstName, lastName, email, username, password);
        this.address = address;
        this.telephone = telephone;
        this.card = null;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
