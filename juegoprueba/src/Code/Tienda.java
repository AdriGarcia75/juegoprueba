package Code;

public class Tienda{

    private final int limiteArmasVenta = 5;
    private final Arma[] listaArmasVenta;
    private final int limitePocionesVenta = 3;
    private int pocionesVenta;

    public Tienda(Personaje personaje){
        this.listaArmasVenta = new Arma[limiteArmasVenta];
        //a la hora de crear una tienda, basada en un personaje (ya que necesitamos su nivel para crear las armas
        reponerTienda(personaje);
        this.pocionesVenta = limitePocionesVenta;
    }

    public Arma[] getListaArmasVenta(){
        return listaArmasVenta;
    }

    public int getPocionesVenta() {
        return pocionesVenta;
    }

    public void setPocionesVenta(int pocionesVenta) {
        this.pocionesVenta = pocionesVenta;
    }

    // equivale a "setListaArmasVenta"
    public void reponerTienda(Personaje personaje){
        for (int i = 0; i < limiteArmasVenta; i++){
            this.listaArmasVenta[i] = new Arma(personaje.getNivel());
        }
    }

    public void printListaArmasVenta() {
        for (Arma arma : listaArmasVenta){
            System.out.println(arma);
        }
    }

    //este metodo equipará automaticamente el arma comprada y si el personaje llevaba una, esta se perderá para siempre
    public void comprarArma(int eleccion, Personaje personaje){
        Arma arma = this.listaArmasVenta[eleccion];
        if (arma.getPrecioArma() <= personaje.getContadorMonedas()){
            personaje.setArmaPersonaje(arma);
            personaje.calcularDano();
            System.out.println("Se ha equipado el arma " + arma);
        } else {
            System.out.println("No tienes suficientes monedas para comprar " + arma);
        }
    }
}
