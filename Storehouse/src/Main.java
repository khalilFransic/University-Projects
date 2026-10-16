import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
Menu0 user = new Menu0();

      ArrayList<Items> items = ItemsFactory.createItems();

        Products crispy=ProductsFactory.createCrispy(items);
        Products burger = ProductsFactory.createBurger(items);
        Products pizza=ProductsFactory.createPizza(items);


ProductsLine line=new ProductsLine(01,"burgerWay",State.ACTIVE);
ProductsLine line2= new ProductsLine(02,"crispyWay",State.ACTIVE);
ProductsLine line3=new ProductsLine(03,"pizzaWay",State.ACTIVE);

        LocalDate start = LocalDate.of(2010, 10, 10);
        LocalDate end   = LocalDate.of(2010, 11, 10);
Tasks task=new Tasks(1,burger,30,"tonySister",TasksState.COMPLET,start,end);
Tasks task1=new Tasks(2,crispy,20,"tonySister",TasksState.UNDERIMP,start,end);
Tasks task2=new Tasks(3,pizza,10,"tonySister",TasksState.UNDERIMP,start,end);
//line.addTask(task);
//line2.addTask(task1);
//line3.addTask(task2);
        task.addProductLine(line);
        task1.addProductLine(line2);
        task2.addProductLine(line3);
SwingMethod.hashTasks.add(task);
SwingMethod.hashTasks.add(task1);
SwingMethod.hashTasks.add(task2);
SwingMethod.hashProductsLine.add(line);
SwingMethod.hashProductsLine.add(line2);
SwingMethod.hashProductsLine.add(line3);
SwingMethod.hashProducts.add(crispy);
SwingMethod.hashProducts.add(pizza);
SwingMethod.hashProducts.add(burger);
for(Items item:items){
    SwingMethod.hashItem.add(item);
}



//System.out.println(pizza.CheckItems());
Worker w= new Worker(task1,crispy,"crispyLine");
 w.start();














    }
}