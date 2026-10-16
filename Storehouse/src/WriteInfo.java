import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WriteInfo {


//    Set<Items> itemsSet = new HashSet<>();
//    Set<Products> productsset=new HashSet<>();
//    Set<Tasks> tasksSet=new HashSet<>();
//    Set<ProductsLine> productsLineSet=new HashSet<>();
//
//    public WriteInfo(Items items, ProductsLine productsLine, Products products, Tasks tasks, Set<Items> itemsSet, Set<Products> productsset, Set<Tasks> tasksSet, Set<ProductsLine> productsLineSet) {
//
//        this.itemsSet = itemsSet;
//        this.productsset = productsset;
//        this.tasksSet = tasksSet;
//        this.productsLineSet = productsLineSet;
//    }

    public  void printInfo() throws IOException {
        StringBuilder sb=new StringBuilder();

        for(Items i: SwingMethod.hashItem) {
           i.toFileFormat();
           sb.append(System.lineSeparator());
        }
        for ( Products p: SwingMethod.hashProducts) {
           p.toFileFormat();
           sb.append(System.lineSeparator());

        }
        for(Tasks t: SwingMethod.hashTasks) {
            t.toFormat();
            sb.append(System.lineSeparator());
        }
        for(ProductsLine p: SwingMethod.hashProductsLine) {
            p.toFormat();
            sb.append(System.lineSeparator());
        }

         FileWriter write=new FileWriter("src/info.txt");
        write.write(sb.toString());
        write.flush();
        write.close();
     }
}
