import java.io.*;

public class Main {
    public static void main(String[] args)
    {

        // TODO: Leer de un archivo
        int M = 10;
        float L = 30;
        float radius = 9.8F;
        int N = 10;
        float radiusNeighbour = 3;

        SimulationFactory simulator = new SimulationFactory(M, L, N, radius, radiusNeighbour);

        // TEST para saber si esta bien
        simulator.printParticles();
        simulator.printGrid();

        // TODO
        simulator.CIM();
        System.out.println("Otro");
        simulator.Force();
    }
}