

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aging extends PageAlgorithm{
    int clockInterrupt;

    Map<String, Boolean> referenceBits;
    Map<String, Integer> pageBytes;

    public Aging(LinkedList<Page> initialMemory, LinkedList<Page> queue, int clockInterrupt) {
        this.memory = new LinkedList<>(initialMemory);
        this.queue = new LinkedList<>(queue);
        this.pageFaults = 0;
        this.memorySize = initialMemory.size();
        this.clockInterrupt = clockInterrupt;

        this.referenceBits = new HashMap<>();
        this.pageBytes = new HashMap<>();

        memory.forEach( (page) ->  {referenceBits.put(page.name, false);
                                    pageBytes.put(page.name, 0);}); 
    }

    @Override
    public void pageToReplace(){
        Page toReplace = memory.getFirst();
        int minCounter = pageBytes.getOrDefault(toReplace.name, 0);
        for(Page page : memory){
            int counter = pageBytes.getOrDefault(page.name, 0);
            if(counter < minCounter){
                toReplace = page;
            }
        }
        memory.remove(toReplace);
    }

    @Override
    public void debug(){
        for (Page p : memory) {
            System.out.println(p.name.equals("0") ? "_" : p.name);
            System.out.println("counter: " + Integer.toBinaryString(pageBytes.getOrDefault(p.name, 0)));
        }
        System.out.println("--------\n");
    }

    @Override
    public void run(){
        int refCount = 0;
        checkEmptiness();

        for (Page page : queue){
            refCount++;
            referenceBits.put(page.name, true);

            if(!findByName(memory, page)){
                pageFaults++;

                if(memory.size() >= memorySize){
                    pageToReplace();
                }
                memory.add(page);

                pageBytes.putIfAbsent(page.name, 0);
            }

            debug();

            if (refCount == clockInterrupt){
                refCount = 0;
                for (String p : pageBytes.keySet()) {
                    boolean R = referenceBits.getOrDefault(p, false);
                    int counter = pageBytes.getOrDefault(p, 0);
                    counter >>= 1;

                    if(R){
                        //adiciona 1 ao bit mais significante
                        counter |= 0b10000000;
                    }
                    referenceBits.put(p, false);
                    pageBytes.put(p, counter);
                }
            }
        }

        System.out.println("FIM DO AGING");
    }
}
