import java.util.Objects;
import java.util.Random;

public class Particle {

    private static int nextId = 0;
    private int id;
    private double x;
    private double y;
    private double radius;
    private int xCell;
    private int yCell;
    private int[][] neighbourCells;

    public Particle(double L, double radius){
        this.id = nextId++;
        Random random = new Random();
        this.x = random.nextDouble(L);
        this.y = random.nextDouble(L);
        this.radius = radius;
    }

    public Particle(double x, double y, double radius){
        this.id = nextId++;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getxCell() {
        return xCell;
    }

    public void setxCell(int xCell) {
        this.xCell = xCell;
    }

    public int getyCell() {
        return yCell;
    }

    public void setyCell(int yCell) {
        this.yCell = yCell;
    }

    public int[][] getNeighbourCells() {
        return neighbourCells;
    }

    public void setNeighbourCells(int[][] neighbourCells) {
        this.neighbourCells = neighbourCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Double.compare(particle.x, x) == 0 && Double.compare(particle.y, y) == 0 && radius == particle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, radius);
    }

    @Override
    public String toString() {
        return "x:" + x + ", Y:" + y + ", gridX:" + xCell + ", gridY:" + yCell;
    }
}

