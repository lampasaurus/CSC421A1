import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search {
	Problem problem;
	
	public Search(Problem problem) { this.problem = problem; }
	
	//Tree-search methods
	public String BreadthFirstTreeSearch() {
		return TreeSearch(new FrontierFIFO());
	}
	
	public String DepthFirstTreeSearch() {
		return TreeSearch(new FrontierLIFO());
	}
	
	public String UniformCostTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}
	
	//Graph-search methods
	public String BreadthFirstGraphSearch() {
		return GraphSearch(new FrontierFIFO());
	}
	
	public String DepthFirstGraphSearch() {
		return GraphSearch(new FrontierLIFO());
	}
	
	public String UniformCostGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}
	
	private String DepthLimitedTreeSearch(Frontier frontier, int depth) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		frontier.insert( initialNode );
		while(true) {
			if(frontier.isEmpty()) return null;
			Node node = frontier.remove();
			if( problem.goal_test(node.state)) return Solution(node);
			if(node.depth < depth)	{
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}
	
	private String DepthLimitedGraphSearch(Frontier frontier, int depth) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			if(frontier.isEmpty())
				return null;
			Node node = frontier.remove();
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			if( !explored.contains(node.state) && (node.depth < depth)) {
				explored.add(node.state);
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}
	//Iterative deepening, tree-search and graph-search
	int LIMIT = 10000;
	public String IterativeDeepeningTreeSearch() {
		String result = null;
		Frontier frontier = new FrontierLIFO();
		for(int n = 0; n < LIMIT; n++){
			result = DepthLimitedTreeSearch(frontier, n);
			if(result != null) return result;
		}
		return null;
	}
	
	public String IterativeDeepeningGraphSearch() {
		String result = null;
		Frontier frontier = new FrontierLIFO();
		for(int n = 0; n < LIMIT; n++){
			result = DepthLimitedGraphSearch(frontier, n);
			if(result != null) return result;
		}
		return null;
	}
	
	public String SLHeuristicGraphSearch(){
		FrontierPriorityQueue frontier = new FrontierPriorityQueue(new ComparatorSL(problem));
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			if( !explored.contains(node.state) ) {
				explored.add(node.state);
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}
	public String SLHeuristicTreeSearch(){
		FrontierPriorityQueue frontier = new FrontierPriorityQueue(new ComparatorSL(problem));
		cnt = 0;
		node_list = new ArrayList<Node>();
		initialNode = MakeNode(problem.initialState);
		frontier.insert(initialNode);
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			frontier.insertAll(Expand(node,problem));
			cnt++;
		}
	}
	//For statistics purposes
	int cnt; //count expansions
	List<Node> node_list; //store all nodes ever generated
	Node initialNode; //initial node based on initial state
	//
	
	private String TreeSearch(Frontier frontier) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			frontier.insertAll(Expand(node,problem));
			cnt++;
		}
	}

	private String GraphSearch(Frontier frontier) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			if( !explored.contains(node.state) ) {
				explored.add(node.state);
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}
	
	private String TreeSearchDepthLimited(Frontier frontier, int limit) {
		return DepthLimitedTreeSearch(frontier, limit);
	}

	private String GraphSearchDepthLimited(Frontier frontier, int limit) {
		return DepthLimitedGraphSearch(frontier, limit);	
	}

	private Node MakeNode(Object state) {
		Node node = new Node();
		node.state = state;
		node.parent_node = null;
		node.path_cost = 0;
		node.line_cost = 0;
		node.depth = 0;
		if(node.state == "Arad") node.line_cost = 366;
		if(node.state == "Burcharest") node.line_cost = 0;
		if(node.state == "Craiova") node.line_cost = 160;
		if(node.state == "Drobeta") node.line_cost = 242;
		if(node.state == "Eforie") node.line_cost = 161;
		if(node.state == "Fagaras") node.line_cost = 176;
		if(node.state == "Giurgiu") node.line_cost = 77;
		if(node.state == "Hirsova") node.line_cost = 151;
		if(node.state == "Iasi") node.line_cost = 226;
		if(node.state == "Lugoj") node.line_cost = 244;
		if(node.state == "Mehadia") node.line_cost = 241;
		if(node.state == "Neamt") node.line_cost = 234;
		if(node.state == "Oradea") node.line_cost = 380;
		if(node.state == "Pitesti") node.line_cost = 100;
		if(node.state == "Rimnicu") node.line_cost = 193;
		if(node.state == "Sibiu") node.line_cost = 253;
		if(node.state == "Timisoara") node.line_cost = 329;
		if(node.state == "Urziceni") node.line_cost = 80;
		if(node.state == "Vaslui") node.line_cost = 199;
		if(node.state == "Zerind") node.line_cost = 374;
		return node;
	}
	
	private Set<Node> Expand(Node node, Problem problem) {
		node.order = cnt;
		
		Set<Node> successors = new HashSet<Node>(); //empty set
		Set<Object> successor_states = problem.getSuccessors(node.state);
		
		for(Object result : successor_states) {
			Node s = new Node();
			s.state = result;
			s.parent_node = node;
			s.path_cost = node.path_cost + problem.step_cost(node.state, result); 
			s.depth = node.depth + 1; 
			successors.add(s);
			
			node_list.add( s );
		}
		
		return successors;
	}
	
	//Create a string to print solution. 
	private String Solution(Node node) {
		
		String solution_str = "(cost=" + node.path_cost + ", expansions=" + cnt + ")\t";
		
		Deque<Object> solution = new ArrayDeque<Object>();
		do {
			solution.push(node.state);
			node = node.parent_node;
		} while(node != null);
		
		while(!solution.isEmpty())
			solution_str += solution.pop() + " ";
		
		return solution_str;
	}
}
