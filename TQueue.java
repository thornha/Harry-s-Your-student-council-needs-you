/**
 * this is the prioty queue and is used to queue element
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
public class TQueue
{
    private NQueue lowP;
    private NQueue highP;
    /**
     * Constructor for objects of class PQueue
     */
    public TQueue()
    {
        lowP = new NQueue();
        highP = new NQueue();
    } 
    void enqueue(element name, boolean high){//this is being used to enqueue people 
        if (high){//this means if they are high priotrty they enqueue in highP else lowP
            highP.enqueue(name);//this enqueues them onto the normal queue
        }
        else {
            lowP.enqueue(name);//^
        }
    }
    void enqueue(element name){//this is so that if someone wanted non priorty they have a way of doing it
            lowP.enqueue(name);//^
    } 
    element dequeue(){//this is being used to dequeue people
        if (highP.queueEmpty()) {//this makes it that until the priorty queue is empty the normal queue will not dequeue
            return lowP.dequeue();//this is using the normal queues dequeueing but only happens when highP is empty
        }
        else{
            return highP.dequeue();//this is using the normal queues dequeueing and happens before the lowP
        } 
    } 
    boolean queueEmpty(){//is checking if the queue is empty
        if (highP.queueEmpty() && lowP.queueEmpty()){//is used to check for both high and low priorty queue people
            return true;
        }
        else {
            return false;
        } 
    } 
}
