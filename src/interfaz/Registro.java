package interfaz;

public class Registro implements Comparable<Registro> {

	private String nombreDelJugador;
	private int puntajeObtenido;

	public Registro(String nombre, int puntaje) {
		nombreDelJugador = nombre;
		puntajeObtenido = puntaje;
	}

	public String obtenerNombre() {
		return nombreDelJugador;
	}

	public int obtenerPuntaje() {
		return puntajeObtenido;
	}

	@Override
	public String toString() {
		return obtenerNombre() + ": " +  obtenerPuntaje() + " puntos";
	}

	public int compareTo(Registro o) {
//		Con esto ordena de menor a mayor
//		return obtenerPuntaje() - o.obtenerPuntaje();

//		Con esto ordena directamente de mayor a menor
		return o.obtenerPuntaje() - obtenerPuntaje();
	}

}