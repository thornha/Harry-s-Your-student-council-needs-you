
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
    TQueue priQueue= new TQueue();
    TQueue nonpriQueue= new TQueue();
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
        float pritotalSServed=0;
        float pritotalTServed=0;
        float pritotalSwait=0;
        float pritotalTwait=0;
        float nonpritotalSServed=0;
        float nonpritotalTServed=0;
        float nonpritotalSwait=0;
        float nonpritotalTwait=0;
        try{
            Scanner reader = new Scanner(arrivals);
            while (reader.hasNextLine() && lineCount <MAXLINES){
                String line=reader.nextLine();
                CSVlines[lineCount]=line;
                lineCount++;
            }
            for (int i = 0; i<lineCount;i++){
                String values[] = CSVlines[i].split(",");
                
                for (int j=0; j< values.length&&j<VALUESPERLINE;j++){
                    if(values[j].equals("")){
                        System.out.println("it made it");
                        values[j]="0";
                    }
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
            System.out.println(ml+stu);
            String sta = AllLinesAllElements[ml][2];
            int students = Integer.parseInt(stu);
            int staff = Integer.parseInt(sta);
            int time = ml;
            System.out.print("the time is "+ time);
            System.out.print("amount of students arriving "+stu);
            System.out.print("amount of staff arriving "+sta);
            for (int sl=0;sl<students;sl++){
                element person= new element(ml + ",s,"+sl);
                priQueue.enqueue(person, false);
                nonpriQueue.enqueue(person, true);
            }
            for (int tl=0;tl<staff;tl++){
                element person= new element(ml + ",t,"+tl);
                priQueue.enqueue(person, true);
                nonpriQueue.enqueue(person, true);
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
            int departed=priQueue.qlength();
            System.out.println("this is the priority queue");
            /*System.out.println(" ");*/
            while (!priQueue.queueEmpty()&&!stop){
                String TheLine=priQueue.dequeue().getName().toString();
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
                System.out.println("length of the queue " + priQueue.qlength() + " and the time arrived and then if they are S or T they are " + arrivalTime + " " + SorT);
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
            pritotalSServed = SServed+pritotalSServed;
            pritotalTServed = TServed+pritotalTServed;
            pritotalSwait = Swait+pritotalSwait;
            pritotalTwait = Twait+pritotalTwait;
            if (pritotalSwait==0){
                Smean = 0;
            }
            else {
                Smean = pritotalSwait/pritotalSServed;
            }
            if (pritotalTwait==0){
                Tmean = 0;
            }
            else {
                Tmean = pritotalTwait/pritotalTServed;
            }
            System.out.println("the total wait for students is " + pritotalSwait + " the total amount of students served is " + pritotalSServed);
            System.out.println("the total wait for staff is " + pritotalTwait + " the total amount of staff served is " + pritotalTServed);
            System.out.println("the mean for students is " + Smean + " the mean for staff is " + Tmean);
            
            dequeue=0;
            stop=false;
            Swait=0;
            Twait=0;
            SServed=0;
            TServed=0;
            Smean = 0;
            Tmean = 0;
            departed=nonpriQueue.qlength();
            System.out.println("this is the nonpriority queue");
            /*System.out.println(" ");*/
            while (!nonpriQueue.queueEmpty()&&!stop){
                String TheLine=nonpriQueue.dequeue().getName().toString();
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
                System.out.println("length of the queue " + nonpriQueue.qlength() + " and the time arrived and then if they are S or T they are " + arrivalTime + " " + SorT);
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
            if (priQueue.queueEmpty()){
                System.out.println("the queue's are empty");
            }
            nonpritotalSServed = SServed+nonpritotalSServed;
            nonpritotalTServed = TServed+nonpritotalTServed;
            nonpritotalSwait = Swait+nonpritotalSwait;
            nonpritotalTwait = Twait+nonpritotalTwait;
            if (nonpritotalSwait==0){
                Smean = 0;
            }
            else {
                Smean = nonpritotalSwait/nonpritotalSServed;
            }
            if (nonpritotalTwait==0){
                Tmean = 0;
            }
            else {
                Tmean = nonpritotalTwait/nonpritotalTServed;
            }
            System.out.println("the total wait for students is " + nonpritotalSwait + " the total amount of students served is " + nonpritotalSServed);
            System.out.println("the total wait for staff is " + nonpritotalTwait + " the total amount of staff served is " + nonpritotalTServed);
            System.out.println("the mean for students is " + Smean + " the mean for staff is " + Tmean);
            
        }
    }
}
