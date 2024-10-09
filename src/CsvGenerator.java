import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CsvGenerator {

    private static final String[] NOMBRES = {"Juan", "Ana", "Carlos", "María", "Luis", "Elena", "Pedro", "Lucía", "Raúl", "Sofia"};
    private static final String[] MONEDAS = {"EUR", "USD", "GBP"};
    private static final Random RANDOM = new Random();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void generarCsv(String nombreArchivo, int numLineas) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            // Escribir encabezado
            writer.append("ID,Cliente,Fecha,Monto,Moneda\n");

            for (int i = 0; i < numLineas; i++) {
                String id = generarId(i);
                String cliente = generarNombreAleatorio();
                String fecha = generarFechaAleatoria();
                String monto = generarMontoAleatorio();
                String moneda = generarMonedaAleatoria();

                writer.append(id).append(",")
                        .append(cliente).append(",")
                        .append(fecha).append(",")
                        .append(monto).append(",")
                        .append(moneda).append("\n");
            }

            System.out.println("Archivo CSV generado exitosamente: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al generar el archivo CSV: " + e.getMessage());
        }
    }

    private static String generarId(int index) {
        // Genera un ID único por cada línea (por ejemplo, usando el índice)
        return String.valueOf(index + 1);
    }

    private static String generarNombreAleatorio() {
        // Devuelve un nombre aleatorio del array de nombres
        int index = RANDOM.nextInt(NOMBRES.length);
        return NOMBRES[index];
    }

    private static String generarFechaAleatoria() {
        // Genera una fecha aleatoria entre 2020 y 2024
        int year = 2020 + RANDOM.nextInt(5);
        int dayOfYear = 1 + RANDOM.nextInt(365);  // Día del año aleatorio
        long fechaMs = new java.util.GregorianCalendar(year, 0, 1).getTimeInMillis() + dayOfYear * 24L * 60 * 60 * 1000;
        return DATE_FORMAT.format(new java.util.Date(fechaMs));
    }

    private static String generarMontoAleatorio() {
        // Genera un monto aleatorio entre 10 y 100,000 con dos decimales
        double monto = 10 + (RANDOM.nextDouble() * 99990);
        return DECIMAL_FORMAT.format(monto).replace(',','.');
    }

    private static String generarMonedaAleatoria() {
        // Devuelve una moneda aleatoria del array de monedas
        int index = RANDOM.nextInt(MONEDAS.length);
        return MONEDAS[index];
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: CsvGenerator <nombre_archivo> <numero_de_lineas>");
            return;
        }

        String nombreArchivo = args[0];
        int numLineas;

        try {
            numLineas = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: El número de líneas debe ser un entero válido.");
            return;
        }

        generarCsv(nombreArchivo, numLineas);
    }
}
