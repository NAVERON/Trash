package solveequation;

public class ABCEqSolver extends EqSolver{

	public ABCEqSolver() {
		super("[0-9]+x [\\+\\-] [0-9]+ = [0-9]+");
	}

	@Override
	String solve(String input) {

		String[] tokens = input.split(" ");
		String output;

		String op = tokens[1];
		int a = Integer.parseInt( tokens[0].substring(0, tokens[0].length()-1) );
		int b = Integer.parseInt(tokens[2]);
		int c = Integer.parseInt(tokens[4]);
		if(a == 0){
			output = "anything";
			return output;
		}
		if(op.equals("-")){
			b = -b;
		}
		int result = c - b;
		output = result + "/" + a;

		if(result%a == 0){  //如果结果是整数，显示整数结果，否则显示除式
			output = String.valueOf(result/a);
		}
		return output;
	}

}





