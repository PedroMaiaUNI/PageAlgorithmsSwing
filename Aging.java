
import java.util.*;

public class Aging {
    private final int memorySize;
    private final List<String> memory;
    private final List<String> queue;
    private final Map<String, Integer> agingCounter = new HashMap<>();
    private final int clockInterrupt;
    private final int tau;

    public Aging(int memorySize, List<String> initialMemory, List<String> queue, int clockInterrupt, int tau) {
        this.memorySize = memorySize;
        this.memory = initialMemory;
        this.queue = queue;
        this.clockInterrupt = clockInterrupt;
        this.tau = tau;
    }

    public int run() {
        int pageFaults = 0;
        int refCount = 0;

        for (String page : queue) {
            refCount++;

            if (!memory.contains(page)) {
                pageFaults++;
                if (memory.contains("0")) {
                    memory.set(memory.indexOf("0"), page);
                } else {
                    String toRemove = memory.stream()
                            .min(Comparator.comparingInt(p -> agingCounter.getOrDefault(p, 0)))
                            .orElse(memory.get(0));
                    memory.set(memory.indexOf(toRemove), page);
                }
            }

            for (String p : memory) {
                agingCounter.put(p, agingCounter.getOrDefault(p, 0) >>> 1);
            }

            agingCounter.put(page, agingCounter.getOrDefault(page, 0) | (1 << (tau - 1)));

            if (refCount % clockInterrupt == 0) {
                for (String p : memory) {
                    agingCounter.put(p, agingCounter.getOrDefault(p, 0) >>> 1);
                }
            }
        }

        return pageFaults;
    }
}

