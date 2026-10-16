import java.util.ArrayList;

public class ProductsFactory {
//    static ReadInfo r= new ReadInfo();

    public static Products createBurger(ArrayList<Items> items) {

        ArrayList<Items> burgerItems = new ArrayList<>();

        for (Items i : items) {
            if (i.getID() == 102 || i.getID() == 103 || i.getID() == 104 ||
                    i.getID() == 105 || i.getID() == 106 || i.getID() == 108) {
                burgerItems.add(i);
            }
        }

        return new Products(1000, "burger", burgerItems, 10, 3);
    }


    public static Products createPizza(ArrayList<Items> items) {

        ArrayList<Items> pizzaItems = new ArrayList<>();

        for (Items i : items) {
            if (i.getID() == 112 || i.getID() == 113 || i.getID() == 114 ||
                    i.getID() == 111) {
                pizzaItems.add(i);
            }
        }

        return new Products(1000, "Pizza", pizzaItems, 10, 3);
    }

    public static Products createCrispy(ArrayList<Items> items) {

        ArrayList<Items> crispyItems = new ArrayList<>();

        for (Items i : items) {
            if (i.getID()==109 || i.getID()==100|| i.getID()==115) {
                crispyItems.add(i);
            }
        }

        return new Products(1000,"burger", crispyItems,10,3);
    }



//    public static ArrayList<Products> receiveItems() {
//        return r.getProductsList();
//    }
}