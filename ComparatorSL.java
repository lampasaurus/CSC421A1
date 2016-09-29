public class ComparatorSL implements java.util.Comparator<Node> {
	Problem problem;	
	public ComparatorSL(Problem problem) { this.problem = problem; }
	
	public int compare(Node x, Node y) {
		return Double.compare( 	x.path_cost + x.line_cost + problem.h(x.state), 
								y.path_cost + y.line_cost + problem.h(y.state) );		
	}
}