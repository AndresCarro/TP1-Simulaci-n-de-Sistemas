import java.util.ArrayList;

public class Grid {

    private final int M;
    private final float L;
    private final float Msize;
    private final ParticlesList[][] ParticleGrid;

    public Grid(int M, float L){
        this.M = M;
        this.L = L;
        this.Msize = L/M;

        this.ParticleGrid = new ParticlesList[M][M];
    }

    public float getM() {
        return M;
    }

    public float getL() {
        return L;
    }

    public ParticlesList[][] getParticleGrid() {
        return ParticleGrid;
    }

    public boolean addParticle(Particle particle){
        int gridX = (int) Math.floor(particle.getX() / Msize);
        int gridY = (int) Math.floor(particle.getY() / Msize);

        if (ParticleGrid[gridX][gridY] == null){
            ParticleGrid[gridX][gridY] = new ParticlesList(new ArrayList<>());
        }
        return ParticleGrid[gridX][gridY].addParticle(particle);
    }

    public void printGrid(){
        int y, x=0;
        for(ParticlesList[] column: ParticleGrid){
            x+=1;
            y=0;
            for(ParticlesList square: column){
                y+=1;
                System.out.println("--------\nx:" + x + ", y:" + y);
                if(square != null){
                    for(Particle particle : square.getParticles()){
                        System.out.println(particle);
                    }
                }
            }
        }
    }

    /*
    public boolean addParticleWithRadius(Particle particle){
        //TODO
        return true;
    }
    */

}
