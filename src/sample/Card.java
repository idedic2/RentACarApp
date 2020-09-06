package sample;

import java.time.LocalDate;
import java.util.Date;

public class Card {
    private int id;
    private String cardNumber;
    private String code;
    private LocalDate expirationDate;

    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }
    private boolean validateCode(String code){
        if(code.length()!=3 && code.length()!=4)return false;
        if(!allDigits(code))return false;
        return true;
    }
    private boolean validateCardNumber(String cardNumber){
        if(cardNumber.length()!=16)return false;
        if(!allDigits(cardNumber))return false;
        return true;
    }
    private boolean validateExpirationDate(LocalDate expirationDate){
        if(expirationDate.getYear()<LocalDate.now().getYear())return false;
        if((expirationDate.getYear()==LocalDate.now().getYear()) && expirationDate.getMonth().getValue()<LocalDate.now().getMonth().getValue())
            return false;
        return true;
    }
    public Card(int id, String cardNumber, String code, LocalDate expirationDate) throws NegativeNumberException {
        if(id<0) throw new NegativeNumberException("Unijeli ste negativan broj");
        if(!validateCode(code)) throw new IllegalArgumentException("Unijeli ste nevalidan kod");
        if(!validateCardNumber(cardNumber))throw new IllegalArgumentException("Unijeli ste nevalidan broj kartice");
        if(!validateExpirationDate(expirationDate))throw new IllegalArgumentException("Unijeli ste nevalidan datum isteka kartice");
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
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if(!validateCardNumber(cardNumber))throw new IllegalArgumentException("Unijeli ste nevalidan broj kartice");
        this.cardNumber = cardNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if(!validateCode(code)) throw new IllegalArgumentException("Unijeli ste nevalidan kod");
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if(!validateExpirationDate(expirationDate))throw new IllegalArgumentException("Unijeli ste nevalidan datum isteka kartice");
        this.expirationDate = expirationDate;
    }
}
