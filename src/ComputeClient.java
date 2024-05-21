import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class ComputeClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Compute comp = (Compute) registry.lookup("Compute");
            System.out.println("Підключено до сервера.");

            Task<Double> piTask = new PiTask();
            Double pi = comp.computeTask(piTask);
            System.out.println("Число Pi: " + pi);

            Task<Double> eTask = new ETask();
            Double eValue = comp.computeTask(eTask);
            System.out.println("Число е: " + eValue);

            System.out.println("Клієнт завершив роботу.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}