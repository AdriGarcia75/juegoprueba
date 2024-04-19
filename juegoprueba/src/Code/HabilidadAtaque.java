package Code;

public class HabilidadAtaque extends Habilidad {

    private final int danoHabilidad;

    public HabilidadAtaque(String nombre, int danoHabilidad){
        super(nombre, danoHabilidad);
        this.danoHabilidad = danoHabilidad;
    }

    @Override
    public void utilizarHabilidad(Personaje objetivoHabilidad) {

    }
}
