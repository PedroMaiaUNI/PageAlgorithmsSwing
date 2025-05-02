
import java.util.*;

public class LRU {
    private final List<String> memory;
    private final List<String> queue;
    private final LinkedHashMap<String, Integer> recentUsage = new LinkedHashMap<>();
    private int time = 0;

    public LRU(List<String> initialMemory, List<String> queue) {
        this.memory = initialMemory;
        this.queue = queue;
    }

    public int run() {
        int pageFaults = 0;

        for (String page : queue) {
            time++;
            recentUsage.put(page, time);

            if (!memory.contains(page)) {
                pageFaults++;

                if (memory.contains("0")) {
                    memory.set(memory.indexOf("0"), page);
                } else {
                    // Encontra a página menos recentemente usada
                    String lruPage = memory.stream()
                        .min(Comparator.comparingInt(p -> recentUsage.getOrDefault(p, 0)))
                        .orElse(memory.get(0));

                    memory.set(memory.indexOf(lruPage), page);
                }
            } else {
                recentUsage.put(page, time); // Atualiza tempo de uso
            }
        }

        return pageFaults;
    }
}
