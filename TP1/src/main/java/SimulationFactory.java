import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        //Particle 1000
        //Particle borderParticle = new Particle(0.1, 0.1, radiusParticle);
        //this.SimulatedGrid.addParticle(borderParticle);
        //this.ParticlesList.add(borderParticle);
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
            particleNeighboursCIM.putIfAbsent(particle1,new LinkedList<>());
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

    public void printNeighboursCIM(long totalTime){
        writeOutput("cim_output.txt",particleNeighboursCIM,totalTime);
    }


    public int NeighboursCIMCount(){
        int counter = 0;
        for(Particle particle : ParticlesList) {
            if(particleNeighboursCIM.get(particle) != null){
                counter += particleNeighboursCIM.get(particle).size();
            }
        }
        return counter;
    }

    public void Force(){
        this.particleNeighboursForce = new HashMap<>();
        for(int i=0; i<ParticlesList.size(); i++) {
            particleNeighboursForce.putIfAbsent(ParticlesList.get(i),new ArrayList<>());
            for(int j=0; j<ParticlesList.size(); j++) {
                //System.out.println("Calculando: " + ParticlesList.get(i) + "--" + ParticlesList.get(j));
                if (!ParticlesList.get(i).equals(ParticlesList.get(j))) {
                    if (SimulatedGrid.isNeighbour(ParticlesList.get(i), ParticlesList.get(j))) {
                        //System.out.println("-------");
                        //System.out.println("Son vecinas");
                        //System.out.println(ParticlesList.get(i));
                        //System.out.println(ParticlesList.get(j));
                        //System.out.println("-------");
                        particleNeighboursForce.putIfAbsent(ParticlesList.get(i), new LinkedList<>());
                        if (!particleNeighboursForce.get(ParticlesList.get(i)).contains(ParticlesList.get(j))) {
                            particleNeighboursForce.get(ParticlesList.get(i)).add(ParticlesList.get(j));
                        }
                        particleNeighboursForce.putIfAbsent(ParticlesList.get(j), new LinkedList<>());
                        if (!particleNeighboursForce.get(ParticlesList.get(j)).contains(ParticlesList.get(i))) {
                            particleNeighboursForce.get(ParticlesList.get(j)).add(ParticlesList.get(i));
                        }
                    }
                }
            }
        }
    }

    public void printNeighboursForce(long totalTime){
        writeOutput("force_output.txt",particleNeighboursForce,totalTime);
    }

    public int NeighboursForceCount(){
        int counter = 0;

        for(Particle particle : ParticlesList) {
            if(particleNeighboursForce.get(particle) != null){
                counter += particleNeighboursForce.get(particle).size();
            }
        }
        return counter;
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

    public void writeOutput(String filename, Map<Particle,List<Particle>> particleNeighbours, long totalTime) {
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
            sb.append("Total time: ").append(totalTime).append("ms");
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutputInJson(String filename) {
        main.java.SimulationOutput output = new main.java.SimulationOutput();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for(Particle p : particleNeighboursCIM.keySet()) {
            List<Integer> neighboursIds = new ArrayList<>();
            for(Particle neighbours : particleNeighboursCIM.get(p)) {
                neighboursIds.add(neighbours.getId());
            }
            output.getParticlesNeighbours().put(p.getId(),neighboursIds);
        }
        String stringOutput = gson.toJson(output);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(stringOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOvitoOutput(int particleId){
        String filename = "output.xyz";
        String COMMON_PARTICLE = "0";
        String SELECTED_PARTICLE = "1";
        String NEIGHBOUR_PARTICLE = "2";
        StringBuilder sb = new StringBuilder();
        sb.append(ParticlesList.size());
        Particle selected_particle = ParticlesList.get(particleId);
        try (FileWriter writer = new FileWriter(filename)) {
            sb.append("\n\n");
            for (Particle particle : ParticlesList){
                sb.append(particle.getX()).append(" ").append(particle.getY());
                sb.append(" ");
                if(particle.getId() == particleId) {
                    sb.append(SELECTED_PARTICLE);
                } else if(this.particleNeighboursCIM.get(selected_particle).contains(particle)) {
                    sb.append(NEIGHBOUR_PARTICLE);
                } else {
                    sb.append(COMMON_PARTICLE);
                }
                sb.append("\n");
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
