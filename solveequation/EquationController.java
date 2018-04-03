package solveequation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EquationController{

	@FXML
	private TextField input;
	@FXML
	private Label result;

	public void solved(){
		EqSolver solver = new ABCEqSolver();
		String get = input.getText();

		String out = solver.mathesForm(get)? solver.solve(get) : "no solver";
		result.setText(get + "\nx = " + out);
		input.setText("");
	}
}
