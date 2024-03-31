package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import logica.Logica2048;

import javax.swing.Timer; // Importar Timer de javax.swing



public class Interfaz {
    private JFrame frame;
    private JLabel[][] grafo;
    private Logica2048 game;
    
    private Timer temporizador;
    private int posicionActualX, posicionObjetivoX;
    private final int duracionAnimacion = 500; // Duración de la animación en milisegundos
    private boolean partidaFinalizada;


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

        JButton startButton = new JButton("Iniciar el Juego");
        startButton.setBounds(10, 447, 150, 30);
        startButton.addActionListener(e -> {
            game = new Logica2048(); // Iniciar nuevo juego
            actualizarTablero();
        });
        frame.getContentPane().add(startButton);
        
        JLabel mensajeEnPantalla = new JLabel("PARTIDA EN CURSO");
        mensajeEnPantalla.setBounds(10, 422, 306, 14);
        frame.getContentPane().add(mensajeEnPantalla);
        
        

        // Agregar KeyListener para manejar las pulsaciones de teclas
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // No es necesario implementar este método
            }

            @Override
            public void keyPressed(KeyEvent e) {
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
                validarSiPartidaFinalizada();//valido si la partida finalizo
                System.out.println("El valor de la variable partidaFinalizada es:" + partidaFinalizada);
                
                if (partidaFinalizada == true) {
                	//gameOver();
                	mensajeEnPantalla.setText("¡Juego terminado! Gracias por jugar!!");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        

        frame.setFocusable(true); // Permitir que la ventana tenga el foco para recibir eventos de teclado
        
    }

    private void actualizarTablero() {
        int[][] tablero = game.obtenerTablero();
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j].setText(tablero[i][j] == 0 ? "" : String.valueOf(tablero[i][j]));
            }
        }
    }
    
    
    public void validarSiPartidaFinalizada() {
        // Mostrar mensaje de fin de juego
    	partidaFinalizada =  game.getPerdioPartida();
        //"¡Juego terminado! Gracias por jugar.");

        // Cerrar la aplicación
        //System.exit(0);
    }
}