import java.util.LinkedList;

public abstract class PageAlgorithm {
    LinkedList<Page> memory;
    LinkedList<Page> queue;

    int memorySize;
    public int pageFaults;

    public boolean findByName(LinkedList<Page> memory, Page page){
        for(Page compare : memory){
            if(compare.name.equals(page.name)){
                return true;
            }
        }
        return false;
    }

    public void checkEmptiness(){
        memory.removeIf(p -> p.name.equals("0"));
    }

    public abstract void pageToReplace();
    public abstract void debug();
    public abstract void run();
    
}
