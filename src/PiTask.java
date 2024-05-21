public class PiTask implements Task<Double> {
    public Double execute() {
        double pi = 0.0;
        for (int i = 0; i < 100000; i++) {
            pi += 4.0 * (Math.atan(1.0 / (5.0 * (2 * i + 1))) - Math.atan(1.0 / (239.0 * (2 * i + 1))));
        }
        return pi;
    }
}