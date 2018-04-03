package solveequation;

public abstract class EqSolver {
	public String pattern;

	public EqSolver(String pattern){
		this.pattern = pattern;
	}
	public boolean mathesForm(String input){
		return input.matches(pattern);
	}

	abstract String solve(String input);
}





