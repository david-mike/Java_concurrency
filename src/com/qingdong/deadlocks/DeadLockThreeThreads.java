package com.qingdong.deadlocks;

public class DeadLockThreeThreads {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();
        StringBuffer s3 = new StringBuffer();
        Thread t1 = new Thread(new ThreadOne(s1, s2, s3));
        Thread t2 = new Thread(new ThreadTwo(s1, s2, s3));
        Thread t3 = new Thread(new ThreadThree(s1, s2, s3));
        t1.start();
        t2.start();
        t3.start();
        System.out.println("All start.");
        try {
            t1.join();
            t2.join();
            t3.join();

        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
        System.out.println("Main finished.");
    }
}

class ThreadOne implements Runnable {
    StringBuffer s1;
    StringBuffer s2;
    StringBuffer s3;
    public ThreadOne(StringBuffer s1, StringBuffer s2, StringBuffer s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }
    @Override
    public void run() {
        synchronized (s1) {
            s1.append(1);
            s2.append(2);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (s2) {
                s3.append(3);
            }
        }

    }
}

class ThreadTwo implements Runnable {
    StringBuffer s1;
    StringBuffer s2;
    StringBuffer s3;
    public ThreadTwo(StringBuffer s1, StringBuffer s2, StringBuffer s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    @Override
    public void run() {
        synchronized (s2) {
            s1.append("a");
            s2.append("b");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (s3) {
                s3.append("c");
            }
        }
    }
}


class ThreadThree implements Runnable {

    StringBuffer s1;
    StringBuffer s2;
    StringBuffer s3;
    public ThreadThree(StringBuffer s1, StringBuffer s2, StringBuffer s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    @Override
    public void run() {
        synchronized (s3) {
            s1.append("x");
            s2.append("y");
            synchronized (s1) {
                s3.append("z");
            }
        }
    }

}
