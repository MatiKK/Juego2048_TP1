package interfaz;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MarcadorInterfaz extends JFrame {

	private JLabel labelPuntuaciones;
	private LinkedList<Registro> registros;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarcadorInterfaz window = new MarcadorInterfaz();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MarcadorInterfaz() {
		initialize();
	}

	public void mostrarEnPantalla() {
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(false);

		registros = new LinkedList<Registro>();

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

	void registrarPuntacion(String nombre, int puntaje){
		Registro nuevoRegistro = new Registro(nombre, puntaje);
		registros.add(nuevoRegistro);
		
		if (registros.size() == 1) {
			labelPuntuaciones.setText(nuevoRegistro.toString());
		} else {
			labelPuntuaciones.setText(
					labelPuntuaciones.getText() + "\n" + nuevoRegistro.toString()
					);
		}

	}
}
