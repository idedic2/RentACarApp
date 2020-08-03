package sample;

import java.time.LocalDate;
import java.util.Date;

public class Card {
    private int id;
    private int cardNumber;
    private int code;
    private LocalDate expirationDate;

    public Card(int id, int cardNumber, int code, LocalDate expirationDate) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
