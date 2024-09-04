//Codigo que ejemplifica el uso de todos los estados de un thread dentro de la simulacion de un cajero automático
class CajeroAutomatico implements Runnable {
    private String nombreCliente;

    public CajeroAutomatico(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombreCliente + " ha comenzado su transaccion (Nuevo --> Listo --> Ejecutando).");
            System.out.println(nombreCliente + " esta validando su tarjeta...");
            Thread.sleep(2000);
            System.out.println(nombreCliente + " ha validado su tarjeta.");

            synchronized (this) {
                System.out.println(nombreCliente + " esta esperando para acceder al sistema bancario (Bloqueado).");
                wait();
            }

            System.out.println(nombreCliente + " esta realizando una operacion bancaria (En ejecucion).");
            Thread.sleep(3000);
            System.out.println(nombreCliente + " ha completado su transaccion.");
        } catch (InterruptedException e) {
            System.out.println(nombreCliente + " fue interrumpido.");
        }

        System.out.println(nombreCliente + " ha finalizado la transaccion (Muerto).");
    }

    public synchronized void desbloquearTransaccion() {
        notify();
    }
}

public class SimulacionCajero {
    public static void main(String[] args) {
        CajeroAutomatico cliente1 = new CajeroAutomatico("Cliente 1");
        CajeroAutomatico cliente2 = new CajeroAutomatico("Cliente 2");

        Thread hilo1 = new Thread(cliente1);
        Thread hilo2 = new Thread(cliente2);

        hilo1.start();
        hilo2.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }

        synchronized (cliente1) {
            cliente1.desbloquearTransaccion();
        }

        synchronized (cliente2) {
            cliente2.desbloquearTransaccion();
        }

        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }

        System.out.println("Todas las transacciones han finalizado.");
    }
}
