package PlayableMenu;

import Code.Monstruo;
import Code.Personaje;
import Code.Tienda;

//clases necesarias para cargar y guardar partida (necesitamos guardar un objeto en un archivo, por eso hago un import de io)
import java.io.*;

import java.util.Scanner;

public class Menu {

    //scanner utilizado en el main
    public static Scanner sc = new Scanner(System.in);
    private static String NOMBRESAVEDATA = "partidaJuegoPrueba.dat";
    
    public static void main(String[] args) {

        //llamamos a una funcion al comenzar para que el usuario pueda crear el personaje
        System.out.println("Bienvenido a 'Juego Prueba', a continuación se te solicitaran datos para crear un personaje");
        Personaje personaje = crearPersonaje();

        System.out.println("¿Quieres cargar una partida existente? Escribe 'si' para cargar partida");
        if (sc.next().equals("si")) {
            // Cargar partida existente
            Personaje partidaGuardada = (Personaje) cargarPartida(NOMBRESAVEDATA);
            if (partidaGuardada != null) {
                personaje = partidaGuardada;
                System.out.println("Partida cargada exitosamente.");
            } else {
                System.out.println("No se pudo cargar la partida. Iniciando una nueva partida.");
            }
        }

        //imprimimos el menu del juego y procedemos a entrar en el bucle para el programa principal
        menu();

        while (personaje.getVidaActual() > 0) {

            int eleccionUsuario = sc.nextInt();
            switch (eleccionUsuario) {
                case 1 -> {
                    System.out.println(personaje);
                }
                case 2 -> {
                    System.out.println(personaje.getContadorMonedas());
                }
                case 3 -> {
                    personaje.subirNivel();
                }
                case 4 -> {
                    //instanciamos un monstruo y lo hacemos pelear con el personaje
                    Monstruo monstruo = new Monstruo((int) (personaje.getVidaBase() * 0.9), (float) (personaje.getDano() * 0.8), personaje.getVelocidad());
                    batalla(personaje, monstruo);
                }
                case 5 -> {
                    menuTienda(personaje, personaje.getTiendaPersonaje());
                }
                case 6 -> {
                    System.out.println("Gracias por jugar!");
                    System.out.println("Quieres guardar la partida? Escribe 'si' para guardar, cualquier otra cosa para NO guardar");
                    boolean guardar = sc.next().equals("si");
                    if (guardar){
                        guardarPartida(NOMBRESAVEDATA, personaje);
                    }
                    return;
                }
                default -> {
                    System.out.println("Introduce una opción válida");
                }
            }
            menu();
        }
    }

    public static void menu() {
        //este newline (\n) nos deja un margen con el texto ya generado, asi podemos mejorar la legibilidad
        System.out.println("\n Juego Prueba - Elige una de las siguientes opciones");
        System.out.println("1. Estado del personaje");
        System.out.println("2. Ver monedas");
        System.out.println("3. Subir nivel");
        System.out.println("4. Combate");
        System.out.println("5. Tienda");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

    public static Personaje crearPersonaje() {

        System.out.println("Que nombre quieres ponerle al personaje?");
        String nombre = sc.next();
        System.out.println("Cuanta vida tendrá el personaje?");
        int vida = sc.nextInt();
        System.out.println("Cuanto daño tendrá el personaje?");
        float dano = sc.nextFloat();
        System.out.println("Cuanta velocidad tendrá el personaje?");
        float velocidad = sc.nextFloat();

        return new Personaje(nombre, vida, dano, velocidad);
    }

    public static void batalla(Personaje personaje, Monstruo monstruo) {
        // Determinar quién ataca primero basado en la velocidad
        boolean turnoPersonaje = personaje.getVelocidad() >= monstruo.getVelocidad();

        // Mientras ambos personajes estén vivos
        while (personaje.getVidaActual() > 0 && monstruo.getVidaActual() > 0) {
            if (turnoPersonaje) {
                // El personaje ataca al monstruo
                personaje.atacar(monstruo);

                // Verificar si el monstruo ha sido derrotado
                if (monstruo.getVidaActual() <= 0) {
                    System.out.println(monstruo.getNombreMonstruo() + " ha sido derrotado.");
                }
            } else {
                // El monstruo ataca al personaje
                monstruo.atacar(personaje);

                // Verificar si el personaje ha sido derrotado
                if (personaje.getVidaActual() <= 0) {
                    System.out.println(personaje.getNombre() + " ha sido derrotado.");
                }
            }

            // Alternar el turno
            turnoPersonaje = !turnoPersonaje;
        }

        // Mostrar el resultado final de la batalla
        if (personaje.getVidaActual() > 0) {
            System.out.println(personaje.getNombre() + " ha ganado la batalla!");
        } else {
            System.out.println(monstruo.getNombreMonstruo() + " ha ganado la batalla!");
        }
    }

    public static void menuTienda(Personaje personaje, Tienda tienda) {
        boolean salirTienda = false;

        while (!salirTienda) {
            System.out.println("\n--- Tienda ---");
            System.out.println("1. Ver lista de armas");
            System.out.println("2. Comprar un arma");
            System.out.println("3. Comprar pociones");
            System.out.println("4. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> tienda.printListaArmasVenta();
                case 2 -> {
                    System.out.print("Selecciona el número del arma que deseas comprar: ");
                    int eleccionArma = sc.nextInt();
                    personaje.comprarArma(eleccionArma);
                }
                case 3 -> personaje.comprarPociones();
                case 4 -> salirTienda = true;
                default -> System.out.println("Opción no válida, por favor intenta de nuevo.");
            }
        }
    }

    //metodos para guardar y cargar partida
    public static void guardarPartida(String nombreArchivo, Object objeto) {
        //abrimos un objectoutputstream, el cual nos permite escribir objetos en un archivo
        try (ObjectOutputStream guardado = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            //escribimos en la variable el objeto que queremos
            guardado.writeObject(objeto);
            System.out.println("Partida guardada correctamente.");
            //recogemos cualquier error de la clase io (input output)
        } catch (IOException e) {
            System.out.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    // Método para cargar la partida
    public static Object cargarPartida(String nombreArchivo) {
        //abrimos un objectinputstream, el cual nos permite leer objetos de un archivo
        try (ObjectInputStream guardado = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            Object objeto = guardado.readObject();
            System.out.println("Partida cargada correctamente.");
            return objeto;
            //añadimos un ligero control de errores, recogemos cualquier error de la clase io (input output) y class not found, por si leemos un objeto que no toca
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }

}