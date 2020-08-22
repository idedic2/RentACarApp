package sample;

import java.time.LocalDate;
import java.util.Date;

public class Card {
    private int id;
    private String cardNumber;
    private String code;
    private LocalDate expirationDate;

    public Card(int id, String cardNumber, String code, LocalDate expirationDate) throws NegativeNumberException {
        if(id<0) throw new NegativeNumberException("Unijeli ste negativan broj");
        this.id = id;
        this.cardNumber = cardNumber;
        this.code = code;
        this.expirationDate = expirationDate;
    }
    public Card(){

}

    public int getId() {
        return id;
    }

    public void setId(int id) throws NegativeNumberException {
        if(id<0) throw new NegativeNumberException("Unijeli ste negativan broj");
        else
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
