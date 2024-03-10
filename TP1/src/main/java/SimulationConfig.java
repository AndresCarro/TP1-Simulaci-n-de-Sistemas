public class SimulationConfig {
    private int M;
    private double L;
    private double radius;
    private int N;
    private double radiusNeighbour;
    private boolean boundaryConditions;
    private String particlesInput;
    private int particle;

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

    public void setBoundaryConditions(boolean boundaryConditions) {
        this.boundaryConditions = boundaryConditions;
    }

    public double getRadiusNeighbour() {
        return radiusNeighbour;
    }

    public boolean getBoundaryConditions() {
        return boundaryConditions;
    }

    public String getParticlesInput() {
        return particlesInput;
    }

    public void setParticlesInput(String particlesInput) {
        this.particlesInput = particlesInput;
    }

    public int getParticle() {
        return particle;
    }

    public void setParticle(int particle) {
        this.particle = particle;
    }

    @Override
    public String toString(){
        return "## Configuration for this simulation ##\nM: " + M + "\nL: " + L + "\nradius: " + radius + "\nN: " + N + "\nradiusNeighbour: " + radiusNeighbour;
    }
}
