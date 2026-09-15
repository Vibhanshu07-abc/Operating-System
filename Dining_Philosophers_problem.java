import java.util.concurrent.Semaphore;

class Philosopher extends Thread {

    int i;
    Semaphore mutex;
    Semaphore[] chopstick;

    Philosopher(int i, Semaphore mutex, Semaphore[] chopstick) {
        this.i = i;
        this.mutex = mutex;
        this.chopstick = chopstick;
    }

    public void run() {

        try {
            System.out.println("Philosopher " + i + " is thinking");

            // wait(mutex)
            mutex.acquire();

            // If philosopher is even
            if (i % 2 == 0) {

                // wait(chopstick[i])
                chopstick[i].acquire();

                // wait(chopstick[(i+1)%5])
                chopstick[(i + 1) % 5].acquire();
            }

            // If philosopher is odd
            else {

                // wait(chopstick[(i+1)%5])
                chopstick[(i + 1) % 5].acquire();

                // wait(chopstick[i])
                chopstick[i].acquire();
            }

            // signal(mutex)
            mutex.release();

            // Eat
            System.out.println("Philosopher " + i + " is eating");
            Thread.sleep(1000);

            // signal(chopstick[(i+1)%5])
            chopstick[(i + 1) % 5].release();

            // signal(chopstick[i])
            chopstick[i].release();

            System.out.println("Philosopher " + i + " finished eating");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}


public class Dining_Philosophers_problem {

    public static void main(String[] args) {

        int n = 5;

        // mutex = 1
        Semaphore mutex = new Semaphore(1);

        // Create 5 chopsticks
        Semaphore[] chopstick = new Semaphore[n];

        for (int i = 0; i < n; i++) {
            chopstick[i] = new Semaphore(1);
        }

        // Create 5 philosophers
        for (int i = 0; i < n; i++) {
            Philosopher p =
                new Philosopher(i, mutex, chopstick);

            p.start();
        }
    }
} 
