package Code;

public abstract class Habilidad {

    private final String nombre;
    private String descripcion;
    private final int danoHabilidad;

    //recordatorio: es una clase abstracta, no podemos llamar al constructor directamente, solo desde las hijas
    public Habilidad(String nombre, int danoHabilidad) {
        this.nombre = nombre;
        this.danoHabilidad = danoHabilidad;
        this.descripcion = null;
    }

    // Getters y setters para nombre y descripción

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void printDescripcion(){
        String descripcion = getDescripcion();
        if (descripcion == null){
            System.err.println("No se ha proporcionado una descripción a la habilidad");
        } else {
            System.out.println(descripcion);
        }
    }

    //por defecto el valor es null
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDano(){
        return this.danoHabilidad;
    }

    // Método abstracto para usar la habilidad
    public abstract void utilizarHabilidad(Personaje personajeObjetivo);
}
