package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MarcadorInterfaz {

	private JFrame frame;
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
					window.frame.setVisible(true);
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
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);

		registros = new LinkedList<Registro>();


		
		JButton btnNewButton = new JButton("Volver al juego");
		btnNewButton.setBounds(321, 11, 103, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
		labelPuntuaciones = new JLabel("Aún no hay puntuaciones registradas.");
		labelPuntuaciones.setVerticalAlignment(SwingConstants.TOP);
		labelPuntuaciones.setBounds(10, 48, 401, 213);
		frame.getContentPane().add(labelPuntuaciones);
		
		JLabel marcadorTitulo = new JLabel("MARCADOR DE PUNTUACIONES");
		marcadorTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		marcadorTitulo.setBounds(10, 11, 301, 18);
		frame.getContentPane().add(marcadorTitulo);
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
