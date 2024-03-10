import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimulationConfig config = readConfig("input.json");
        if(config == null) {
            return;
        }

        double[][] particles = readParticles(config.getParticlesInput()).toArray(new double[0][]);
        SimulationFactory simulator = new SimulationFactory(config.getM(), config.getL(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions(), particles);
        //SimulationFactory simulator = new SimulationFactory(config.getM(), config.getL(), config.getN(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions());

        // PRINT para saber si esta bien
        //simulator.printParticles();
        //simulator.printGrid();

        long startTimeCIM = System.currentTimeMillis();
        simulator.CIM();
        long endTimeCIM = System.currentTimeMillis();
        long executionTimeCIM = endTimeCIM - startTimeCIM;
        System.out.println("Tiempo de ejecución de CIM: " + executionTimeCIM + " milisegundos");
        simulator.printNeighboursCIM();


        long startTimeForce = System.currentTimeMillis();
        simulator.Force();
        long endTimeForce = System.currentTimeMillis();
        long executionTimeForce = endTimeForce - startTimeForce;
        System.out.println("Tiempo de ejecución de Force: " + executionTimeForce + " milisegundos");
        simulator.printNeighboursForce();
    }


    public static SimulationConfig readConfig(String path){
        Gson gson = new Gson();
        SimulationConfig sConfig = null;
        try (FileReader reader = new FileReader("input.json")) {
            sConfig = gson.fromJson(reader, SimulationConfig.class);
            //System.out.println(sConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sConfig;
    }

    public static List<double[]>  readParticles(String path) {
        List<double[]> vector = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            // Leer el archivo línea por línea
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Dividir la línea en dos números
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    double[] pair = new double[2];
                    pair[0] = Double.parseDouble(parts[0]);
                    pair[1] = Double.parseDouble(parts[1]);
                    vector.add(pair);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Mostrar el vector resultante
        //for (double[] pair : vector) {
        //    System.out.println("[" + pair[0] + ", " + pair[1] + "]");
        //}
        return vector;

    }
}