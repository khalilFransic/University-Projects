public class Worker extends Thread
{
    private Tasks  task;
    private Products product;
    public Worker(Tasks task, Products product, String name) {
        super(name);
        this.task = task;
        this.product = product;
    }
    @Override
    public void run(){

        while(!task.isFinished()){
            if(product.DecreaseAmount()){
                task.Progress();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



}
