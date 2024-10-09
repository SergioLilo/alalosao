import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String rutaFichero = "src/monedas";

    public static void main(String[] args) {

        Scanner teclado=new Scanner(System.in);
        List<Process> procesos = new ArrayList<>();
        System.out.println("Introduce el numero de procesos");
        int numProc=teclado.nextInt();

        try  {
            Files.deleteIfExists(Path.of("errores_conversion.csv"));
            ProcessBuilder constructorProcesos= new ProcessBuilder("java", ProcesamientoArchivo.class.getName());
            constructorProcesos.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            constructorProcesos.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores_conversion.csv")));
            constructorProcesos.directory(new File("out/production/PracticaPsP"));

            for (int i=0; i < numProc; i++) {
                constructorProcesos.command("java",ProcesamientoArchivo.class.getName(),String.valueOf(numProc),String.valueOf(i));
               Process proceso = constructorProcesos.start();
               procesos.add(proceso);
            }
            procesos.getLast().waitFor();

            BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones_final.csv"));
            escrbirResultado(numProc, writer);


        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escrbirResultado(int numProc, BufferedWriter writer) throws IOException {
        for (int j = 0; j < numProc; j++) {
            BufferedReader lectura = new BufferedReader(new FileReader("out/production/PracticaPsP/FicheroTemp" + j+".csv"));
            String linea;

            while ((linea = lectura.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }
            writer.flush();
            lectura.close();
            Path ruta = Paths.get("out/production/PracticaPsP/FicheroTemp" +j+".csv");
            Files.delete(ruta);
        }
    }
}