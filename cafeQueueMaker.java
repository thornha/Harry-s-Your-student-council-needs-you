
/**
 * this is the class that enqueues infomation and dequeues it and gathers the statistics of this.
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
    TQueue priQueue= new TQueue();//priQueue is made to connect to TQueue which is the priorty queue 
    NQueue nonQueue= new NQueue();//nonQueue is made to connect to NQueue which is the normal queue
    Scanner keyboard = new Scanner(System.in);
    String filename="arrivals.csv";//the name of the fine i will be analysing
    final int MAXLINES=100;//this is going to be used to make sure there is a limit to the amount of data analysed being no more then 100 lines
    final int VALUESPERLINE=4;//this is used to make it that it only reads the first 4 coloms so that it won't keep reading 
    int priWaitArrayS[]=new int[MAXLINES];//makes an int array for how much is able to go into the array
    int priWaitArrayT[]=new int[MAXLINES];//^
    int nonWaitArrayS[]=new int[MAXLINES];//^
    int nonWaitArrayT[]=new int[MAXLINES];//^
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
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];//this is to create a array that is sized to be able to store all the infomation from the file
        AllLinesAllElements=loadData();//this is using a method to make it have all the infomation from the file is stored in the array        
        for (int ml=1;ml<lineCount;ml++){ //this for loop is so that it will go minute by minute and be able to give a progress report each minute         
            String stu = AllLinesAllElements[ml][1];//grabing the stat for students this minute
            String sta = AllLinesAllElements[ml][2];//grabing the stat for staff this minute
            String departing = AllLinesAllElements[ml][3];//grabing the stat for amount of people leaving this minute
            if(!isInt(stu)){//using a method to check if the stat is a int if isn't then it will make it zero so that the program still works
                stu = "0";
            }
            if(!isInt(sta)){//^
                sta = "0";
            }
            if(!isInt(departing)){//^
                departing = "0";
            }
            int students= random(Integer.parseInt(stu));//using a method to make the number have a chance to be 1 more or 1 less so that it's more realstic. it is also turning it into a int
            int staff= random(Integer.parseInt(sta));//^
            int leave= random(Integer.parseInt(departing));//^
            int time = ml;
            System.out.println("minute "+ time);
            System.out.println("students arriving "+students);
            System.out.println("staff arriving "+staff);
            System.out.println("amount leaving " + leave);
            bulkEnqueueing(students,",s,",time, false);//this is using a method which will take the stats and enqueue them
            bulkEnqueueing(staff,",t,",time, true);//^
            int dequeue=1;
            boolean stop=false;
            int priSwait=0;//all below are set to zero so that it will give the stats per minute instead of just at the end
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
            float nonTmean = 0;//all above are set to zero so that it will give the stats per minute instead of just at the end
            if(leave==0){//so if no one leaves that minute it dosn't dequeue at all
                stop=true;
            }
            while (!priQueue.queueEmpty()&&!nonQueue.queueEmpty()&&!stop){//this is making it that while the queues aren't empty and stop isn't true that it will dequeue
                String priTheLine=priQueue.dequeue().getName().toString();//is getting the infomation of who was dequeued
                String nonTheLine=nonQueue.dequeue().getName().toString();//^
                String pripersonStats[] = priTheLine.split(",");//is using split to make it that all the infomation is seprated
                String nonpersonStats[] = nonTheLine.split(",");//^
                int priarrivalTime = Integer.parseInt(pripersonStats[0]);//grabing when they arrived and making it a integer
                int nonarrivalTime = Integer.parseInt(nonpersonStats[0]);//^
                char priSorT = pripersonStats[1].charAt(0);//grabing if they are S or T and making it a char
                char nonSorT = nonpersonStats[1].charAt(0);//^
                if (priSorT=='s'){//is checking if they are a student 
                    priSwait=ml-priarrivalTime+priSwait;//is getting the minutes wait time for students
                    priSServed++;
                    priWaitArrayS[ml-priarrivalTime]++;//is making it that it will record what this persons wait time was
                }
                else if(priSorT=='t') {//is checking if they are a staff
                    priTwait=ml-priarrivalTime+priTwait;//is getting the minutes wait time for staff
                    priTServed++;
                    priWaitArrayT[ml-priarrivalTime]++;//^
                }
                if (nonSorT=='s'){//^
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
                if (leave<dequeue){//so that it will stop when all the people that are meant to leave have left
                    stop = true;
                }                
            } 
            if (priQueue.queueEmpty()&&nonQueue.queueEmpty()){//this is so that the person can know that the queue is empty so that if 5 leave but it said 6 was suppost to leave they know why
                System.out.println(" ");
                System.out.println("the queue is empty");
            }
            int priSmode = modeMath(priWaitArrayS);//is using a method to find the mode
            int priTmode = modeMath(priWaitArrayT);//^
            int nonSmode = modeMath(nonWaitArrayS);//^
            int nonTmode = modeMath(nonWaitArrayT);//^
            pritotalSServed = priSServed+pritotalSServed;//gathering the total served
            pritotalTServed = priTServed+pritotalTServed;//^
            nontotalSServed = nonSServed+nontotalSServed;//^
            nontotalTServed = nonTServed+nontotalTServed;//^
            pritotalSwait = priSwait+pritotalSwait;//gathering the total wait time
            pritotalTwait = priTwait+pritotalTwait;//^ 
            nontotalSwait = nonSwait+nontotalSwait;//^
            nontotalTwait = nonTwait+nontotalTwait;//^
            priSmean= mean(pritotalSwait,pritotalSServed);//is using a method to find the mean
            priTmean= mean(pritotalTwait,pritotalTServed);//^
            nonSmean= mean(nontotalSwait,nontotalSServed);//^
            nonTmean= mean(nontotalTwait,nontotalTServed);//^
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
    public File getFile(){//this method is to check that the file exists
        File arrivals=new File(filename);
        boolean exists = arrivals.exists();
        while(!exists){//is making it that if the file dosn't exist then you can give the name of an existing file
            System.out.println("file dosn't exist say name of existing file"); 
            filename=keyboard.nextLine();
            arrivals=new File(filename);
            exists = arrivals.exists();
        }
        return arrivals;
    }
    public String[][] loadData(){//this method analysing the file and is putting all it's infomation into a array
        String CSVlines[] = new String[MAXLINES];//making it that it limits the amount of lines that will be used
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];//this makes it a muilty dimensonal array with the max size of what will be analysed from the file
        File arrivals=getFile();
        try{//this is so if the code breaks it won't infinatly loop
            Scanner reader = new Scanner(arrivals);
            while (reader.hasNextLine() && lineCount <MAXLINES){//so that it will read all of the file that is meant to be read
                String line=reader.nextLine();
                CSVlines[lineCount]=line;//makes the array have all of the file in it
                lineCount++;
            }
            for (int i = 0; i<lineCount;i++){//this is so that it will do this for all the lines that have been read
                String values[] = CSVlines[i].split(",");//this is spliting the line of i and putting all it's infomation into diffrent boxs of the values array
                if (values.length==0) {//this is to make it that if the line is blank it will do this
                    values= new String[]{"0","0","0","0"};//this is so if the line is blank it will make the line have all 0s so that the program can continue
                }
                for (int j=0; j< values.length&&j<VALUESPERLINE;j++){//this is so that it will read all 4 numbers
                    if(values[j].equals("")||values[j].equals(" ")||values[j].length()==0){//this is making it check if the space is blank or has nothing and that if that is true do this
                        values[j]="0";//this is making values j 0 so that is dosn't error the program
                    }
                    AllLinesAllElements[i][j]=values[j];//this is putting all the numbers into the muilty diminsonal array
                }
                for (int j=values.length;j<VALUESPERLINE;j++){//this is checking that all the coloms are there if not then do this
                    AllLinesAllElements[i][j]="0";//this is so if the file is missing a colom it will put 0 where the missing coloms numbers would have been
                }
            }
        }
        catch (IOException e){//for if it breaks it will do this
            e.printStackTrace();
        }
        return AllLinesAllElements;
    }
    public int random(int person){//this method is making it that there is a slight chance for the number to be diffrent by 1
        if(Math.random()<0.1){//is using random to make a chance of person changing
            return person+1;
        }
        else if(Math.random()>0.9){//^
            person=person-1;
            if (person <0){//this is so it dosn't return a negitive if it's 0-1
                return 0;
            }
            else{
                return person;
            }
        }
        else{
            return person;
        }
    }
    public boolean isInt(String num) {//this method is checking that whats being put in can become a integer
        for(int i=0;i<num.length();i++){//making it know how much it needs to check num
            if (num.charAt(i)<'0'||num.charAt(i)>'9') {//making it check that num is a number
                return false;
            }
        }
        return true;
    }
    void bulkEnqueueing(int amount, String stuOrSta, int minute, boolean ifStaff){//this method is used to enqueue people
        for(int i=0; i<amount;i++){//is used to make it enqueue as many people as it's suppost too
            element priperson= new element(minute + stuOrSta +i);//getting stats of the person and making them an element
            element nonperson = new element(minute + stuOrSta +i);//^
            priQueue.enqueue(priperson, ifStaff);//enqueueing the person
            nonQueue.enqueue(nonperson);//^
        }
    }
    public float mean(float totalWait, float totalServed){//this method is to calculate the mean
        if (totalWait==0){//so if it is 0 it won't divide and have a math error
            return 0;
        }
        else {
            return totalWait/totalServed;//calculating the mean
        }
    }
    public int modeMath(int waitArray[]){//this method is to find the mode through knowing where the largest number is in an array
        int largestNum= waitArray[0];
        int mode=0;
        for (int i=1; i<waitArray.length;i++){//this is used to make sure that all the numbers of the array are checked
            if (waitArray[i] >largestNum){//this is so that if the number is larger it will become the number
                largestNum =waitArray[i];
                mode=i;//this is becuase the array is of each time a person had to wait that long so the location of the highist amount is the mode
            }
        }
        return mode;
    }
}
