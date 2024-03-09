import java.util.Objects;
import java.util.Random;

public class Particle {

    private float x;
    private float y;
    private float radius;
    private int xCell;
    private int yCell;
    private int[][] neighbourCells;

    public Particle(float L, float radius){
        Random random = new Random();
        this.x = random.nextFloat(L);
        this.y = random.nextFloat(L);
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
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
        return Float.compare(particle.x, x) == 0 && Float.compare(particle.y, y) == 0 && radius == particle.radius;
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

