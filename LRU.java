import java.util.LinkedList;

public class LRU extends PageAlgorithm{

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

    @Override
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

    public void updateUsageHistory(Page page) {
        //remover da pilha para recolocar no topo
        usageHistory.removeIf(p -> p.name.equals(page.name));
        usageHistory.add(page);
    }

    public void debug(){
        for(Page p : memory){
            System.out.println(p.name.equals("0") ? "_" : p.name);
        }
        System.out.println("--------\n");
    }

    @Override
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
            updateUsageHistory(page);
        }
        System.out.println("FIM DO LRU");
    }
}
    
