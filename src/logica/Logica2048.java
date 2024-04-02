package logica;

import java.util.Random;

public class Logica2048 {
    private static int tamanio = 4; // Tamaño del tablero (4x4)
    private int[][] tablero; // Representación del tablero
    private boolean gameOver; // Indica si el juego ha terminado
    private Random random; // Generador de números aleatorios
    private boolean enMovimiento;
    private boolean tableroLleno;//flag para validar si el tablero esta lleno
    
    private boolean perdioPartida; //inicializa en false por defecto
    private boolean ganoPartida; //inicializa en false por defecto
    private int valorGanador;
    

    // Enumeración para las direcciones posibles de movimiento
    public enum Direccion {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    
    public Logica2048() {
        tablero = new int[tamanio][tamanio];
        random = new Random();
        agregarFichaRandom();
        agregarFichaRandom();
        gameOver = false;
        tableroLleno = false;
        valorGanador = 64;
    }
    
    public void updateValorGanador(int valor) {
    	valorGanador = valor;
    }
    
    public int getValorGanador() {
    	return valorGanador;
    }
    
    public boolean getGanoPartida() {
    	return ganoPartida;
    }
    
    public boolean getPerdioPartida(){
    	return perdioPartida;
    }
    
    public void validarPartidaPerdida() {//valido si la partida esta perdida
    	if (tableroLleno()) {
    		if (combinacionesPosibles()) {
    			System.out.println("################-------no quedan combinaciones posibles-----------");
    			perdioPartida = true;
    			System.out.println("Se actualizo variable perdioPartida: " + perdioPartida);
    		}else {
    			System.out.println("XXXXXXXXXXXXX---- valide y la partida continua HAY COMBINACIONES-----");
    			perdioPartida = false;
    		}
    	}else {
        	System.out.println("XXXXXXXXXXXXX----valide y la partida continua TABLERO CON LUGAR-----");
    		perdioPartida = false;
    	}

    }
    
    
    public boolean combinacionesPosibles() {
        // Chequeo todo el tablero
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio - 1; j++) {
                if (tablero[i][j] == tablero[i][j + 1] || tablero[j][i] == tablero[j + 1][i]) {
                    return false; // Hay al menos una combinación posible
                }
            }
        }
        return true; // No hay combinaciones posibles
    }
    
    public boolean tableroLleno() {
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if (tablero[i][j] == 0) {
                	tableroLleno = false;
                    return false; // El tablero tiene espacios vacios
                }
            }
        }
        tableroLleno = true;
        return true; // El tablero está lleno
    }
    
    
    public boolean busquedaPiezaGanadora() {
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if (tablero[i][j] == valorGanador) {
                	ganoPartida = true;
                    return true; // gano partida
                }
            }
        }
        ganoPartida = false;
        return false; // sigue sin ganar
    }
    
    

    private void agregarFichaRandom() {
    	if (!tableroLleno) {
    		// Añade una nueva ficha (2) en una posición aleatoria vacía del tablero
            //int value = 2; //random.nextDouble() < 0.9 ? 2 : 4; // Probabilidad de 90% para 2 y 10% para 4
            int value = random.nextDouble () < 0.9 ? 2 : 4;
            int row, col;
            do {
                row = random.nextInt(tamanio);
                col = random.nextInt(tamanio);
            } while (tablero[row][col] != 0); // Busca una posición vacía
            tablero[row][col] = value;
    	}else {
    		System.out.println("El tablero esta lleno no puedo agregar mas fichas");
    	}
        
    }

    public boolean getMove() {
    	return enMovimiento;
    }
    
    public void move(Direccion direccion) {
        // Variable para verificar si se realizó algún movimiento
    	enMovimiento = false;

        // Aplicar lógica de movimiento según la dirección especificada
        switch (direccion) {
            case UP:
            	enMovimiento = moveUp();
            	busquedaPiezaGanadora();
            	validarPartidaPerdida();
                break;
            case DOWN:
            	enMovimiento = moveDown();
            	busquedaPiezaGanadora();
            	validarPartidaPerdida();
                break;
            case LEFT:
            	enMovimiento = moveLeft();
            	busquedaPiezaGanadora();
            	validarPartidaPerdida();
                break;
            case RIGHT:
            	enMovimiento = moveRight();
            	busquedaPiezaGanadora();
            	validarPartidaPerdida();
                break;
        }

        // Si se realizó algún movimiento, añadir una nueva ficha y verificar el estado del juego
        if (enMovimiento) {
            agregarFichaRandom(); // Añadir una nueva ficha aleatoria al final del movimiento
            //gameOver = checkGameOver(); // Verificar si el juego ha terminado después del movimiento
        }
        
        
        
        
        
        
    }
    
    private boolean moveUp() {
        enMovimiento = false;

        // Recorrer cada columna del tablero
        for (int col = 0; col < tamanio; col++) {
            // Combinar y mover las fichas hacia arriba en la columna actual
            for (int row = 1; row < tamanio; row++) {
            	System.out.println("valido fila: " + row + " y columna: " + col + " cuenta con una ficha" + " valor: " + tablero[row][col]);
            	if (tablero[row][col] != 0) {
                	
                    // Combinar fichas si es posible
                    for (int k = row - 1; k >= 0; k--) {
                        if (tablero[k][col] == 0) {//TENGO LUGAR ARRIBA
                        	System.out.println("------------------------");
                        	System.out.println(k + "-" + col + " esta vacia" + " y como arriba esta vacio muevo ficha:" + tablero[k+1][col] +  " fila:" + (k+1) + " col:" + col + " a: " + "fila:" + k + " col:" + col);
                            // Mover la ficha hacia arriba
                            tablero[k][col] = tablero[k + 1][col];
                            
                            System.out.println("dejo: " + (k+1) + "-" +col + " vacia");
                            tablero[k + 1][col] = 0;
                            enMovimiento = true;
                        } else if (tablero[k][col] == tablero[k + 1][col]) {
                            // Combinar fichas
                        	System.out.println("como fila: " + k + " columna: " + col + " y fila: " + (k+1) + " columna: " + col + " tienen misma valor, las sumo");
                            tablero[k][col] *= 2;
                            tablero[k + 1][col] = 0;
                            enMovimiento = true;
                            System.out.println("obteniendo valor: " + tablero[k][col]);
                            break;
                        } else {
                            // No se puede combinar, salir del bucle
                            break;
                        }
                    }
                }
            }
        }

        return enMovimiento;
    }

    private boolean moveDown() {
    	enMovimiento = false;

        // Recorrer cada columna del tablero
        for (int col = 0; col < tamanio; col++) {
            // Combinar y mover las fichas hacia abajo en la columna actual
            for (int row = tamanio - 2; row >= 0; row--) {
            	System.out.println("valido fila: " + row + " y columna: " + col + " cuenta con una ficha" + " valor: " + tablero[row][col]);
            	if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = row + 1; k < tamanio; k++) {
                        if (tablero[k][col] == 0) { //ABAJO ESTA VACIA
                            // Mover la ficha hacia abajo
                        	System.out.println("------------------------");
                        	System.out.println(k + "-" + col + " esta vacia" + " y como abajo esta vacio muevo ficha:" + tablero[k-1][col] +  " fila:" + (k-1) + " col:" + col + " a: " + "fila:" + k + " col:" + col);
                            
                            tablero[k][col] = tablero[k - 1][col];
                            tablero[k - 1][col] = 0;
                            enMovimiento = true;
                        } else if (tablero[k][col] == tablero[k - 1][col]) {
                            // Combinar fichas
                        	System.out.println("como fila: " + k + " columna: " + col + " y fila: " + (k-1) + " columna: " + col + " tienen el mismo valor, las sumo");
                            tablero[k][col] *= 2;
                            tablero[k - 1][col] = 0;
                            enMovimiento = true;
                            System.out.println("obteniendo valor: " + tablero[k][col]);
                            break;
                        } else {
                            // No se puede combinar, salir del bucle
                            break;
                        }
                    }
                }
            }
        }

        return enMovimiento;
    }

    private boolean moveLeft() {
        boolean enMovimiento = false;

        // Recorrer cada fila del tablero
        for (int row = 0; row < tamanio; row++) {
            // Combinar y mover las fichas hacia la izquierda en la fila actual
            for (int col = 1; col < tamanio; col++) {
            	System.out.println("valido fila: " + row + " y columna: " + col + " cuenta con una ficha" + " valor: " + tablero[row][col]);
                if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = col - 1; k >= 0; k--) {
                        if (tablero[row][k] == 0) {//EL LUGAR IZQUIERDO ESTA VACIO
                            // Mover la ficha hacia la izquierda
                        	System.out.println("------------------------");
                        	System.out.println(row + "-" + k + " esta vacia" + " y como a la izquierda esta vacio muevo ficha:" + tablero[row][k+1] +  " fila:" + row + " col:" + (k+1) + " a: " + "fila:" + row + " col:" + k);                            
                            tablero[row][k] = tablero[row][k + 1];
                            tablero[row][k + 1] = 0;
                            enMovimiento = true;
                        } else if (tablero[row][k] == tablero[row][k + 1]) {
                            // Combinar fichas
                        	System.out.println("como fila: " + row + " columna: " + k + " y fila: " + (row) + " columna: " + (k+1) + " tienen el mismo valor, las sumo");
                            
                        	tablero[row][k] *= 2;
                            tablero[row][k + 1] = 0;
                            enMovimiento = true;
                            System.out.println("obteniendo valor: " + tablero[row][k]);                            
                            break;
                        } else {
                            // No se puede combinar, salir del bucle
                            break;
                        }
                    }
                }
            }
        }

        return enMovimiento;
    }

    private boolean moveRight() {
        boolean enMovimiento = false;

        // Recorrer cada fila del tablero
        for (int row = 0; row < tamanio; row++) {
            // Combinar y mover las fichas hacia la derecha en la fila actual
            for (int col = tamanio - 2; col >= 0; col--) {
            	System.out.println("valido fila: " + row + " y columna: " + col + " cuenta con una ficha" + " valor: " + tablero[row][col]);
            	if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = col + 1; k < tamanio; k++) {
                        if (tablero[row][k] == 0) {// Ficha de la derecha vacia
                            // Mover la ficha hacia la derecha                            
                        	System.out.println("------------------------");
                        	System.out.println(row + "-" + k + " esta vacia" + " y como a la derecha esta vacio muevo ficha:" + tablero[row][k-1] +  " fila:" + row + " col:" + (k-1) + " a: " + "fila:" + row + " col:" + k);                            
                            tablero[row][k] = tablero[row][k - 1];
                            tablero[row][k - 1] = 0;
                            enMovimiento = true;
                        } else if (tablero[row][k] == tablero[row][k - 1]) {
                            // Combinar fichas
                        	System.out.println("como fila: " + row + " columna: " + (k-1) + " y fila: " + (row) + " columna: " + (k) + " tienen el mismo valor, las sumo");
                        	tablero[row][k] *= 2;
                            tablero[row][k - 1] = 0;
                            enMovimiento = true;
                            System.out.println("obteniendo valor: " + tablero[row][k]);
                            break;
                        } else {
                            // No se puede combinar, salir del bucle
                            break;
                        }
                    }
                }
            }
        }

        return enMovimiento;
    }



    public boolean isGameOver() {
        return gameOver;
    }
    
    public int[][] obtenerTablero() {
        return tablero;
    }
    

    // Métodos adicionales para obtener información sobre el tablero, puntaje, etc.
}
