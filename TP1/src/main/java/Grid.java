import java.util.ArrayList;

public class Grid {

    private final int M;
    private final double L;
    private final double Msize;
    private final double radiusNeighbour;
    private final boolean boundaryConditions;
    private final ParticlesList[][] ParticleGrid;

    public Grid(int M, double L, boolean boundaryConditions, double radiusNeighbour){
        this.boundaryConditions = boundaryConditions;
        this.M = M;
        this.L = L;
        this.Msize = L/M;
        this.radiusNeighbour = radiusNeighbour;
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

        //System.out.println(particle);
        //for(int[] cells: generateNeighbourCells(gridX, gridY)) {
        //    System.out.println(cells[0] + "--" + cells[1]);
        //}

        if (ParticleGrid[gridX][gridY] == null){
            ParticleGrid[gridX][gridY] = new ParticlesList(new ArrayList<>());
        }
        boolean aux = ParticleGrid[gridX][gridY].addParticle(particle);

        /*
        if(aux){
            double relativeX = particle.getX() - gridX*Msize;
            double relativeY = particle.getY() - gridY*Msize;
            boolean right = false, left = false, upper = false, button = false;
            if( relativeX + particle.getRadius() > Msize){
                right = true;
                if (ParticleGrid[(gridX+1)%M][gridY] == null){
                    ParticleGrid[(gridX+1)%M][gridY] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX+1)%M][gridY].addParticle(particle);
            }
            if( relativeX - particle.getRadius() < 0){
                left = true;
                if (ParticleGrid[(gridX-1+M)%M][gridY] == null){
                    ParticleGrid[(gridX-1+M)%M][gridY] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX-1+M)%M][gridY].addParticle(particle);
            }
            if( relativeY + particle.getRadius() > Msize){
                upper = true;
                if (ParticleGrid[gridX][(gridY+1)%M] == null){
                    ParticleGrid[gridX][(gridY+1)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[gridX][(gridY+1)%M].addParticle(particle);
            }
            if( relativeY - particle.getRadius() < 0){
                button = true;
                if (ParticleGrid[gridX][(gridY-1+M)%M] == null){
                    ParticleGrid[gridX][(gridY-1+M)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[gridX][(gridY-1+M)%M].addParticle(particle);
            }
            if(right && upper){
                if (ParticleGrid[(gridX+1)%M][(gridY+1)%M] == null){
                    ParticleGrid[(gridX+1)%M][(gridY+1)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX+1)%M][(gridY+1)%M].addParticle(particle);
            }
            if(right && button){
                if (ParticleGrid[(gridX+1)%M][(gridY-1+M)%M] == null){
                    ParticleGrid[(gridX+1)%M][(gridY-1+M)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX+1)%M][(gridY-1+M)%M].addParticle(particle);
            }
            if(left && upper){
                if (ParticleGrid[(gridX-1+M)%M][(gridY+1)%M] == null){
                    ParticleGrid[(gridX-1+M)%M][(gridY+1)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX-1+M)%M][(gridY+1)%M].addParticle(particle);
            }
            if(left && button){
                if (ParticleGrid[(gridX-1+M)%M][(gridY-1+M)%M] == null){
                    ParticleGrid[(gridX-1+M)%M][(gridY-1+M)%M] = new ParticlesList(new ArrayList<>());
                }
                ParticleGrid[(gridX-1+M)%M][(gridY-1+M)%M].addParticle(particle);
            }
        }
        */

        return aux;
    }

    public int[][] generateNeighbourCells(int gridX, int gridY) {
        if (boundaryConditions) {
            return new int[][]{
                    {gridX, gridY},
                    {gridX, (gridY + 1) % M},
                    {(gridX + 1) % M, (gridY + 1) % M},
                    {(gridX + 1) % M, gridY},
                    {(gridX + 1) % M, (gridY - 1 + M) % M}};
        }
        if (gridY + 1 == M) {
            if (gridX + 1 == M) {
                return new int[][]{{gridX, gridY}};
            }
            return new int[][]{
                    {gridX, gridY},
                    {gridX + 1, gridY},
                    {gridX + 1, gridY - 1}};
        } else {
            if (gridX + 1 == M) {
                return new int[][]{
                        {gridX, gridY},
                        {gridX, gridY + 1}};
            } else {
                if(gridY - 1 < 0){
                    return new int[][]{
                            {gridX, gridY},
                            {gridX, (gridY + 1)},
                            {(gridX + 1), (gridY + 1)},
                            {(gridX + 1), gridY}};
                }else{
                    return new int[][]{
                            {gridX, gridY},
                            {gridX, (gridY + 1)},
                            {(gridX + 1), (gridY + 1)},
                            {(gridX + 1), gridY},
                            {(gridX + 1), (gridY - 1)}};
                }
            }
        }
    }

    public boolean isNeighbour(Particle particle1, Particle particle2){
        double directx = Math.abs(particle1.getX() - particle2.getX());
        double dx, dy;
        if(directx*2 > L && boundaryConditions){
            dx = (L - directx);
        }else{
            dx = directx;
        }

        double directy = Math.abs(particle1.getY() - particle2.getY());
        if(directy*2 > L && boundaryConditions){
            dy = (L - directy);
        }else{
            dy = directy;
        }

        //System.out.println(dy + "---" + dx);
        double hypotenuse = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);
        double deltaBorder = hypotenuse - (particle1.getRadius() + particle2.getRadius());
        return radiusNeighbour>deltaBorder;
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

}
