
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
    NQueue nonpriQueue= new NQueue();
    Scanner keyboard = new Scanner(System.in);
    String filename="arrivals.csv";
    final int MAXLINES=100;
    final int VALUESPERLINE=4;
    /**
     * Constructor for objects of class makesQueue
     */
    public cafeQueueMaker()
    {
        File arrivals=new File(filename);
        boolean exists = arrivals.exists();
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
        while(!exists){
            System.out.println("file dosn't exist say name of existing file"); 
            filename=keyboard.nextLine();
            arrivals=new File(filename);
            exists = arrivals.exists();
        }
        try{
            Scanner reader = new Scanner(arrivals);
            while (reader.hasNextLine() && lineCount <MAXLINES){
                String line=reader.nextLine();
                CSVlines[lineCount]=line;
                lineCount++;
            }
            for (int i = 0; i<lineCount;i++){
                String values[] = CSVlines[i].split(",");
                System.out.println("number of values " + values.length);
                if (values.length==0) values= new String[]{"0","0","0","0"};
                for (int j=0; j< values.length&&j<VALUESPERLINE;j++){
                    System.out.println(values[j].length());
                    if(values[j].equals("")||values[j].equals(" ")||values[j].length()==0){
                        System.out.println("it made it "+i+j);
                        values[j]="0";
                    }
                    else System.out.println("not null "+values[j]);
                    AllLinesAllElements[i][j]=values[j];
                }
                for (int j=values.length;j<VALUESPERLINE;j++){
                    AllLinesAllElements[i][j]="0";
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
            if(!isInt(stu)){
                stu = "0";
            }
            if(!isInt(sta)){
                sta = "0";
            }
            System.out.println(stu+" "+sta);
            int students = Integer.parseInt(stu);
            int staff = Integer.parseInt(sta);
            int time = ml;
            int arrived=students+staff;
            System.out.print("the time is "+ time);
            System.out.print("amount of students arriving "+stu);
            System.out.print("amount of staff arriving "+sta);
            for (int sl=0;sl<students;sl++){
                element person= new element(ml + ",s,"+sl);
                element nonperson = new element(ml + ",s,"+sl);
                priQueue.enqueue(person, false);
                nonpriQueue.enqueue(nonperson);
            }
            for (int tl=0;tl<staff;tl++){
                element person= new element(ml + ",t,"+tl);
                element nonperson= new element(ml + ",t,"+tl);
                priQueue.enqueue(person, true);
                nonpriQueue.enqueue(nonperson);
            }
            String departing = AllLinesAllElements[ml][3];
            if(!isInt(departing)){
                departing = "0";
            }
            int leave = Integer.parseInt(departing);
            int dequeue=1;
            boolean stop=false;
            int priSwait=0;
            int priTwait=0;
            int priSServed=0;
            int priTServed=0;
            int nonSwait=0;
            int nonTwait=0;
            int nonSServed=0;
            int nonTServed=0;
            float priSmean = 0;
            float priTmean = 0;
            float nonSmean = 0;
            float nonTmean = 0;
            int departed=priQueue.qlength();
            System.out.println("this is the priority queue");
            /*System.out.println(" ");*/
            if(leave==0){
                stop=true;
            }
            while (!priQueue.queueEmpty()&&!nonpriQueue.queueEmpty()&&!stop){
                String priTheLine=priQueue.dequeue().getName().toString();
                String nonTheLine=nonpriQueue.dequeue().getName().toString();
                String pripersonStats[] = priTheLine.split(",");
                String nonpersonStats[] = nonTheLine.split(",");
                int priarrivalTime = Integer.parseInt(pripersonStats[0]);
                int nonarrivalTime = Integer.parseInt(nonpersonStats[0]);
                char priSorT = pripersonStats[1].charAt(0);
                char nonSorT = nonpersonStats[1].charAt(0);
                int priwait=0;
                int nonwait=0;
                
                for (int LT=priarrivalTime;LT<ml;LT++){
                    if (priSorT=='s'){
                        priSwait++;
                    }
                    else {
                        priTwait++;
                    }
                    priwait++;
                }
                for (int LT=nonarrivalTime;LT<ml;LT++){
                    if (nonSorT=='s'){
                        nonSwait++;
                    }
                    else {
                        nonTwait++;
                    }
                    nonwait++;
                }
                System.out.println("there pri queue and arrived at "+priarrivalTime+" and are a "+priSorT + " and waited for " +priwait);
                System.out.println("length of the pri queue " + priQueue.qlength() + " time arrived " + priarrivalTime + " " + priSorT);
                System.out.println("there non pri queue and arrived at "+nonarrivalTime+" and are a "+nonSorT + " and waited for " +nonwait);
                System.out.println("length of the non pri queue " + priQueue.qlength() + " time arrived " + nonarrivalTime + " " + nonSorT);
                dequeue = 1+dequeue;
                if (priSorT=='s'){
                    priSServed++;
                }
                else {
                    priTServed++;
                }
                if (nonSorT=='s'){
                    nonSServed++;
                }
                else {
                    nonTServed++;
                }
                if (leave<dequeue){
                    stop = true;
                }                
            } 
            if (priQueue.queueEmpty()&&nonpriQueue.queueEmpty()){
                System.out.println("the queue is empty");
            }
            pritotalSServed = priSServed+pritotalSServed;
            pritotalTServed = priTServed+pritotalTServed;
            pritotalSwait = priSwait+pritotalSwait;
            pritotalTwait = priTwait+pritotalTwait;
            nonpritotalSServed = nonSServed+nonpritotalSServed;
            nonpritotalTServed = nonTServed+nonpritotalTServed;
            nonpritotalSwait = nonSwait+nonpritotalSwait;
            nonpritotalTwait = nonTwait+nonpritotalTwait;
            if (pritotalSwait==0){
                priSmean = 0;
            }
            else {
                priSmean = pritotalSwait/pritotalSServed;
            }
            if (pritotalTwait==0){
                priTmean = 0;
            }
            else {
                priTmean = pritotalTwait/pritotalTServed;
            }
            if (nonpritotalSwait==0){
                nonSmean = 0;
            }
            else {
                nonSmean = pritotalSwait/pritotalSServed;
            }
            if (nonpritotalTwait==0){
                nonTmean = 0;
            }
            else {
                nonTmean = pritotalTwait/pritotalTServed;
            }
            System.out.println("for pri the total wait for students is " + pritotalSwait + " the total amount of students served is " + pritotalSServed);
            System.out.println("for pri the total wait for staff is " + pritotalTwait + " the total amount of staff served is " + pritotalTServed);
            System.out.println("for pri the mean for students is " + priSmean + " the mean for staff is " + priTmean);
            System.out.println("for non pri the total wait for students is " + nonpritotalSwait + " the total amount of students served is " + nonpritotalSServed);
            System.out.println("for non pri the total wait for staff is " + nonpritotalTwait + " the total amount of staff served is " + nonpritotalTServed);
            System.out.println("for non pri the mean for students is " + nonSmean + " the mean for staff is " + nonTmean);
            
            
        }
    }
    public boolean isInt(String num) {
        for(int i=0;i<num.length();i++){
            if (num.charAt(i)<'0'||num.charAt(i)>'9') {
                return false;
            }
        }
        return true;
    }
}
