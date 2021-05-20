
/**
 * Write a description of class TQueue here.
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
public class TQueue
{
    // instance variables - replace the example below with your own
    private NQueue lowP;
    private NQueue highP;

    /**
     * Constructor for objects of class PQueue
     */
    public TQueue()
    {
        // initialise instance variables
        lowP = new NQueue();
        highP = new NQueue();
    }
    void enqueue(element name, boolean high){
        if (high){
            highP.enqueue(name);
        }
        else {
            lowP.enqueue(name);
        }
    }
    void enqueue(element name){
            lowP.enqueue(name);
    }
    element dequeue(){
        if (highP.queueEmpty()) {
            return lowP.dequeue();
        }
        else{
            return highP.dequeue();
        }
    }
    boolean queueEmpty(){
        if (highP.queueEmpty() && lowP.queueEmpty()){
            return true;
        }
        else {
            return false;
        }
    }
    public int qlength(){
        int l = lowP.qlength();
        int h = highP.qlength();
        int p = l + h;
        return p;
    }
    public int calc(element e){
        if (e!=null){
            return calc(e.follower)+1;
        }
        else{
            return 0;
        }
    }
}
