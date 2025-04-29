import java.util.*;

public class PageReplacementSimulator {

    private final int memorySize;
    private final int queueSize;
    private final int pageCount;
    private final List<String> pageNames;
    private final List<String> pageQueue;
    private final List<String> actionQueue;
    private final List<String> initialMemory;
    private final int clockInterrupt;
    private final int tau;

    public PageReplacementSimulator(
        int memorySize, int queueSize, int pageCount,
        List<String> pageNames, List<String> pageQueue, List<String> actionQueue,
        List<String> initialMemory, int clockInterrupt, int tau
    ) {
        this.memorySize = memorySize;
        this.queueSize = queueSize;
        this.pageCount = pageCount;
        this.pageNames = pageNames;
        this.pageQueue = pageQueue;
        this.actionQueue = actionQueue;
        this.initialMemory = initialMemory;
        this.clockInterrupt = clockInterrupt;
        this.tau = tau;
    }

    public void runSimulations() {
        FIFO fifo = new FIFO(memorySize, new ArrayList<>(initialMemory), pageQueue);
        int fifoFaults = fifo.run();
        System.out.println("- FIFO - " + fifoFaults + " faltas de página");

        LRU lru = new LRU(memorySize, new ArrayList<>(initialMemory), pageQueue);
        int lruFaults = lru.run();
        System.out.println("- LRU - " + lruFaults + " faltas de página");

        NFU nfu = new NFU(memorySize, new ArrayList<>(initialMemory), pageQueue, clockInterrupt);
        int nfuFaults = nfu.run();
        System.out.println("- NFU - " + nfuFaults + " faltas de página");

        Aging aging = new Aging(memorySize, new ArrayList<>(initialMemory), pageQueue, clockInterrupt, tau);
        int agingFaults = aging.run();
        System.out.println("- Envelhecimento - " + agingFaults + " faltas de página");
    }

    public String runSimulationsAndGetOutput() {
        StringBuilder sb = new StringBuilder();
    
        FIFO fifo = new FIFO(memorySize, new ArrayList<>(initialMemory), pageQueue);
        int fifoFaults = fifo.run();
        sb.append("- FIFO - ").append(fifoFaults).append(" faltas de página\n");
    
        LRU lru = new LRU(memorySize, new ArrayList<>(initialMemory), pageQueue);
        int lruFaults = lru.run();
        sb.append("- LRU - ").append(lruFaults).append(" faltas de página\n");
    
        NFU nfu = new NFU(memorySize, new ArrayList<>(initialMemory), pageQueue, clockInterrupt);
        int nfuFaults = nfu.run();
        sb.append("- NFU - ").append(nfuFaults).append(" faltas de página\n");
    
        Aging aging = new Aging(memorySize, new ArrayList<>(initialMemory), pageQueue, clockInterrupt, tau);
        int agingFaults = aging.run();
        sb.append("- Envelhecimento - ").append(agingFaults).append(" faltas de página\n");
    
        return sb.toString();
    }
}
