
import java.util.*;

public class NFU {
    private final List<String> memory;
    private final List<String> queue;
    private final Map<String, Integer> usageCounter = new HashMap<>();
    private final int clockInterrupt;

    public NFU(List<String> initialMemory, List<String> queue, int clockInterrupt) {
        this.memory = initialMemory;
        this.queue = queue;
        this.clockInterrupt = clockInterrupt;
    }

    public int run() {
        int pageFaults = 0;
        int refCount = 0;

        for (String page : queue) {
            refCount++;
            usageCounter.put(page, usageCounter.getOrDefault(page, 0) + 1);

            if (!memory.contains(page)) {
                pageFaults++;
                if (memory.contains("0")) {
                    memory.set(memory.indexOf("0"), page);
                } else {
                    String leastUsed = memory.stream()
                            .min(Comparator.comparingInt(p -> usageCounter.getOrDefault(p, 0)))
                            .orElse(memory.get(0));
                    memory.set(memory.indexOf(leastUsed), page);
                }
            }

            if (refCount % clockInterrupt == 0) {
                for (String key : usageCounter.keySet()) {
                    usageCounter.put(key, usageCounter.get(key) / 2);
                }
            }
        }

        return pageFaults;
    }
}
