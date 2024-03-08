import java.util.Objects;
import java.util.Random;

public class Particle {

    private float x;
    private float y;
    private int radius;

    public Particle(float radius, Grid grid){
        Random random = new Random();
        this.x = random.nextFloat(grid.getL());
        this.y = random.nextFloat(grid.getL());
        if (radius == 0) {
            grid.addParticle(this);
        } else {
            grid.addParticleWithRadius(this);
        }
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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
}

