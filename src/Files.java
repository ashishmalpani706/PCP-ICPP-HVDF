import java.io.*;
import java.util.ArrayList;

public class Files {
    public static void printToConsoleFromFile(String scheduler) throws IOException {

        Helper h = new Helper();

        String fileName = "..\\output.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(FileNotFoundException fnfe){
            System.err.println("FileNotFoundException : "+fnfe.getMessage());
        }
//
//        scheduler = scheduler.toLowerCase();
//
//        String dir = "..\\"+scheduler+"_outputs";
//
//        Run.outputToAFile(dir+"\\deadline_misses.txt");



    }



}
