import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class ReadInfo {
    Items items=new Items();
    ArrayList<Items> itemslist=new ArrayList<Items>();
    Tasks tasks=new Tasks();
    ArrayList<Tasks> tasksList =new ArrayList<>();
    Products products=new Products();
    ArrayList<Products> productsList =new ArrayList<>();
    ProductsLine productsLine=new ProductsLine();
    ArrayList<ProductsLine> productsLineList=new ArrayList<>();

    public Items getItems() {
        return items;
    }

    public ArrayList<Items> getItemslist() {
        return itemslist;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public ArrayList<Tasks> getTasksList() {
        return tasksList;
    }

    public Products getProducts() {
        return products;
    }

    public ArrayList<Products> getProductsList() {
        return productsList;
    }

    public ProductsLine getProductsLine() {
        return productsLine;
    }

    public ArrayList<ProductsLine> getProductsLineList() {
        return productsLineList;
    }

    public ReadInfo()  {



        try {
            itemslist=items.read8FromFile();
        } catch (IOException e) {
            throw new RuntimeException(ExError.getErrorMsg(107));
        }




        try {
            productsList=products.readFromFile();
        }
        catch (IOException e) {
            throw new RuntimeException(ExError.getErrorMsg(107));
        }




        try {
            productsLineList=productsLine.readProductLineFile();
        }
        catch (IOException e) {
            throw new RuntimeException(ExError.getErrorMsg(107));
        }




        try {
            tasksList=tasks.readTaskFromFile();
        }
        catch (IOException e) {
            throw new RuntimeException(ExError.getErrorMsg(107));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }






}
