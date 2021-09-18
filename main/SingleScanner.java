import java.util.Scanner;

public class SingleScanner {
    private static Scanner instance;
    private SingleScanner(){
    }
    public static Scanner getSingleScanner(){
        if (instance==null ){
            instance=new Scanner(System.in);
        }
        return instance;
    }

    public static Scanner getScanner() {
        return instance;
    }
}
