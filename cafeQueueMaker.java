
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
    int priWaitArrayS[]=new int[MAXLINES];
    int priWaitArrayT[]=new int[MAXLINES];
    int nonpriWaitArrayS[]=new int[MAXLINES];
    int nonpriWaitArrayT[]=new int[MAXLINES];
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
                if (values.length==0) {
                    values= new String[]{"0","0","0","0"};
                }
                for (int j=0; j< values.length&&j<VALUESPERLINE;j++){
                    if(values[j].equals("")||values[j].equals(" ")||values[j].length()==0){
                        values[j]="0";
                    }
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
            String stu = AllLinesAllElements[ml][1];
            String sta = AllLinesAllElements[ml][2];
            if(!isInt(stu)){
                stu = "0";
            }
            if(!isInt(sta)){
                sta = "0";
            }
            int students = Integer.parseInt(stu);
            int staff = Integer.parseInt(sta);
            if(Math.random()<0.1){
                students++;
            }
            else if(Math.random()>0.9){
                students--;
            }
            if(Math.random()<0.1){
                staff++;
            }
            else if(Math.random()>0.9){
                staff--;
            }
            int time = ml;
            System.out.println("minute "+ time);
            System.out.println("students arriving "+stu);
            System.out.println("staff arriving "+sta);
            bulkEqueueing(students,",s,",time, false);
            bulkEqueueing(staff,",t,",time, true);
            String departing = AllLinesAllElements[ml][3];
            if(!isInt(departing)){
                departing = "0";
            }
            int leave = Integer.parseInt(departing);
            if(Math.random()<0.1){
                leave++;
            }
            else if(Math.random()>0.9){
                leave--;
            }
            System.out.println("amount leaving " + leave);
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
                System.out.println(" ");
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
            priWaitArrayS[ml]=priSwait;
            priWaitArrayT[ml]=priTwait;
            nonpriWaitArrayS[ml]=nonSwait;
            nonpriWaitArrayT[ml]=nonTwait;
            
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
            System.out.println(" ");
            System.out.println("priority queue");
            System.out.println("total wait for students is " + pritotalSwait + " the total amount of students served is " + pritotalSServed);
            System.out.println("total wait for staff is " + pritotalTwait + " the total amount of staff served is " + pritotalTServed);
            System.out.println("mean for students is " + priSmean + " and mean for staff is " + priTmean);
            System.out.println(" ");
            System.out.println("non priority queue");
            System.out.println("total wait for students is " + nonpritotalSwait + " the total amount of students served is " + nonpritotalSServed);
            System.out.println("total wait for staff is " + nonpritotalTwait + " the total amount of staff served is " + nonpritotalTServed);
            System.out.println("mean for students is " + nonSmean + " and mean for staff is " + nonTmean);
            System.out.println(" ");
            System.out.println(" ");
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
    void bulkEqueueing(int amount, String stuOrSta, int minute,boolean ifStaff){
        for(int i=0; i<amount;i++){
                    element person= new element(minute + stuOrSta +i);
                    element nonperson = new element(minute + stuOrSta +i);
                    priQueue.enqueue(person, ifStaff);
                    nonpriQueue.enqueue(nonperson);
        }
    }
}
