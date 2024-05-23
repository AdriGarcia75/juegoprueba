package Code;

import java.io.Serializable;

public abstract class Entidad implements Serializable {

    //declaracion de estados comunes para Personaje y Monstruo
    private int vidaBase;
    private int vidaActual;
    private float dano;
    //determinará quien de los dos (personaje, o monstruo) ataca primero
    private float velocidad;
    //atributo que determina el nivel del personaje/monstruo, este tendrá impacto directo sobre las estadisticas base de cualquiera
    private int nivel;

    //creamos un constructor con las estadisticas base, que ambos Personaje y Monstruo usarán
    public Entidad(int vidaBase, float dano, float velocidad) {
        //no se admiten personajes con vida o daño menor/igual a 0
        try {
            if (vidaBase <= 0 || dano <= 0 || velocidad <= 0) {
                throw new IllegalArgumentException("ERROR - Se ha detectado algun atributo con valor menor o igual a 0.");
            }
        } catch (IllegalArgumentException e) {
            //hacemos un err.println para imprimir un error y utilizamos getMessage en el error para imprimir exactamente el error que es
            System.err.println(e.getMessage() + " Se utilizaran valores por defecto para crear el personaje. ");
            //sobreescribiremos los datos de entrada y utilizaremos valores por defecto
            vidaBase = 20;
            dano = 3;
            velocidad = 2;
        }
        this.vidaBase = vidaBase;
        this.vidaActual = vidaBase;
        this.dano = dano;
        this.velocidad = velocidad;
    }

    //getters y setters

    public int getVidaBase() {
        return vidaBase;
    }

    public void setVidaBase(int vidaBase) {
        this.vidaBase = vidaBase;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public float getDano() {
        return dano;
    }

    public void setDano(float dano) {
        this.dano = dano;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) { this.velocidad = velocidad; }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    //funcion que incrementa las estadisticas del personaje/monstruo a partir del nivel
    public abstract void incrementoEstadisticas();
}
