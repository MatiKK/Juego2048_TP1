package logica;

import java.util.Random;

public class Logica2048 {
    private static final int tamanio = 4; // Tamaño del tablero (4x4)
    private int[][] tablero; // Representación del tablero
    private boolean gameOver; // Indica si el juego ha terminado
    private Random random; // Generador de números aleatorios
    private boolean enMovimiento;

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
        addRandomTile();
        addRandomTile();
        gameOver = false;
    }

    private void addRandomTile() {
        // Añade una nueva ficha (2 o 4) en una posición aleatoria vacía del tablero
        int value = random.nextDouble() < 0.9 ? 2 : 4; // Probabilidad de 90% para 2 y 10% para 4
        int row, col;
        do {
            row = random.nextInt(tamanio);
            col = random.nextInt(tamanio);
        } while (tablero[row][col] != 0); // Busca una posición vacía
        tablero[row][col] = value;
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
                break;
            case DOWN:
            	enMovimiento = moveDown();
                break;
            case LEFT:
            	enMovimiento = moveLeft();
                break;
            case RIGHT:
            	enMovimiento = moveRight();
                break;
        }

        // Si se realizó algún movimiento, añadir una nueva ficha y verificar el estado del juego
        if (enMovimiento) {
            addRandomTile(); // Añadir una nueva ficha aleatoria al final del movimiento
            //gameOver = checkGameOver(); // Verificar si el juego ha terminado después del movimiento
        }
        
        
        
        
        
        
    }
    
    private boolean moveUp() {
        enMovimiento = false;

        // Recorrer cada columna del tablero
        for (int col = 0; col < tamanio; col++) {
            // Combinar y mover las fichas hacia arriba en la columna actual
            for (int row = 1; row < tamanio; row++) {
                if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = row - 1; k >= 0; k--) {
                        if (tablero[k][col] == 0) {
                            // Mover la ficha hacia arriba
                            tablero[k][col] = tablero[k + 1][col];
                            tablero[k + 1][col] = 0;
                            enMovimiento = true;
                        } else if (tablero[k][col] == tablero[k + 1][col]) {
                            // Combinar fichas
                            tablero[k][col] *= 2;
                            tablero[k + 1][col] = 0;
                            enMovimiento = true;
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
                if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = row + 1; k < tamanio; k++) {
                        if (tablero[k][col] == 0) {
                            // Mover la ficha hacia abajo
                            tablero[k][col] = tablero[k - 1][col];
                            tablero[k - 1][col] = 0;
                            enMovimiento = true;
                        } else if (tablero[k][col] == tablero[k - 1][col]) {
                            // Combinar fichas
                            tablero[k][col] *= 2;
                            tablero[k - 1][col] = 0;
                            enMovimiento = true;
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
                if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = col - 1; k >= 0; k--) {
                        if (tablero[row][k] == 0) {
                            // Mover la ficha hacia la izquierda
                            tablero[row][k] = tablero[row][k + 1];
                            tablero[row][k + 1] = 0;
                            enMovimiento = true;
                        } else if (tablero[row][k] == tablero[row][k + 1]) {
                            // Combinar fichas
                            tablero[row][k] *= 2;
                            tablero[row][k + 1] = 0;
                            enMovimiento = true;
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
                if (tablero[row][col] != 0) {
                    // Combinar fichas si es posible
                    for (int k = col + 1; k < tamanio; k++) {
                        if (tablero[row][k] == 0) {
                            // Mover la ficha hacia la derecha
                            tablero[row][k] = tablero[row][k - 1];
                            tablero[row][k - 1] = 0;
                            enMovimiento = true;
                        } else if (tablero[row][k] == tablero[row][k - 1]) {
                            // Combinar fichas
                            tablero[row][k] *= 2;
                            tablero[row][k - 1] = 0;
                            enMovimiento = true;
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
