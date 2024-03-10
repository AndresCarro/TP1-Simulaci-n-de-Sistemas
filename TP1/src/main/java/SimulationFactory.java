import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationFactory {

    private final List<Particle> ParticlesList;
    private final Grid SimulatedGrid;

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
        Particle aux1 = new Particle(0.1, 0.1, radiusParticle);
        this.SimulatedGrid.addParticle(aux1);
        this.ParticlesList.add(aux1);
        Particle aux2 = new Particle(0.1, L - 0.1, radiusParticle);
        this.SimulatedGrid.addParticle(aux2);
        this.ParticlesList.add(aux2);
    }

    public void CIM(){
        double counter = 0;
        Map<Particle,List<Particle>> particleNeighbours = new HashMap<>();
        for(Particle particle1 : ParticlesList){
            particleNeighbours.putIfAbsent(particle1,new LinkedList<>());
        }
        for(Particle particle1 : ParticlesList){
            for(int[] neighbourCell : particle1.getNeighbourCells()){
                ParticlesList aux = SimulatedGrid.getParticleGrid()[neighbourCell[0]][neighbourCell[1]];
                if(aux != null){
                    System.out.println("analizando: " + neighbourCell[0] + "--" + neighbourCell[1]);
                    for(Particle particle2 : aux.getParticles()) {
                        if (!particle2.equals(particle1)) {
                            System.out.println("Calculando: " + particle1 + "--" + particle2);
                            if (SimulatedGrid.isNeighbour(particle1, particle2)) {
                                counter++;
                                System.out.println("-------");
                                System.out.println("Son vecinas");
                                System.out.println(particle1);
                                System.out.println(particle2);
                                System.out.println("-------");
                                if (!particleNeighbours.get(particle1).contains(particle2)) {
                                    particleNeighbours.get(particle1).add(particle2);
                                }
                                if (!particleNeighbours.get(particle2).contains(particle1)) {
                                    particleNeighbours.get(particle2).add(particle1);
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
        writeOutput("cim_output.txt",particleNeighbours);
        System.out.println("\n\nVecinos de CIM: " + counter + "\n\n");
    }

    public void Force(){
        int counter = 0;
        Map<Particle,List<Particle>> particleNeighbours = new HashMap<>();
        for(Particle particle1 : ParticlesList){
            particleNeighbours.putIfAbsent(particle1,new LinkedList<>());
        }
        for(int i=0; i<ParticlesList.size(); i++) {
            particleNeighbours.putIfAbsent(ParticlesList.get(i),new ArrayList<>());
            for(int j=i+1; j<ParticlesList.size(); j++) {
                System.out.println("Calculando: " + ParticlesList.get(i) + "--" + ParticlesList.get(j));
                if(SimulatedGrid.isNeighbour(ParticlesList.get(i), ParticlesList.get(j))) {
                    counter++;
                    System.out.println("-------");
                    System.out.println("Son vecinas");
                    System.out.println(ParticlesList.get(i));
                    System.out.println(ParticlesList.get(j));
                    System.out.println("-------");
                    if(!particleNeighbours.get(ParticlesList.get(i)).contains(ParticlesList.get(j))){
                        particleNeighbours.get(ParticlesList.get(i)).add(ParticlesList.get(j));
                    }
                    if(!particleNeighbours.get(ParticlesList.get(j)).contains(ParticlesList.get(i))){
                        particleNeighbours.get(ParticlesList.get(j)).add(ParticlesList.get(i));
                    }
                    //particleNeighbours.get(ParticlesList.get(j)).add(ParticlesList.get(i));
                }
            }
        }
        writeOutput("force_output.txt",particleNeighbours);
        System.out.println("\n\nVecinos de Force: " + counter + "\n\n");
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
