import java.time.LocalDate;
import java.util.*;

public class SwingMethod {
    Scanner in = new Scanner(System.in);

   static Set<ProductsLine> hashProductsLine = new HashSet<ProductsLine>();
   static Set<Items> hashItem = new HashSet<Items>();
   static Set<Tasks> hashTasks = new HashSet<Tasks>();
    static Set<Products> hashProducts = new HashSet<Products>();


    public void addNewProductsLine(int number, String name, String stateInput, int taskNumber, int numOfTasks) {
        ProductsLine Line = new ProductsLine(number, name, State.valueOf(stateInput.toUpperCase()));
        ArrayList<Tasks> tasks=new ArrayList<>();
        for (int i = 0; i < numOfTasks; i++) {
            for (Tasks task : hashTasks) {
                if(numOfTasks == taskNumber){
                    tasks.add(task);
                }
            }
        }
        Line.setTasks(tasks);


        hashProductsLine.add(Line);
    }


    public void changeProductsState(int ID, String state) {
        ProductsLine Line;

        for (ProductsLine i : hashProductsLine) {
            if (i.getLineNum() == ID) {
                System.out.println("Enter the new State");
                i.setState(State.valueOf(state.toUpperCase()));
            } else
                throw new RuntimeException(ExError.getErrorMsg(505));
        }
    }

    public void addNewItem(int ID, String name, String category, double price, int avamount) {
        Items item = new Items(ID, name, category, price, avamount);

        hashItem.add(item);
    }

    public void addNewAmount() {
        System.out.println("please enter the item name");
        boolean check = false;
        String itemName = in.nextLine();
        for (Items i : hashItem) {
            if (i.getName().equals(itemName)) {
                System.out.println("please enter the amount of the item");
                int n = i.getAvamount();
                i.setAvamount(in.nextInt() + n);
                check = true;
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(100));
        }
    }

    public void showItems() {
        for (Items i : hashItem) {
            System.out.println(i.getName() + ":" + i.getAvamount() + " Cat:" + i.getCategory() + " P:" + i.getPrice() + " ID:" + i.getID());
        }
    }

    public void changItems(String name, Double price, int avamount) {
        boolean check = false;
        for (Items i : hashItem) {
            if (i.getName().equals(name)) {
                i.setPrice(price);
                i.setAvamount(avamount);
                check = true;
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(100));
        }
    }

    public void deleteItems(String name) {
        boolean check = false;
        Items f = null;
        for (Items i : hashItem) {
            if (i.getName().equals(name)) {
                f=i;
                check = true;
            }
        }
        hashItem.remove(f);
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(100));
        }
    }

    public List<Items> searchItemsByCategory(String category) {

        List<Items> result = new ArrayList<>();

        for (Items i : hashItem) {
            if (i.getCategory().equalsIgnoreCase(category)) {
                result.add(i);
            }
        }

        return result;
    }

    public Items searchItemsByName(String name) {
        for (Items i : hashItem) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    public void addTasks(int missionNumber, String product, String productLineName, int reqAmount, String agent, String state, LocalDate startingDate, LocalDate endingDate ) {
        boolean check = false;
        boolean check1 = false;
        Products products = null;
        for(Products p : hashProducts){
            if(product.equals(p.getName())){
                products = p;
                check = true;
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(101));
        }

        Tasks task = new Tasks(missionNumber, products, reqAmount, agent, TasksState.valueOf(state.toUpperCase()), startingDate, endingDate);

        for (ProductsLine i : hashProductsLine) {
            if (productLineName.equalsIgnoreCase(i.getLineName())) {
                task.setLine(i);
                i.setProducts(task.getProducts());
                Producer p = new Producer("Producer",i);
                p.start();
                try {
                    p.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                check1 = true;
            }
        }
        if (!check1) {
            throw new RuntimeException(ExError.getErrorMsg(102));
        }
        hashTasks.add(task);
    }

    public void deleteTasks(int ID) {
        boolean check = false;
        Tasks task = null;
        for (Tasks i : hashTasks) {
            if (i.getMissionNumber() == ID) {
                task = i;
                check = true;
            }
        }
        hashTasks.remove(task);
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(103));
        }
    }

    public List<Object[]> TasksLine(String search) {
        List<Object[]> result = new ArrayList<>();
        boolean check = false;
        for (ProductsLine i : hashProductsLine) {
            if (i.getLineName().equalsIgnoreCase(search)) {
                for (Tasks task : i.getTasks()) {
                    Object[] row = new Object[]{task.getMissionNumber(), task.getState().toString(), task.getAgent(), task.getStartingDate(), task.getEndingDate()};
                    result.add(row);
                }
                check = true;
            }
            if (!check) {
                throw new RuntimeException(ExError.getErrorMsg(102));
            }
        }
        return result;
    }

    public List<Object[]> searchTasksProducts(String nameProduct) {
        List<Object[]> result = new ArrayList<>();
        boolean check = false;
        System.out.println("please enter the name of the products");
        for (Tasks i : hashTasks) {
            if (i.getProducts().getName().equalsIgnoreCase(nameProduct)) {
                Object[] row = new Object[]{i.getMissionNumber(), i.getState().toString(), i.getAgent(), i.getStartingDate(), i.getEndingDate()};
                result.add(row);
                check = true;
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(101));
        }
        return result;
    }

    public List<Object[]> showTasks() {
        boolean check = false;
        List<Object[]> result = new ArrayList<>();
        for (Tasks i : hashTasks) {
            if (i.getState().equals(TasksState.UNDERIMP) || i.getState().equals(TasksState.COMPLET)) {
                Object[] row = new Object[]{i.getMissionNumber(), i.getState().toString(), i.getAgent(), i.getStartingDate(), i.getEndingDate()};
                result.add(row);
                System.out.println(row);
                System.out.println("Task state = " + i.getState());
                check = true;
            }

        }
        if (!check) {
            System.out.println("there are no active tasks in the database");
        }
        System.out.println("hashTasks size = " + hashTasks.size());



        return result;
    }

    public List<Object[]> showActiveTasks() {
        boolean check = false;
        List<Object[]> result = new ArrayList<>();
        for (Tasks i : hashTasks) {
            if (i.getState().equals(TasksState.COMPLET)) {
                Object[] row = new Object[]{i.getMissionNumber(), i.getState().toString(), i.getAgent(), i.getStartingDate(), i.getEndingDate()};
                result.add(row);
                check = true;
            }

        }
        if (!check) {
            System.out.println("there are no active tasks in the database");
        }
        return result;
    }

    public List<Object[]> showUnderImpTasks() {
        boolean check = false;
        List<Object[]> result = new ArrayList<>();
        for (Tasks i : hashTasks) {
            if (i.getState().equals(TasksState.UNDERIMP)) {
                Object[] row = new Object[]{i.getMissionNumber(), i.getState().toString(), i.getAgent(), i.getStartingDate(), i.getEndingDate()};
                result.add(row);
                check = true;
            }

        }
        if (!check) {
            System.out.println("there are no active tasks in the database");
        }
        return result;
    }

//    public void showTheStopTasks() {
//        boolean check = false;
//        for (Tasks i : hashTasks) {
//            if (i.getState().equals(TasksState.COMPLET)) {
//                System.out.println("the Task is :" + i.getMissionNumber());
//                check = true;
//            }
//        }
//        if (!check) {
//            System.out.println("there are no stop tasks in the database");
//        }
//    }

    public void showTheTasks() {
        boolean check = false;
        ProductsLine productsLine = null;
        System.out.println("please enter the name of the products");
        String nameProducts = in.nextLine();
        System.out.println("How many tasks does the productLine did ");
        int numTasks = in.nextInt();
        for(int e=0;e<numTasks;e++) {
            check = false;
            System.out.println("please enter the ID of the Tasks ");
            int idTasks = in.nextInt();
            for (ProductsLine i : hashProductsLine) {
                for (Tasks j : i.getTasks()) {
                    if (i.getProducts().getName() == nameProducts && idTasks == j.getMissionNumber()) {
                        check = true;
                        productsLine = i;
                    }
                }
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(102));
        }
        if (check) {
            System.out.println("the productLine is :"+productsLine.getLineNum());
        }
    }


    public String showSpecificProducts(int ID) {
        boolean check = false;
        for (ProductsLine i : hashProductsLine) {
            if (i.getLineNum() == ID) {
               return i.getProducts().getName();
            }
        }
        if (!check) {
            throw new RuntimeException(ExError.getErrorMsg(102));
        }
        return null;
    }

    public void PorductLineTasks(String name) {

    }

    public List<Object[]> showAllProductsLine() {
        List<Object[]> result = new ArrayList<>();
        for (ProductsLine i : hashProductsLine) {
            System.out.println("the Product from the line :" + i.getLineNum() + "is " + i.getProducts().getName());
            Object[] row = new Object[]{i.getLineNum(),i.getLineName(), i.getProducts().getName()};
            result.add(row);
        }
        return result;
    }

/*public void showValueProducts(){
        boolean check=false;
        System.out.println("please enter the Date ");
        String date = in.nextLine();
        for(Tasks  i : hashTasks){
            if(i.getDate1()==date){
                System.out.println("the Product is :"+i.getProducts().getName() );
            }
        }
    }
}*/




}