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

        JButton startButton = new JButton("Reiniciar game");
        startButton.setBounds(144, 458, 150, 30);
        startButton.addActionListener(e -> {
            game = new Logica2048(); // Iniciar nuevo juego
            actualizarTablero();
            grafo = new JLabel[4][4];
            frame.requestFocus();//SIGUE SIN FUNCIONAR VALIDAR!!
        	
        });
        frame.getContentPane().add(startButton);
        
        JLabel mensajeEnPantalla = new JLabel("Mueva una tecla para iniciar la partida");
        mensajeEnPantalla.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mensajeEnPantalla.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeEnPantalla.setBounds(10, 421, 400, 26);
        frame.getContentPane().add(mensajeEnPantalla);
        
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
                	mensajeEnPantalla.setText("Perdiste :( ¡Juego terminado! Gracias por jugar!!");
                	mensajeEnPantalla.setForeground(Color.red);
                }else if (partidaGanada == true) {
                	mensajeEnPantalla.setText("Ganaste :D ¡Juego terminado! Gracias por jugar!!");
                	mensajeEnPantalla.setForeground(Color.GREEN.darker());
                }else {
                	mensajeEnPantalla.setText("Partida en curso");
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
        boolean recomendacionHecha = false; 
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                grafo[i][j].setText(tablero[i][j] == 0 ? "" : String.valueOf(tablero[i][j]));
                grafo[i][j].setBackground(Color.WHITE); 
            }
        }      
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo[i].length; j++) {
                if (!recomendacionHecha && tablero[i][j] != 0) {
                    if ((i > 0 && tablero[i - 1][j] == tablero[i][j]) || 
                        (i < grafo.length - 1 && tablero[i + 1][j] == tablero[i][j]) || 
                        (j > 0 && tablero[i][j - 1] == tablero[i][j]) || 
                        (j < grafo[i].length - 1 && tablero[i][j + 1] == tablero[i][j])) { 
                        grafo[i][j].setBackground(Color.RED);
                        if (i > 0 && tablero[i - 1][j] == tablero[i][j]) {
                            grafo[i - 1][j].setBackground(Color.RED);
                        }
                        if (i < grafo.length - 1 && tablero[i + 1][j] == tablero[i][j]) {
                            grafo[i + 1][j].setBackground(Color.RED);
                        }
                        if (j > 0 && tablero[i][j - 1] == tablero[i][j]) {
                            grafo[i][j - 1].setBackground(Color.RED);
                        }
                        if (j < grafo[i].length - 1 && tablero[i][j + 1] == tablero[i][j]) {
                            grafo[i][j + 1].setBackground(Color.RED);
                        }
                        recomendacionHecha = true; 
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