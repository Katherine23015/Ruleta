import java.util.Random;
import java.util.Scanner;
public class ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    public static Random rng = new Random();
    public static int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(in);
            ejecutarOpcion(opcion, in);
        } while (opcion != 3);
    }

    public static void mostrarMenu() {
        System.out.println("\n--- RULETA ---");
        System.out.println("1. Iniciar ronda");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
        System.out.print("Seleccione opción: ");
    }

    public static int leerOpcion(Scanner in) {
        while (!in.hasNextInt()) {
            System.out.println("Error: Ingrese un numero válido");
            in.next();
        }
        return in.nextInt();
    }

    public static void ejecutarOpcion(int opcion, Scanner in) {
        switch (opcion) {
            case 1:
                iniciarRonda(in);
                break;
            case 2:
                mostrarEstadisticas();
                break;
            case 3:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    public static void iniciarRonda(Scanner in) {
        System.out.println("Monto a apostar");
        int monto = in.nextInt();

        char tipo = leerTipoApuesta(in);
        int numeroGanador = girarRuleta();
        boolean esGanador = evaluarResultado(numeroGanador, tipo);

        registrarResultado(numeroGanador, monto, esGanador);
        mostrarResultado(numeroGanador, tipo, monto, esGanador);
    }

    public static char leerTipoApuesta(Scanner in) {
        System.out.println("R = Rojo | N = Negro | P = Par | I = Impar");
        return in.next().toUpperCase().charAt(0);
    }

    public static int girarRuleta() {
        return rng.nextInt(37);
    }

    public static boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false;
        if (tipo == 'R') return esRojo(numero);
        if (tipo == 'N') return !esRojo(numero);
        if (tipo == 'P') return numero % 2 == 0;
        if (tipo == 'I') return numero % 2 != 0;
        return false;
    }

    public static boolean esRojo(int n) {
        for (int rojo : numerosRojos) {
            if (n == rojo) return true;
        }
        return false;
    }


    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = apuesta;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }


    public static void mostrarResultado(int numero, char tipo, int monto, boolean
            acierto) {
        System.out.println("Número: " + numero);
        System.out.println("Apuesta: " + tipo + " | Monto: " + monto);

        if (acierto)
            System.out.println("Ganaste!");
        else
            System.out.println("Perdiste.");
    }

    public static void mostrarEstadisticas() {
        if (historialSize == 0) {
            System.out.println("No hay rondas registradas aún.");
            return;
        }

        int totalApostado = 0;
        int totalAciertos = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i]; // [cite: 18]
            if (historialAciertos[i]) totalAciertos++; // [cite: 19]
        }
        double porcentajeAcierto = (double) totalAciertos / historialSize * 100; // [cite: 20]

        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Total apostado: $" + totalApostado);
        System.out.println("Total de aciertos: " + totalAciertos);
        System.out.printf("Porcentaje de acierto: %.2f%%\n", porcentajeAcierto);
    }
}