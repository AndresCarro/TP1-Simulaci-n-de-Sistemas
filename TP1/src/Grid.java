import java.util.ArrayList;

public class Grid {

    private final int M;
    private final double L;
    private final double Msize;
    private final ParticlesList[][] ParticleGrid;

    public Grid(int M, double L){
        this.M = M;
        this.L = L;
        this.Msize = L/M;

        this.ParticleGrid = new ParticlesList[M][M];
    }

    public double getM() {
        return M;
    }

    public double getL() {
        return L;
    }

    public ParticlesList[][] getParticleGrid() {
        return ParticleGrid;
    }

    public boolean addParticle(Particle particle){
        int gridX = (int) Math.floor(particle.getX() / Msize);
        int gridY = (int) Math.floor(particle.getY() / Msize);

        particle.setxCell(gridX);
        particle.setyCell(gridY);
        particle.setNeighbourCells(generateNeighbourCells(gridX, gridY));
        System.out.println(particle);

        for(int[] cells: generateNeighbourCells(gridX, gridY)){
            System.out.println(cells[0] + "--" + cells[1]);
        }

        if (ParticleGrid[gridX][gridY] == null){
            ParticleGrid[gridX][gridY] = new ParticlesList(new ArrayList<>());
        }
        return ParticleGrid[gridX][gridY].addParticle(particle);
    }

    public int[][] generateNeighbourCells(int gridX, int gridY){
        return new int[][]{{gridX, (gridY+1)%M},
                {(gridX+1)%M, (gridY+1)%M},
                {(gridX+1)%M, gridY},
                {(gridX+1)%M, (gridY-1+M)%M}};
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
