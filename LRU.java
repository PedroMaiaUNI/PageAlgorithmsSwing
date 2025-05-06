import java.util.LinkedList;

public class LRU {
    LinkedList<Page> memory;
    LinkedList<Page> queue;

    int memorySize;
    int pageFaults;

    LinkedList<Page> usageHistory;

    public LRU(LinkedList<Page> initialMemory, LinkedList<Page> queue) {
        this.memory = new LinkedList<>(initialMemory);
        this.queue = new LinkedList<>(queue);
        this.pageFaults = 0;
        this.memorySize = initialMemory.size();

        this.usageHistory = new LinkedList<>();
        for(Page page : initialMemory){
            updateUsageHistory(page);
        }
    }

    public void pageToReplace(){
        //TIRA O MAIS ANTIGO DA PILHA (FUNDO DA PILHA)
        //USA EQUALS PARA COMPARAR QUAL É O ELEMENTO NA MEMORIA CORRESPONDENTE
        for(Page p1 : usageHistory){
            for(Page p2 : memory){
                if(p1.equals(p2)){
                    memory.remove(p2);
                    return;
                }
            }
        }
    }

    public boolean findByName(LinkedList<Page> memory, Page page){
        for(Page compare : memory){
            if(compare.name.equals(page.name)){
                return true;
            }
        }
        return false;
    }

    public void updateUsageHistory(Page page) {
        //remover da pilha para recolocar no topo
        usageHistory.removeIf(p -> p.name.equals(page.name));
        usageHistory.add(page);
    }

    public void checkEmptiness(){
        memory.removeIf(p -> p.name.equals("0"));
    }

    public void run() {
        checkEmptiness();

        for (Page page : queue) {
            if (!findByName(memory, page)) {
                pageFaults++;

                //SE MEMORIA != CHEIA:      ADICIONA NA MEMORIA
                //                          ADICIONA NA FILA DE REFERENCIA: está na fila?: move pro final
                //                                                           não está na fila?: adiciona no final

                //SE MEMORIA == CHEIA:      ESCOLHE PAGINA PRA REMOVER
                //                          SUBSTITUI PAGINA PELA QUE QUER INSERIR
                //                          ADICIONA NA PILHA DE REFERENCIA: está na fila?: move pro final
                //                                                           não está na fila?: adiciona no final

                if(memory.size() >= memorySize) {
                    pageToReplace();
                }  
                memory.add(page);
            }
            for(Page p : memory){
                System.out.println(p.name.equals("0") ? "_" : p.name);
            }
            System.out.println("--------\n");
            updateUsageHistory(page);
        }
        System.out.println("FIM DO LRU");
    }
}
    
