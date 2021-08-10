
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
    NQueue nonQueue= new NQueue();
    Scanner keyboard = new Scanner(System.in);
    String filename="arrivals.csv";
    final int MAXLINES=100;
    final int VALUESPERLINE=4;
    int priWaitArrayS[]=new int[MAXLINES];
    int priWaitArrayT[]=new int[MAXLINES];
    int nonWaitArrayS[]=new int[MAXLINES];
    int nonWaitArrayT[]=new int[MAXLINES];
    int lineCount=0;
    float pritotalSServed=0;
    float pritotalTServed=0;
    float nontotalSServed=0;
    float nontotalTServed=0;
    float pritotalSwait=0;
    float pritotalTwait=0;
    float nontotalSwait=0;
    float nontotalTwait=0;
    /**
     * Constructor for objects of class makesQueue
     */
    public cafeQueueMaker()
    {
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];
        AllLinesAllElements=loadData();        
        for (int ml=1;ml<lineCount;ml++){          
            String stu = AllLinesAllElements[ml][1];
            String sta = AllLinesAllElements[ml][2];
            String departing = AllLinesAllElements[ml][3];
            if(!isInt(stu)){
                stu = "0";
            }
            if(!isInt(sta)){
                sta = "0";
            }
            if(!isInt(departing)){
                departing = "0";
            }
            int students= random(Integer.parseInt(stu));
            int staff= random(Integer.parseInt(sta));
            int leave= random(Integer.parseInt(departing));
            int time = ml;
            System.out.println("minute "+ time);
            System.out.println("students arriving "+students);
            System.out.println("staff arriving "+staff);
            bulkEqueueing(students,",s,",time, false);
            bulkEqueueing(staff,",t,",time, true);
            System.out.println("amount leaving " + leave);
            int dequeue=1;
            boolean stop=false;
            int priSwait=0;
            int priTwait=0;
            int nonSwait=0;
            int nonTwait=0;
            int priSServed=0;
            int priTServed=0;
            int nonSServed=0;
            int nonTServed=0;
            float priSmean = 0;
            float priTmean = 0;
            float nonSmean = 0;
            float nonTmean = 0;
            if(leave==0){
                stop=true;
            }
            while (!priQueue.queueEmpty()&&!nonQueue.queueEmpty()&&!stop){
                String priTheLine=priQueue.dequeue().getName().toString();
                String nonTheLine=nonQueue.dequeue().getName().toString();
                String pripersonStats[] = priTheLine.split(",");
                String nonpersonStats[] = nonTheLine.split(",");
                int priarrivalTime = Integer.parseInt(pripersonStats[0]);
                int nonarrivalTime = Integer.parseInt(nonpersonStats[0]);
                char priSorT = pripersonStats[1].charAt(0);
                char nonSorT = nonpersonStats[1].charAt(0);
                if (priSorT=='s'){
                    priSwait=ml-priarrivalTime+priSwait;
                    priSServed++;
                    priWaitArrayS[ml-priarrivalTime]++;
                }
                else if(priSorT=='t') {
                    priTwait=ml-priarrivalTime+priTwait;
                    priTServed++;
                    priWaitArrayT[ml-priarrivalTime]++;
                }
                if (nonSorT=='s'){
                    nonSwait=ml-nonarrivalTime+nonSwait;
                    nonSServed++;
                    nonWaitArrayS[ml-nonarrivalTime]++;
                }
                else if (nonSorT=='t'){
                    nonTwait=ml-nonarrivalTime+nonTwait;
                    nonTServed++;
                    nonWaitArrayT[ml-nonarrivalTime]++;
                }         
                dequeue = 1+dequeue;
                if (leave<dequeue){
                    stop = true;
                }                
            } 
            if (priQueue.queueEmpty()&&nonQueue.queueEmpty()){
                System.out.println(" ");
                System.out.println("the queue is empty");
            }
            int priSmode = modeMath(priWaitArrayS);
            int priTmode = modeMath(priWaitArrayT);
            int nonSmode = modeMath(nonWaitArrayS);
            int nonTmode = modeMath(nonWaitArrayT);
            pritotalSServed = priSServed+pritotalSServed;
            pritotalTServed = priTServed+pritotalTServed;
            nontotalSServed = nonSServed+nontotalSServed;
            nontotalTServed = nonTServed+nontotalTServed;
            pritotalSwait = priSwait+pritotalSwait;
            pritotalTwait = priTwait+pritotalTwait;            
            nontotalSwait = nonSwait+nontotalSwait;
            nontotalTwait = nonTwait+nontotalTwait;
            priSmean= mean(pritotalSwait,pritotalSServed);
            priTmean= mean(pritotalTwait,pritotalTServed);
            nonSmean= mean(nontotalSwait,nontotalSServed);
            nonTmean= mean(nontotalTwait,nontotalTServed);
            System.out.println(" ");
            System.out.println("priority queue");
            System.out.println("total wait for students is " + pritotalSwait + " the total amount of students served is " + pritotalSServed + " the mode for students is " + priSmode);
            System.out.println("total wait for staff is " + pritotalTwait + " the total amount of staff served is " + pritotalTServed + " the mode for staff is " + priTmode);
            System.out.println("mean for students is " + priSmean + " and mean for staff is " + priTmean);
            System.out.println(" ");
            System.out.println("non priority queue");
            System.out.println("total wait for students is " + nontotalSwait + " the total amount of students served is " + nontotalSServed + " the mode for students is " + nonSmode);
            System.out.println("total wait for staff is " + nontotalTwait + " the total amount of staff served is " + nontotalTServed + " the mode for staff is " + nonTmode);
            System.out.println("mean for students is " + nonSmean + " and mean for staff is " + nonTmean);
            System.out.println(" ");
            System.out.println(" ");
        }
    }
    public File getFile(){
        File arrivals=new File(filename);
        boolean exists = arrivals.exists();
        while(!exists){
            System.out.println("file dosn't exist say name of existing file"); 
            filename=keyboard.nextLine();
            arrivals=new File(filename);
            exists = arrivals.exists();
        }
        return arrivals;
    }
    public String[][] loadData(){
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];
        File arrivals=getFile();
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
        return AllLinesAllElements;
    }
    public int random(int person){
        if(Math.random()<0.1){
            return person+1;
        }
        else if(Math.random()>0.9){
            return person-1;
        }
        else{
            return person;
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
    void bulkEqueueing(int amount, String stuOrSta, int minute, boolean ifStaff){
        for(int i=0; i<amount;i++){
                    element person= new element(minute + stuOrSta +i);
                    element nonperson = new element(minute + stuOrSta +i);
                    priQueue.enqueue(person, ifStaff);
                    nonQueue.enqueue(nonperson);
        }
    }
    public float mean(float totalWait, float totalServed){
        if (totalWait==0){
            return 0;
        }
        else {
            return totalWait/totalServed;
        }
    }
    public int modeMath(int waitArray[]){
        int largestNum= waitArray[0];
        int mode=0;
        for (int i=1; i<waitArray.length;i++){
            if (waitArray[i] >largestNum){
                largestNum =waitArray[i];
                mode=i;
            }
        }
        return mode;
    }
}
