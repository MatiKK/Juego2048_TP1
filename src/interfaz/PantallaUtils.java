package interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;

public class PantallaUtils {

	private final static Toolkit miPantalla = Toolkit.getDefaultToolkit();
	private final static Dimension dimensionDeMiPantalla = miPantalla.getScreenSize();

	public final static int anchoPantalla = dimensionDeMiPantalla.width;
	public final static int altoPantalla = dimensionDeMiPantalla.height;


}
