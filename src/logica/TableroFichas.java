package logica;

import java.util.Random;

public class TableroFichas {

	/**
	 * Array bidimensional que representa un tablero
	 */
	private Ficha[][] array2Dfichas;

	private Random randomGenerator;

	private int alturaTablero;
	private int anchuraTablero;

	private int valorObjetivo;
	private int cantidadFichasActual;

	/**
	 * Constante que establece que la partida actual no tiene un valor establecido como objetivo
	 * a alcanzar, y que simplemente la partida fluirá hasta que el jugador pierda.
	 */
	private static int PARTIDA_SIN_LIMITE = 0;

	/**
	 * Seguramente despues lo borre
	 */
	private int valorMasGrandeAlcanzado;

	/**
	 * Cantidad de fichas con las que empieza el juego, sirve nomás para cuando se
	 * reinicia el tablero
	 */
	private int cantidadFichasInicial;

	/**
	 * Vale {@code true} si la partida ya no puede seguir, ya sea porque el jugador
	 * ganó (alcanzó el valor objetivo) o perdió (no lo alcanzó y se llenó el
	 * tablero)
	 */
	private boolean terminoLaPartida;

	/**
	 * Vale {@code true} si el jugador alcanzó el valor objetivo. Si esto vale
	 * {@code true}, {@code terminoLaPartida} también debería
	 */
	private boolean seAlcanzoElValorObjetivo;

	/**
	 * Direcciones entendibles para esta clase para reconocer qué acciones
	 * hacer de acuerdo al input de un usuario (ej. key event)
	 */
	private enum Direccion {
		ARRIBA,
		ABAJO,
		IZQUIERDA,
		DERECHA,
		INVALIDO // En caso de que se ingrese un movimiento no válido
	}

	/**
	 * Construye un tablero con los valores predeterminados de una partida del juego 2048
	 */
	public TableroFichas() {
		this(Constantes2048.TABLERO_ALTO,
			Constantes2048.TABLERO_ANCHO,
			Constantes2048.VALOR_OBJETIVO,
			Constantes2048.CANTIDAD_INICIAL_FICHAS
			);
	}

	/**
	 * Construye un Tablero de Fichas con los valores dados
	 * @param filas cantidad de filas del tablero
	 * @param columnas cantiad de columnas del tablero
	 * @param valorGanador valor al cual el jugador debe alcanzar o superar para ganar la partida
	 * @param fichasInicial cantidad de fichas con la que se comienzan
	 */
	public TableroFichas(int filas, int columnas, int valorGanador, int fichasInicial) {

		alturaTablero = filas;
		anchuraTablero = columnas;

		array2Dfichas = new Ficha[alturaTablero][anchuraTablero];
		for (int i = 0; i < alturaTablero; i++)
			for (int j = 0; j < anchuraTablero; j++)
				array2Dfichas[i][j] = new Ficha(0);

		randomGenerator = new Random();
		valorObjetivo = valorGanador;
		cantidadFichasInicial = fichasInicial;
		setUpInicial();
	}

	/**
	 * Reinicia todos los valores para empezar una nueva partida
	 */
	public void reestablecer() {
		setUpInicial();
	}

	/**
	 * Configura la clase de modo que sea posible comenzar una partida
	 */
	private void setUpInicial() {
		terminoLaPartida = false;
		seAlcanzoElValorObjetivo = false;
		cantidadFichasActual = 0;
		valorMasGrandeAlcanzado = 0;
		for (int i = 0; i < alturaTablero; i++)
			for (int j = 0; j < anchuraTablero; j++)
				obtenerFicha(i,j).reestablecerValorACero();
		agregarFichasRandom(cantidadFichasInicial);
	}

	/**
	 * Devuelve la cantidad de filas del tablero
	 * @return la cantidad de filas del tablero
	 */
	public int alturaTablero() {
		return alturaTablero;
	}

	/**
	 * la cantidad de columnas del tablero
	 * @return la cantidad de columnas del tablero
	 */
	public int anchuraTablero() {
		return anchuraTablero;
	}

	/**
	 * Devuelve el valor que coincida en la ubicación dada
	 * @param fila fila donde está el valor que se busca
	 * @param columnacolumna donde está el valor que se busca
	 * @return el valor que se encuentra en las "coordenadas" dadas
	 */
	public Ficha obtenerFicha(int fila, int columna) {
		return array2Dfichas[fila][columna];
	}

	private int obtenerValorEnFicha(int fila, int columna) {
		return obtenerFicha(fila,columna).obtenerValor();
	}

	/**
	 * Asigna un nuevo valor objetivo.
	 * 
	 * @param valor el nuevo valor objetivo
	 */
	public void actualizarValorObjetivo(int valor) {
		valorObjetivo = valor;
//		if (valorMasGrandeAlcanzado < valorObjetivo) {
//			seAlcanzoElValorObjetivo = false;
//			terminoLaPartida = false;
//		}
	}

	/**
	 * Establece que no se contará con un valor objetivo, permitiendo
	 * jugar sin un objetivo, o hasta que se pierda la partida.
	 */
	public void jugarSinLimite() {
		valorObjetivo = PARTIDA_SIN_LIMITE;
		seAlcanzoElValorObjetivo = false;
		terminoLaPartida = false;
	}

	/**
	 * Devuelve el valor objetivo
	 * @return el valor objetivo
	 */
	public int obtenerValorObjetivo() {
		return valorObjetivo;
	}

	/**
	 * @return {@code true} si se alcanzó el valor objetivo
	 */
	public boolean seAlcanzoElValorObjetivo() {
		return seAlcanzoElValorObjetivo;
	}

	/**
	 * @return la cantidad de fichas que puede contener el tablero
	 */
	private int espacioParaFichas() {
		return alturaTablero * anchuraTablero;
	}

	/**
	 * @return {@code true} si el tablero está completo, es decir,
	 * no hay espacio para agregar una ficha más
	 */
	public boolean elTableroEstaCompleto() {
		return espacioParaFichas() == cantidadFichasActual;
	}

	/**
	 * Agrega una cantidad dada de fichas aleatorias. En caso de no haber
	 * espacio, se chequeará si se encuentra en el caso de que se perdió la partida.
	 * @param cantidad cantidad de fichas aleatorias a agregar
	 */
	public void agregarFichasRandom(int cantidad) {
		if (!elTableroEstaCompleto()) {
			while (cantidadFichasActual + cantidad > espacioParaFichas())
				cantidad--;
			for (int i = 0; i < cantidad; i++) {
				int row, col;
				do {
					row = randomGenerator.nextInt(alturaTablero);
					col = randomGenerator.nextInt(anchuraTablero);
				} while (obtenerValorEnFicha(row, col) != 0); // Busca una posición vacía
				int value = randomGenerator.nextDouble() < 0.9 ? 2 : 4;
				obtenerFicha(row,col).actualizarValor(value);
				if (value > valorMasGrandeAlcanzado)
					valorMasGrandeAlcanzado = value;
				cantidadFichasActual++;
			}
		} else {
			validarPartidaPerdida();
		}

	}

	/**
	 * Verifica si se perdió la partida. Este método solo es llamado
	 * si el tablero se encuentra completo.
	 */
	private void validarPartidaPerdida() {
		terminoLaPartida = !hayCombinacionesPosibles();
	}

	/**
	 * Verifica si existen combinaciones posibles dentro del tablero
	 * que permitan la continuidad de la partida. Este método solo es llamado
	 * si el tablero se encuentra completo.
	 * Si el tablero está completo y no hay combinaciones posibles, se dice que
	 * se perdió la partida.
	 * @return {@code true} si existen combinaciones de fichas posibles
	 */
	private boolean hayCombinacionesPosibles() {
		// Chequeo todo el tablero
		for (int i = 0; i < alturaTablero; i++) {
			for (int j = 0; j < anchuraTablero - 1; j++) {
				if (i < alturaTablero - 1)
					if (obtenerValorEnFicha(i, j) == obtenerValorEnFicha(i + 1, j))
						return true;
				if (j < anchuraTablero - 1)
					if (obtenerValorEnFicha(i, j) == obtenerValorEnFicha(i, j + 1))
						return true;
			}
		}
		return false; // No hay combinaciones posibles
	}

	/**
	 * Interpreta el input de un usuario como una {@link Direccion}.
	 * @param direccion input del usuario
	 * @return una {@link Direccion} entendible para esta clase
	 */
	private Direccion interpretarMovimiento(char direccion) {
		switch (direccion) {
		// Arriba
		case 'w':
		case 'W':
		case 0x26: // Flecha arriba
			return Direccion.ARRIBA;
		// Izquierda
		case 'a':
		case 'A':
		case 0x25: // Flecha izquierda
			return Direccion.IZQUIERDA;
		// Abajo
		case 's':
		case 'S':
		case 0x28: // Flecha abajo
			return Direccion.ABAJO;
		// Derecha
		case 'd':
		case 'D':
		case 0x27: // Flecha derecha
			return Direccion.DERECHA;
		// No se presionó ninguna de las anteriores
		default:
			return Direccion.INVALIDO;
		}
	}

	/**
	 * Mueve todas las fichas del tablero hacia la dirección dada por el usuario.
	 * Este método devuelve un valor entero, el cual es el puntaje obtenido tras
	 * mover todas las fichas del tablero en la dirección dada.
	 * <p>
	 * Cuando dos fichas de mismo valor {@code n} se chocan, se combinan, y se suma 
	 * {@code 2*n} al puntaje de la partida.
	 * 
	 * @param direccion dirección a la que se desean mover las fichas
	 * @return el puntaje obtenido producto de mover todas las fichas en la dirección dada.
	 * Si la dirección no es válida o entendible para la clase, devuelve {@code -1}
	 */
	public int moverFichas(char direccion) {
		switch (interpretarMovimiento(direccion)) {
		case ARRIBA:
			return moverFichasHaciaArriba();
		case ABAJO:
			return moverFichasHaciaAbajo();
		case IZQUIERDA:
			return moverFichasHaciaIzquierda();
		case DERECHA:
			return moverFichasHaciaDerecha();
		case INVALIDO:
		default:
			return -1;
		}
	}

	/**
	 * Mueve todas las fichas del tablero hacia arriba.
	 * @return el puntaje obtenido producto de mover todas las fichas hacia arriba
	 * @see {@link #moverFichas(char)}
	 */
	private int moverFichasHaciaArriba() {
		int puntaje = 0;
		// Recorrer cada columna del tablero
		for (int col = 0; col < anchuraTablero; col++) {
			// Combinar y mover las fichas hacia arriba en la columna actual
			for (int row = 1; row < alturaTablero; row++) {
				if (obtenerValorEnFicha(row, col) != 0) {
					// Combinar fichas si es posible
					for (int k = row - 1; k >= 0; k--) {
						Ficha fichaActual = obtenerFicha(k,col);
						Ficha fichaSiguiente = obtenerFicha(k+1,col);
						if (fichaActual.obtenerValor() == 0) {// TENGO LUGAR ARRIBA
							// Mover la ficha hacia arriba
							fichaActual.intercambiarValores(fichaSiguiente);
							fichaSiguiente.reestablecerValorACero();
						} else if (fichaActual.compartenValor(fichaSiguiente)) {
							// Combinar fichas
							fichaActual.duplicarValor();
							fichaSiguiente.reestablecerValorACero();
							int aux = fichaActual.obtenerValor();
							puntaje += aux;
							cantidadFichasActual--;
							valorMasGrandeAlcanzado = aux;
							chequearSiEsElValorObjetivo(aux);
							// System.out.println("obteniendo valor: " + tablero[k][col]);
							break;
						} else {
							// No se puede combinar, salir del bucle
							break;
						}
					}
				}
			}
		}

		return puntaje;
	}

	/**
	 * Mueve todas las fichas del tablero hacia abajo.
	 * @return el puntaje obtenido producto de mover todas las fichas hacia abajo
	 * @see {@link #moverFichas(char)}
	 */
	private int moverFichasHaciaAbajo() {
		int puntaje = 0;
		// Recorrer cada columna del tablero
		for (int col = 0; col < anchuraTablero; col++) {
			// Combinar y mover las fichas hacia abajo en la columna actual
			for (int row = alturaTablero - 2; row >= 0; row--) {
				if (obtenerValorEnFicha(row, col) != 0) {
					// Combinar fichas si es posible
					for (int k = row + 1; k < alturaTablero; k++) {

						Ficha fichaActual = obtenerFicha(k,col);
						Ficha fichaSiguiente = obtenerFicha(k-1,col);

						if (fichaActual.obtenerValor() == 0) {
							fichaActual.intercambiarValores(fichaSiguiente);
							fichaSiguiente.reestablecerValorACero();
						
						} else if (fichaActual.compartenValor(fichaSiguiente)) {
							fichaActual.duplicarValor();
							fichaSiguiente.reestablecerValorACero();
							int aux = fichaActual.obtenerValor();
							puntaje += aux;
							cantidadFichasActual--;
							valorMasGrandeAlcanzado = aux;
							chequearSiEsElValorObjetivo(aux);
							break;
						} else {
							break;
						}

					}
				}
			}
		}

		return puntaje;
	}

	/**
	 * Mueve todas las fichas del tablero hacia la izquierda.
	 * @return el puntaje obtenido producto de mover todas las fichas hacia la izquierda
	 * @see {@link #moverFichas(char)}
	 */
	private int moverFichasHaciaIzquierda() {
		int puntaje = 0;
		// Recorrer cada fila del tablero
		for (int row = 0; row < alturaTablero; row++) {
			// Combinar y mover las fichas hacia la izquierda en la fila actual
			for (int col = 1; col < anchuraTablero; col++) {
				if (obtenerValorEnFicha(row, col) != 0) {
					// Combinar fichas si es posible
					for (int k = col - 1; k >= 0; k--) {
						Ficha fichaActual = obtenerFicha(row,k);
						Ficha fichaSiguiente = obtenerFicha(row, k + 1);
						if (fichaActual.obtenerValor() == 0) {// TENGO LUGAR izquierda
							// Mover la ficha hacia izquierda
							fichaActual.intercambiarValores(fichaSiguiente);
							fichaSiguiente.reestablecerValorACero();
						} else if (fichaActual.compartenValor(fichaSiguiente)) {
							// Combinar fichas
							fichaActual.duplicarValor();
							fichaSiguiente.reestablecerValorACero();
							int aux = fichaActual.obtenerValor();
							puntaje += aux;
							cantidadFichasActual--;
							valorMasGrandeAlcanzado = aux;
							chequearSiEsElValorObjetivo(aux);
							// System.out.println("obteniendo valor: " + tablero[k][col]);
							break;
						} else {
							// No se puede combinar, salir del bucle
							break;
						}

					}
				}
			}
		}

		return puntaje;
	}

	/**
	 * Mueve todas las fichas del tablero hacia la derecha.
	 * @return el puntaje obtenido producto de mover todas las fichas hacia la derecha
	 * @see {@link #moverFichas(char)}
	 */
	private int moverFichasHaciaDerecha() {
		int puntaje = 0;
		// Recorrer cada fila del tablero
		for (int row = 0; row < alturaTablero; row++) {
			// Combinar y mover las fichas hacia la derecha en la fila actual
			for (int col = anchuraTablero - 2; col >= 0; col--) {
				if (obtenerValorEnFicha(row, col) != 0) {
					// Combinar fichas si es posible
					for (int k = col + 1; k < anchuraTablero; k++) {
						Ficha fichaActual = obtenerFicha(row,k);
						Ficha fichaSiguiente = obtenerFicha(row, k - 1);
						if (fichaActual.obtenerValor() == 0) {// TENGO LUGAR derecha
							// Mover la ficha hacia derecha
							fichaActual.intercambiarValores(fichaSiguiente);
							fichaSiguiente.reestablecerValorACero();
						} else if (fichaActual.compartenValor(fichaSiguiente)) {
							// Combinar fichas
							fichaActual.duplicarValor();
							fichaSiguiente.reestablecerValorACero();
							int aux = fichaActual.obtenerValor();
							puntaje += aux;
							cantidadFichasActual--;
							valorMasGrandeAlcanzado = aux;
							chequearSiEsElValorObjetivo(aux);
							// System.out.println("obteniendo valor: " + tablero[k][col]);
							break;
						} else {
							// No se puede combinar, salir del bucle
							break;
						}

					}
				}
			}
		}

		return puntaje;
	}

	/**
	 * Chequea si el valor dado es el valor objetivo.
	 * En caso de serlo, se considera que se ganó la partida y,
	 * en consecuencia, que la partida terminó.
	 * @param valor valor a chequear
	 */
	private void chequearSiEsElValorObjetivo(int valor) {
		if (valorObjetivo == PARTIDA_SIN_LIMITE)
			return; // Modo sin límite
		if (valor >= valorObjetivo) {
			seAlcanzoElValorObjetivo = true;
			terminoLaPartida = true;
		}
	}

	/**
	 * @return {@code true} si la partida terminó
	 */
	public boolean terminoLaPartida() {
		return terminoLaPartida;
	}

	public Ficha[][] obtenerTablero() {
		return array2Dfichas;
	}

}
