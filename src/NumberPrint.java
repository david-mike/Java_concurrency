
public class NumberPrint implements Runnable{
    static int counter = 1;
    LockObject lo;
    public NumberPrint(LockObject lo) {
        this.lo = lo;
    }
    @Override
    public void run() {
        synchronized (lo) {
            for (char ch = 'A'; ch <= 'Z'; ch += 1) {

                while (lo.status == 2) {
                    try {
                        lo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(counter++);
                System.out.print(counter++);

                lo.status = 2;
                lo.notify();
            }
        }
    }

}
