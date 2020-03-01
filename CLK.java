import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class CLK {
    private Queue<Process> readyQueue;
    private ArrayList<Process> pList;
    private int quantum;
    private int quantumCtr;
    private int timer;


    public CLK(ArrayList<Process> pList, int quantum){
        this.quantum = quantum;
        this.readyQueue = new LinkedList<Process>();
        this.quantumCtr = 0;
        this.pList = pList;
        this.timer = 0;
    }

    public void runCLK(){
        while(checkPageRemain()){
            //check if process is blocked
            addToReady();

            if(!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.element();
                int headPage = currentProcess.getHead();
                //load process into memory
                if(!currentProcess.checkForPageClk(headPage)){
                    quantumCtr++;
                    currentProcess.loadPageClk(headPage);
                    checkFault(currentProcess);
                    checkPageRemain(currentProcess);
                    checkQuantum(currentProcess);
                }else if(currentProcess.checkForPageClk(headPage)) {
                    quantumCtr++;
                    currentProcess.setUsed();
                    checkFault(currentProcess);
                    checkPageRemain(currentProcess);
                    checkQuantum(currentProcess);
                }
            }
            timer++;
        }
    }

    public void checkFault(Process current){
        if(current.getTotalPages() > 0 && !current.checkForPageClk(current.checkHead())){
            current.restartBlocked();
            current.setFault(timer + 1);
            current.setIsInReady(false);
            readyQueue.poll();
            quantumCtr = 0;
        }
    }

    public void checkPageRemain(Process current){
        if(current.getTotalPages() == 0){
            current.setIsInReady(false);
            quantumCtr = 0;
            readyQueue.poll();
        }
    }

    public void checkQuantum(Process current){
        if(quantumCtr == quantum) {
            if (readyQueue.size() > 1) {
                current.setIsInReady(false);
                readyQueue.poll();
                quantumCtr = 0;
            } else{
                quantumCtr = 0;
            }
        }
    }

    public void addToReady(){
        for(int i = 0; i < pList.size(); i++){
            if(pList.get(i).getBlocked()) {
                pList.get(i).decBlockedTime();
            }else if(!pList.get(i).getIsInReady() && pList.get(i).getTotalPages() > 0){
                pList.get(i).setIsInReady(true);
                readyQueue.add(pList.get(i));
            }
        }
    }

    public Boolean checkPageRemain(){
        boolean pagesRemain = false;
        for(int i = 0; i < pList.size(); i++){
            if(pList.get(i).getTotalPages() > 0){
                pagesRemain = true;
            }else{
                pList.get(i).setTurnAroundTime(timer);
            }
        }
        return pagesRemain;
    }
}

