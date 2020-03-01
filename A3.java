import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class A3 {
    public static void main(String[] args) {
        int memFrames = Integer.parseInt(args[0]);
        int timeQuantum = Integer.parseInt(args[1]);
        int framesPerProcess = memFrames/(args.length-2);
        ArrayList<Process> procListLru= new ArrayList<Process>();
        ArrayList<Process> procListClk= new ArrayList<Process>();
        try {
            for(int i = 0; i < args.length - 2; i++) {
                Queue<Integer> pageList = new LinkedList<Integer>();
                BufferedReader inputStream = new BufferedReader(new FileReader(args[i + 2]));
                String line = inputStream.readLine();
                line = inputStream.readLine();
                do{
                    pageList.add(Integer.parseInt(line));
                    line = inputStream.readLine();
                }while(!line.contentEquals("end"));
                inputStream.close();
                Process pLru = new Process(i+1, pageList, framesPerProcess);
                procListLru.add(pLru);
                Process pClk = new Process(i+1, pageList, framesPerProcess);
                procListClk.add(pClk);
            }
        }catch(Exception e) {
            System.out.println(e);
        }

        LRU algLRU = new LRU(procListLru, timeQuantum);
        algLRU.runLRU();

        CLK algCLK = new CLK(procListClk, timeQuantum);
        algCLK.runCLK();

        //print out summary LRU
        for(int i = 0; i < procListLru.size(); i++){
            System.out.print("Process ID: " + procListLru.get(i).getpID() + " ");
            System.out.print("Turnaround Time: " + procListLru.get(i).getTurnaroundTime() + " ");
            System.out.print("Faults: " + procListLru.get(i).getFaults() + " ");
            System.out.println("Fault Times: " + procListLru.get(i).getFaultTimes());
        }
        System.out.println();
        //print out summary CLK
        for(int i = 0; i < procListClk.size(); i++){
            System.out.print("Process ID: " + procListClk.get(i).getpID() + " ");
            System.out.print("Turnaround Time: " + procListClk.get(i).getTurnaroundTime() + " ");
            System.out.print("Faults: " + procListClk.get(i).getFaults() + " ");
            System.out.println("Fault Times: " + procListClk.get(i).getFaultTimes());
        }
    }
}
