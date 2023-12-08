package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class Utils {

    public static void sendMessage(PrintStream out, String message) {
        out.println(message);
    }

    public static String getMessage(BufferedReader in) {
        try{
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
