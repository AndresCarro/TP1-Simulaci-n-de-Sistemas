import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        SimulationConfig sConfig = null;
        try (FileReader reader = new FileReader("TP1/input.json")) {
            sConfig = gson.fromJson(reader, SimulationConfig.class);
            System.out.println(sConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(sConfig == null) {
            return;
        }

        SimulationFactory simulator = new SimulationFactory(sConfig.getM(), sConfig.getL(), sConfig.getN(), sConfig.getRadius(), sConfig.getRadiusNeighbour());

        // TEST para saber si esta bien
        //simulator.printParticles();
        //simulator.printGrid();

        simulator.CIM();
        System.out.println("CAMBIO DE METODO:");
        simulator.Force();
    }
}