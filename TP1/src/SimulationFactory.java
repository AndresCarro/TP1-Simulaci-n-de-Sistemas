import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimulationFactory {

    private final List<Particle> ParticlesList;
    private final Grid SimulatedGrid;
    private final double radiusNeighbour;
    private final List<Particle>[] neighbourParticlesCIM;

    public SimulationFactory(int M, double L, int N, double radiusParticle, double radiusNeighbour) {
        this.radiusNeighbour = radiusNeighbour;
        this.SimulatedGrid = new Grid(M, L);
        this.ParticlesList = new ArrayList<>();
        this.neighbourParticlesCIM = new List[N];

        for (int i = 0; i < N; i++) {
            this.neighbourParticlesCIM[i] = new ArrayList<>();
            Particle auxParticle = new Particle(L, radiusParticle);
            while (!this.SimulatedGrid.addParticle(auxParticle)) {
                auxParticle = new Particle(L, radiusParticle);
            }
            this.ParticlesList.add(auxParticle);
        }
    }

    public void CIM(){
        double counter = 0;
        for(Particle particle1 : ParticlesList){
            for(int[] neighbourCell : particle1.getNeighbourCells()){
                ParticlesList aux = SimulatedGrid.getParticleGrid()[neighbourCell[0]][neighbourCell[1]];
                if(aux != null){
                    System.out.println("analizando: " + neighbourCell[0] + "--" + neighbourCell[1]);
                    for(Particle particle2 : aux.getParticles()){
                        System.out.println("Calculando: " + particle1 + "--" + particle2);
                        if(isNeighbour(particle1, particle2)) {
                            counter++;
                            System.out.println("-------");
                            System.out.println("Son vecinas");
                            System.out.println(particle1);
                            System.out.println(particle2);
                            System.out.println("-------");
                        }
                    }
                }
            }
            for(Particle particle2 : SimulatedGrid.getParticleGrid()[particle1.getxCell()][particle1.getyCell()].getParticles()){
                if(!particle2.equals(particle1)){
                    if(isNeighbour(particle1, particle2)){
                        counter+=0.5;
                        // TODO: hacer insert con list => no repetir vecinos
                        System.out.println("-------");
                        System.out.println("Son vecinas");
                        System.out.println(particle1);
                        System.out.println(particle2);
                        System.out.println("-------");
                    }
                }
            }
        }
        System.out.println("\n\n\ncounter:" + counter + "\n\n\n");
    }

    public void Force(){
        int counter = 0;
        for(int i=0; i<ParticlesList.size(); i++) {
            for(int j=i+1; j<ParticlesList.size(); j++) {
                if(isNeighbour(ParticlesList.get(i), ParticlesList.get(j))) {
                    counter++;
                    /*
                    System.out.println("-------");
                    System.out.println("Son vecinas");
                    System.out.println(ParticlesList.get(i));
                    System.out.println(ParticlesList.get(j));
                    System.out.println("-------");

                     */
                }
            }
        }
        System.out.println("\n\n\ncounter:" + counter + "\n\n\n");

    }

    public boolean isNeighbour(Particle particle1, Particle particle2){
        int dx = (int) Math.abs(particle1.getX() - particle2.getX());
        int dy = (int) Math.abs(particle1.getY() - particle2.getY());
        double hypotenuse = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);
        return radiusNeighbour>hypotenuse;
    }

    public void printParticles() {
        System.out.println("Particulas:");
        for (Particle particle : ParticlesList){
            System.out.println(particle);
        }
    }

    public void printGrid(){
        System.out.println("Grilla:");
        SimulatedGrid.printGrid();
    }

}
