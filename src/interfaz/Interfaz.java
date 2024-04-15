package interfaz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import logica.Constantes2048;
import logica.TableroFichas;

//import javax.swing.Timer; // Importar Timer de javax.swing

public class Interfaz {
	private JFrame frame;
	private TableroFichas tableroInternoDelJuego;
	private MarcadorInterfaz frameMarcador;
	private LabelFicha[][] array2Dfichas;
	private int puntajeActualDelJugador;
	private JLabel labelPuntaje;
	private JLabel labelEstadoDeJuego;
	private JLabel labelValorObjetivo;

	// private JButton cargarDatosButton;
	// cargarDatosButton = new JButton("Cargar Datos");
	/*
	 * ver si los puedo usar despues para animacion mas suave private Timer
	 * temporizador; private int posicionActualX, posicionObjetivoX; private final
	 * int duracionAnimacion = 500; // Duración de la animación en milisegundos
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Interfaz window = new Interfaz();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public Interfaz() {
		initialize();
		actualizarTablero(); // Actualizar tablero al iniciar la interfaz
	}

	private void initialize() {

		puntajeActualDelJugador = 0;
		tableroInternoDelJuego = new TableroFichas();

		// ------------------ FRAMES ----------------------

		// JFRAME PRINCIPAL
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(251, 230, 198));
		int anchoFrame = 500;
		int altoFrame = 650;
		frame.setBounds(
				(PantallaUtils.anchoPantalla - anchoFrame) / 2,
				(PantallaUtils.altoPantalla - altoFrame) / 2,
				anchoFrame,
				altoFrame);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (jugadorQuiereSalirDelJuego())
					salirDelJuego();
			}
		});
		frame.getContentPane().setLayout(null);

		// JFRAME MARCADOR
		frameMarcador = new MarcadorInterfaz(frame);
		frameMarcador.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frameMarcador.setVisible(false); // Oculta el frame en lugar de cerrarlo
				frame.requestFocus();
			}
		});




		// ------------------ LABELS ----------------------

		// JLABEL[][] TABLERO DE FICHAS
		JPanel panelContenedorDeLasFichas = new JPanel();
		panelContenedorDeLasFichas.setBounds(39, 10, 400, 400);
		panelContenedorDeLasFichas.setBackground(COLOR_MARCO);
		panelContenedorDeLasFichas.setBorder(new LineBorder(COLOR_MARCO, 4));

		int altura_tablero = tableroInternoDelJuego.alturaTablero();
		int anchura_tablero = tableroInternoDelJuego.anchuraTablero();
		panelContenedorDeLasFichas.setLayout(new GridLayout(altura_tablero, anchura_tablero, 5, 5));
		frame.getContentPane().add(panelContenedorDeLasFichas);

		array2Dfichas = new LabelFicha[altura_tablero][anchura_tablero];
		for (int i = 0; i < altura_tablero; i++) {
			for (int j = 0; j < anchura_tablero; j++) {
				array2Dfichas[i][j] = new LabelFicha(tableroInternoDelJuego.obtenerFicha(i, j));
				array2Dfichas[i][j].setOpaque(true);
				array2Dfichas[i][j].setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto
				array2Dfichas[i][j].setFont(new Font("Arial", Font.BOLD, 28)); // Tamaño de la fuente
				panelContenedorDeLasFichas.add(array2Dfichas[i][j]);
			}
		}

		// JLABEL ESTADO DE JUEGO
		labelEstadoDeJuego = new JLabel("Mueva una tecla para iniciar la partida");
		labelEstadoDeJuego.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelEstadoDeJuego.setHorizontalAlignment(SwingConstants.CENTER);
		labelEstadoDeJuego.setBounds(39, 421, 400, 26);
		frame.getContentPane().add(labelEstadoDeJuego);

		// JLABEL TEXTO PUNTAJE:
		JLabel labelPuntajeTexto = new JLabel("Puntaje: ");
		labelPuntajeTexto.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelPuntajeTexto.setBounds(17, 459, 76, 26);
		frame.getContentPane().add(labelPuntajeTexto);

		// JLABEL PUNTAJE
		labelPuntaje = new JLabel(String.valueOf(puntajeActualDelJugador));
		labelPuntaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelPuntaje.setBounds(99, 463, 46, 19);
		frame.getContentPane().add(labelPuntaje);

		// JLABEL TEXTO DIFICULTAD
		JLabel labelTextoDificultad = new JLabel("Dificultad:");
		labelTextoDificultad.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelTextoDificultad.setBounds(17, 502, 76, 26);
		frame.getContentPane().add(labelTextoDificultad);

		// JLABEL DIFICULTAD (VALOR OBJETIVO)
		labelValorObjetivo = new JLabel(String.valueOf(tableroInternoDelJuego.obtenerValorObjetivo()));
		labelValorObjetivo.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelValorObjetivo.setBounds(99, 502, 42, 26);
		frame.getContentPane().add(labelValorObjetivo);




		// ------------------ BOTONES ----------------------

		// JBUTTON REINICIAR JUEGO
		JButton botonReiniciarJuego = new JButton("Reiniciar juego");
		botonReiniciarJuego.setBounds(304, 458, 150, 30);
		botonReiniciarJuego.addActionListener(e -> {
			reiniciarJuego();
		});
		frame.getContentPane().add(botonReiniciarJuego);

		// JBUTTON SALIR DEL JUEGO
		JButton botonSalirDelJuego = new JButton("Salir del juego");
		botonSalirDelJuego.setBounds(225, 539, 150, 30);
		botonSalirDelJuego.addActionListener(e -> {
			salirDelJuego();
		});
		frame.getContentPane().add(botonSalirDelJuego);

		// JBUTTON ELEGIR DIFICULTAD (VALOR OBJETIVO)
		JButton botonElegirDificultad = new JButton("Elegir dificultad");
		botonElegirDificultad.setBounds(170, 498, 124, 30);
		botonElegirDificultad.addActionListener(e -> {
			pedirDificultad();
			frame.requestFocus();
		});
		frame.getContentPane().add(botonElegirDificultad);

		// JBUTTON SUGERIR JUGADA
		JButton botonSugerirJugada = new JButton("Sugerir jugada");
		botonSugerirJugada.setBounds(170, 458, 124, 30);
		botonSugerirJugada.addActionListener(e -> {
			recomendarJugada(array2Dfichas);
			frame.requestFocus();
		});
		frame.getContentPane().add(botonSugerirJugada);

		// JBUTTON MOSTRAR MARCADOR
		JButton botonMostrarMarcador = new JButton("Marcador");
		botonMostrarMarcador.setBounds(304, 498, 150, 30);
		botonMostrarMarcador.addActionListener(e -> {
			frameMarcador.mostrarEnPantalla();
		});
		frame.getContentPane().add(botonMostrarMarcador);

		// JFRAME PRINCIPAL KEY LISTENER (MOVIMIENTO DEL USUARIO)
		frame.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {

				if (!tableroInternoDelJuego.terminoLaPartida()) { // Solo permitir movimientos si la partida no ha
																	// finalizado

					char key = (char) e.getKeyCode();

					int puntajeObtenidoPorMovimiento = tableroInternoDelJuego.moverFichas(key);

					// Si es -1, el movimiento fue invalido
					// No continuar
					if (puntajeObtenidoPorMovimiento == -1) {
						return;
					}

					tableroInternoDelJuego.agregarFichasRandom(Constantes2048.CANTIDAD_NUEVAS_FICHAS_TRAS_JUGADA);

					if (puntajeObtenidoPorMovimiento > 0) {
						puntajeActualDelJugador += puntajeObtenidoPorMovimiento;
						actualizarPuntajeEnPantalla();
					}

					desmarcarSugerencias();// limpio la sugerencia en caso de que exista
					actualizarTablero(); // Actualizar tablero después de mover

					// Verificar si el jugador ha ganado después del movimiento
					if (tableroInternoDelJuego.terminoLaPartida()) {
						// Si el jugador ganó
						if (tableroInternoDelJuego.seAlcanzoElValorObjetivo()) {
							labelEstadoDeJuego.setText("¡Felicidades! Has ganado.");
							labelEstadoDeJuego.setForeground(Color.GREEN.darker()); // Color de texto verde oscuro para
																					// indicar victoria
							if (jugadorQuiereSeguirLaPartida()) {

								tableroInternoDelJuego.jugarSinLimite();
								labelValorObjetivo.setText("Sin objetivo");

							} else {
								if (jugadorQuiereGuardarPuntuacion()) {
									registrarPuntuacionAlcanzada();
								}
								if (jugadorQuiereJugarNuevaPartida()) {
									reiniciarJuego();
								} else {
									salirDelJuego();
								}
							}
							// Si el jugador perdió
						} else {
							labelEstadoDeJuego.setText("¡Juego terminado! Gracias por jugar.");
							labelEstadoDeJuego.setForeground(Color.RED); // Color de texto rojo para indicar derrota
							if (jugadorQuiereGuardarPuntuacion()) {
								registrarPuntuacionAlcanzada();
							}
							if (jugadorQuiereJugarNuevaPartida()) {
								reiniciarJuego();
							} else {
								salirDelJuego();
							}
						}
					} else {
						labelEstadoDeJuego.setText("Partida en curso");
						labelEstadoDeJuego.setForeground(Color.BLACK); // Color de texto negro para partida en curso
					}
				}
			}

		});

		frame.setFocusable(true); // Permitir que la ventana tenga el foco para recibir eventos de teclado
	}

	// Métodos auxiliares

	/**
	 * Hace que la ejecución del juego termine
	 */
	private void salirDelJuego() {
		System.out.println("Gracias por jugar");
		System.exit(0);
	}

	/**
	 * Reestablece todos los valores necesarios para que se pueda lograr una nueva
	 * partida desde cero
	 */
	private void reiniciarJuego() {

		tableroInternoDelJuego.reestablecer();
		tableroInternoDelJuego.actualizarValorObjetivo(Constantes2048.VALOR_OBJETIVO);
		labelValorObjetivo.setText(String.valueOf(tableroInternoDelJuego.obtenerValorObjetivo()));

		puntajeActualDelJugador = 0;
		actualizarPuntajeEnPantalla();
		actualizarTablero();
		labelEstadoDeJuego.setText("Mueva una tecla para iniciar la partida");
		frame.requestFocus();
	}

	/**
	 * Chequea si la dificultad sugerida por el usuario (valor entero para
	 * establecer como objetivo) es potencia de 2.
	 * 
	 * @param numero numero sugerido por el usuario
	 * @return {@code true} si {@code numero} es una potencia de 2
	 */
	private boolean esPotenciaDeDos(int numero) {
		if (numero <= 0) {
			return false;
		}
		while (numero > 1) {
			if (numero % 2 != 0) {
				return false; // Si el num no es divisible por 2, no es potencia de 2
			}
			numero = numero / 2; // Dividir el num por 2 para verificar si es potencia de 2
		}
		return true;
	}

	/**
	 * Actualiza el label que contiene el valor del puntaje
	 */
	private void actualizarPuntajeEnPantalla() {
		labelPuntaje.setText(String.valueOf(puntajeActualDelJugador));
	}

	/**
	 * Despliega por pantalla un {@link TwoOptionsChooser} que le pregunta al
	 * usuario desea guardar su puntuación alcanzada
	 * 
	 * @return {@code true} si el usuario desea guardar su puntuación alcanzada en
	 *         el marcador
	 */
	private boolean jugadorQuiereGuardarPuntuacion() {
		return TwoOptionsChooser.responderPreguntaLogica(frame, "¿Quieres registrar tu puntuación?");
	}

	/**
	 * Despliega por pantalla un {@link TwoOptionsChooser} que le pregunta al
	 * usuario si desea continuar la partida. Este método solo se llamará si el
	 * usuarió alcanzó el valor objetivo
	 * 
	 * @return {@code true} si el usuario desea continuar la partida
	 */
	private boolean jugadorQuiereSeguirLaPartida() {
		return TwoOptionsChooser.responderPreguntaLogica(frame, "¿Quieres continuar la partida?");
	}

	/**
	 * Despliega por pantalla un {@link TwoOptionsChooser} que le pregunta al
	 * usuario desea comenzar una nueva partida
	 * 
	 * @return {@code true} si el usuario desea comenzar una nueva partida
	 */
	private boolean jugadorQuiereJugarNuevaPartida() {
		return TwoOptionsChooser.responderPreguntaLogica(frame, "¿Quieres jugar una nueva partida?");
	}

	/**
	 * Despliega por pantalla un {@link TwoOptionsChooser} que le pregunta al
	 * usuario desea salir del juego
	 * 
	 * @return {@code true} si el usuario desea salir del juego
	 */
	private boolean jugadorQuiereSalirDelJuego() {
		return TwoOptionsChooser.responderPreguntaLogica(frame, "¿Seguro quieres salir del juego?");
	}

	/**
	 * Se le solicita al usuario ingresar nueva dificultad para establecer como
	 * nuevo valor objetivo. Este método despliega un {@link JOptionPane}, donde se
	 * va a pedir que se ingrese un valor númerico; más específicamente un entero
	 * que sea potencia de 2. La aparición del {@code JOptionPane} no va a cesar
	 * hasta que el input del usuario no cumpla las condiciones ya dichas.
	 */
	private void pedirDificultad() {
		String input; // = JOptionPane.showInputDialog(frame, "Ingrese el numero que se deberá
		// alcanzar.");
		boolean numeroValido = false;
		int numero;// = Integer.parseInt(input);

		while (!numeroValido) {
			input = JOptionPane.showInputDialog(frame, "Ingrese el número que se deberá alcanzar.");

			try {
				numero = Integer.parseInt(input);
				// Verificar si el número ingresado es válido
				if (esPotenciaDeDos(numero)) {
					numeroValido = true;
					numero = Integer.parseInt(input);
					tableroInternoDelJuego.actualizarValorObjetivo(numero);

				} else {
					JOptionPane.showMessageDialog(frame, "Ingrese un número válido (2, 4, 8, 16, ..., 2048).", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		// numero = Integer.parseInt(input);
		// tableroInternoDelJuego.updateValorGanador(numero);

		labelValorObjetivo.setText(String.valueOf(tableroInternoDelJuego.obtenerValorObjetivo()));

	}

	/**
	 * Registra la puntuación alcanzada por el usuario, que será visible luego en el
	 * marcador.
	 */
	private void registrarPuntuacionAlcanzada() {
		String nombre = JOptionPane.showInputDialog(frame, "Ingrese su nombre para registrar su puntuación:");
		int puntuacion = puntajeActualDelJugador;
		frameMarcador.registrarPuntacion(nombre, puntuacion);
	}

	/**
	 * Actualiza gráficamente las fichas que se ven por pantalla, tanto el valor que
	 * tienen como el color que les corresponda por valor.
	 */
	private void actualizarTablero() {
		for (int i = 0; i < array2Dfichas.length; i++) {
			for (int j = 0; j < array2Dfichas[i].length; j++) {
				array2Dfichas[i][j].chequearValores();
			}
		}
	}

	/**
	 * Color del marco del juego (tablero de fichas)
	 */
	private static final Color COLOR_MARCO = Constantes2048.COLOR_MARCO;

	private void recomendarJugada(LabelFicha[][] tablero) {
		int combinacionesHorizontales = contarCombinacionesHorizontales(tablero);
		int combinacionesVerticales = contarCombinacionesVerticales(tablero);

		if (combinacionesHorizontales > 1 || combinacionesVerticales > 1) {
			Random random = new Random();
			boolean ejecutarHorizontal = random.nextBoolean();

			if (ejecutarHorizontal) {
				ejecutarCombinacionesHorizontales(tablero);
			} else {
				ejecutarCombinacionesVerticales(tablero);
			}
		} else {
			if (combinacionesHorizontales > 0) {
				ejecutarCombinacionesHorizontales(tablero);
			} else if (combinacionesVerticales > 0) {
				ejecutarCombinacionesVerticales(tablero);
			} else {
				actualizarTablero();
			}
		}
	}

	private int contarCombinacionesHorizontales(LabelFicha[][] tablero) {
		int combinaciones = 0;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j].valorDeFicha() != 0) {
					int col = j - 1;
					while (col >= 0 && tablero[i][col].valorDeFicha() == 0) {
						col--;
					}
					if (col >= 0 && tablero[i][col].valorDeFicha() == tablero[i][j].valorDeFicha()) {
						combinaciones++;
					}
				}
			}
		}
		return combinaciones;
	}

	private int contarCombinacionesVerticales(LabelFicha[][] tablero) {
		int combinaciones = 0;
		for (int j = 0; j < tablero[0].length; j++) {
			for (int i = 0; i < tablero.length; i++) {
				if (tablero[i][j].valorDeFicha() != 0) {
					int row = i - 1;
					while (row >= 0 && tablero[row][j].valorDeFicha() == 0) {
						row--;
					}
					if (row >= 0 && tablero[row][j].valorDeFicha() == tablero[i][j].valorDeFicha()) {
						combinaciones++;
					}
				}
			}
		}
		return combinaciones;
	}

	private void ejecutarCombinacionesHorizontales(LabelFicha[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j].valorDeFicha() != 0) {
					int col = j - 1;
					while (col >= 0 && tablero[i][col].valorDeFicha() == 0) {
						col--;
					}
					if (col >= 0 && tablero[i][col].valorDeFicha() == tablero[i][j].valorDeFicha()) {
						marcarCombinacionHorizontal(tablero, i, col, j);
						return;
					}
				}
			}
		}
	}

	private void ejecutarCombinacionesVerticales(LabelFicha[][] tablero) {
		for (int j = 0; j < tablero[0].length; j++) {
			for (int i = 0; i < tablero.length; i++) {
				if (tablero[i][j].valorDeFicha() != 0) {
					int row = i - 1;
					while (row >= 0 && tablero[row][j].valorDeFicha() == 0) {
						row--;
					}
					if (row >= 0 && tablero[row][j].valorDeFicha() == tablero[i][j].valorDeFicha()) {
						marcarCombinacionVertical(tablero, row, j, i);
						return;
					}
				}
			}
		}
	}

	private void marcarCombinacionHorizontal(LabelFicha[][] tablero, int row, int startCol, int endCol) {
		LabelFicha value = tablero[row][startCol];
		for (int col = startCol; col <= endCol; col++) {
			if (tablero[row][col].valorDeFicha() == value.valorDeFicha()) {
				marcarCelda(row, col);
			}
		}
	}

	private void marcarCombinacionVertical(LabelFicha[][] tablero, int startRow, int col, int endRow) {
		LabelFicha value = tablero[startRow][col];
		for (int row = startRow; row <= endRow; row++) {
			if (tablero[row][col].valorDeFicha() == value.valorDeFicha()) {
				marcarCelda(row, col);
			}
		}
	}

	private void marcarCelda(int row, int col) {
		array2Dfichas[row][col].marcarComoSugerencia();
	}

	private void desmarcarSugerencias() {
		for (LabelFicha[] row : array2Dfichas) {
			for (LabelFicha ficha: row) {
				if (ficha.esSugerencia())
					ficha.desmarcarComoSugerencia();
			}
		}
	}

}