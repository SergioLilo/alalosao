import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String rutaFichero = "src/monedas";
    public static String h="HOLA";
    public static void main(String[] args) {

        Scanner teclado=new Scanner(System.in);
        System.out.println("Introduce el numero de procesos");
        int numProc=teclado.nextInt();

        try  {

            ProcessBuilder constructorProcesos= new ProcessBuilder("java", ProcesamientoArchivo.class.getName());
            constructorProcesos.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            constructorProcesos.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores_conversion.csv")));
            constructorProcesos.directory(new File("out/production/PracticaPsP"));


            for (int i = 0; i < numProc; i++) {
                constructorProcesos.command("java",ProcesamientoArchivo.class.getName(),String.valueOf(numProc),String.valueOf(i));
                constructorProcesos.start();
            }


            for (int i = 0; i < numProc; i++) {
                BufferedReader lectura=new BufferedReader(new FileReader("out/production/PracticaPsP/FicheroTemp"+i));
                String linea;
                BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones_final.csv"));

                while ((linea=lectura.readLine())!=null){
                writer.write(linea);
                writer.newLine();
                }

            }


        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}