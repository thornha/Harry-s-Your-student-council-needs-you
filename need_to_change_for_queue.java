
/**
 * Write a description of class fileSpliter here.
 *
 * @author harry thornburrow
 * @version 27/05/2021
 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
public class need_to_change_for_queue
{
    // instance variables - replace the example below with your own
    final String filename="arrivals.csv";
    final int MAXLINES=100;
    final int VALUESPERLINE=4;
    /**
     * Constructor for objects of class fileSpliter
     */
    public need_to_change_for_queue()
    {
        File theFile=new File(filename);
        String CSVlines[] = new String[MAXLINES];
        String AllLinesAllElements[][]=new String[MAXLINES][VALUESPERLINE];
        int linecount=0;
        try{
            Scanner reader = new Scanner(theFile);
            while (reader.hasNextLine() && linecount <MAXLINES){
                String line=reader.nextLine();
                CSVlines[linecount]=line;
                linecount++;
            }
            for (int i=0; i<linecount; i++) {
                System.out.println(CSVlines[i]);
            }
            for (int i = 0; i<linecount;i++){
                String values[] = CSVlines[i].split(",");
                for (int j=0; j< values.length;j++){
                    System.out.print(values[j]+"****");
                }
                System.out.println("");
                for (int j=0; j< values.length;j++){
                    AllLinesAllElements[i][j]=values[j];
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("the fisrt column read");
        for (int i=0;i<linecount;i++){
            System.out.println(AllLinesAllElements[i][0]);
        }
        System.out.println("Staff arriving at any given time.");
        for (int i=0;i<linecount;i++){
            System.out.println("at "+AllLinesAllElements[i][0]+" "+AllLinesAllElements[i][2]+" arrived.");
        }
    }
}

