package logica;

public class Ficha {

	private int valor;

	public Ficha(int valor) {
		this.valor = valor;
	}

	public void duplicarValor(){
		valor *= 2;
	}

	public void reestablecerValorACero() {
		valor = 0;
	}

	// Solo usable para TableroFichas cuando se reiniciar
	protected void actualizarValor(int nuevoValor) {
		valor = nuevoValor;
	}

	public int obtenerValor() {
		return this.valor;
	}

	public boolean compartenValor(Ficha f1) {
		return this.valor == f1.valor;
	}

	public void intercambiarValores(Ficha f1) {
		int valorAux = this.valor;
		this.valor = f1.valor;
		f1.valor = valorAux;
	}

}
