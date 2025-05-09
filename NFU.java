import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NFU extends PageAlgorithm{
    int clockInterrupt;

    //no momento sou incapaz de raciocionar como fazer isso certo...
    //então fica uma implementação gulosa e preguiçosa
    Map<String, Boolean> referenceBits;
    Map<String, Integer> pageCounters;

    public NFU(LinkedList<Page> initialMemory, LinkedList<Page> queue, int clockInterrupt) {
        this.memory = new LinkedList<>(initialMemory);
        this.queue = new LinkedList<>(queue);
        this.pageFaults = 0;
        this.memorySize = initialMemory.size();
        this.clockInterrupt = clockInterrupt;

        this.referenceBits = new HashMap<>();
        this.pageCounters = new HashMap<>();

        memory.forEach( (page) ->  {referenceBits.put(page.name, false);
                                    pageCounters.put(page.name, 0);
                                    }); 
    }

    @Override
    public void pageToReplace(){
        //como na implementação de NFU, em caso de empate de counter um slot aleatorio é escolhido para ser subsituído
        //escolhi que seria melhor implementar um "FIFO" como critério de desempate
        //ou seja, ele pega o primeiro (mais velho) da memória primeiro
        //e se ningúem tiver counter menor, ele é o removido
        Page toReplace = memory.getFirst();
        for(Page page : memory){
            if(pageCounters.get(toReplace.name) > pageCounters.get(page.name)){
                toReplace = page;
            }
        }
        memory.remove(toReplace);
    }

    @Override
    public void debug(){
        for(Page p : memory){
            System.out.println(p.name.equals("0") ? "_" : p.name);
            System.out.println("contador da pagina: " + pageCounters.get(p.name));
        }
        System.out.println("pagefaults: " + pageFaults);
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

                pageCounters.putIfAbsent(page.name, 0);
            }
            debug();

            //atualização do contador
            //a cada interrupção de clock
            if (refCount == clockInterrupt){
                refCount = 0;
                for (String p : referenceBits.keySet()) {
                    boolean R = referenceBits.getOrDefault(p, false);
                    int counter = pageCounters.getOrDefault(p, 0);

                    if(R){
                        counter++;
                    }
                    referenceBits.put(p, false);
                    pageCounters.put(p, counter);
                }
            }
            
        }
        System.out.println("FIM DO NRU");
    }
}
