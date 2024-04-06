package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.Logica2048;

public class Interfaz {
    private JFrame frame;
    private JLabel[][] grafo;
    private Logica2048 game;
    private boolean partidaFinalizada;
    private boolean partidaGanada;
    private JLabel dificultadLabel;
    
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

    public Interfaz() {
        initialize();
        startNewGame();
        dificultadLabel = new JLabel("Dificultad: "); 
        dificultadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dificultadLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dificultadLabel.setBounds(10, 529, 400, 26);
        frame.getContentPane().add(dificultadLabel);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel boardPanel = new JPanel();
        boardPanel.setBounds(10, 10, 400, 400);
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5));
        frame.getContentPane().add(boardPanel);

        grafo = new JLabel[4][4];
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j] = new JLabel();
                grafo[i][j].setOpaque(true);
                grafo[i][j].setBackground(Color.WHITE);
                grafo[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                grafo[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                boardPanel.add(grafo[i][j]);
            }
        }

        JButton startButton = new JButton("Reiniciar juego");
        startButton.setBounds(144, 458, 150, 30);
        startButton.addActionListener(e -> startNewGame());
        frame.getContentPane().add(startButton);

        JLabel mensajeEnPantalla = new JLabel("Mueva una tecla para iniciar la partida");
        mensajeEnPantalla.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mensajeEnPantalla.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeEnPantalla.setBounds(10, 421, 400, 26);
        frame.getContentPane().add(mensajeEnPantalla);

        JButton exitButton = new JButton("Salir del juego");
        exitButton.setBounds(144, 499, 150, 30);
        exitButton.addActionListener(e -> System.exit(0));
        frame.getContentPane().add(exitButton);

        JButton btnElegirDificultad = new JButton("Elegir dificultad");
        btnElegirDificultad.setBounds(10, 458, 124, 30);
        btnElegirDificultad.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Ingrese el número que se deberá alcanzar.");
            int numero = Integer.parseInt(input);
            game.updateValorGanador(numero);
            actualizarDificultadLabel(); 
            frame.requestFocus();  
        });
        frame.getContentPane().add(btnElegirDificultad);

        JButton btnSugerirJugada = new JButton("Sugerir jugada");
        btnSugerirJugada.setBounds(304, 458, 150, 30);
        btnSugerirJugada.addActionListener(e -> {
            recomendarJugada(game.obtenerTablero());
            frame.requestFocus();
        });
        frame.getContentPane().add(btnSugerirJugada);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (!partidaFinalizada) {
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
                    actualizarTablero();
                    validarSiPartidaFinalizada();
                    if (partidaFinalizada) {
                        if (partidaGanada) {
                            mensajeEnPantalla.setText("¡Ganaste! ¡Juego terminado! Gracias por jugar!!");
                            mensajeEnPantalla.setForeground(Color.GREEN.darker());
                        } else {
                            mensajeEnPantalla.setText("Perdiste :( ¡Juego terminado! Gracias por jugar!!");
                            mensajeEnPantalla.setForeground(Color.RED);
                        }
                        frame.setFocusable(false);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        frame.setFocusable(true);
    }

    private void startNewGame() {
        game = new Logica2048();
        actualizarTablero();
        partidaFinalizada = false;
        partidaGanada = false;
        frame.requestFocus();
        frame.setFocusable(true);
        actualizarDificultadLabel(); 
    }


    private void actualizarTablero() {
        int[][] tablero = game.obtenerTablero();
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j].setText(tablero[i][j] == 0 ? "" : String.valueOf(tablero[i][j]));
                grafo[i][j].setBackground(Color.WHITE); 
            }
        }
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

    
    private void validarSiPartidaFinalizada() {
        partidaGanada = game.getGanoPartida();
        partidaFinalizada = game.getPerdioPartida() || partidaGanada;
    }
    
    private void actualizarDificultadLabel() {
        if (dificultadLabel != null) {
            dificultadLabel.setText("Dificultad: " + game.getValorGanador());
        }
    }
}
