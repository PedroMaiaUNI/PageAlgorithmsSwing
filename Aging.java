import java.util.*;

public class Aging {
    private final int memorySize;
    private final List<String> pageQueue;
    private final int clockInterrupt;

    private Map<String, Integer> agingCounters;
    private Map<String, Integer> referenceBits;
    private List<String> memory;

    public Aging(int memorySize, List<String> initialMemory, List<String> pageQueue, int clockInterrupt) {
        this.memorySize = memorySize;
        this.pageQueue = pageQueue;
        this.clockInterrupt = clockInterrupt;
        
        this.agingCounters = new HashMap<>();
        this.referenceBits = new HashMap<>();
        this.memory = new ArrayList<>(initialMemory);
    }

    public int run() {
        int faults = 0;
        int instructionsSinceLastInterrupt = 0;

        for (String page : pageQueue) {
            // Página está na memória
            if (memory.contains(page)) {
                referenceBits.put(page, 1); // Referenciada
            } else {
                faults++;
                // Substituição se memória cheia
                if (memory.size() >= memorySize) {
                    String pageToReplace = findPageToReplace();
                    memory.remove(pageToReplace);
                    agingCounters.remove(pageToReplace);
                    referenceBits.remove(pageToReplace);
                }
                memory.add(page);
                agingCounters.put(page, 0);
                referenceBits.put(page, 1);
            }

            instructionsSinceLastInterrupt++;

            // Atualização periódica
            if (instructionsSinceLastInterrupt == clockInterrupt) {
                updateAgingCounters();
                instructionsSinceLastInterrupt = 0;
            }
        }

        return faults;
    }

    private void updateAgingCounters() {
        for (String page : memory) {
            int counter = agingCounters.getOrDefault(page, 0);
            int refBit = referenceBits.getOrDefault(page, 0);
            counter = (counter >>> 1) | (refBit << 7); // shift right, insert ref bit at MSB
            agingCounters.put(page, counter);
            referenceBits.put(page, 0); // limpa o bit de referência
        }
    }

    private String findPageToReplace() {
        String victim = null;
        int min = Integer.MAX_VALUE;
        for (String page : memory) {
            int counter = agingCounters.getOrDefault(page, 0);
            if (counter < min) {
                min = counter;
                victim = page;
            }
        }
        return victim;
    }
}
