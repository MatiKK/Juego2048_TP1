package interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;

import java.util.Comparator;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Esta clase es un {@link JFrame} representa un historial de puntajes,
 * al cual se le pueden agregar registros, que de forma automática se 
 * ordenan de mayor a menor y se insertan en el frame.
 */
public class MarcadorInterfaz extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6088765981757107546L;

	private JLabel labelPuntuaciones;
	private TreeSet<Registro> registros;
	private JFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarcadorInterfaz window = new MarcadorInterfaz(null);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MarcadorInterfaz(JFrame parentComponent) {
		parent = parentComponent;
		initialize();
	}

	public void mostrarEnPantalla() {
		int anchoMarcador = 450;
		int altoMarcador = 300;

		int x = parent == null ? 100 :
			parent.getX() + parent.getWidth() / 2 - anchoMarcador / 2;
		int y = parent == null ? 100 :
			parent.getY() + parent.getHeight() / 2 - altoMarcador / 2;
		setBounds(
				x, y, anchoMarcador, altoMarcador);

		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(false);

		registros = new TreeSet<>(new Comparator<Registro>() {
			public int compare(Registro r1, Registro r2) {
				return r1.compareTo(r2);
			}
		});

		getContentPane().setLayout(null);

		labelPuntuaciones = new JLabel("Aún no hay puntuaciones registradas.");
		labelPuntuaciones.setVerticalAlignment(SwingConstants.TOP);
		labelPuntuaciones.setBounds(10, 48, 401, 213);
		getContentPane().add(labelPuntuaciones);

		JLabel marcadorTitulo = new JLabel("MARCADOR DE PUNTUACIONES");
		marcadorTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		marcadorTitulo.setBounds(10, 11, 301, 18);
		getContentPane().add(marcadorTitulo);
	}

	/**
	 * Registra una nueva puntuación en el marcador
	 * @param nombre nombre del usuario que registrá la puntuación
	 * @param puntaje puntaje obtenido
	 */
	void registrarPuntacion(String nombre, int puntaje) {
		Registro nuevoRegistro = new Registro(nombre, puntaje);
		registros.add(nuevoRegistro);

		StringBuilder sb = new StringBuilder();

		sb.append("<html>");
		for (Registro r : registros) {
			sb.append(r).append("<br>");
		}
		sb.append("</html>");
		labelPuntuaciones.setText(sb.toString());
		// Prefiero usar otra forma, como una jtable o algo más pasable
		// Pero esto es inicial
	}
}
