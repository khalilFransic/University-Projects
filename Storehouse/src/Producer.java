public class Producer extends Thread{
      private ProductsLine line ;
     Producer(String name ,ProductsLine line){
        super(name);
        this.line=line;
    }
    public void run(){
        while(!line.IsFinished()){
            line.ProduceProducts();

        }
    }

}
