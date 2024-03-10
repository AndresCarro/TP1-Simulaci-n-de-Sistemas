import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //oneIteration(config);

        TimeMAnalysis();
    }

    public static void TimeMAnalysis(){
        SimulationConfig config = readConfig("inputMTest.json");
        if(config == null) {
            return;
        }

        try {
            FileWriter writer = new FileWriter("TimeMAnalysis.csv");
            writer.write("Method, M, Time, results");

            for (int M = 1; M < 40; M++) {
                double[][] particles = readParticles(config.getParticlesInput()).toArray(new double[0][]);
                SimulationFactory simulator = new SimulationFactory(M, config.getL(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions(), particles);
                long startTimeCIM = System.currentTimeMillis();
                simulator.CIM();
                long endTimeCIM = System.currentTimeMillis();
                long tiempoCIM = endTimeCIM - startTimeCIM;
                writer.write("\nCIM, " + M + ", " + tiempoCIM + ", " + simulator.NeighboursCIMCount());

                long startTimeForce = System.currentTimeMillis();
                simulator.Force();
                long endTimeForce = System.currentTimeMillis();
                long tiempoForce = endTimeForce - startTimeForce;
                writer.write("\nForce, " + M + ", " + tiempoForce + ", " + simulator.NeighboursForceCount());
            }
            writer.close();

        } catch(IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static void oneIteration(){
        SimulationConfig config = readConfig("input.json");
        if(config == null) {
            return;
        }

        double[][] particles = readParticles(config.getParticlesInput()).toArray(new double[0][]);
        SimulationFactory simulator = new SimulationFactory(config.getM(), config.getL(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions(), particles);

        int particleSelected = config.getParticle();
        System.out.println("La particula a revisar es: " + particleSelected);

        long startTimeCIM = System.currentTimeMillis();
        simulator.CIM();
        long endTimeCIM = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de CIM: " + (endTimeCIM - startTimeCIM) + " milisegundos");
        simulator.printNeighboursCIM();


        long startTimeForce = System.currentTimeMillis();
        simulator.Force();
        long endTimeForce = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de Force: " + (endTimeForce - startTimeForce) + " milisegundos");
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