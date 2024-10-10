import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProcesamientoArchivo {



    public static void main(String[] args) {

        ArrayList<Transaccion> parteFichero = new ArrayList<>();
        List<String> fichero = new ArrayList<>();

        int numProc=Integer.parseInt(args[0]);
        int i =Integer.parseInt(args[1]);
        String rutaFichero = args[2];

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                fichero.add(linea);
            }
            fichero.removeFirst();
            int j=0;
            int k=fichero.size()/numProc;

            j=importarLineas(numProc,i,j,k,fichero,parteFichero);
            comprobadorLineasSobrantes(numProc, i, j, fichero, parteFichero);

            System.out.println("Lineas Leidas:"+parteFichero.size()+" del proceso numero "+(i+1)+
                    "\n"+ "Primer ID Leido: "+parteFichero.getFirst().getId()+"\n"+
            "Ultimo ID Leido: "+parteFichero.getLast().getId());

            conversionMoneda(parteFichero);
            comprobarMoroso(parteFichero);
            escribirResultado(parteFichero,i);

            //System.out.println(parteFichero);

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
    private static int importarLineas(int numProc, int i, int j, int k, List<String> fichero, ArrayList<Transaccion> parteFichero){

        for (int l = 0; l < i; l++) {
            j=j+fichero.size()/numProc;
            k=k+fichero.size()/numProc;
        }
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
        return j;
    }
    private static void conversionMoneda(ArrayList<Transaccion> parteFichero){

        for (Transaccion tr: parteFichero){
            if (tr.getMoneda().equals("USD")){
                tr.setMonto( tr.getMonto()*0.85d);
                tr.setMoneda("EUR");
            }else if(tr.getMoneda().equals("GBP")){

                tr.setMonto((tr.getMonto()*1.17d));
                tr.setMoneda("EUR");

            }

        }
    }
    private static void comprobarMoroso(ArrayList<Transaccion> parteFichero){

        for (Transaccion tr: parteFichero){
            if(tr.getMonto()>=50_000d){
                System.err.println("[ERROR] Fraude detectado en la transacción ID: "+tr.getId()+". Monto: "+tr.getMonto()+"€");
                tr.setMoroso(true);
            }
        }

    }
    private static void escribirResultado(ArrayList<Transaccion> parteFichero,int i) throws IOException {


        DecimalFormat df = new DecimalFormat("#.00");
        BufferedWriter escritor = new BufferedWriter(new FileWriter("FicheroTemp"+i+".csv"));

        for (Transaccion tr: parteFichero){
            escritor.write(tr.getId()+","+ tr.getCliente()+","+tr.getFecha()+","+df.format(tr.getMonto())+","+tr.getMoneda());
            if (tr.isMoroso()){
                escritor.write(",ALERTA");
            }
            escritor.newLine();
        }
        escritor.flush();
    }

}
