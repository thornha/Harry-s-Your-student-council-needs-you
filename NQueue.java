
/**
 * Write a description of class NQueue here.
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
public class NQueue
{
    // instance variables - replace the example below with your own
    private element head;
    private element tail;
    public boolean queueEmpty()
    {
        if(head==null){
            return true;
        }
        else{
            return false;
        }
    }
    public void enqueue(element next)
    {
        if (queueEmpty()){
            tail = next;
            head = next; 
        }
        else {//makes tails follower be next and then changes tail to be next
            tail.addfollower(next);
            tail = next;
        }
    }
    public element dequeue(){
        if (!queueEmpty()){ 
            element nexthead=head.nextfollower();
            element oldhead=head;
            head=nexthead;
            return oldhead;
        }
        else{
            System.out.println("error");
            return null;
        }
    }
    public int qlength(){
        return calc(head);
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
