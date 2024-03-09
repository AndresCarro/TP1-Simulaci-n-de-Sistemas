import java.io.*;

public class Main {
    public static void main(String[] args)
    {

        // TODO: Leer de un archivo
        int M = 10;
        double L = 30;
        double radius = 9.8F;
        int N = 1000;
        double radiusNeighbour = 3;

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