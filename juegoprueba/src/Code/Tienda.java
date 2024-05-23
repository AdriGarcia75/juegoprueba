package Code;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tienda implements Serializable {

    private final int limiteArmasVenta = 5;
    private final ArrayList<Arma> listaArmasVenta;
    private final int limitePocionesVenta = 3;
    private int pocionesVenta;
    private int precioPociones;

    public Tienda(Personaje personaje){
        this.listaArmasVenta = new ArrayList<Arma>();
        //a la hora de crear una tienda, basada en un personaje (ya que necesitamos su nivel para crear las armas
        reponerTienda(personaje);
        this.pocionesVenta = limitePocionesVenta;

        //al crear la tienda se la asignamos al jugador que haya sido iniciado en el programa (este mismo se pasa por parametro del constructor)
        personaje.setTiendaPersonaje(this);
    }

    public ArrayList<Arma> getListaArmasVenta(){
        return listaArmasVenta;
    }

    public int getPocionesVenta() {
        return pocionesVenta;
    }

    public void setPocionesVenta(int pocionesVenta) {
        this.pocionesVenta = pocionesVenta;
    }

    public void actualizarPrecioPociones(Personaje personaje){
        this.precioPociones = personaje.getNivel() + 2;
    }

    public int getPrecioPociones() { return precioPociones; }

    public void setPrecioPociones(int precioPociones) { this.precioPociones = precioPociones; }

    //utilizamos el toString de arma para poder imprimir la lista entera
    public void printListaArmasVenta() {
        for (Arma arma : listaArmasVenta){
            System.out.println(arma);
        }
    }

    public void reponerTienda(Personaje personaje){
        //volvemos a generar las armas con un bucle
        for (int i = 0; i < limiteArmasVenta; i++){
            //crearemos una arma para cada posicion en la tienda y luego la añadimos a la lista
            this.listaArmasVenta.add(new Arma(personaje.getNivel()));
        }
        //volvemos a asignar la cantidad de pociones a su valor por defecto
        this.pocionesVenta = limitePocionesVenta;
    }

    public void eliminarArmaTienda(int eleccion){
        this.listaArmasVenta.remove(eleccion);
    }

}
