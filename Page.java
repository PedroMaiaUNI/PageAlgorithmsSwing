public class Page {
    String name;
    boolean R = false;  //bit de referencia
    int counter = 0;    //contador de referencia, util para NFU e Aging
    byte counterAging = 00000000;

    public Page(String name) {
        this.name = name;
    }
    public Page(){
        this.name = "0";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Page)) return false;
        Page other = (Page) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
