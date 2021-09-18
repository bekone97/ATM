import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static final String SPACE = " ";
    public static final int NUMBER_INDEX = 0;
    public static final int PIN_INDEX = 1;
    public static final int BALANCE_INDEX = 2;
    public static final int COUND_INDEX = 3;
    public static final int STATUS_INDEX = 4;
    public static final int DATE_INDEX = 5;
    private static File file;
public Repository(String string){
    this.file = new File(string);
}
    public  void saveCards(List<Card> cardList){
        try (FileWriter fileWriter = new FileWriter(file)){
            for (Card card:
                    cardList) {
                fileWriter.write(new StringBuilder().append(card.getNumber()).append(SPACE).append(card.getPin())
                        .append(SPACE).append(card.getBalance()).append(SPACE).append(card.getCount())
                        .append(SPACE).append(card.isBlocked()).append(SPACE).append(card.getDate())
                        .append("\n").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  List<Card> getCards(){
        List<Card> cardList = new ArrayList<>();
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(file))) {
            String line;
            while((line=bufferedReader.readLine())!=null){
                String[] cardInformation = line.split(SPACE);
                   cardList.add(Card.getNewCard(cardInformation[NUMBER_INDEX],
                           Integer.parseInt(cardInformation[PIN_INDEX]),
                           Double.parseDouble(cardInformation[BALANCE_INDEX]),
                           Integer.parseInt(cardInformation[COUND_INDEX]),
                           Boolean.parseBoolean(cardInformation[STATUS_INDEX]),
                           cardInformation[DATE_INDEX].equals("null")?null:LocalDate.parse(cardInformation[DATE_INDEX])));
                }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return cardList;
    }
}
