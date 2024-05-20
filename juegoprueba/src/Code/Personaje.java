package Code;

public class Personaje extends Entidad {
    //hacer: guardar el estado del personaje (sencillo), la cosa, como cargas el personaje XD, (habra que hacer un cargador y guardar el personaje en un archivo ig)

    //declaracion de estados y atributos para el personaje
    private String nombre;
    //limite de vida del personaje, este sube con cada nivel y la vida actual NUNCA sobrepasará el limite
    private int danoBase;
    //daño total: daño base + el daño del arma equipada
    private float danoTotal;
    // private int velocidad;
    private int contadorExperiencia = 0;
    // private int nivelPersonaje = 1;
    private int requisitoExperiencia = 100;
    private int contadorMonstruos = 0;
    private int contadorMonedas = 0;
    //variable que guarda el estado de la tienda - esta se actualizará/reiniciará al subir el personaje de nivel
    private Tienda tiendaPersonaje;
    //variable que guarda el arma que puede llevar equipada el personaje
    private Arma armaPersonaje;
    //variable que guarda la cantidad de pociones que lleva el personaje
    private int pocionesPersonaje = 0;

    //constructor para el personaje
    public Personaje(String nombre, int vidaBase, int dano, int velocidad) {
        //llamamos al constructor de la clase padre
        super(vidaBase, dano, velocidad);
        //asignamos los valores a los atributos restantes, propios de la clase Personaje
        this.nombre = nombre;
        this.danoBase = dano;
        this.danoTotal = dano;
        //pasamos por parametro el personaje mismo a la tienda, es necesario para cumplir con la logica del programa
        this.tiendaPersonaje = new Tienda(this);

    }

    //lista de setters/getters y metodos para Personaje
    public int getContadorExperiencia() {
        return this.contadorExperiencia;
    }

    public int getContadorMonedas() {
        return this.contadorMonedas;
    }

    public int getContadorMonstruos() {
        return contadorMonstruos;
    }

    public int getRequisitoExperiencia() {
        return this.contadorExperiencia;
    }

    public void setContadorExperiencia(int contadorExperiencia) {
        this.contadorExperiencia = contadorExperiencia;
    }

    public void incrementarContadorMonedas(int monedas) {
        this.contadorMonedas += monedas;
    }

    public void incrementarContadorMonstruos() {
        this.contadorMonstruos++;
    }

    //calculamos la cantidad de vida que la pocion podra curar al personaje
    public int getCantidadCuracion(){
        return (getVidaBase() * 20 / 100) + getNivel() + 10;
    }

    //setter para el requisito de experiencia
    public void setRequisitoExperiencia(int requisitoExperiencia) {
        this.requisitoExperiencia = requisitoExperiencia;
    }

    public Tienda getTiendaPersonaje() {
        return tiendaPersonaje;
    }

    public void setTiendaPersonaje(Tienda tiendaPersonaje) {
        this.tiendaPersonaje = tiendaPersonaje;
    }

    public Arma getArmaPersonaje() {
        return armaPersonaje;
    }

    public void setArmaPersonaje(Arma armaPersonaje) {
        this.armaPersonaje = armaPersonaje;
        calcularDano();
        System.out.println("Se ha equipado el arma " + armaPersonaje + ", ahora tu daño es " + this.danoTotal);
    }

    public void calcularDano(){
        this.danoTotal = armaPersonaje.getDanoArma() + this.danoBase;
    }

    //cuando subamos de nivel, habremos de recalcular el nuevo limite de experiencia para el siguiente nivel
    private int incrementoRequisitoExperiencia() {
        return getRequisitoExperiencia() + getNivel() * 5;
    }

    //metodo el que podemos saber si podemos subir de nivel
    public boolean aptoSubirNivel() {
        return getContadorExperiencia() >= getRequisitoExperiencia();
    }

    public void incrementoEstadisticas() {
        //esta formula simula un "escalado" para las estadisticas
        setDano(getDano() + (int) (getNivel() * 1.1));
        setVidaBase(getVidaBase() + (int) (getNivel() * 1.1) + 2);
        setVelocidad(getVelocidad() + (float) (getNivel() * 0.1));
    }

    //esta funcion llama al metodo de incrementar las "estadisticas" del personaje a parte de actualizar los requerimientos para subir de nivel
    public void subirNivel() {
        if (aptoSubirNivel()) {
            //recalculamos la experiencia - nos quedamos con el sobrante de experiencia (por ejemplo; 1002/1000, nos quedamos con 2 de exp)
            setContadorExperiencia(this.contadorExperiencia - this.requisitoExperiencia);
            //recalculamos y asignamos el nuevo requisito de experiencia
            setRequisitoExperiencia(incrementoRequisitoExperiencia());
            //llamamos a la funcion de incremento de estadisticas
            incrementoEstadisticas();
            //y por ultimo incrementamos el nivel del personaje
            setNivel(getNivel() + 1);
        } else {
            int experienciaRestante = getRequisitoExperiencia() - getContadorExperiencia();
            System.out.println("No es posible subir de nivel aún, necesitas " + experienciaRestante + " puntos de experiencia");
        }
    }

    public void atacar(Entidad objetivo){

    }

    public void curar(){
        if (pocionesPersonaje > 0){
            int cantidadCuracion = getCantidadCuracion();
            //con este math.min nos aseguramos que si la suma de vidaActual + vida a curar es mayor a la vida maxima permitida, se asignará la vida base y si no, curamos normal
            setVidaActual(Math.min(getVidaActual() + cantidadCuracion, getVidaBase()));
        } else {
            System.out.println("No te quedan pociones, no puedes curarte.");
        }
    }
}
