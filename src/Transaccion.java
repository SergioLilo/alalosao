
    public class Transaccion {

    private String id;
    private String cliente;
    private String fecha;
    private double monto;
    private String moneda;
    private boolean moroso;

    public Transaccion(String id, String cliente, String fecha, double monto, String moneda) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.monto = monto;
        this.moneda = moneda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

        public boolean isMoroso() {
            return moroso;
        }

        public void setMoroso(boolean moroso) {
            this.moroso = moroso;
        }

        @Override
        public String toString() {
            return "Transaccion{" +
                    "id='" + id + '\'' +
                    ", cliente='" + cliente + '\'' +
                    ", fecha='" + fecha + '\'' +
                    ", monto=" + monto +
                    ", moneda='" + moneda + '\'' +
                    '}';
        }
    }
