package compute;

public class BlackScholes {

    // Black-Scholes formula
    public static double callPrice(double S, double X, double r, double sigma, double T) {
        double d1 = (Math.log(S/X) + (r + sigma * sigma/2) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);
        return S * Gaussian.Phi(d1) - X * Math.exp(-r * T) * Gaussian.Phi(d2);
    }

    // estimate by Monte Carlo simulation
    public static double call(double S, double X, double r, double sigma, double T) {
        int N = 10000;
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            double eps = StdRandom.gaussian();
            double price = S * Math.exp(r*T - 0.5*sigma*sigma*T + sigma*eps*Math.sqrt(T));
            double value = Math.max(price - X, 0);
            sum += value;
        }
        double mean = sum / N;

        return Math.exp(-r*T) * mean;
    }

    // estimate by Monte Carlo simulation
    public static double call2(double S, double X, double r, double sigma, double T) {
        int N = 10000;
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            double price = S;
            double dt = T/10000.0;
            for (double t = 0; t <= T; t = t + dt) {
                price += r*price*dt +sigma*price*Math.sqrt(dt)*StdRandom.gaussian();
            }
            double value = Math.max(price - X, 0);
            sum += value;
        }
        double mean = sum / N;

        return Math.exp(-r*T) * mean;
    }



    public static void main(String[] args) {
        double S     = Double.parseDouble(args[0]);
        double X     = Double.parseDouble(args[1]);
        double r     = Double.parseDouble(args[2]);
        double sigma = Double.parseDouble(args[3]);
        double T     = Double.parseDouble(args[4]);
        System.out.println(callPrice(S, X, r, sigma, T));
        System.out.println(call(S, X, r, sigma, T));
        System.out.println(call2(S, X, r, sigma, T));
    }
}