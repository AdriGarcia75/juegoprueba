package Code;

import java.util.ArrayList;
import java.util.HashSet;

public class Personaje {
    //hacer: guardar el estado del personaje (sencillo), la cosa, como cargas el personaje XD, (habra que hacer un cargador y guardar el personaje en un archivo ig)

    //declaracion de estados y atributos para el personaje
    private final String nombre;
    //cap de vida
    private int vida;
    //vida "usable", esta va a ir variando mediante el uso de habilidades de oponentes
    private int vidaActual;
    private int dano;
    //el contador de experiencia, cuando pueda subir de nivel volverá a 0 hasta llenarse
    private int contadorExperiencia = 0;
    //el nivel del personaje, todos comienzan a nivel uno y podran subir de nivel adquiriendo experiencia
    private int nivelPersonaje = 1;
    //este requisito ira incrementando mediante una formula - no tiene sentido que salga "never read" xd
    private int requisitoExperiencia = 100;
    //limite de cantidad de habilidades, de daño y de cura , no lo haré de momento (no es prioritario)
    /* private final int limiteHabilidadesAtaque = 3;
    private final int limiteHabilidadesCura = 3; */
    //lista de habiliades, ,de cura y de ataque, se controlará el limite a la hora de introducir nuevas habilidades
    private HashSet<HabilidadAtaque> listaHabilidadesAtaque = new HashSet<HabilidadAtaque>();
    private HashSet<HabilidadCura> listaHabilidadesCura = new HashSet<HabilidadCura>();

    //constructor para el personaje
    //declaramos nuestro personaje con una vidaBase y un daño base
    public Personaje(String nombre, int vidaBase, int danoBase){
        //no se admiten personajes con vida o daño menor/igual a 0
        try {
            if (vidaBase <= 0 || danoBase <= 0){
                throw new IllegalArgumentException("El daño o vida base deben ser mayores a 0");
            }
        } catch (IllegalArgumentException e){
            //hacemos un err.println para imprimir un error y utilizamos getMessage en el error para imprimir exactamente el error que es
            System.err.println("Error al crear el personaje: " + e.getMessage());
        }
        //asignamos los valores introducidos en el instanciamiento del objeto
        this.nombre = nombre;
        this.vida = vidaBase;
        this.dano = danoBase;
    }

    //lista de setters/getters y metodos para Personaje

    //para hacer: metodo de incrementar experiencia, (hecho, falta implementarlo / darle uso) por ejemplo puede ser llamado luego de matar un enemigo (osea que lo hagas adri XD)

    //setter para incrementar experiencia (puede ser llamado tras derrotar un enemigo
    public void incrementarExperiencia(int cantidad){
        this.contadorExperiencia += cantidad;
    }
    //getter para el nombre
    public String getNombre(){ return this.nombre;}
    //getter para la vida
    public int getVida() {
        return this.vida;
    }
    //getter para el daño
    public int getDano() {
        return this.dano;
    }
    //setter para la vida
    public void setVida(int vida) {
        this.vida = vida;
    }
    //setter para el daño
    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }
    //getter para el nivel
    public int getNivelPersonaje(){
        return this.nivelPersonaje;
    }
    //getter para la experiencia acumulada del personaje
    public int getContadorExperiencia(){
        return this.contadorExperiencia;
    }
    //getter para la experiencia necesaria para subir de nivel
    public int getRequisitoExperiencia(){
        return this.contadorExperiencia;
    }

    //setters para las listas de habiliades - de momento no tienen limite (no es prioritario pero me gustaria añadirle un limite)
    //setter para la lista de habilidades de ataque
    public void setHabilidadAtaqueEnLista(HabilidadAtaque habilidadAtaqueInput){
        this.listaHabilidadesAtaque.add(habilidadAtaqueInput);
    }
    //setter para la lista de habilidades de cura
    public void setHabilidadCuraEnLista(HabilidadCura habilidadCuraInput){
        this.listaHabilidadesCura.add(habilidadCuraInput);
    }

    //setter pero para borrar, de momento no lo haré (no es prioritario)

    //getter con el que podemos saber si podemos subir de nivel
    public boolean apto(){
        return getContadorExperiencia() >= getRequisitoExperiencia();
    }

    //setter para el contador de experiencia - este se usará a la hora de incrementar el nivel del personaje, no por el usuario
    public void setContadorExperiencia(int contadorExperiencia) {
        this.contadorExperiencia = contadorExperiencia;
    }

    //setter para el nivel del personaje
    public void setNivelPersonaje(int nivelPersonaje) {
        this.nivelPersonaje = nivelPersonaje;
    }

    //setter para el requisito de experiencia
    public void setRequisitoExperiencia(int requisitoExperiencia) {
        this.requisitoExperiencia = requisitoExperiencia;
    }

    //cuando subamos de nivel, habremos de recalcular el "cap" de experiencia para el siguiente nivel
    private int incrementoRequisitoExperiencia() {
        return getRequisitoExperiencia() + getNivelPersonaje() * 15;
    }

    //funcion que incrementa las "estadisticas"
    private void incrementoEstadisticas() {
        //esto simula una especie de "escalado" para las estadisticas
        this.dano += (int) (getNivelPersonaje() * 1.1);
        this.vida += (int) (getNivelPersonaje() * 1.1) + 2;
    }

    //esta funcion llama al metodo de incrementar las "estadisticas" del personaje a parte de actualizar los requerimientos para subir de nivel
    public void subirNivel(){
        if (getContadorExperiencia() >= getRequisitoExperiencia()){
            //llamamos al setter del cap de exp con argumento la funcion de recalcular el cap
            setRequisitoExperiencia(incrementoRequisitoExperiencia());
            //recalculamos la experiencia - nos quedamos con el sobrante de experiencia (por ejemplo; 1002/1000, nos quedamos con 2 de exp)
            setContadorExperiencia(getContadorExperiencia() - getRequisitoExperiencia());
            //incrementamos el nivel del personaje
            setNivelPersonaje(getNivelPersonaje() + 1);
            //llamamos a la funcion de incremento de estadisticas
            incrementoEstadisticas();
        } else {
            int experienciaRestante = getRequisitoExperiencia() - getContadorExperiencia();
            System.out.println("No es posible subir de nivel, necesitas " + experienciaRestante + " puntos de experiencia");
        }
    }
}
