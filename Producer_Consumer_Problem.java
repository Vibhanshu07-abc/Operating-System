import java.util.concurrent.Semaphore;

class Q {
    int Goods;

    Semaphore producerkey = new Semaphore(1);
    Semaphore consumerkey= new Semaphore(0);

    void put(int Goods) {
        try {
            producerkey.acquire();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        this.Goods = Goods;
        System.out.println("Goods are Produced by farmers " + Goods);

        consumerkey.release();
    }

    void get() {
        try {
            consumerkey.acquire();
        } catch (InterruptedException e) {
             System.out.println(e.getMessage());
        }

        System.out.println("Goods are consumed by customer " + Goods);

        producerkey.release();
    }
}

class Producer implements Runnable {
    Q temp;

    Producer(Q obj) {
        temp = obj;
        new Thread(this, "Producer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            temp.put(i);
        }
    }
}

class Consumer implements Runnable {
    Q temp;

    Consumer(Q obj) {
        temp = obj;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            temp.get();
        }
    }
}

class Producer_Consumer_Problem {
    public static void main(String args[]) {
        Q q = new Q();

        new Consumer(q);
        new Producer(q);
    }
}