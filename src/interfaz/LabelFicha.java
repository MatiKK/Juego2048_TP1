package interfaz;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import logica.Ficha;
import logica.Constantes2048;

/**
 * Clase que representa un JLabel con una {@link Ficha} con
 * la que se va a relacionar para actualizar valores del Label tales
 * como texto y color.
 */
@SuppressWarnings("serial")
public class LabelFicha extends JLabel {


	private static final Color COLOR_SUGERENCIA = Color.RED;
	private static final Color COLOR_MARCO = Constantes2048.COLOR_MARCO;
	private static Color[] COLORES = Constantes2048.COLORES_SEGUN_VALOR;

	private Ficha ficha;
	private int valorActualEnPantalla;
	
	public LabelFicha(Ficha f) {
		super();
		ficha = f;
		valorActualEnPantalla = ficha.obtenerValor();
		actualizarGrafica();
	}

	public int valorDeFicha() {
		return valorActualEnPantalla;
	}

	public void chequearValores() {
		int valorFicha = ficha.obtenerValor();
		if (valorFicha != valorActualEnPantalla) {
			valorActualEnPantalla = valorFicha;
			actualizarGrafica();
		}
	}

	private void actualizarGrafica(){
		actualizarColorEnBaseAlValor();
		actualizarTextoValor();
	}

	private void actualizarColorEnBaseAlValor() {
		int index;
		if (valorActualEnPantalla == 0) index = 0;
		else if (valorActualEnPantalla > 2048) index = 12;
		// Tomar log2(valorActualEnPantalla)
		else index = (int) (Math.log10(valorActualEnPantalla) / Math.log10(2));

		// Cambiar color de fondo de la ficha
		setBackground(COLORES[index]);
		// Cambiar color del número de la ficha
		setForeground(index < 3 ? Color.BLACK : Color.WHITE);
	}

	private void actualizarTextoValor() {
		setText(String.valueOf(valorActualEnPantalla > 0 ? valorActualEnPantalla : ""));
	}

	private boolean esSugerencia = false;

	public boolean esSugerencia() {
		return esSugerencia;
	}

	public void marcarComoSugerencia() {
		setBorder(new LineBorder(COLOR_SUGERENCIA));
		esSugerencia = true;
	}

	public void desmarcarComoSugerencia() {
		setBorder(new LineBorder(COLOR_MARCO));
		esSugerencia = false;
	}

}
