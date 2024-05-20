package PlayableMenu;

public class Menu {

    // Códigos de escape ANSI para colores en terminal
    // Nota: colores "brillantes" (más claritos, parecen un pelin mas brillantes solo) 0;3X -> 0;9X
    public static final String RESET = "\033[0m";  // Reset
    public static final String ROJO = "\033[0;31m";    // Rojo
    public static final String VERDE = "\033[0;32m";   // Verde
    public static final String AMARILLO = "\033[0;33m"; // Amarillo
    public static final String AZUL = "\033[0;34m";    // Azul
    public static final String MAGENTA = "\033[0;35m"; // Magenta
    public static final String CIAN = "\033[0;36m";    // Cian
    public static final String BLANCO = "\033[0;37m";  // Blanco


    public static void main(String[] args) {

        System.out.println(VERDE + "Hello world!" + RESET);
    }



    //hacer menú
}