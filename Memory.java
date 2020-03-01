import java.util.*;
public class Memory {
    private int [] framesLRU;
    private int [][] framesClk;
    private int totalFrames;
    private int pageLocationLRU;
    private int pageLocationClk;
    private int pointerClk;
    private Boolean memoryFull;

    //LRU queue variables
    private int rear = 0;

    public Memory(int total){
        this.totalFrames = total;
        this.framesLRU = new int[total];
        this.framesClk = new int[total][2];
        this.pageLocationLRU = 0;
        this.pageLocationClk = 0;
        this.pointerClk = 0;
        this.memoryFull = false;
    }

    public void addPageClk(int page){
        if(framesClk[pointerClk][1] == 0) {
            framesClk[pointerClk][0] = page;
            framesClk[pointerClk][1] = 1;
            pointerClk++;
        }else if(framesClk[pointerClk][1] == 1){
            pointerClk++;
        }
        if(pointerClk == totalFrames){
            memoryFull = true;
        }
        if(memoryFull && pointerClk == totalFrames){
            for(int i = 0; i < totalFrames; i++){
                framesClk[i][1] = 0;
            }
            pointerClk = 0;
        }
    }

    public void setUsedBitClk(){framesClk[pageLocationClk][1] = 1;}

    public Boolean checkPageExistsClk(int page){
        for(int i = 0; i < totalFrames; i++){
            if(framesClk[i][0] == page){
                pageLocationClk = i;
                return true;
            }
        }
        return false;
    }

    //if full LRU replacement or else add next page
    public void enQueueLRU(int page){
        if (totalFrames == rear) {
            for (int i = 0; i < rear-1; i++) {
                framesLRU[i] = framesLRU[i+1];
            }
            framesLRU[rear-1] = page;
        }else {
            framesLRU[rear] = page;
            rear++;
        }
    }

    //if more then one process
    public void replacePageLRU(int page){
        if(rear > 1) {
            for (int i = pageLocationLRU; i < rear-1; i++) {
                framesLRU[i] = framesLRU[i + 1];
            }
            framesLRU[rear-1] = page;
        }else {
            framesLRU[rear-1] = page;
        }
    }

    public Boolean checkPageExistsLRU(int page){
        for(int i = 0; i < totalFrames; i++){
            if(framesLRU[i] == page){
                pageLocationLRU = i;
                return true;
            }
        }
        return false;
    }

}
