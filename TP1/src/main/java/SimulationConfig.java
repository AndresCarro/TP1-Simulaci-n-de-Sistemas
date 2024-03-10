public class SimulationConfig {
    private int M;
    private double L;
    private double radius;
    private int N;
    private double radiusNeighbour;

    public SimulationConfig(){

    }

    public void setM(int m) {
        M = m;
    }

    public void setL(double l) {
        L = l;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setN(int n) {
        N = n;
    }

    public void setRadiusNeighbour(double radiusNeighbour) {
        this.radiusNeighbour = radiusNeighbour;
    }

    public int getM() {
        return M;
    }

    public double getL() {
        return L;
    }

    public double getRadius() {
        return radius;
    }

    public int getN() {
        return N;
    }

    public double getRadiusNeighbour() {
        return radiusNeighbour;
    }

    @Override
    public String toString(){
        return "## Configuration for this simulation ##\nM: " + M + "\nL: " + L + "\nradius: " + radius + "\nN: " + N + "\nradiusNeighbour: " + radiusNeighbour;
    }
}