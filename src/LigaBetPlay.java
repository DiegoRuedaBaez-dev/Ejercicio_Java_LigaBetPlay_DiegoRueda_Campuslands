import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Equipo {
    String nombre;
    int pj, pg, pp, pe, gf, gc, tp;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.pj = 0;
        this.pg = 0;
        this.pp = 0;
        this.pe = 0;
        this.gf = 0;
        this.gc = 0;
        this.tp = 0;
    }

    public void actualizarEstadisticas(int golesFavor, int golesContra) {
        this.pj++;
        this.gf += golesFavor;
        this.gc += golesContra;

        if (golesFavor > golesContra) {
            this.pg++;
            this.tp += 3;
        } else if (golesFavor < golesContra) {
            this.pp++;
        } else {
            this.pe++;
            this.tp++;
        }
    }
}

public class LigaBetPlay {
    private List<Equipo> equipos;

    public LigaBetPlay() {
        this.equipos = new ArrayList<>();
    }

    public void registrarEquipo(String nombre) {
        equipos.add(new Equipo(nombre));
    }

    public void registrarResultado(String local, String visitante, int golesLocal, int golesVisitante) {
        Equipo equipoLocal = encontrarEquipo(local);
        Equipo equipoVisitante = encontrarEquipo(visitante);

        if (equipoLocal != null && equipoVisitante != null) {
            equipoLocal.actualizarEstadisticas(golesLocal, golesVisitante);
            equipoVisitante.actualizarEstadisticas(golesVisitante, golesLocal);
        } else {
            System.out.println("Uno o ambos equipos no están registrados.");
        }
    }

    private Equipo encontrarEquipo(String nombre) {
        for (Equipo equipo : equipos) {
            if (equipo.nombre.equalsIgnoreCase(nombre)) {
                return equipo;
            }
        }
        return null;
    }

    public void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú Principal:");
            System.out.println("1. Registrar equipo");
            System.out.println("2. Registrar resultado");
            System.out.println("3. Generar reportes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del equipo: ");
                    String nombreEquipo = scanner.nextLine();
                    registrarEquipo(nombreEquipo);
                    break;
                case 2:
                    System.out.print("Equipo local: ");
                    String local = scanner.nextLine();
                    System.out.print("Equipo visitante: ");
                    String visitante = scanner.nextLine();
                    System.out.print("Goles del equipo local: ");
                    int golesLocal = scanner.nextInt();
                    System.out.print("Goles del equipo visitante: ");
                    int golesVisitante = scanner.nextInt();
                    registrarResultado(local, visitante, golesLocal, golesVisitante);
                    break;
                case 3:
                    mostrarSubMenuReportes();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public void mostrarSubMenuReportes() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Submenú de Reportes:");
            System.out.println("1. Nombre del equipo que más goles anotó");
            System.out.println("2. Nombre del equipo que más puntos tiene");
            System.out.println("3. Nombre del equipo que más partidos ganó");
            System.out.println("4. Total de goles anotados por todos los equipos");
            System.out.println("5. Promedio de goles anotados en el torneo");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            Equipo maxGoles = null;
            Equipo maxPuntos = null;
            Equipo maxGanados = null;
            int totalGoles = 0;

            for (Equipo equipo : equipos) {
                totalGoles += equipo.gf;

                if (maxGoles == null || equipo.gf > maxGoles.gf) {
                    maxGoles = equipo;
                }
                if (maxPuntos == null || equipo.tp > maxPuntos.tp) {
                    maxPuntos = equipo;
                }
                if (maxGanados == null || equipo.pg > maxGanados.pg) {
                    maxGanados = equipo;
                }
            }

            switch (opcion) {
                case 1:
                    System.out.println("Equipo que más goles anotó: " + (maxGoles != null ? maxGoles.nombre : "N/A"));
                    break;
                case 2:
                    System.out.println("Equipo con más puntos: " + (maxPuntos != null ? maxPuntos.nombre : "N/A"));
                    break;
                case 3:
                    System.out.println(
                            "Equipo con más partidos ganados: " + (maxGanados != null ? maxGanados.nombre : "N/A"));
                    break;
                case 4:
                    System.out.println("Total de goles anotados por todos los equipos: " + totalGoles);
                    break;
                case 5:
                    System.out.println("Promedio de goles anotados en el torneo: "
                            + (equipos.size() > 0 ? (double) totalGoles / equipos.size() : 0));
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        LigaBetPlay liga = new LigaBetPlay();
        liga.mostrarMenuPrincipal();
    }
}
