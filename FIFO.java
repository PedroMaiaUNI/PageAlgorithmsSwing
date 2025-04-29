
import java.util.*;

public class FIFO {
    private final int memorySize;
    private final List<String> memory;
    private final List<String> queue;

    public FIFO(int memorySize, List<String> initialMemory, List<String> queue) {
        this.memorySize = memorySize;
        this.memory = new ArrayList<>(initialMemory);
        this.queue = queue;
    }

    public int run() {
        Queue<String> fifoQueue = new LinkedList<>();
        int pageFaults = 0;

        for (String page : queue) {
            if (!memory.contains(page)) {
                pageFaults++;
                if (fifoQueue.size() < memorySize) {
                    memory.set(memory.indexOf("0"), page);
                    fifoQueue.add(page);
                } else {
                    String toRemove = fifoQueue.poll();
                    memory.set(memory.indexOf(toRemove), page);
                    fifoQueue.add(page);
                }
            }
        }

        return pageFaults;
    }
}
