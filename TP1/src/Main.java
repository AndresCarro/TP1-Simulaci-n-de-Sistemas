import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args)
    {

        int M = 10;
        float L = 10;
        float radius = 9.8F;
        int N = 10;

        SimulationFactory simulator = new SimulationFactory(M, L, N, radius);

        simulator.printParticles();

        simulator.printGrid();

        simulator.CIM();

        simulator.Force();
    }
}