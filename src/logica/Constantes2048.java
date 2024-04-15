package logica;

import java.awt.Color;

/**
 * Constantes predeterminadas del juego 2048.
 */
public final class Constantes2048 {

	public final static int TABLERO_ALTO = 4; // 4
	public final static int TABLERO_ANCHO = 4; // 4

	public final static int CANTIDAD_INICIAL_FICHAS = 2; // 2
	public final static int CANTIDAD_NUEVAS_FICHAS_TRAS_JUGADA = 1;
	public final static int VALOR_OBJETIVO = 2048; // 2048

	public final static Color COLOR_MARCO = new Color(173, 159, 148);

	public final static Color[] COLORES_SEGUN_VALOR = new Color[] {
			new Color(192, 178, 165), 	// vacío 0
			new Color(238, 228, 218), 	// 2
			new Color(237, 224, 200), 	// 4
			new Color(242, 177, 121), 	// 8
			new Color(245, 149, 99), 	// 16
			new Color(246, 124, 95), 	// 32
			new Color(246, 94, 59), 	// 64
			new Color(237, 207, 114), 	// 128
			new Color(237, 204, 97), 	// 256
			new Color(237, 200, 80), 	// 512
			new Color(237, 197, 63), 	// 1024
			new Color(237, 194, 46), 	// 2048
			new Color(60, 58, 50) 		// mayor a 2048
	};

}
