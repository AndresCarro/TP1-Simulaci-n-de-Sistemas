import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        SimulationConfig input = readInput("input.json");
        if(input == null) {
            return;
        }

        SimulationFactory simulator = new SimulationFactory(input.getM(), input.getL(), input.getN(), input.getRadius(), input.getRadiusNeighbour(), input.getBoundaryConditions());

        // PRINT para saber si esta bien
        //simulator.printParticles();
        //simulator.printGrid();

        simulator.CIM();
        System.out.println("CAMBIO DE METODO:");
        simulator.Force();
    }


    public static SimulationConfig readInput(String path){
        Gson gson = new Gson();
        SimulationConfig sConfig = null;
        try (FileReader reader = new FileReader("input.json")) {
            sConfig = gson.fromJson(reader, SimulationConfig.class);
            System.out.println(sConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sConfig;
    }
}