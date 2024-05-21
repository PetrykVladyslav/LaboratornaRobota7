import java.math.BigDecimal;
public class ETask implements Task<Double> {
    public Double execute() {
        BigDecimal e = BigDecimal.ZERO;
        BigDecimal term;
        int n = 0;
        while (n < 1000) {
            term = BigDecimal.valueOf(1).divide(factorial(n), 100, BigDecimal.ROUND_HALF_UP);
            e = e.add(term);
            n++;
        }
        return e.setScale(16, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    private static BigDecimal factorial(int n) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }
}