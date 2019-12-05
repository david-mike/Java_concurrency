public class Client {
    public static void main(String[] args) {
        LockObject lo = new LockObject();
        Thread t1 = new Thread(new NumberPrint(lo));
        Thread t2 = new Thread(new CharacterPrint(lo));
        t1.start();
        t2.start();
    }
}
