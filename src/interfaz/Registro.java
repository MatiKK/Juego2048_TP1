package interfaz;

public class Registro {

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

}