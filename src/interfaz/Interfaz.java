package interfaz;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import logica.Logica2048;


//import javax.swing.Timer; // Importar Timer de javax.swing



public class Interfaz {
    private JFrame frame;
    private MarcadorInterfaz marcador;
    private JLabel[][] grafo;
    private Logica2048 game;
    /*ver si los puedo usar despues para animacion mas suave
    private Timer temporizador;
    private int posicionActualX, posicionObjetivoX;
    private final int duracionAnimacion = 500; // Duración de la animación en milisegundos
    */
    private boolean partidaFinalizada;
    private boolean partidaGanada;
    private int puntaje;

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
        puntaje = 0;
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
        frame.setBounds(100, 100, 500, 650); // Ajustar tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        marcador = new MarcadorInterfaz();
        marcador.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	marcador.setVisible(false); // Oculta el frame en lugar de cerrarlo
            	frame.requestFocus();
            }
        });
        
        
        
        
        
        JPanel boardPanel = new JPanel();
        boardPanel.setBounds(39, 10, 400, 400);
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

        // Puntaje
        JLabel puntajePantallaTexto = new JLabel("Puntaje: ");
        puntajePantallaTexto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        puntajePantallaTexto.setBounds(20, 458, 50, 14);
        frame.getContentPane().add(puntajePantallaTexto);
        
        JLabel puntajePantalla = new JLabel(String.valueOf(puntaje));
        puntajePantalla.setFont(new Font("Tahoma", Font.PLAIN, 12));
        puntajePantalla.setBounds(88, 458, 46, 14);
        frame.getContentPane().add(puntajePantalla);

        
        JButton startButton = new JButton("Reiniciar juego");
        startButton.setBounds(304, 458, 150, 30);
        startButton.addActionListener(e -> {
            
        	//registrarPuntaje();
        	
        	game.resetGame();

            puntajePantalla.setText("0");
            puntaje = 0;

            actualizarTablero(); 
            partidaFinalizada = false; 
            partidaGanada = false; 
            mensajeEnPantalla.setText("Mueva una tecla para iniciar la partida");

            frame.requestFocus(); 
        });
        frame.getContentPane().add(startButton);
        
        JButton exitButton = new JButton("Salir del juego");
        exitButton.setBounds(225, 539, 150, 30);
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
        btnElegirDificultad.setBounds(170, 498, 124, 30);
        btnElegirDificultad.addActionListener(e -> {
        	String input = JOptionPane.showInputDialog(frame, "Ingrese el numero que se deberá alcanzar.");
        	
        	int numero = Integer.parseInt(input);
        	
        	game.updateValorGanador(numero);
        	mensajeEnPantalla_Num.setText(String.valueOf(game.getValorGanador()));
        	
        	frame.requestFocus();// soluciono problema con botones ya que sin esto no me reconoce las teclas y no puedo continuar //comentario para informe
        	
        });
        frame.getContentPane().add(btnElegirDificultad);
        
        JButton btnSugerirJugada = new JButton("Sugerir jugada");
        btnSugerirJugada.setBounds(170, 458, 124, 30);
        btnSugerirJugada.addActionListener(e -> {
        	//System.exit(0);//salgo del juego
        	recomendarJugada(game.obtenerTablero());
        	frame.requestFocus();
        });
        frame.getContentPane().add(btnSugerirJugada);

        
        JButton btnMarcador = new JButton("Marcador");
        btnMarcador.setBounds(304, 498, 150, 30);
        btnMarcador.addActionListener(e -> {
        	marcador.mostrarEnPantalla();
        });
        frame.getContentPane().add(btnMarcador);


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
                    int puntajeGanado = 0;

                    /*
                     * Se podría hacer directamente puntaje += game.move(...)
                     * y abajo del switch directamente puntajePantalla.setText(puntaje)
                     * pero yo para que no lo haga siempre,
                     * capaz apretas cualquier tecla y lo actualiza igual
                     * fijense, igual termina siendo lo mismo
                     */

                    switch (key) {
                        case KeyEvent.VK_UP:
                        	puntajeGanado = game.move(Logica2048.Direccion.UP);
                            break;
                        case KeyEvent.VK_DOWN:
                        	puntajeGanado = game.move(Logica2048.Direccion.DOWN);
                            break;
                        case KeyEvent.VK_LEFT:
                        	puntajeGanado = game.move(Logica2048.Direccion.LEFT);
                            break;
                        case KeyEvent.VK_RIGHT:
                        	puntajeGanado = game.move(Logica2048.Direccion.RIGHT);
                            break;
                    }

                    actualizarTablero(); // Actualizar tablero después de mover
                    validarSiPartidaFinalizada(); // Validar si la partida ha finalizado

                    if (puntajeGanado > 0) {
                    	puntaje += puntajeGanado;
                    	puntajePantalla.setText(String.valueOf(puntaje));
                    }

                    // Verificar si el jugador ha ganado después del movimiento
                    if (partidaGanada) {
                        partidaFinalizada = true; // Establecer partida finalizada si se ha ganado
                        mensajeEnPantalla.setText("¡Felicidades! Has ganado.");
                        mensajeEnPantalla.setForeground(Color.GREEN.darker()); // Color de texto verde oscuro para indicar victoria
                        registrarPuntaje();
                        // preguntar si quiere volver a jugar
                    } else if (partidaFinalizada) {
                        mensajeEnPantalla.setText("¡Juego terminado! Gracias por jugar.");
                        mensajeEnPantalla.setForeground(Color.RED); // Color de texto rojo para indicar derrota
                        registrarPuntaje();
                        // preguntar si quiere volver a jugar
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
    
    
    
    private String pedirNombre() {
    	return JOptionPane.showInputDialog(frame, "Ingrese su nombre para registrar su puntuación:");
    }
    
    void registrarPuntaje() {
    	String nombre = pedirNombre();
    	int puntuacion = puntaje;
    	marcador.registrarPuntacion(nombre, puntuacion);
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

    private void recomendarJugada(int[][] tablero) {
        resetearColores();
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
            }
        }
    }

    private int contarCombinacionesHorizontales(int[][] tablero) {
        int combinaciones = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] != 0) {
                    int col = j - 1;
                    while (col >= 0 && tablero[i][col] == 0) {
                        col--;
                    }
                    if (col >= 0 && tablero[i][col] == tablero[i][j]) {
                        combinaciones++;
                    }
                }
            }
        }
        return combinaciones;
    }

    private int contarCombinacionesVerticales(int[][] tablero) {
        int combinaciones = 0;
        for (int j = 0; j < tablero[0].length; j++) {
            for (int i = 0; i < tablero.length; i++) {
                if (tablero[i][j] != 0) {
                    int row = i - 1;
                    while (row >= 0 && tablero[row][j] == 0) {
                        row--;
                    }
                    if (row >= 0 && tablero[row][j] == tablero[i][j]) {
                        combinaciones++;
                    }
                }
            }
        }
        return combinaciones;
    }

    private void ejecutarCombinacionesHorizontales(int[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] != 0) {
                    int col = j - 1;
                    while (col >= 0 && tablero[i][col] == 0) {
                        col--;
                    }
                    if (col >= 0 && tablero[i][col] == tablero[i][j]) {
                        marcarCombinacionHorizontal(tablero, i, col, j);
                        return;
                    }
                }
            }
        }
    }

    private void ejecutarCombinacionesVerticales(int[][] tablero) {
        for (int j = 0; j < tablero[0].length; j++) {
            for (int i = 0; i < tablero.length; i++) {
                if (tablero[i][j] != 0) {
                    int row = i - 1;
                    while (row >= 0 && tablero[row][j] == 0) {
                        row--;
                    }
                    if (row >= 0 && tablero[row][j] == tablero[i][j]) {
                        marcarCombinacionVertical(tablero, row, j, i);
                        return;
                    }
                }
            }
        }
    }


    private void marcarCombinacionHorizontal(int[][] tablero, int row, int startCol, int endCol) {
        int value = tablero[row][startCol];
        for (int col = startCol; col <= endCol; col++) {
            if (tablero[row][col] == value) {
                marcarCelda(row, col);
            }
        }
    }

    private void marcarCombinacionVertical(int[][] tablero, int startRow, int col, int endRow) {
        int value = tablero[startRow][col];
        for (int row = startRow; row <= endRow; row++) {
            if (tablero[row][col] == value) {
                marcarCelda(row, col);
            }
        }
    }

    private void marcarCelda(int row, int col) {
        grafo[row][col].setBackground(Color.RED);
    }

    private void resetearColores() {
        for (JLabel[] row : grafo) {
            for (JLabel label : row) {
                label.setBackground(Color.WHITE);
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