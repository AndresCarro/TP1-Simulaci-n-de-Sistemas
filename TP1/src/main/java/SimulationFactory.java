import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationFactory {

    private final List<Particle> ParticlesList;
    private final Grid SimulatedGrid;
    private Map<Particle,List<Particle>> particleNeighboursCIM;
    private Map<Particle,List<Particle>> particleNeighboursForce;


    public SimulationFactory(int M, double L, int N, double radiusParticle, double radiusNeighbour, boolean boundaryConditions) {
        this.SimulatedGrid = new Grid(M, L, boundaryConditions, radiusNeighbour);
        this.ParticlesList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            Particle auxParticle = new Particle(L, radiusParticle);
            while (!this.SimulatedGrid.addParticle(auxParticle)) {
                auxParticle = new Particle(L, radiusParticle);
            }
            this.ParticlesList.add(auxParticle);
        }
    }

    public SimulationFactory(int M, double L, double radiusParticle, double radiusNeighbour, boolean boundaryConditions, double[][] particles) {
        this.SimulatedGrid = new Grid(M, L, boundaryConditions, radiusNeighbour);
        this.ParticlesList = new ArrayList<>();

        for (double[] positions : particles) {
            Particle auxParticle = new Particle(positions[0], positions[1], radiusParticle);
            while (!this.SimulatedGrid.addParticle(auxParticle)) {
                auxParticle = new Particle(L, radiusParticle);
            }
            this.ParticlesList.add(auxParticle);
        }
    }

    public void CIM(){
        this.particleNeighboursCIM = new HashMap<>();
        for(Particle particle1 : ParticlesList){
            for(int[] neighbourCell : particle1.getNeighbourCells()){
                ParticlesList aux = SimulatedGrid.getParticleGrid()[neighbourCell[0]][neighbourCell[1]];
                if(aux != null){
                    //System.out.println("analizando: " + neighbourCell[0] + "--" + neighbourCell[1]);
                    for(Particle particle2 : aux.getParticles()) {
                        if (!particle2.equals(particle1)) {
                            //System.out.println("Calculando: " + particle1 + "--" + particle2);
                            if (SimulatedGrid.isNeighbour(particle1, particle2)) {
                                //System.out.println("-------");
                                //System.out.println("Son vecinas");
                                //System.out.println(particle1);
                                //System.out.println(particle2);
                                //System.out.println("-------");
                                particleNeighboursCIM.putIfAbsent(particle1,new LinkedList<>());
                                if (!particleNeighboursCIM.get(particle1).contains(particle2)) {
                                    particleNeighboursCIM.get(particle1).add(particle2);
                                }
                                particleNeighboursCIM.putIfAbsent(particle2,new LinkedList<>());
                                if (!particleNeighboursCIM.get(particle2).contains(particle1)) {
                                    particleNeighboursCIM.get(particle2).add(particle1);
                                }
                            }
                        }
                    }
                }
            }
            /*
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
                        if(!particleNeighbours.get(particle1).contains(particle2)){
                            particleNeighbours.get(particle1).add(particle2);
                        }
                        if(!particleNeighbours.get(particle2).contains(particle1)){
                            particleNeighbours.get(particle2).add(particle1);
                        }
                    }
                }
            }
            */
        }
    }

    public void printNeighboursCIM(){
        writeOutput("cim_output.txt",particleNeighboursCIM);
    }

    public void Force(){
        this.particleNeighboursForce = new HashMap<>();
        for(int i=0; i<ParticlesList.size(); i++) {
            particleNeighboursForce.putIfAbsent(ParticlesList.get(i),new ArrayList<>());
            for(int j=i+1; j<ParticlesList.size(); j++) {
                //System.out.println("Calculando: " + ParticlesList.get(i) + "--" + ParticlesList.get(j));
                if(SimulatedGrid.isNeighbour(ParticlesList.get(i), ParticlesList.get(j))) {
                    //System.out.println("-------");
                    //System.out.println("Son vecinas");
                    //System.out.println(ParticlesList.get(i));
                    //System.out.println(ParticlesList.get(j));
                    //System.out.println("-------");
                    particleNeighboursForce.putIfAbsent(ParticlesList.get(i),new LinkedList<>());
                    if(!particleNeighboursForce.get(ParticlesList.get(i)).contains(ParticlesList.get(j))){
                        particleNeighboursForce.get(ParticlesList.get(i)).add(ParticlesList.get(j));
                    }
                    particleNeighboursForce.putIfAbsent(ParticlesList.get(j),new LinkedList<>());
                    if(!particleNeighboursForce.get(ParticlesList.get(j)).contains(ParticlesList.get(i))){
                        particleNeighboursForce.get(ParticlesList.get(j)).add(ParticlesList.get(i));
                    }
                }
            }
        }
    }

    public void printNeighboursForce(){
        writeOutput("force_output.txt",particleNeighboursForce);
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

    public void writeOutput(String filename, Map<Particle,List<Particle>> particleNeighbours) {
        StringBuilder sb = new StringBuilder();
        try (FileWriter writer = new FileWriter(filename)) {
            for(Particle particle: particleNeighbours.keySet()) {
                sb.append("Particle id: ").append(particle.getId()).append("\t Neigbour particles: ");
                if(particleNeighbours.get(particle).isEmpty()) {
                    sb.append("-");
                }
                List<Particle> particleItem = particleNeighbours.get(particle);
                for(Particle neighbours : particleItem) {
                    sb.append(neighbours.getId()).append(" ");
                }
                sb.append("\n");
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wirteOvitoOutput(){
        String filename = "ovito_output.xyz";
        StringBuilder sb = new StringBuilder();
        sb.append(ParticlesList.size());
        String z = "0.0";
        try (FileWriter writer = new FileWriter(filename)) {
            sb.append("\n");
            for (Particle particle : ParticlesList){
                sb.append(particle.getX()).append("\t").append(particle.getY()).append("\n");
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
