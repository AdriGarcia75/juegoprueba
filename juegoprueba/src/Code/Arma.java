package Code;

import java.io.Serializable;
import java.util.Random;

public class Arma implements Serializable {

    private final String[] listaNombresArmas = {"Espada", "Hacha", "Martillo", "Lanza"};

    private final String nombreArma;
    private int danoArma;
    private final int danoBaseArma = 5;
    private int precioArma;

    //este constructor será llamado desde la clase Tienda, la cual le pasará por parámetro el nivel del personaje
    public Arma(int nivelPersonaje){
        this.nombreArma = nombreArmaAleatorio(listaNombresArmas);
        //esta pequeña formula dara
        this.danoArma = danoBaseArma + nivelPersonaje * 2;
        this.precioArma = nivelPersonaje * 5;
    }

    public String nombreArmaAleatorio(String[] listaNombresArmas){
        //escogemos de manera aleatoria un nombre para el Monstruo utilizando la clase Random de java
        Random random = new Random();
        return listaNombresArmas[random.nextInt(listaNombresArmas.length)];
    }

    public int getPrecioArma() {
        return precioArma;
    }

    public int getDanoArma() {
        return danoArma;
    }

    @Override
    public String toString() {
        return "Arma{" +
                "nombreArma='" + nombreArma + '\'' +
                ", danoArma=" + danoArma +
                '}';
    }
}
