package interfaz;

/**
 * Clase que representa un registro, guardando información acerca de un estado
 * de juego que consta de un puntaje alcanzado y de un nombre (del jugador que
 * creó este registro/logró cierto puntaje).
 */
public class Registro implements Comparable<Registro> {

	private String nombreDelJugador;
	private int puntajeObtenido;

	public Registro(String nombre, int puntaje) {
		nombreDelJugador = nombre;
		puntajeObtenido = puntaje;
	}

	/*
	 * TODO hacerlo mas lindo
	 */
	@Override
	public String toString() {
		return nombreDelJugador + ": " + puntajeObtenido + " puntos. ";
	}

	public int compareTo(Registro r) {
//		Con esto ordena de menor a mayor
//		return obtenerPuntaje() - o.obtenerPuntaje();

//		Con esto ordena directamente de mayor a menor
		return r.puntajeObtenido - puntajeObtenido;
	}

}