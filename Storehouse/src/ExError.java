import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExError {

    public static String getErrorMsg(int errorCode){

        try {
            File file = new File("src/Error.txt");
            return Files.lines(Paths.get(file.toURI()))
                    .filter(Line -> Line.startsWith(errorCode+"="))
                    .map(line ->line.split("=",2)[1])
                    .findFirst()
                    .orElse("file not found");
        } catch (IOException e) {
            return "Error file not found";

        }

    }
}
