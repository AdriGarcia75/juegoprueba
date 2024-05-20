package Code;

public class Tienda{

    private final int limiteArmasVenta = 5;
    private final Arma[] listaArmasVenta;
    private final int limitePocionesVenta = 3;

    public Tienda(Personaje personaje){
        this.listaArmasVenta = new Arma[limiteArmasVenta];
        reponerTienda(personaje);
    }

    public void reiniciarTienda(Tienda tienda){

    }

    public void reponerTienda(Personaje personaje){
        for (int i = 0; i < limiteArmasVenta; i++){
            this.listaArmasVenta[i] = new Arma(personaje.getNivel());
        }
    }

}
