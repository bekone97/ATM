import java.time.LocalDate;

public class Card {
    private String number;
    private int pin;
    private double balance;
    private boolean isBlocked;
    private int count;
    private LocalDate date ;

    private Card(String number, int pin, double balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
        this.isBlocked=false;
        this.count=0;
        this.date=null;
    }

    private Card(String number, int pin, double balance, int count,boolean status,  LocalDate date) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
        this.isBlocked = status;
        this.count = count;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlock(boolean block) {
        this.isBlocked = block;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    static public Card getNewCard(String number,int pin, double balance, int count, boolean status,LocalDate date) {
        if (((number.length() == 19&&number.matches(ATM.TYPE_NUMBER))  )) {
            return new Card(number, pin, balance,count , status,date );
        } else {
            System.out.println("Вы ввели неправильно номер карты, попробуйте еще раз");
            return null;
        }
    }
}
