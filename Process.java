import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
public class Process {
    private int pID;
    private Queue<Integer> pageQueue;
    private ArrayList<Integer> faultTimes = new ArrayList<Integer>();
    private int turnAroundTime;
    private int totalPages;
    private Boolean blocked;
    private int blockedTimer;
    private Memory processMem;
    private Boolean isInReady;


    public Process(int pID, Queue<Integer> pQueue, int frames){
        this.pID = pID;
        this.pageQueue = new LinkedList<Integer>(pQueue);
        this.totalPages = pQueue.size();
        this.blocked = true;
        this.processMem = new Memory(frames);
        this.blockedTimer = 6;
        this.isInReady = false;
        this.faultTimes.add(0);
    }
    public int getTurnaroundTime(){
        return turnAroundTime;
    }

    public int getFaults(){
        return faultTimes.size();
    }

    public String getFaultTimes(){
        String x = new String();
        x += "{";
        for(int i = 0; i < faultTimes.size() - 1; i++){
            x += faultTimes.get(i) + ", ";
        }
        x += faultTimes.get(faultTimes.size()-1) + "}";
        return x;
    }

    public int getHead(){
        totalPages--;
        return pageQueue.poll();
    }

    public int checkHead(){
        return pageQueue.element();
    }

    public Boolean getBlocked(){
        return blocked;
    }

    public void setTurnAroundTime(int time){
        if (turnAroundTime == 0) {
            turnAroundTime = time;
        }
    }

    public void setFault(int time){
        faultTimes.add(time);
    }

    public void setIsInReady(Boolean inReady){
        this.isInReady = inReady;
    }

    public Boolean getIsInReady(){
        return isInReady;
    }

    public void decBlockedTime(){
        blockedTimer--;
        if(blockedTimer == 0){
            blocked = false;
        }
    }

    public void restartBlocked(){
        blockedTimer = 6;
        blocked = true;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public int getpID(){
        return pID;
    }

    public void loadPageLRU(int headPage){ processMem.enQueueLRU(headPage); }

    public void loadPageClk(int headPage){
        processMem.addPageClk(headPage);
    }

    public void sendToFront(int headPage){
        processMem.replacePageLRU(headPage);
    }

    public void setUsed(){
        processMem.setUsedBitClk();
    }

    public Boolean checkForPageLRU(int headPage){
        return processMem.checkPageExistsLRU(headPage);
    }

    public Boolean checkForPageClk(int headPage){
        return processMem.checkPageExistsClk(headPage);
    }


}
