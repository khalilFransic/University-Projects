import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Products
{   int Num;
    String Name;
    ArrayList<Items> items;
    int Amount;
    int Require;


    public Products() {
    }

    public Products(int num, String name, ArrayList<Items> items, int amount , int require) {
        Num = num;
        Name = name;
        this.items = items;
        Amount = amount;
        Require = require;
    }

    public void setNum(int num) {
        Num = num;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setRequire(int require) {
        Require = require;
    }

    public int getNum() {
        return Num;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public int getAmount() {
        return Amount;
    }

    public int getRequire() {
        return Require;
    }

    public int CheckItems(){
        int min=items.get(0).getAvamount();
         int MinIndex = 0;
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).getAvamount() < min) {
                min = items.get(i).getAvamount();
                MinIndex = i;
            }
        }
//         int MaxIndex=0;
//        int Maxlim=items.get(0).getLimit();
//        for(Items i:items){
//            if(i.getLimit()>Maxlim)
//                Maxlim=i.getLimit();
//             MaxIndex= items.indexOf(i);
//        }
//        if(MinIndex==MaxIndex)
//            return  (min-Maxlim)/Require;
        int num1 =(items.get(MinIndex).Avamount-items.get(MinIndex).getLimit())/Require;//from the lowest item amount how may products can I make
//        int num2=(items.get(MaxIndex).Avamount-items.get(MaxIndex).getLimit())/Require;
//        if(num1>num2){
//            return num2;
//            }
//        else
            return num1;

}

public synchronized boolean DecreaseAmount(){
        Tasks task = new Tasks();
         int BookAmount=task.getReqAmount();
         Amount=Amount-BookAmount;
            BookAmount--;
            return true;
        }


    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();

        sb.append("PRODUCT :")
                .append(Num).append(";")
                .append(Name).append(";")
                .append(Amount).append(";")
                .append(Require).append(";")
                .append("ITEMS[");

        for (Items i : items) {
            sb.append(i.toFileFormat()).append("|");
        }

        sb.append("]");
        return sb.toString();
    }


    public ArrayList<Products> readFromFile() throws IOException {
        BufferedReader br =   new BufferedReader(new FileReader("info.txt"));
        String line;
        ArrayList<Products> products = new ArrayList<>();
           Items i = new Items();

        while( (line=br.readLine()) != null){
            if(line.startsWith("ITEMS")||line.startsWith("PRODUCTLNIE")||line.startsWith("TASK"))
                continue;
            String[] parts=line.split(":");
            String[] splitItems=parts[1].split("ITEMS\\[");
            String[] product=splitItems[0].split(";");
            int id=Integer.parseInt(product[0]);
            String name=product[1];
            int amount=Integer.parseInt(product[2]);
            int require=Integer.parseInt(product[3]);
            String itemsPart = splitItems[1].replace("]", "");
            ArrayList<Items> items  = i.readProductsItems(itemsPart);
            Products p=new Products(id,name,items,amount,require);
            products.add(p);

           }

        return products;
        }

        public Products readProductsForProductLine(String productsTxt) throws IOException {
            BufferedReader br =   new BufferedReader(new FileReader("info.txt"));
            String line;
            Items i = new Items();

            line= br.readLine();
           String[] productsArr=productsTxt.split(":");
            String[] product=productsArr[0].split(";");
            int id=Integer.parseInt(product[0]);
            String name=product[1];
            int amount=Integer.parseInt(product[2]);
            int require=Integer.parseInt(product[3]);
            String itemsPart = productsArr[1].replace("]", "");
            ArrayList<Items> items  = i.readProductsItems(itemsPart);
            Products p=new Products(id,name,items,amount,require);
            return p;

            }

        }
