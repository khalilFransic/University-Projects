import java.util.ArrayList;

public class ItemsFactory {
//    static ReadInfo r= new ReadInfo();

    public static ArrayList<Items> createItems() {

        ArrayList<Items> items = new ArrayList<>();

        items.add(new Items(100,"chicken","food",10,100));
        items.add(new Items(102,"beef","food",20,70));
        items.add(new Items(103,"whiteBread","food",5,100));
        items.add(new Items(104,"brownBread","food",5,100));
        items.add(new Items(105,"pickle","food",2,200));
        items.add(new Items(106,"lettuce","food",2,200));
        items.add(new Items(107,"pork","food",20,70));
        items.add(new Items(108,"onion","food",1,200));
        items.add(new Items(109,"oil","food",10,200));
        items.add(new Items(110,"rice","food",10,200));
        items.add(new Items(111,"tomato","food",1,500));
        items.add(new Items(112,"cheese","food",7,500));
        items.add(new Items(113,"paste","food",5,300));
        items.add(new Items(114,"sauce","food",5,340));
        items.add(new Items(115,"flavour","flavour",4,600));

        return items;
    }

//    public static ArrayList<Items> receiveItems() {
//        return r.getItemslist();
//    }


}
