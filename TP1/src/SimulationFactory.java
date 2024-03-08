import java.util.ArrayList;
import java.util.List;

public class SimulationFactory {

    private final List<Particle> ParticlesList;
    private final Grid SimulatedGrid;

    public SimulationFactory(int M, float L, int N, float radius) {
        this.SimulatedGrid = new Grid(M, L);
        this.ParticlesList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Particle auxParticle = new Particle(L, radius);
            while (!this.SimulatedGrid.addParticle(auxParticle)) {
                auxParticle = new Particle(L, radius);
            }
            this.ParticlesList.add(auxParticle);
        }
    }

    public void CIM(){
        return;
    }

    public void Force(){
        return;
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
