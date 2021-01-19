package tutorial;

import com.bot.ranksystem_20.model.Card;
import org.junit.jupiter.api.Test;

import java.io.*;

public class Tutorial {

    @Test
    public void test() {
        try (OutputStream outputStream = new FileOutputStream("C:\\book\\test.txt");
             ObjectOutput objectOutput = new ObjectOutputStream(outputStream);) {
            Card card = new Card();
            card.setPoints(100);
            card.setId(1L);
            card.setUrl("url watafucka");
            objectOutput.writeObject(card);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = new FileInputStream("C:\\book\\test.txt");
             ObjectInput objectInput = new ObjectInputStream(inputStream);) {
            Card card = (Card) objectInput.readObject();
            System.out.println(card);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
