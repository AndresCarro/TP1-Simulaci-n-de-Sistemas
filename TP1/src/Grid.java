import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Grid {

    private final int M;
    private final float L;
    private final float Msize;
    private List<Particle>[][] ParticleGrid;

    public Grid(int M, float L){
        this.M = M;
        this.L = L;
        this.Msize = L/M;
        this.ParticleGrid = new List[M][M];
    }

    public float getM() {
        return M;
    }

    public float getL() {
        return L;
    }

    public List<Particle>[][] getParticleGrid() {
        return ParticleGrid;
    }

    public boolean addParticle(Particle particle){
        int gridX = (int) Math.floor(particle.getX() / Msize);
        int gridY = (int) Math.floor(particle.getY() / Msize);

        if (ParticleGrid[gridX][gridY] == null){
            ParticleGrid[gridX][gridY] = new LinkedList<Particle>();
        }
        return ParticleGrid[gridX][gridY].add(particle);
    }

    public boolean addParticleWithRadius(Particle particle){
        //TODO
        return true;
    }

}
