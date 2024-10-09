import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcesamientoArchivo {

    public static String rutaFichero = "monedas";

    public static void main(String[] args) {


        ArrayList<Transaccion> parteFichero = new ArrayList<>();
        List<String> fichero = new ArrayList<>();

        int j = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        int numProc=Integer.parseInt(args[2]);
        int i =Integer.parseInt(args[3]);

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                fichero.add(linea);
            }
            fichero.removeFirst();
            String partes[];

            for (; j < k && j < fichero.size(); j++) {

                partes = fichero.get(j).split(",");
                String id = partes[0];
                String cliente = partes[1];
                String fecha = partes[2];
                double monto = Double.parseDouble(partes[3]);
                String moneda = partes[4];
                parteFichero.add(new Transaccion(id, cliente, fecha, monto, moneda));
            }

            comprobadorLineasSobrantes(numProc, i, j, fichero, parteFichero);
            System.out.println(parteFichero);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void comprobadorLineasSobrantes(int numProc, int i, int j, List<String> fichero, ArrayList<Transaccion> parteFichero) {
        String[] partes;
        if(numProc == i +1) {
            for (; j < fichero.size(); j++) {


                partes = fichero.get(j).split(",");
                String id = partes[0];
                String cliente = partes[1];
                String fecha = partes[2];
                double monto = Double.parseDouble(partes[3]);
                String moneda = partes[4];

                parteFichero.add(new Transaccion(id, cliente, fecha, monto, moneda));


            }
        }
    }
}
