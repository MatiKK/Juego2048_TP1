package interfaz;


import java.awt.EventQueue;
import javax.swing.JFrame;

import java.util.Comparator;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MarcadorInterfaz extends JFrame {

	private JLabel labelPuntuaciones;
	private TreeSet<Registro> registros;

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

	void registrarPuntacion(String nombre, int puntaje){
		Registro nuevoRegistro = new Registro(nombre, puntaje);
		registros.add(nuevoRegistro);

		StringBuilder sb = new StringBuilder();

//		Si está ordenado de menor a mayor
//		java.util.Iterator<Registro> iteratorRegistros = registros.descendingIterator();
//		while (iteratorRegistros.hasNext())
//			System.out.println(iteratorRegistros.next());

//		Si está ordenado de mayor a menor
		sb.append("<html>");
		for (Registro r: registros) {
			sb.append(r).append("<br>");
			System.out.println(r);
		}
		sb.append("</html>");
		labelPuntuaciones.setText(sb.toString());
		// Prefiero usar otra forma, como una jtable o algo más pasable
		// Pero esto es inicial
	}
}
