package Code;

import java.util.Random;

public class Monstruo extends Entidad {

    //lista de posibles nombres para el monstruo
    private final String[] listaNombresMonstruo = {"Limo", "Rana", "Trol", "Zombi", "Duende"};
    //no hay diferencia entre enemigos, simplemente el nombre se elegirá aleatoriamente
    private final String nombreMonstruo;
    private int monedasSoltadas;
    private int experienciaSoltada;

    public Monstruo (int vidaBase, float dano, float velocidad){
        super(vidaBase, dano, velocidad);

        this.nombreMonstruo = nombreMonstruoAleatorio(listaNombresMonstruo);
        this.monedasSoltadas = actualizarMonedasSoltadas();
        this.experienciaSoltada = actualizarMonedasSoltadas();
    }

    public String nombreMonstruoAleatorio(String[] listaNombresMonstruo){
        //escogemos de manera aleatoria un nombre para el Monstruo utilizando la clase Random de java
        Random random = new Random();
        return listaNombresMonstruo[random.nextInt(listaNombresMonstruo.length)];
    }

    public String getNombreMonstruo() {
        return nombreMonstruo;
    }

    public int getMonedasSoltadas() {
        return this.monedasSoltadas;
    }

    public void setMonedasSoltadas(int monedasSoltadas){
        this.monedasSoltadas = monedasSoltadas;
    }

    public int getExperienciaSoltada(){
        return this.experienciaSoltada;
    }

    public void setExperienciaSoltada(int experienciaSoltada){
        this.experienciaSoltada = experienciaSoltada;
    }

    public int actualizarMonedasSoltadas(){
        return getMonedasSoltadas() + (int) (getNivel() * 1.1);
    }

    public int actualizarExperienciaSoltada(){
        return getExperienciaSoltada() + (int) (getNivel() * 2) + 10;
    }

    public void incrementoEstadisticas(){
        setDano(getDano() + (int) (getNivel() * 1.1));
        setVidaBase(getVidaBase() + (int) (getNivel() * 0.9) + 1);
        setVelocidad(getVelocidad() + (float) (getNivel() * 0.1));
        setNivel(getNivel() + 1);
    }

    public void subirNivel(Personaje personaje){
        //actualizar monedas y exp soltadas + lo restante
        //el monstruo probará de subir de nivel (solo se ejecutará cuando el monstruo esté por detrás en nivel respecto al personaje
        if (getNivel() < personaje.getNivel()){
            int cantidadNiveles = personaje.getNivel() - getNivel();
            //por si en algun momento el monstruo queda más de un nivel por detrás, implementar en un bucle las veces que ha de subir de nivel prevendrá ese probelam
            for (int i = 0; i < cantidadNiveles; i++){
                incrementoEstadisticas();
                //actualizamos los valores de
                setMonedasSoltadas(actualizarMonedasSoltadas());
                setExperienciaSoltada(actualizarExperienciaSoltada());
            }
        } else {
            System.out.println("En este momento el monstruo no puede subir de nivel ");
        }
    }


    public void atacar(Personaje objetivo){
        float danoInfligido = this.getDano();

        // Reducir la vida del objetivo
        objetivo.setVidaActual((int) (objetivo.getVidaActual() - danoInfligido));

        // Imprimir el resultado del ataque
        System.out.println(this.nombreMonstruo + " atacó a " + objetivo.getNombre() + " e infligió " + danoInfligido + " de daño.");

        // Comprobar si el objetivo ha sido derrotado
        if (objetivo.getVidaActual() <= 0) {
            System.out.println(objetivo.getNombre() + " ha sido derrotado.");
            // Aquí podrías agregar lógica adicional, como finalizar el juego o restablecer el personaje
        }
    }

    @Override
    public String toString() {
        return "Monstruo{" +
                "nombreMonstruo='" + nombreMonstruo + '\'' +
                '}';
    }
}