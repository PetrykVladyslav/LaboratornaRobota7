import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
public class ComputeEngine implements Compute {
    public ComputeEngine() {
        super();
    }
    public <T> T computeTask(Task<T> t) throws RemoteException {
        System.out.println("Завдання отримано: " + t.getClass().getName());
        T result = t.execute();
        System.out.println("Завдання виконано: " + t.getClass().getName());
        return result;
    }
    public static void main(String[] args) {
        Registry registry = null;
        Compute engine = null;

        try {
            System.setProperty("java.rmi.server.codebase", "http://localhost:8000/classes/");

            registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI-реєстр запущений на порту 1099");

            engine = new ComputeEngine();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            registry.rebind("Compute", stub);
            System.out.println("ComputeEngine готовий до роботи.");
        } catch (RemoteException e) {
            System.err.println("Виняток ComputeEngine:");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть 'exit', щоб закрити сервер.");
        String input;
        do {
            input = scanner.nextLine();
        } while (!input.equals("exit"));

        try {
            if (engine != null) {
                UnicastRemoteObject.unexportObject(engine, true);
                System.out.println("Сервер закрито.");
            }
            if (registry != null) {
                UnicastRemoteObject.unexportObject(registry, true);
                System.out.println("RMI-реєстр закрито.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}