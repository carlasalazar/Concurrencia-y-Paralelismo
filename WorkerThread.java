class WorkerThread extends Thread {
    private String threadName;
    private int delay;

    public WorkerThread(String name, int delay) {
        this.threadName = name;
        this.delay = delay;
    }

    public void run() {
        System.out.println(threadName + " iniciado.");

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " ejecutando: " + i);
                // Hace una pausa en la ejecución del hilo durante 'delay' milisegundos
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrumpido.");
        }

        System.out.println(threadName + " finalizado.");
    }
}

public class ThreadControlExample {
    public static void main(String[] args) {
      
        WorkerThread thread1 = new WorkerThread("Hilo 1", 1000);
        WorkerThread thread2 = new WorkerThread("Hilo 2", 1500);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Hilos interrumpidos.");
        }

        System.out.println("Todos los hilos han finalizado.");
    }
}
