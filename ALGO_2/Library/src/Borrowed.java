import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Borrowed {
    String name;
    int age;
    boolean graduated;
    List<String> returnTime =new ArrayList<>();
    List<Integer> bookId = new ArrayList<>();
//    LocalDate date;
    int limit=0;
    ArrayList<LocalDate> Date = new ArrayList<>(3);

    public String toString() {return  name + Date+ returnTime;}
    public Borrowed(String name, int age, boolean graduated, String returnTime) {
        this.name = name;
        this.age = age;
        this.graduated = graduated;
        this.returnTime.add(limit, returnTime);
    }

}


