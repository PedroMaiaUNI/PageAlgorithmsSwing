import java.util.LinkedList;

public class FIFO {
    LinkedList<Page> memory;
    LinkedList<Page> queue;

    int memorySize;
    int pageFaults;

    public FIFO(LinkedList<Page> initialMemory, LinkedList<Page> queue) {
        this.memory = new LinkedList<>(initialMemory);
        this.queue = new LinkedList<>(queue);
        this.pageFaults = 0;
        this.memorySize = initialMemory.size();
    }

    public boolean findByName(LinkedList<Page> memory, Page page){
        for(Page compare : memory){
            if(compare.equals(page)){
                return true;
            }
        }
        return false;
    }

    public void pageToReplace(){
        memory.poll();
    }

    public void checkEmptiness(){
        memory.removeIf(p -> p.name.equals("0"));
    }

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
            for(Page p : memory){
                System.out.println(p.name.equals("0") ? "_" : p.name);
            }
            System.out.println("--------\n");
        }
        System.out.println("FIM DO FIFO");
    }
}
