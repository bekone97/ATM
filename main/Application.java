
public class Application {
    public static void main(String[] args) {
     Terminal terminal = new Terminal();
     Repository repository = new Repository("main\\Cards.txt");
     ATM atmFirst = new ATM(terminal,repository);
    }
}
