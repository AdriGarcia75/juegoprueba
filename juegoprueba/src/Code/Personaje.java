package Code;

public class Personaje {
    //hacer: guardar el estado del personaje (sencillo), la cosa, como cargas el personaje XD, (habra que hacer un cargador y guardar el personaje en un archivo ig)

    //declaracion de estados y atributos para el personaje
    private final String nombre;
    //cap de vida
    private int vidaBase;
    private int vidaActual;
    private int dano;
    //el contador de experiencia, cuando pueda subir de nivel volverá a 0 hasta llenarse
    private int contadorExperiencia = 0;
    //el nivel del personaje, todos comienzan a nivel uno y podran subir de nivel adquiriendo experiencia
    private int nivelPersonaje = 1;
    //este requisito ira incrementando mediante una formula
    private int requisitoExperiencia = 100;

    //constructor para el personaje
    //declaramos nuestro personaje con una vidaBase y un daño base
    public Personaje(String nombre, int vidaBase, int danoBase) {
        //no se admiten personajes con vida o daño menor/igual a 0
        try {
            if (vidaBase <= 0 || danoBase <= 0) {
                throw new IllegalArgumentException("El daño o vida base deben ser mayores a 0");
            }
        } catch (IllegalArgumentException e) {
            //hacemos un err.println para imprimir un error y utilizamos getMessage en el error para imprimir exactamente el error que es
            System.err.println("Error al crear el personaje: " + e.getMessage());
        }
        //asignamos los valores introducidos en el instanciamiento del objeto
        this.nombre = nombre;
        this.vidaBase = vidaBase;
        this.dano = danoBase;
    }

    //lista de setters/getters y metodos para Personaje
    public void incrementarExperiencia(int cantidad) {

        this.contadorExperiencia += cantidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getVida() {
        return this.vidaBase;
    }

    public int getDano() {
        return this.dano;
    }

    public void setVida(int vida) {
        this.vidaBase = vida;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getNivelPersonaje() {
        return this.nivelPersonaje;
    }

    public int getContadorExperiencia() {
        return this.contadorExperiencia;
    }

    public int getRequisitoExperiencia() {
        return this.contadorExperiencia;
    }

    public void setContadorExperiencia(int contadorExperiencia) {
        this.contadorExperiencia = contadorExperiencia;
    }

    public void setNivelPersonaje(int nivelPersonaje) {
        this.nivelPersonaje = nivelPersonaje;
    }

    //setter para el requisito de experiencia
    public void setRequisitoExperiencia(int requisitoExperiencia) {
        this.requisitoExperiencia = requisitoExperiencia;
    }

    //cuando subamos de nivel, habremos de recalcular el nuevo limite de experiencia para el siguiente nivel
    private int incrementoRequisitoExperiencia() {
        return getRequisitoExperiencia() + getNivelPersonaje() * 15;
    }

    //metodo el que podemos saber si podemos subir de nivel
    public boolean apto() {
        return getContadorExperiencia() >= getRequisitoExperiencia();
    }

    //funcion que incrementa las "estadisticas"
    private void incrementoEstadisticas() {
        //esto simula una especie de "escalado" para las estadisticas
        this.dano += (int) (getNivelPersonaje() * 1.1);
        this.vidaBase += (int) (getNivelPersonaje() * 1.1) + 2;
    }

    //esta funcion llama al metodo de incrementar las "estadisticas" del personaje a parte de actualizar los requerimientos para subir de nivel
    public void subirNivel() {
        if (this.contadorExperiencia >= this.requisitoExperiencia) {
            //recalculamos el nuevo requisito de experiencia
            this.requisitoExperiencia = incrementoRequisitoExperiencia();
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
