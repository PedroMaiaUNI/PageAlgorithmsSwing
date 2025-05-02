import java.util.*;

public class PageReplacementSimulator {

    private final int memorySize;
    private final List<String> pageQueue;
    private final List<String> initialMemory;
    private final int clockInterrupt;

    public PageReplacementSimulator(
        int memorySize, List<String> pageQueue, 
        List<String> initialMemory, int clockInterrupt) {
            this.memorySize = memorySize;
            this.pageQueue = pageQueue;
            this.initialMemory = initialMemory;
            this.clockInterrupt = clockInterrupt;
    }

    public String runSimulations() {
        StringBuilder sb = new StringBuilder();
    
        FIFO fifo = new FIFO(memorySize, new ArrayList<>(initialMemory), pageQueue);
        int fifoFaults = fifo.run();
        sb.append("- FIFO - ").append(fifoFaults).append(" faltas de página\n");
    
        LRU lru = new LRU(new ArrayList<>(initialMemory), pageQueue);
        int lruFaults = lru.run();
        sb.append("- LRU - ").append(lruFaults).append(" faltas de página\n");
    
        NFU nfu = new NFU(new ArrayList<>(initialMemory), pageQueue, clockInterrupt);
        int nfuFaults = nfu.run();
        sb.append("- NFU - ").append(nfuFaults).append(" faltas de página\n");
    
        Aging aging = new Aging(memorySize, new ArrayList<>(initialMemory), pageQueue, clockInterrupt);
        int agingFaults = aging.run();
        sb.append("- Envelhecimento - ").append(agingFaults).append(" faltas de página\n");
    
        return sb.toString();
    }
}
