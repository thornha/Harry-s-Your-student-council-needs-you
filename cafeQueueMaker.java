
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
        for (int ml=0;ml<lineCount;ml++){          
            System.out.println(AllLinesAllElements[ml][0]);
            String stu = AllLinesAllElements[ml][1];
            String sta = AllLinesAllElements[ml][2];
            int students = Integer.parseInt(stu);
            int staff = Integer.parseInt(sta);
            for (int sl=0;sl<students;sl++){
                element person= new element(sl);
                theQueue.enqueue(person, false);
            }
            for (int tl=0;tl<staff;tl++){
                element person= new element(tl);
                theQueue.enqueue(person, true);
            }
            while (!theQueue.queueEmpty()){
                System.out.println(theQueue.qlength()+""+theQueue.dequeue().getName());
            }  
        }
    }
}
