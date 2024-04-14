package interfaz;


import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * {@code TwoOptionsChooser} es una clase con la que puedes
 * obligar al usuario a elegir entre dos opciones dadas.
 * <p>
 * Esta clase despliega por pantalla un {@link JDialog}
 * que detiene el flujo de ejecución hasta que se hayan elegido
 * alguna de los dos opciones presentadas en botones.
 */
public class TwoOptionsChooser extends JDialog {

	/**
	 * Launch the application.
	 * borrar luego
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

	private static final long serialVersionUID = -738248705569194188L;

	/**
	 * Opción default TRUE
	 */
	private final static String YES = "Sí";
	/**
	 * Opción default FALSE
	 */
	private final static String NO = "No";

	private boolean elegidaOpcion1;

	/**
	 * Crea un {@code TwoOptionsChooser} en base a la pregunta dada
	 * y las dos posibles respuestas propuestas.
	 * @param question pregunta que se le va a hacer al usuario
	 * @param op1 primera opción posible
	 * @param op2 segunda opción posible
	 */
	public TwoOptionsChooser(String question, String op1, String op2) {
		initialize(question, op1, op2);
		setVisible(true);
	}

	/**
	 * Crea un {@code TwoOptionsChooser} en base a una preguntá lógica,
	 * la cual se debe responder con SÍ o NO.
	 * @param question pregunta que se le va a hacer al usuario
	 */
	public static TwoOptionsChooser preguntaLogica(String logicQuestion) {
		return new TwoOptionsChooser(logicQuestion, YES, NO);
	}

	/**
	 * Le hace al usuario una pregunta lógica que debe responder con SÍ o NO.
	 * Este método devuelve un booleano de acuerdo de si eligió que SÍ.
	 * @param preguntaLogica pregunta lógica que se le va a hacer al usuario
	 * @return {@code true} si el usuario eligió que SI, {@code false} si eligió NO
	 */
	public static boolean responderPreguntaLogica(String preguntaLogica) {
		return preguntaLogica(preguntaLogica).opcionElegida();
	}

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
	 * @return {@code true} si el usuario eligió la primera opción,
	 * {@code false} si eligió la segunda
	 */
	public boolean opcionElegida() {
		return elegidaOpcion1;
	}
}