import java.util.LinkedList;

public class FIFO extends PageAlgorithm {

    public FIFO(LinkedList<Page> initialMemory, LinkedList<Page> queue) {
        this.memory = new LinkedList<>(initialMemory);
        this.queue = new LinkedList<>(queue);
        this.pageFaults = 0;
        this.memorySize = initialMemory.size();
    }

    @Override
    public void pageToReplace(){
        memory.poll();
    }

    @Override
    public void debug(){
        for(Page p : memory){
            System.out.println(p.name.equals("0") ? "_" : p.name);
        }
        System.out.println("--------\n");
    }

    @Override
    public void run(){
        checkEmptiness();

        //se der pagefault, dá .poll (tira a cabeça) na linkedlist
        //adiciona a nova pagina no final

        for (Page page : queue){
            if(!findByName(memory, page)){
                pageFaults++;
                if(memory.size() >= memorySize){
                    pageToReplace();
                }
                memory.add(page);  
            }
            debug();
        }
        System.out.println("FIM DO FIFO");
    }
}
