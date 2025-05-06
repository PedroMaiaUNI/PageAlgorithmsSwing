import java.util.LinkedList;

public class Simulator {
    LinkedList<String> queue;
    LinkedList<String> initialMemory;

    int clockInterrupt;

    public Simulator(int clockInterrupt, LinkedList<String> queue, LinkedList<String> initialMemory) {
        this.clockInterrupt = clockInterrupt;
        this.queue = queue;
        this.initialMemory = initialMemory;
    } 
    
    public String run(){
        StringBuilder sb = new StringBuilder();

        LinkedList<Page> initialMemoryPages = new LinkedList<>();
        LinkedList<Page> queuePages = new LinkedList<>();
        
        for(String string : initialMemory){
            initialMemoryPages.add(new Page(string));
        }
        for(String string : queue){
            queuePages.add(new Page(string));
        }

        FIFO Fifo = new FIFO(initialMemoryPages, queuePages);
        Fifo.run();
        int fifoFaults = Fifo.pageFaults;
        sb.append("- FIFO - ").append(fifoFaults).append(" faltas de página\n");

        LRU lru = new LRU(initialMemoryPages, queuePages);
        lru.run();
        int lruFaults = lru.pageFaults;
        sb.append("- LRU - ").append(lruFaults).append(" faltas de página\n");

        NFU nfu = new NFU(initialMemoryPages, queuePages, clockInterrupt);
        nfu.run();
        int nfuFaults = nfu.pageFaults;
        sb.append("- NFU - ").append(nfuFaults).append(" faltas de página\n");

        Aging aging = new Aging(initialMemoryPages, queuePages, clockInterrupt);
        aging.run();
        int agingFaults = aging.pageFaults;
        sb.append("- Aging - ").append(agingFaults).append(" faltas de página\n");

        return sb.toString();
    }
}