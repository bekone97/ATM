import java.time.LocalDate;
import java.util.List;

public class ATM {
    public static final String TYPE_NUMBER = "([0-9]{4}[-]){3}[0-9]{4}";
    private Terminal terminal;
    private Repository repository;
    public static final double LIMIT_FOR_ADD = 1_000_000.00;
    public static final double LIMIT_FOR_WITHDRAW = 1_000_000.00;
    private  List<Card> cardList;
public ATM(Terminal terminal,Repository repository){
    this.repository=repository;
    this.terminal = terminal;
    this.cardList= repository.getCards();
    menu();
}
public  Card getUserCard(){
    Card card;
    do {
        System.out.println("Enter the card number");
       card = getCard(terminal.getInformation());
    }while(card==null);
    int pin = 0;
    do {
        if (checkStatus(card)){
            System.out.println("This card is blocked");
           checkChoiceOfUser(true);
           card = getUserCard();
        }else {
            try {
                pin = terminal.getPin();
            }catch (NumberFormatException e){
                System.err.println("You entered the incorrect data");
            }
        }
    }while(!checkPin(card,pin));
    return card;
}
    public double  getBalance(Card card){
        Double cardBalance = card.getBalance();
        System.out.println("Current card balance is :" +cardBalance);
        return cardBalance;
    }
    public  void addBalance(Card card,Double sum){
        card.setBalance(card.getBalance()+sum);
        System.out.println("Current balance is :"+card.getBalance());
    }
    private double EnterSumForAmount() {
        double sum;
        try {
            sum = Double.parseDouble(terminal.getInformation());
            while (checkMoneyForAmount(sum)) {
                sum = Double.parseDouble(terminal.getInformation());
            }
        }catch (NumberFormatException e){
            System.err.println("You entered incorrect data, please try again");
            sum= EnterSumForAmount();
        }
        return sum;
    }
    public static boolean checkMoneyForAmount( double money){
        boolean incorrectSum=money<0||money> LIMIT_FOR_ADD;
        if (incorrectSum){
            System.out.println("You entered incorrect sum, please try again");
        }
        return incorrectSum||money<0;

    }
    public  void withdrawMoney(Card card){
        double money = EnterForWithdraw(card);
        card.setBalance(card.getBalance() -money);
        System.out.println("Current balance is :"+card.getBalance());
    }
    private double EnterForWithdraw(Card card) {
        System.out.println("Enter sum  you want to withdraw");
        double money =0;
        do {
            try {
                money = Double.parseDouble(terminal.getInformation());
            }catch (NumberFormatException e){
                System.err.println("You entered incorrect sum");
                money=EnterForWithdraw(card);
            }
        }while (checkMoneyForWithdraw(card,money));
        return money;
    }

    public static boolean checkMoneyForWithdraw(Card card, double money){
    boolean incorrectSum=money>card.getBalance()||money> LIMIT_FOR_WITHDRAW;
        if (incorrectSum||money<0){
            System.out.println("You entered incorrect sum");
        }
            return incorrectSum||money<0;

    }

    public  boolean checkNumber(String number) {
        if (((number.length() == 19)&&number.matches(TYPE_NUMBER))) {
            return true;
        } else {
            System.out.println("You entered incorrect data");
            checkChoiceOfUser(true);
            return false;
        }
    }

    private void checkChoiceOfUser(boolean status) {
        while (status) {
            System.out.println("If you want to continue work, enter number 1\n" +
                    "If you want to complete the work, enter any other number");
            try {
                switch (Integer.parseInt(terminal.getInformation())) {
                    case 1:
                        status = false;
                        break;
                    default:
                        repository.saveCards(cardList);
                        System.exit(0);
                }
            } catch (NumberFormatException e) {
                System.err.println("You entered incorrect data\"");
            }
        }
    }

    public  boolean checkStatus(Card card) {
    boolean status;
    LocalDate now = LocalDate.now();
        if (card.isBlocked()) {
            status = checkDateOfBlocked(card, now);
        }else if (card.getCount() > 2) {
            status = makeBlock(card, now);
        } else {
                status = false;
            }
        return status;
        }

    private boolean makeBlock(Card card, LocalDate date) {
        card.setBlock(true);
        card.setDate(date);
        return true;
    }

    private boolean checkDateOfBlocked(Card card, LocalDate now) {
        boolean status=card.getDate().plusDays(3).isBefore(now)|| card.getDate().plusDays(3).isEqual(now);
        if (!status) {
            card.setBlock(false);
            card.setCount(0);
        }
        return status;
    }

    public  boolean checkPin(Card card,int pin) {
        if (card.getPin() == pin) {
            card.setCount(0);
            return true;
        } else {
            card.setCount(card.getCount()+1);
            return false;
    }
    }
    public  Card getCard(String number) {
        if (checkNumber(number)) {
            return cardList.stream()
                    .filter(card->card.getNumber().equals(number))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }


    public  void menu(){
        System.out.println("Hello");
        Card card = getUserCard();
        boolean status = false;
            do {
        System.out.println("If you'll enter  1 you can see balance of the card\n " +
                "If you'll enter  2 you can withdraw money from the card\n" +
                "If you'll enter  3  you can add money on the card\n" +
                "If you'll enter  4 you complete the work" );
                status = choiceOperation(card, status);
            }while (status);
        repository.saveCards(cardList);
        }

    private boolean choiceOperation(Card card, boolean status) {
        try {
            switch (Integer.parseInt(terminal.getInformation())) {
                case 1:
                    status = true;
                    getBalance(card);
                    break;
                case 2:
                    status = true;
                    withdrawMoney(card);
                    break;
                case 3:
                    System.out.println("\n" +
                            "Enter the amount by which you would like to increase the card balance");
                    status = true;
                    double sum = EnterSumForAmount();
                    addBalance(card,sum);
                    break;
                case 4:
                    repository.saveCards(cardList);
                    System.exit(0);
                    break;
                default:
                    System.out.println("You entered the incorrect number, try again");
                    status = true;
                    break;
            }
            checkChoiceOfUser(true);
        }catch (NumberFormatException e ){
            System.err.println("You entered the incorrect data");
        }
        return status;
    }
}




