package calculator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Calculator extends JFrame implements ActionListener, MyUnicodeConstants, MyColors, PropertyChangeListener {
	private static final int LABELBREITE = 353;
	private static final float HISTORY_FONT_SIZE = 16f, DISPLAY_FONT_SIZE = 50f;
	private static final String COMMA_DEFINITION = ".";
	private JPanel pnlTasten, pnlDisplay;
	private JLabel lblHistory, lblDisplay;
	private Taste btnMC, btnMR, btnC, btnCE;
	private Double op1, op2, memory = 0.0;
	private String operand1 = "", operator = "", operand2 = "";
	private Marker marker;

	private enum Marker {
		PLUS, MINUS, TIMES, DIVIDE, NOTHING;
	}

	public static void main(String[] args) {
		new Calculator();
	}

	public Calculator() {
		initComponents();
		initFrame();
		this.setVisible(true);
	}

	private void initComponents() {
		this.setTitle("Calculator by 0xflotus - V0.0.2beta");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(360, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		pnlDisplay = new JPanel();
		pnlDisplay.setBounds(0, 0, LABELBREITE, 250);
		pnlDisplay.setLayout(null);
		this.add(pnlDisplay);
		lblHistory = new JLabel("History");
		lblHistory.setBounds(0, 0, LABELBREITE, 50);
		lblHistory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHistory.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHistory.setBackground(HISTORY_COLOR);
		lblHistory.setOpaque(true);
		lblHistory.setFont(lblHistory.getFont().deriveFont(HISTORY_FONT_SIZE));
		pnlDisplay.add(lblHistory);
		lblDisplay = new JLabel("Display");
		lblDisplay.setBounds(0, 50, LABELBREITE, 200);
		lblDisplay.setOpaque(true);
		lblDisplay.setBackground(DISPLAY_COLOR);
		lblDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDisplay.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDisplay.setFont(lblDisplay.getFont().deriveFont(DISPLAY_FONT_SIZE));
		lblDisplay.addPropertyChangeListener(this);
		pnlDisplay.add(lblDisplay);
		pnlTasten = new JPanel();
		pnlTasten.setBounds(0, 200, 360, 450);
		pnlTasten.setLayout(null);
		this.add(pnlTasten);
		btnMC = new Taste("MC", "mem", 0, 0, 1, 1, this);
		btnMR = new Taste("MR", "mem", 1, 0, 1, 1, this);
		pnlTasten.add(btnMC);
		pnlTasten.add(btnMR);
		pnlTasten.add(new Taste("MS", "mem", 2, 0, 1, 1, this));
		pnlTasten.add(new Taste("M+", "mem", 3, 0, 1, 1, this));
		pnlTasten.add(new Taste("M-", "mem", 4, 0, 1, 1, this));
		pnlTasten.add(new Taste(WURZEL, "unary", 0, 1, 1, 1, this));
		pnlTasten.add(new Taste(PIZEICHEN, "static", 1, 1, 1, 1, this));
		pnlTasten.add(new Taste("x" + HOCH2, "unary", 2, 1, 1, 1, this));
		pnlTasten.add(new Taste("x" + HOCH3, "unary", 3, 1, 1, 1, this));
		pnlTasten.add(new Taste("1/x", "unary", 4, 1, 1, 1, this));
		pnlTasten.add(new Taste(PFEILNACHLINKS, "del", 4, 2, 1, 1, this));
		btnC = new Taste("C", "clear", 4, 3, 1, 1, this);
		btnCE = new Taste("CE", "clearAll", 4, 4, 1, 1, this);
		pnlTasten.add(btnC);
		pnlTasten.add(btnCE);
		pnlTasten.add(new Taste("=", "result", 4, 5, 1, 1, this));
		pnlTasten.add(new Taste("0", "digit", 1, 5, 1, 1, this));
		pnlTasten.add(new Taste("1", "digit", 0, 4, 1, 1, this));
		pnlTasten.add(new Taste("2", "digit", 1, 4, 1, 1, this));
		pnlTasten.add(new Taste("3", "digit", 2, 4, 1, 1, this));
		pnlTasten.add(new Taste("4", "digit", 0, 3, 1, 1, this));
		pnlTasten.add(new Taste("5", "digit", 1, 3, 1, 1, this));
		pnlTasten.add(new Taste("6", "digit", 2, 3, 1, 1, this));
		pnlTasten.add(new Taste("7", "digit", 0, 2, 1, 1, this));
		pnlTasten.add(new Taste("8", "digit", 1, 2, 1, 1, this));
		pnlTasten.add(new Taste("9", "digit", 2, 2, 1, 1, this));
		pnlTasten.add(new Taste("+", "operator", 3, 2, 1, 1, this));
		pnlTasten.add(new Taste("-", "operator", 3, 3, 1, 1, this));
		pnlTasten.add(new Taste(MALZEICHEN, "operator", 3, 4, 1, 1, this));
		pnlTasten.add(new Taste(DIVISIONSZEICHEN, "operator", 3, 5, 1, 1, this));
		pnlTasten.add(new Taste(PLUSMINUS, "plusminus", 0, 5, 1, 1, this));
		pnlTasten.add(new Taste(",", "comma", 2, 5, 1, 1, this));
	}

	private void initFrame() {
		setEnableAlleTasten(true);
		lblDisplay.setForeground(NORMAL_COLOR);
		lblHistory.setText("");
		lblDisplay.setText("0");
		btnMR.setEnabled(false);
		btnMC.setEnabled(false);
		memory = 0.0;
		marker = Marker.NOTHING;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		switch (b.getName()) {
		case "mem":
			memoryFunc(b);
			break;
		case "digit":
			digitFunc(b);
			break;
		case "unary":
			unaryFunc(b);
			break;
		case "static":
			staticFunc(b);
			break;
		case "operator":
			operatorFunc(b);
			break;
		case "clear":
			initFrame();
			break;
		case "del":
			deleteLastDigit();
			break;
		case "clearAll":
			reset();
			break;
		case "result":
			giveResult();
			break;
		case "plusminus":
			changePrefix();
			break;
		case "comma":
			setComma();
			break;
		default:
			System.out.println("Keine gueltige Taste");
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (lblDisplay.getText()) {
		case "Infinity":
			lblDisplay.setText(UNENDLICH);
			lblDisplay.setForeground(WARNING_COLOR);
			setEnableAlleTasten(false);
			btnC.setEnabled(true);
			btnCE.setEnabled(true);
			break;
		case "NaN":
			lblDisplay.setForeground(WARNING_COLOR);
			break;
		}
	}

	private void memoryFunc(JButton b) {
		Double d = Double.parseDouble(lblDisplay.getText());
		switch (b.getText()) {
		case "MS":
			btnMC.setEnabled(true);
			btnMR.setEnabled(true);
			memory = Double.parseDouble(lblDisplay.getText());
			break;
		case "MR":
			showDisplay(String.valueOf(memory), true);
			break;
		case "MC":
			memory = 0.0;
			btnMC.setEnabled(false);
			btnMR.setEnabled(false);
			break;
		case "M+":
			showDisplay(String.valueOf(Double.sum(memory, d)), true);
			break;
		case "M-":
			showDisplay(String.valueOf(Double.sum(memory, (-1 * d))), true);
			break;
		default:
			System.out.println("Es wurde keine memTaste gedrueckt.");
		}
	}

	private void digitFunc(JButton b) {
		String value = lblDisplay.getText().equals("0") ? b.getText() : lblDisplay.getText() + b.getText();
		showDisplay(value);
	}

	private void unaryFunc(JButton b) {
		Double d = Double.parseDouble(lblDisplay.getText());
		switch (b.getText()) {
		case WURZEL:
			lblHistory.setText(WURZEL + lblDisplay.getText());
			showDisplay(String.format("%.9f", Math.sqrt(d)));
			break;
		case "x" + HOCH2:
			lblHistory.setText(lblDisplay.getText() + HOCH2);
			showDisplay(String.valueOf(Math.pow(d, 2)), true);
			break;
		case "x" + HOCH3:
			lblHistory.setText(lblDisplay.getText() + HOCH3);
			showDisplay(String.valueOf(Math.pow(d, 3)), true);
			break;
		case "1/x":
			lblHistory.setText("1/" + lblDisplay.getText());
			showDisplay(String.valueOf(1 / d), true);
			break;
		default:
			System.out.println("Keine unaryTaste gedrueckt.");
		}
	}

	private void staticFunc(JButton b) {
		if (b.getText().equals(PIZEICHEN)) {
			lblHistory.setText(PIZEICHEN);
			showDisplay(String.format("%.9f", Math.PI));
		}
	}

	private void operatorFunc(JButton b) {
		String operationszeichen = b.getText();
		this.operand1 = lblDisplay.getText();
		this.op1 = Double.parseDouble(operand1);
		switch (operationszeichen) {
		case "+":
			compute(Marker.PLUS, "+");
			break;
		case "-":
			compute(Marker.MINUS, "-");
			break;
		case MALZEICHEN:
			compute(Marker.TIMES, MALZEICHEN);
			break;
		case DIVISIONSZEICHEN:
			compute(Marker.DIVIDE, DIVISIONSZEICHEN);
			break;
		default:
			System.out.println("Unbekannte mathematische Funktion");
		}
		operand2 = "";
		op2 = 0.0;
		aktualisiereHistory();
	}

	private void compute(Marker rechenart, String zeichen) {
		this.marker = rechenart;
		this.operator = zeichen;
		showDisplay("0", true);
	}

	private void reset() {
		lblDisplay.setForeground(NORMAL_COLOR);
		showDisplay("0", true);
	}

	private void deleteLastDigit() {
		String value = lblDisplay.getText().length() > 1
				? lblDisplay.getText().substring(0, lblDisplay.getText().length() - 1) : "0";
		showDisplay(value, true);
	}

	private void giveResult() {
		this.operand2 = lblDisplay.getText();
		this.op2 = Double.parseDouble(operand2);
		Double d = 0.0;
		switch (marker) {
		case PLUS:
			d = op1 + op2;
			break;
		case MINUS:
			d = op1 - op2;
			break;
		case TIMES:
			d = op1 * op2;
			break;
		case DIVIDE:
			d = op1 / op2;
			break;
		default:
			System.out.println("Keine gueltige Rechenart");
		}
		aktualisiereHistory();
		showDisplay(String.valueOf(d), true);
	}

	private void changePrefix() {
		showDisplay(String.valueOf(Double.parseDouble(lblDisplay.getText()) * -1), true);
	}

	private void setComma() {
		if (!lblDisplay.getText().contains(COMMA_DEFINITION))
			showDisplay(lblDisplay.getText() + COMMA_DEFINITION);
	}

	private void setEnableAlleTasten(boolean enabled) {
		for (Component t : pnlTasten.getComponents())
			if (t instanceof Taste)
				t.setEnabled(enabled);
	}

	private void aktualisiereHistory() {
		lblHistory.setText(operand1 + operator + operand2);
	}

	private void showDisplay(String text, boolean gesondert) {
		String resVal = text.replaceAll(",", ".");
		if (gesondert) {
			resVal = resVal.equals("-") ? "0" : resVal;
			double d = Double.parseDouble(resVal);
			resVal = d == (int) d ? String.valueOf((int) d) : String.valueOf(d);
			lblDisplay.setText(resVal);
		} else {
			lblDisplay.setText(resVal);
		}
	}

	private void showDisplay(String text) {
		showDisplay(text, false);
	}
}
