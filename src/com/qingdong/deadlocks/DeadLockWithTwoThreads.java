package com.qingdong.deadlocks;

public class DeadLockWithTwoThreads {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();
        Thread a = new Thread(new ThreadA(s1, s2));
        Thread b = new Thread(new ThreadB(s1, s2));
        a.start();
        b.start();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s1);
        System.out.println(s2);
    }
}

class ThreadA implements Runnable {
    StringBuffer s1;
    StringBuffer s2;
    public ThreadA (StringBuffer s1, StringBuffer s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public void run() {
        synchronized (s1) {
            //for (int i = 1; i < 10; i++)
            s1.append(1);
            s2.append('a');
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (s2) {
                s1.append(2);
                s2.append('b');
            }

        }
    }
}

class ThreadB implements Runnable {
    StringBuffer s1;
    StringBuffer s2;
    public ThreadB (StringBuffer s1, StringBuffer s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public void run() {
        // the moniter order is reversed in threadB
        synchronized (s2) {
            //for (int i = -1; i > -10; i--)
            s1.append(3);
            s2.append('c');
//            System.out.println(s1);
//            System.out.println(s2);
            synchronized (s1) {
                s1.append(4);
                s2.append('d');
            }

        }
    }
}