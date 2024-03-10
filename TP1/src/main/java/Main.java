import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // TODO: Leer de un archivo
        int M = 10;
        double L = 30;
        double radius = 0.05;
        int N = 10;
        double radiusNeighbour = 0.11;
        boolean boundaryConditions = true;

        SimulationFactory simulator = new SimulationFactory(M, L, N, radius, radiusNeighbour, boundaryConditions);

        // TEST para saber si esta bien
        //simulator.printParticles();
        //simulator.printGrid();

        simulator.CIM();
        System.out.println("CAMBIO DE METODO:");
        simulator.Force();
    }
}