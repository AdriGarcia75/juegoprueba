package Code;

public class Personaje extends Entidad {
    //hacer: guardar el estado del personaje (sencillo), la cosa, como cargas el personaje XD, (habra que hacer un cargador y guardar el personaje en un archivo ig)

    //declaracion de estados y atributos para el personaje
    private String nombre;
    //daño total: daño base + el daño del arma equipada
    private float danoTotal;
    private int contadorExperiencia = 0;
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
    public Personaje(String nombre, int vidaBase, float dano, float velocidad) {
        //llamamos al constructor de la clase padre
        super(vidaBase, dano, velocidad);
        //asignamos los valores a los atributos restantes, propios de la clase Personaje
        this.nombre = nombre;
        this.danoTotal = dano;
        //pasamos por parametro el personaje mismo a la tienda
        this.tiendaPersonaje = new Tienda(this);
    }

    //lista de setters/getters y metodos para Personaje


    public String getNombre() { return nombre; }

    public int getContadorExperiencia() {
        return this.contadorExperiencia;
    }

    public int getContadorMonedas() {
        return this.contadorMonedas;
    }

    public void setContadorMonedas(int monedas) { this.contadorMonedas = monedas; }

    public int getContadorMonstruos() {
        return contadorMonstruos;
    }

    public void setContadorMonstruos(int monstruo) {
        this.contadorMonstruos += monstruo;
    }

    public int getRequisitoExperiencia() {
        return this.contadorExperiencia;
    }

    public void setContadorExperiencia(int contadorExperiencia) {
        this.contadorExperiencia = contadorExperiencia;
    }

    public void incrementarContadorExperiencia(int contadorExperiencia){
        this.contadorExperiencia += contadorExperiencia;
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

    public int getPocionesPersonaje() { return pocionesPersonaje; }

    public void setPocionesPersonaje(int pociones) { this.pocionesPersonaje = pociones; }

    public void setArmaPersonaje(Arma armaPersonaje) {
        this.armaPersonaje = armaPersonaje;
        calcularDano();
        System.out.println("Se ha equipado el arma " + armaPersonaje + ", ahora tu daño es " + this.danoTotal);
    }

    public void calcularDano(){
        this.danoTotal = armaPersonaje.getDanoArma() + getDano();
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
            //recalculamos la experiencia - (por ejemplo; 1002/1000, subimos de nivel y nos quedamos con 2 de exp)
            setContadorExperiencia(this.contadorExperiencia - this.requisitoExperiencia);
            //recalculamos y asignamos el nuevo requisito de experiencia
            setRequisitoExperiencia(incrementoRequisitoExperiencia());
            //llamamos a la funcion de incremento de estadisticas y subimos de nivel
            incrementoEstadisticas();
            setNivel(getNivel() + 1);
            //y por ultimo incrementamos el nivel del personaje + actualizamos los estados de la tienda
            tiendaPersonaje.reponerTienda(this);
            tiendaPersonaje.actualizarPrecioPociones(this);
        } else {
            int experienciaRestante = getRequisitoExperiencia() - getContadorExperiencia();
            System.out.println("No es posible subir de nivel aún, necesitas " + experienciaRestante + " puntos de experiencia");
        }
    }

    public void atacar(Monstruo objetivo) {

        //calculamos el daño que hariamos al enemigo
        float danoInfligido = this.danoTotal;
        objetivo.setVidaActual( (int) (objetivo.getVidaActual() - danoInfligido));

        //imprimimos el ataque
        System.out.println(this.nombre + " atacó a " + objetivo.getNombreMonstruo() + " e infligió " + danoInfligido + " de daño.");

        //y comprobamos si el enemigo ha sido derrotado
        if (objetivo.getVidaActual() <= 0) {
            System.out.println(objetivo.getNombreMonstruo() + " ha sido derrotado.");
            // Incrementar contadores de monstruos derrotados y experiencia del personaje
            incrementarContadorMonstruos();
            incrementarContadorExperiencia(objetivo.getExperienciaSoltada());
            incrementarContadorMonedas(objetivo.getMonedasSoltadas());
        }
    }


    public void curar(){
        if (pocionesPersonaje > 0){
            int cantidadCuracion = getCantidadCuracion();
            //con este math.min nos aseguramos que si la suma de vidaActual + vida a curar es mayor a la vida maxima permitida, se asignará la vida base y si no, curamos normal
            setVidaActual(Math.min(getVidaActual() + cantidadCuracion, getVidaBase()));
            setPocionesPersonaje(getPocionesPersonaje() - 1);
        } else {
            System.out.println("No te quedan pociones, no puedes curarte.");
        }
    }

    //esta funcion es unicamente utilizada exclusivamente por comprarArma y comprarPociones
    private void comprar(int monedas){
        setContadorMonedas(getContadorMonedas() - monedas);
    }

    //metodos relacionados con la tienda
    //este metodo equipará automaticamente el arma comprada y si el personaje llevaba una, esta se perderá para siempre
    public void comprarArma(int eleccion){
        Arma arma;
        try {
            arma = tiendaPersonaje.getListaArmasVenta().get(eleccion);
        } catch (IndexOutOfBoundsException e){
            System.err.println("No has elegido una opción de arma válida");
            return; //devolvemos (es como una especie de break) para marcar que el método interrumpe su ejecucion cuando se provoca un out of bounds
        }
        if (arma.getPrecioArma() <= getContadorMonedas()){
            //eliminamos de la tienda el arma y la compramos (actualizamos las monedas del personaje)
            tiendaPersonaje.eliminarArmaTienda(eleccion);
            comprar(arma.getPrecioArma());
            //equipamos el arma y recalculamos el daño total del personaje
            setArmaPersonaje(arma);
            calcularDano();
            System.out.println("Se ha equipado el arma " + arma);

        } else {
            System.out.println("No tienes suficientes monedas para comprar " + arma);
        }
    }

    public void comprarPociones() {
        //miramos si no quedan pociones o si no nos basta el dinero, en caso contrario, pues procedemos con la compra de la pocion
        if (tiendaPersonaje.getPocionesVenta() <= 0) {
            System.out.println("No quedan pociones a la venta");
        } else if (getContadorMonedas() < tiendaPersonaje.getPrecioPociones()) {
            System.out.println("No tienes el dinero suficiente para comprar pociones");
        } else {
            //compramos la pocion y se la damos al personaje
            comprar(tiendaPersonaje.getPrecioPociones());
            setPocionesPersonaje(getPocionesPersonaje() + 1);
            tiendaPersonaje.setPocionesVenta(tiendaPersonaje.getPocionesVenta() - 1);
            System.out.println("Has comprado 1 poción de vida");
        }
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' + " " +
                ", vidaBase=" + getVidaBase() + " " +
                ", vidaActual=" + getVidaActual() + " " +
                ", danoTotal=" + danoTotal + " " +
                ", velocidad=" + getVelocidad() + " " +
                ", armaPersonaje=" + armaPersonaje +
                '}';
    }
}
