import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class Items {
    int ID;
    String Name;
    String Category;
    double Price;
    int Avamount;
    final int Limit=50;

    public Items() {

    }




    public Items(int ID, String name, String category, double price, int avamount) {
        this.ID = ID;
        Name = name;
        Category = category;
        Price = price;
        Avamount = avamount;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }


    public void setCategory(String category) {
        Category = category;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public void setAvamount(int avamount) {
        Avamount = avamount;
    }



    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getCategory() {
        return Category;
    }

    public double getPrice() {
        return Price;
    }

    public int getAvamount() {
        return Avamount;
    }

    public int getLimit() {
        return Limit;
    }


    @Override
    public String toString() {
        return "Items{" +
                "ID=" +String.valueOf(ID) +
                ", Name='" + Name + '\'' +
                ", Category='" + Category + '\'' +
                ", Price=" + String.valueOf(Price) +
                ", Avamount=" + String.valueOf(Avamount) +
                ", Limit=" + String.valueOf(Limit) +
                '}';
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Items that = (Items) o;
        return ID == that.ID;
    }

    public int hashCode(){
        return Objects.hash(ID);
    }

    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();

        sb.append("ITEMS :")
                .append(ID).append(";")
                .append(Name).append(";")
                .append(Category).append(";")
                .append(Price).append(";")
                .append(Avamount);

        return sb.toString();

    }
    public ArrayList<Items> read8FromFile() throws IOException {
        BufferedReader bw = new BufferedReader(new FileReader("info.txt"));
        String Line;
        ArrayList<Items> listArr = new ArrayList<>();
        int ID1=0;
        String Name1=null;
        String Category1=null;
        int avamount1=0;
        Double price1=null;

        while( (Line=bw.readLine()) !=null){
            if(Line.startsWith("PRODUCT")||Line.startsWith("PRODUCTLINE")||Line.startsWith("TASK"))
                continue;
            String[] part= Line.split(":");
            String[] data =part[1].split(";");
            ID1=Integer.parseInt(data[0]);
            Name1=data[1];
            Category1=data[2];
            price1 =Double.parseDouble(data[3]);
            avamount1=Integer.parseInt(data[4]);
            Items items=new Items(ID1,Name1,Category1, price1,avamount1);
            listArr.add(items);
        }

        return listArr;

    }

    public ArrayList<Items> readProductsItems(String itemsText) {

        ArrayList<Items> itemsList = new ArrayList<>();

        String[] itemsArr = itemsText.split("\\|");

        for (String itemStr : itemsArr) {

            if (itemStr.isEmpty()) continue;

            String[] data = itemStr.split(";");

            Items item = new Items(
                    Integer.parseInt(data[0]), // ID
                    data[1],                   // Name
                    data[2],                   // Category
                    Double.parseDouble(data[3]), // Price
                    Integer.parseInt(data[4])  // Avamount
            );

            itemsList.add(item);
        }

        return itemsList;
    }




}


