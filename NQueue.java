
/**
 * this is the normal queue and is used to queue element
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
public class NQueue
{
    private element head;
    private element tail;
    public boolean queueEmpty()//this method is checking if the queue is empty 
    {
        if(head==null){//this is looking at head to check if it is empty
            return true;
        }
        else{
            return false;
        }
    }
    public void enqueue(element next)//this is being used to enqueue people
    {
        if (queueEmpty()){//this is checking if the queue is empty becuase if it is then the next will be the head and tail
            tail = next;
            head = next; 
        }
        else {//makes tails follower be next and then changes tail to be next
            tail.addfollower(next);
            tail = next;
        }
    }
    public element dequeue(){//this is being used to dequeue people
        if (!queueEmpty()){ // using a method to make sure that the queue isn't empty
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
}
