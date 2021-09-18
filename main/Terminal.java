import java.util.Scanner;

public class Terminal {
    private Scanner scanner;


    public String getInformation(){
    return SingleScanner.getSingleScanner().nextLine();
    }
    public int getPin(){
        System.out.println("Enter pin-code of card");
        return Integer.parseInt(getInformation());
    }
}
