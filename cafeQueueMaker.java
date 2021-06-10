
/**
 * Write a description of class cafeQueue here.
 *
 * @author Harry Thornburrow
 * @version 20/05/2021
 */
import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
public class cafeQueueMaker
{
    TQueue theQueue= new TQueue();
    Scanner keyboard = new Scanner(System.in);
    final String filename="arrivals.csv";
    final int MAXLINES=100;
    final int VALUESPERLINE=4;
   

    /**
     * Constructor for objects of class makesQueue
     */
    public cafeQueueMaker()
    {
        File arrivals=new File(filename);
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];
        int lineCount=0;
        float totalSServed=0;
        float totalTServed=0;
        float totalSwait=0;
        float totalTwait=0;
        try{
            Scanner reader = new Scanner(arrivals);
            while (reader.hasNextLine() && lineCount <MAXLINES){
                String line=reader.nextLine();
                CSVlines[lineCount]=line;
                lineCount++;
            }
            for (int i = 0; i<lineCount;i++){
                String values[] = CSVlines[i].split(",");
                for (int j=0; j< values.length;j++){
                    AllLinesAllElements[i][j]=values[j];
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for (int ml=1;ml<lineCount;ml++){          
            System.out.println(AllLinesAllElements[ml][0]);
            String stu = AllLinesAllElements[ml][1];
            String sta = AllLinesAllElements[ml][2];
            int students = Integer.parseInt(stu);
            int staff = Integer.parseInt(sta);
            int time = ml;
            System.out.println("the time is "+ time);
            System.out.println("amount of students arriving "+stu);
            System.out.println("amount of staff arriving "+sta);
            for (int sl=0;sl<students;sl++){
                element person= new element(ml + ",s,"+sl);
                theQueue.enqueue(person, false);
            }
            for (int tl=0;tl<staff;tl++){
                element person= new element(ml + ",t,"+tl);
                theQueue.enqueue(person, true);
            }
            String departing = AllLinesAllElements[ml][3];
            int leave = Integer.parseInt(departing);
            int dequeue=0;
            boolean stop=false;
            int Swait=0;
            int Twait=0;
            int SServed=0;
            int TServed=0;
            float Smean = 0;
            float Tmean = 0;
            int departed=theQueue.qlength();
            while (!theQueue.queueEmpty()&&!stop){
                String TheLine=theQueue.dequeue().getName().toString();
                String personStats[] = TheLine.split(",");
                int arrivalTime = Integer.parseInt(personStats[0]);
                char SorT = personStats[1].charAt(0);
                int wait=0;
                
                for (int LT=arrivalTime;LT<ml;LT++){
                    if (SorT=='s'){
                        Swait++;
                    }
                    else {
                        Twait++;
                    }
                    wait++;
                }
                System.out.println("they arrived at "+arrivalTime+" and are a "+SorT + " and waited for " +wait);
                System.out.println("length of the queue " + theQueue.qlength() + " and the time arrived and then if they are S or T they are " + arrivalTime + " " + SorT);
                dequeue = 1+dequeue;
                if (SorT=='s'){
                    SServed++;
                }
                else {
                    TServed++;
                }
                if (leave<dequeue){
                    stop = true;
                }                
            } 
            if (theQueue.queueEmpty()){
                System.out.println("the queue is empty");
            }
            totalSServed = SServed+totalSServed;
            totalTServed = TServed+totalTServed;
            totalSwait = Swait+totalSwait;
            totalTwait = Twait+totalTwait;
            if (totalSwait==0){
                Smean = 0;
            }
            else {
                Smean = totalSwait/totalSServed;
            }
            if (totalTwait==0){
                Tmean = 0;
            }
            else {
                Tmean = totalTwait/totalTServed;
            }
            System.out.println("the total wait for students is " + totalSwait + " the total amount of students served is " + totalSServed);
            System.out.println("the total wait for staff is " + totalTwait + " the total amount of staff served is " + totalTServed);
            System.out.println("the mean for students is " + Smean + " the mean for staff is " + Tmean);
        }
    }
}
