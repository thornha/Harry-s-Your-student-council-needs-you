
/**
 * Write a description of class cafeQueue here.
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
import java.lang.Math;
import java.util.Scanner;
public class cafeQueueMaker
{
    TQueue theQueue= new TQueue();
    Scanner keyboard = new Scanner(System.in);
   

    /**
     * Constructor for objects of class makesQueue
     */
    public cafeQueueMaker()
    {
        for(int i=0;i<10;i++){
            element person= new element(keyboard.nextLine());        
            if (Math.random()<0.45){
                System.out.println("person " + person.name + " is importent!");
                theQueue.enqueue(person, true);
            }
            else{
                theQueue.enqueue(person, false);
            }
        }      
        while (!theQueue.queueEmpty()){
            System.out.println(theQueue.qlength()+""+theQueue.dequeue().getName());
        }
    }
}
