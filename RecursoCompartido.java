//Programa que demuestra la exclusion mutua asegurada por el semaforo, evitando que multiples hilos modifiquen la variable contador simultáneamente y garantizando la consistencia de los datos.
import java.util.concurrent.Semaforo;

class RecursoCompartido {
    private int contador = 0;

    // Semáforo para asegurar la exclusion mutua
    private final Semaphore semaforo = new Semaphore(1);

    // Metodo para incrementar el contador (Seccion Critica)
    public void incrementar() {
        try {
            // Adquirir el semaforo antes de entrar a la seccion critica
            semaforo.acquire();

            // Seccion critica: Solo un hilo puede acceder a esta parte del código
            System.out.println(Thread.currentThread().getName() + " esta incrementando el valor...");
            int nuevoContador = contador + 1;

            // Simulando una pequeña pausa
            Thread.sleep(100);

            contador = nuevoContador;
            System.out.println(Thread.currentThread().getName() + " ha incrementado el valor a: " + contador);

        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Liberar el semaforo
            semaforo.release();
        }
    }

    public int obtenerContador() {
        return contador;
    }
}

class Trabajador extends Thread {
    private final RecursoCompartido recurso;

    public Trabajador(RecursoCompartido recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            recurso.incrementar();
        }
    }
}

public class Principal {
    public static void main(String[] args) {
        // Recurso compartido entre los hilos
        RecursoCompartido recurso = new RecursoCompartido();

        // Crear multiples hilos que accederan al recurso compartido
        Trabajador hilo1 = new Trabajador(recurso);
        Trabajador hilo2 = new Trabajador(recurso);
        Trabajador hilo3 = new Trabajador(recurso);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            // Esperar a que todos los hilos terminen
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valor final del contador: " + recurso.obtenerContador());
    }
}

//Programa ejecutado:
//Thread-0 está incrementando el valor...
//Thread-0 ha incrementado el valor a: 1
//Thread-1 está incrementando el valor...
//Thread-1 ha incrementado el valor a: 2
//Thread-2 está incrementando el valor...
//Thread-2 ha incrementado el valor a: 3
//...
//Valor final del contador: 15

