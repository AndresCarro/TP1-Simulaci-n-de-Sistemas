import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        oneIteration();

        //MAnalysis();

        //NAnalysis();

        //MLAnalysis();
    }

    public static void oneIteration(){
        SimulationConfig config = readConfig("input.json");
        if(config == null) {
            return;
        }
        System.out.println("M:" + config.getM() + " L:" + config.getL() + "N:" + config.getN() + " Radius:" +config.getRadius() + " RadiusNei:" +config.getRadiusNeighbour() + " Boundary:" + config.getBoundaryConditions());

        SimulationFactory simulator = new SimulationFactory(config.getM(),config.getL(),config.getN(),config.getRadius(),config.getRadiusNeighbour(),config.getBoundaryConditions());

        int particleSelected = config.getParticle();
        System.out.println("La particula a revisar es: " + particleSelected);

        long startTimeCIM = System.currentTimeMillis();
        simulator.CIM();
        long endTimeCIM = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de CIM: " + (endTimeCIM - startTimeCIM) + " milisegundos");
        simulator.printNeighboursCIM(endTimeCIM-startTimeCIM);


        long startTimeForce = System.currentTimeMillis();
        simulator.Force();
        long endTimeForce = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de Force: " + (endTimeForce - startTimeForce) + " milisegundos");
        simulator.printNeighboursForce(endTimeForce-startTimeForce);
        simulator.writeOvitoOutput(config.getParticle());
        simulator.writeOutputInJson("cim_output.json");
    }

    public static void MAnalysis(){
        SimulationConfig config = readConfig("inputMTest.json");
        if(config == null) {
            return;
        }
        System.out.println(config.getL() + " " +config.getRadius() + " " +config.getRadiusNeighbour() + " " + config.getBoundaryConditions());
        try {
            FileWriter writer = new FileWriter("MAnalysis.csv");
            writer.write("Method,M,Time,results");
            double[][] particles = readParticles(config.getParticlesInput()).toArray(new double[0][]);


            for (int M = 1; M < 50; M++) {
                SimulationFactory simulator = new SimulationFactory(M, config.getL(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions(), particles);

                long startTimeCIM = System.currentTimeMillis();
                simulator.CIM();
                long endTimeCIM = System.currentTimeMillis();
                long tiempoCIM = endTimeCIM - startTimeCIM;
                writer.write("\nCIM," + M + "," + tiempoCIM + "," + simulator.NeighboursCIMCount());
            }
            for (int M = 1; M < 50; M++) {
                SimulationFactory simulator = new SimulationFactory(M, config.getL(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions(), particles);

                long startTimeForce = System.currentTimeMillis();
                simulator.Force();
                long endTimeForce = System.currentTimeMillis();
                long tiempoForce = endTimeForce - startTimeForce;
                writer.write("\nForce," + M + "," + tiempoForce + "," + simulator.NeighboursForceCount());
            }
            writer.close();

        } catch(IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static void NAnalysis(){
        SimulationConfig config = readConfig("inputNTest.json");
        if(config == null) {
            return;
        }
        System.out.println("M:" + config.getM() + " L:" + config.getL() + " Radius:" +config.getRadius() + " RadiusNei:" +config.getRadiusNeighbour() + " Boundary:" + config.getBoundaryConditions());

        try {
            FileWriter writer = new FileWriter("NAnalysis.csv");
            writer.write("Method,N,Time");

            for (int N = 1; N < 3000; N++) {
                SimulationFactory simulator = new SimulationFactory(config.getM(), config.getL(), N, config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions());

                long startTimeCIM = System.currentTimeMillis();
                simulator.CIM();
                long endTimeCIM = System.currentTimeMillis();
                long tiempoCIM = endTimeCIM - startTimeCIM;
                writer.write("\nCIM," + N + "," + tiempoCIM);

                long startTimeForce = System.currentTimeMillis();
                simulator.Force();
                long endTimeForce = System.currentTimeMillis();
                long tiempoForce = endTimeForce - startTimeForce;
                writer.write("\nForce," + N + "," + tiempoForce);
            }
            writer.close();

        } catch(IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static void MLAnalysis(){
        SimulationConfig config = readConfig("inputMLTest.json");
        if(config == null) {
            return;
        }
        System.out.println("N:" + config.getL() + " Radius:" +config.getRadius() + " RadiusNei:" +config.getRadiusNeighbour() + " Boundary:" + config.getBoundaryConditions());

        try {
            FileWriter writer = new FileWriter("MLAnalysis.csv");
            writer.write("N,M,L");

            for(int L = 1; L < 150; L++){
                boolean next = false;
                int Mout = 0, Lout = 0, Nout = 0;
                for (int M = 1; M < 300 && !next; M++) {
                    SimulationFactory simulator = new SimulationFactory(M, L, config.getN(), config.getRadius(), config.getRadiusNeighbour(), config.getBoundaryConditions());
                    System.out.println(L + " + " + M + '\n');

                    simulator.CIM();
                    simulator.Force();
                    if(simulator.NeighboursForceCount() == simulator.NeighboursCIMCount()){
                        Mout = M;
                        Lout = L;
                        Nout = config.getN();
                    }else{
                        next = true;
                    }
                }
                writer.write("\n" + Nout + ", " + Mout + "," + Lout);
            }

            writer.close();

        } catch(IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static SimulationConfig readConfig(String path){
        Gson gson = new Gson();
        SimulationConfig sConfig = null;
        try (FileReader reader = new FileReader(path)) {
            sConfig = gson.fromJson(reader, SimulationConfig.class);
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