public class Counter {
    private int value = 0;
    public int get() {
        return value;
    }
    public void inc() {
        ++value;
    }
    public void dec(int v) {
        value -= v;
    }
    public void reset() {
        value = 0;
    }
}
