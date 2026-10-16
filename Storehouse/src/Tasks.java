import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class Tasks {
    int MissionNumber;
    Products products;
    int ReqAmount;
    String Agent;
    LocalDate startingDate;
    LocalDate endingDate;
    TasksState State;
    ProductsLine  Line;
    int Counter = 0;
    DateTimeFormatter sdf =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");



    public Tasks() {

    }

    public Tasks(int missionNumber, Products products, int reqAmount, String agent, TasksState state , LocalDate startingDate, LocalDate endingDate  ) {
        MissionNumber = missionNumber;
        this.products = products;
        ReqAmount = reqAmount;
        Agent = agent;
        State = state;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public int getMissionNumber() {
        return MissionNumber;
    }

    public void setMissionNumber(int missionNumber) {
        MissionNumber = missionNumber;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getReqAmount() {
        return ReqAmount;
    }

    public void setReqAmount(int reqAmount) {
        ReqAmount = reqAmount;
    }

    public String getAgent () {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public TasksState getState() {
        return State;
    }

    public void setState(TasksState state) {
        State = state;
    }

    public ProductsLine getLine() {
        return Line;
    }

    public void setLine(ProductsLine line) {
        Line = line;
    }

    public int getCounter() {
        return Counter;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }



    public  void Progress (){

     Counter++;

   if(ReqAmount<=0)
         throw new RuntimeException(ExError.getErrorMsg(101));

     if(ReqAmount> products.CheckItems())
         throw new IllegalStateException(ExError.getErrorMsg(105));

     products.DecreaseAmount();

     System.out.println("The Progress is "+getProgressPercentage() + "%");

 }

    public void addProductLine(ProductsLine productLine) {
        this.Line = productLine;
        if (!productLine.getTasks().contains(this)) {
            productLine.addTask(this);
        }
    }

 public synchronized double getProgressPercentage(){
     return (double) (Counter * 100) /ReqAmount;
 }
    public synchronized boolean isFinished(){
     return Counter==ReqAmount;
    }

    public String toFormat(){
        StringBuilder sb =new StringBuilder();
        sb.append("TASKS:")
        .append(MissionNumber)
        .append(",")
        .append(ReqAmount)
        .append(",")
        .append(Agent)
        .append(",")
        .append(State)
        .append(",")
        .append("PRODUCT[")
        .append(products.toFileFormat())
        .append("]")
        .append("LINE[");

        sb.append(Line.toFormat())

        .append("]");
        return sb.toString();
    }


    public ArrayList<Tasks> readTaskFromFile() throws IOException, ParseException {
        BufferedReader br =   new BufferedReader(new FileReader("Info.txt"));
        ArrayList<Tasks> tasks = new ArrayList<>();
        String line;

        while ((line=br.readLine())!=null){
            if(line.startsWith("PRODUCT")||line.startsWith("PRODUCTLINE ")||line.startsWith("ITEMS"))
                continue;
            String[] split = line.split(":");
            String[] splitArr =split[1].split("LINE\\[");

            String lineI= splitArr[1];
            ProductsLine pl= Line.readProductLineForTasks(lineI);

            String[] splitArr2=splitArr[0].split("PRODUCT\\[");
            String productArr=splitArr2[1];
            Products p=products.readProductsForProductLine(productArr);

           String[] tasksArr=splitArr2[0].split(",");
            int missionNumber=Integer.parseInt(tasksArr[0]);
            int reqAmount=Integer.parseInt(tasksArr[1]);
            String agent= tasksArr[2];
            String s= String.valueOf(TasksState.valueOf((tasksArr[3])));
            String startStr = line.split("START\\[")[1].split("]")[0];
            String endStr = line.split("END\\[")[1].split("]")[0];
            LocalDate startDate =
                    LocalDate.parse(startStr.trim(), sdf);
            LocalDate endDate =
                    LocalDate.parse(endStr.trim(), sdf);
            Tasks t=new Tasks(missionNumber,p,reqAmount,agent,State,startDate,endDate);
            tasks.add(t);
            }
        return tasks;
        }

//        public ArrayList<Tasks> readTasksForProductLine(String taskLine) throws IOException {
//        ArrayList<Tasks> tasks = new ArrayList<>();
//        String[] split = taskLine.split("\\|");
//        for(String spl : split){
//
//            String[] tSplit = spl.split(":");
//            String[] splitArr = tSplit[1].split("LINE\\[");
//            String lineI= splitArr[1];
//            ProductsLine pl= Line.readProductLineForTasks(lineI);
//
//            String[] splitArr2=splitArr[0].split("PRODUCT\\[");
//            String productArr=splitArr2[1];
//            Products p=products.readProductsForProductLine(productArr);
//
//            String[] tasksArr=splitArr2[0].split(",");
//            int missionNumber=Integer.parseInt(tasksArr[0]);
//            int reqAmount=Integer.parseInt(tasksArr[1]);
//            String agent= tasksArr[2];
//            String s= String.valueOf(TasksState.valueOf((tasksArr[3])));
//
//
//            Tasks t=new Tasks(missionNumber,p,reqAmount,agent,State);
//            tasks.add(t);
//
//        }
//
//        return tasks;
//
//
//        }

    }
