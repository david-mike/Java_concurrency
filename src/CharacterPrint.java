public class CharacterPrint implements Runnable {
    LockObject lo;
    public CharacterPrint(LockObject lo) {
        this.lo = lo;
    }
    @Override
    public void run() {
        synchronized (lo) {
            for (char ch = 'A'; ch <= 'Z'; ch += 1) {
                while (lo.status == 1) {
                    try {
                        lo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(ch);
                lo.status = 1;
                lo.notify();
            }
        }

    }
}
