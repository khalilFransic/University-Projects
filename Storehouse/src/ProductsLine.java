import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductsLine {
    int LineNum;
    String LineName;
    private State state;
     ArrayList<Tasks> tasks ;
    Products products;



    int ReqProduce;



    public ProductsLine() {
    }

    public ProductsLine(int lineNum, String lineName, State state /*String task /* products*/) {
        LineNum = lineNum;
        LineName = lineName;
        this.state = state;
        this.tasks = new ArrayList<>();
       // this.task = task;
       // this.products = products;
    }

    public int getReqProduce() {
        return ReqProduce;
    }

    public void setReqProduce(int reqProduce) {
        ReqProduce = reqProduce;
    }

    public void setLineNum(int lineNum) {
        LineNum = lineNum;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getLineNum() {
        return LineNum;
    }

    public String getLineName() {
        return LineName;
    }

    public State getState() {
        return state;
    }

    public void setTasks(ArrayList< Tasks> tasks) {
        this.tasks = tasks;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ArrayList<Tasks> getTasks() {
        return tasks;
    }

    public Products getProducts() {
        return products;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ProductsLine that = (ProductsLine) o;
        return LineNum == that.LineNum;
    }

    public int hashCode(){
        return Objects.hash(LineNum);
    }

    int NumOfProducts=0;

    public synchronized int ProduceProducts( ){

        if(ReqProduce>100)
            throw new RuntimeException(ExError.getErrorMsg(404));
           int remain=0;

            for (Items i : products.getItems()) {
                 remain = i.getAvamount() - products.Require;
                i.setAvamount(remain);
            }

            NumOfProducts = products.Amount++;
            products.setAmount(NumOfProducts+products.Amount);
        return NumOfProducts;
    }

    public synchronized boolean IsFinished(){
        return NumOfProducts==ReqProduce;
    }

    public void addTask(Tasks task) {
        tasks.add(task);
        this.products = task.getProducts();
        if (task.getLine() != this) {
            task.setLine(this);
        }
    }


    public String  toFormat(){
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCTLINE :")
        .append(LineNum)
        .append(",")
        .append(LineName)
        .append(",")
        .append(state)
        .append(",")
        .append("TASKS[");
        for (Tasks task : tasks) {
            sb.append(task.toFormat()).append("|");
        }
        sb.append("]")
        .append("PRODUCT[");
        products.toFileFormat();
        sb.append("]");

        return sb.toString();

    }


    public ArrayList<ProductsLine> readProductLineFile() throws IOException {
        BufferedReader br = new BufferedReader( new FileReader("Info.txt"));
        String line;
        ArrayList<ProductsLine> productsLines = new ArrayList<>();

        line=br.readLine();
        Products l=new Products();
        while (line != null) {
            if(line.startsWith("ITEMS")||line.startsWith("PRODUCT")|| line.startsWith("TASK"))
                continue;
                String[] part= line.split(":");
                String[] nPart= part[1].split("PRODUCT\\[");
                String[] lPart= nPart[0].split("TASKS\\[");
                String[] aPart= lPart[0].split(",");
                int num=Integer.parseInt(aPart[0]);
                String name=aPart[1];
                String state=aPart[2];
                String tasks=aPart[3];
                String productsParts= nPart[1].replace("]","");
                Products p= l.readProductsForProductLine(productsParts);

               ProductsLine pl= new ProductsLine(num,name,State.valueOf(state));
               productsLines.add(pl);
        }
        return productsLines;

    }


    public ProductsLine readProductLineForTasks(String productLineTxt) throws IOException {
        Products l=new Products();

        String[] part= productLineTxt.split(":");
        String[] nParts = part[1].split("\\[");
        String[] lPart= nParts[0].split(",");
        int num=Integer.parseInt(lPart[0]);
        String name=lPart[1];
        String state=lPart[2];
        String tasks=lPart[3];
        String productsParts= nParts[1].replace("]","");
        Products p= l.readProductsForProductLine(productsParts);

        return new ProductsLine(num,name,State.valueOf(state));
    }







}



