package interfaz;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TwoOptionsChooser extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Test
				String qu = "Elige una de las opciones";
				String o1 = "Opción 1";
				String o2 = "Opción 2";
				TwoOptionsChooser window = new TwoOptionsChooser(qu, o1, o2);

				boolean x = window.opcionElegida();
				System.out.println("Elegiste " + ((x) ? o1 : o2));
			}
		});
	}

	private final static String YES = "Sí";
	private final static String NO = "No";

	private boolean elegidaOpcion1;

	/**
	 * Create the application.
	 */
	public TwoOptionsChooser(String question, String op1, String op2) {
		initialize(question, op1, op2);
		setVisible(true);
	}

	public static TwoOptionsChooser preguntaLogica(String logicQuestion) {
		return new TwoOptionsChooser(logicQuestion, YES, NO);
	}

	public static boolean responderPreguntaLogica(String preguntaLogica) {
		return preguntaLogica(preguntaLogica).opcionElegida();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String question, String op1, String op2) {

		// Esto hace que el flujo de ejecución se detenga en el juego
		// y no siga hasta que se elija una opción
		setModalityType(DEFAULT_MODALITY_TYPE);

		// Prevenir que se pueda cerrar al ventana apretando la x
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		elegidaOpcion1 = false;

		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(null);

		// Mostrar pregunta
		JLabel lblNewLabel = new JLabel(question);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 264, 14);
		getContentPane().add(lblNewLabel);

		// Opcion 1
		JButton btnOpcion1 = new JButton(op1);
		btnOpcion1.setBounds(23, 47, 89, 23);
		getContentPane().add(btnOpcion1);
		btnOpcion1.addActionListener(e -> {
			elegidaOpcion1 = true;
			dispose();
		});

		// Opcion 2
		JButton btnOpcion2 = new JButton(op2);
		btnOpcion2.setBounds(170, 47, 89, 23);
		getContentPane().add(btnOpcion2);
		btnOpcion2.addActionListener(e -> {
			elegidaOpcion1 = false;
			dispose();
		});
	}

	/**
	 * 
	 * @return {@code true} si se eligió la opción 1 <br>
	 * 		  {@code false} si se eligió la opción 2
	 */
	public boolean opcionElegida() {
		return elegidaOpcion1;
	}
}