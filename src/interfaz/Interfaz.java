package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.Logica2048;

//import javax.swing.Timer; // Importar Timer de javax.swing



public class Interfaz {
    private JFrame frame;
    private JLabel[][] grafo;
    private Logica2048 game;
    /*ver si los puedo usar despues para animacion mas suave
    private Timer temporizador;
    private int posicionActualX, posicionObjetivoX;
    private final int duracionAnimacion = 500; // Duración de la animación en milisegundos
    */
    private boolean partidaFinalizada;
    private boolean partidaGanada;
    
    //private JButton cargarDatosButton;
    //cargarDatosButton = new JButton("Cargar Datos");


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
    	
        game = new Logica2048();
        initialize();
        actualizarTablero(); // Actualizar tablero al iniciar la interfaz
        validarSiPartidaFinalizada();
        
        

        
        
        /*if (game.tableroLleno()) {
        	System.out.println("WARNING: TABLERO LLENO");
        	game.combinacionesPosibles();
        	if (game.validarPartidaPerdida()) {
        		System.out.println("XXXXXXXXXXX-------------JUEGO TERMINADO: PERDISTE---------XXXXXXXXXXXXX");
        	}else {
        		System.out.println("*********Todavia hay movimientos**********");
        	}
            // Realizar acciones cuando el tablero está lleno
            // Por ejemplo, mostrar un mensaje de fin de juego
        }
        */
        
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 600); // Ajustar tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        
        
        
        
        
        
        
        JPanel boardPanel = new JPanel();
        boardPanel.setBounds(10, 10, 400, 400);
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Layout para organizar las etiquetas
        frame.getContentPane().add(boardPanel);
        
        
        
        
        
        

        grafo = new JLabel[4][4];
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j] = new JLabel();
                grafo[i][j].setOpaque(true);
                grafo[i][j].setBackground(Color.WHITE);
                grafo[i][j].setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto
                grafo[i][j].setFont(new Font("Arial", Font.BOLD, 24)); // Tamaño de la fuente
                boardPanel.add(grafo[i][j]);
            }
        }

       
        
        JLabel mensajeEnPantalla = new JLabel("Mueva una tecla para iniciar la partida");
        mensajeEnPantalla.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mensajeEnPantalla.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeEnPantalla.setBounds(10, 421, 400, 26);
        frame.getContentPane().add(mensajeEnPantalla);
        
        JButton startButton = new JButton("Reiniciar juego");
        startButton.setBounds(144, 458, 150, 30);
        startButton.addActionListener(e -> {
            game.resetGame(); 
            actualizarTablero(); 
            partidaFinalizada = false; 
            partidaGanada = false; 
            mensajeEnPantalla.setText("Mueva una tecla para iniciar la partida");

            frame.requestFocus(); 
        });
        frame.getContentPane().add(startButton);
        
        JButton exitButton = new JButton("Salir del juego");
        exitButton.setBounds(144, 499, 150, 30);
        exitButton.addActionListener(e -> {
        	System.exit(0);//salgo del juego
        });
        frame.getContentPane().add(exitButton);
        

        
        JLabel mensajeEnPantalla_1 = new JLabel("Alcanzar Nro:");
        mensajeEnPantalla_1.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeEnPantalla_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        mensajeEnPantalla_1.setBounds(10, 499, 87, 26);
        frame.getContentPane().add(mensajeEnPantalla_1);
        
        JLabel mensajeEnPantalla_Num = new JLabel(String.valueOf(game.getValorGanador()));
        mensajeEnPantalla_Num.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeEnPantalla_Num.setFont(new Font("Tahoma", Font.PLAIN, 12));
        mensajeEnPantalla_Num.setBounds(92, 499, 42, 26);
        frame.getContentPane().add(mensajeEnPantalla_Num);
        
        
        
        JButton btnElegirDificultad = new JButton("Elegir dificultad");
        btnElegirDificultad.setBounds(10, 458, 124, 30);
        btnElegirDificultad.addActionListener(e -> {
        	String input = JOptionPane.showInputDialog(frame, "Ingrese el numero que se deberá alcanzar.");
        	
        	int numero = Integer.parseInt(input);
        	
        	game.updateValorGanador(numero);
        	mensajeEnPantalla_Num.setText(String.valueOf(game.getValorGanador()));
        	
        	frame.requestFocus();// soluciono problema con botones ya que sin esto no me reconoce las teclas y no puedo continuar //comentario para informe
        	
        });
        frame.getContentPane().add(btnElegirDificultad);
        
        JButton btnSugerirJugada = new JButton("Sugerir jugada");
        btnSugerirJugada.setBounds(304, 458, 150, 30);
        btnSugerirJugada.addActionListener(e -> {
        	//System.exit(0);//salgo del juego
        	recomendarJugada(game.obtenerTablero());
        	frame.requestFocus();
        });
        frame.getContentPane().add(btnSugerirJugada);
        
        
        
        
        

     // Agregar KeyListener para manejar las pulsaciones de teclas
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // No es necesario implementar este método
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!partidaFinalizada) { // Solo permitir movimientos si la partida no ha finalizado
                    int key = e.getKeyCode();
                    switch (key) {
                        case KeyEvent.VK_UP:
                            game.move(Logica2048.Direccion.UP);
                            break;
                        case KeyEvent.VK_DOWN:
                            game.move(Logica2048.Direccion.DOWN);
                            break;
                        case KeyEvent.VK_LEFT:
                            game.move(Logica2048.Direccion.LEFT);
                            break;
                        case KeyEvent.VK_RIGHT:
                            game.move(Logica2048.Direccion.RIGHT);
                            break;
                    }
                    actualizarTablero(); // Actualizar tablero después de mover
                    validarSiPartidaFinalizada(); // Validar si la partida ha finalizado

                    // Verificar si el jugador ha ganado después del movimiento
                    if (partidaGanada) {
                        partidaFinalizada = true; // Establecer partida finalizada si se ha ganado
                        mensajeEnPantalla.setText("¡Felicidades! Has ganado.");
                        mensajeEnPantalla.setForeground(Color.GREEN.darker()); // Color de texto verde oscuro para indicar victoria
                    } else if (partidaFinalizada) {
                        mensajeEnPantalla.setText("¡Juego terminado! Gracias por jugar.");
                        mensajeEnPantalla.setForeground(Color.RED); // Color de texto rojo para indicar derrota
                    } else {
                        mensajeEnPantalla.setText("Partida en curso");
                        mensajeEnPantalla.setForeground(Color.BLACK); // Color de texto negro para partida en curso
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No es necesario implementar este método
            }
        });
        

        frame.setFocusable(true); // Permitir que la ventana tenga el foco para recibir eventos de teclado
        
    }

    private void actualizarTablero() {
        int[][] tablero = game.obtenerTablero();
        //boolean recomendacionHecha = false; 
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j].setText(tablero[i][j] == 0 ? "" : String.valueOf(tablero[i][j]));
                grafo[i][j].setBackground(Color.WHITE); 
            }
        }
        //recomendarJugada(tablero);
    }

    private boolean recomendacionHecha = false; 

	private void recomendarJugada(int[][] tablero) {
		if (!recomendacionHecha) {
	        for (int i = 0; i < grafo.length; i++) {
	            for (int j = 0; j < grafo[i].length; j++) {
	            	// Ignorar bloques valor 0
	            	if (tablero[i][j] == 0) continue;

	            	if (i > 0) {
	            		// Buscar index del primer bloque arriba
	            		int aux = i - 1;
	            		while (aux >= 0) {
	            			if (tablero[aux][j] != 0) break;
	            			else aux--;
	            		}
	            		// Si index != -1 y los bloques son iguales
	            		if (aux >= 0 && tablero[aux][j] == tablero[i][j]) {
	            			grafo[i][j].setBackground(Color.RED);
	            			grafo[aux][j].setBackground(Color.RED);
	            			recomendacionHecha = true;
	            			return;
	                    }
	            	}

	            	// Buscar index del primer bloque abajo
	                if (i < grafo.length - 1) {
	            		int aux = i + 1;
	            		while (aux < grafo.length) {
	            			if (tablero[aux][j] != 0) break;
	            			else aux++;
	            		}
	            		// si el index != grafo.length - 1 y los bloques son iguales
	            		if (aux < grafo.length && tablero[aux][j] == tablero[i][j]) {
		            		grafo[i][j].setBackground(Color.RED);
		            		grafo[aux][j].setBackground(Color.RED);
		                    recomendacionHecha = true;
		                    return;
	                    }
	                 }
	                
	                // Buscar index del primer bloque a la izquierda
	                if (j > 0) {
		            		int aux = j - 1;
		            		while (aux >= 0) {
		            			if (tablero[i][aux] != 0) break;
		            			aux--;
		            		}
		            		// si index != -1 y los bloques son iguales
		            		if (aux >= 0 && tablero[i][aux] == tablero[i][j]) {
			            		grafo[i][j].setBackground(Color.RED);
			            		grafo[i][aux].setBackground(Color.RED);
			                    recomendacionHecha = true;
			                    return;
		                    }
	                 }
	                 // Buscar index del primer bloque a la derecha
	                 if (j < grafo[0].length - 1) {
		            		int aux = j + 1;
		            		while (aux < grafo[0].length) {
		            			if (tablero[i][aux] != 0) break;
		            			aux++;
		            		}
		            		// Si index != grafo[0].length - 1 y los bloques son iguales
		            		if (aux < grafo[0].length && tablero[i][aux] == tablero[i][j]) {
		            			grafo[i][j].setBackground(Color.RED);
		            			grafo[i][aux].setBackground(Color.RED);
		            			recomendacionHecha = true;
		            			return;
		                    }
	                 }
	            }
	        }
		}
	}
 
    
    public void validarSiPartidaFinalizada() {
        partidaGanada = game.getGanoPartida();
    	partidaFinalizada =  game.getPerdioPartida();
        //"¡Juego terminado! Gracias por jugar.");

        // Cerrar la aplicación
        //System.exit(0);
    }
}