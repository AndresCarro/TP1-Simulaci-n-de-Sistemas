package main.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationOutput {
    private Map<Integer, List<Integer>> particlesNeighbours ;

    public SimulationOutput() {
        this.particlesNeighbours = new HashMap<>();
    }

    public Map<Integer, List<Integer>> getParticlesNeighbours() {
        return particlesNeighbours;
    }

    public void setParticlesNeighbours(Map<Integer, List<Integer>> particlesNeighbours) {
        this.particlesNeighbours = particlesNeighbours;
    }
}
