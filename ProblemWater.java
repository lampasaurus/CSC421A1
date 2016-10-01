import java.util.*;
public class ProblemWater{
	
	public static void main(String[] args) throws Exception{
		solver s = new solver();
		s.solve();
	}
}



class solver{
	int MAXDEPTH = 8;
	
	
	public  String solve(){
		int[][] initialstate = new int[MAXDEPTH][4];
		for(int n = 0; n < MAXDEPTH; n++){
			initialstate[n][0] = 0;
			initialstate[n][1] = 0;
			initialstate[n][2] = 0;
			initialstate[n][3] = 0;
		}
		int[][] result = recursiveSolve(initialstate, initialstate[0], 0);
		//return ("Cost = "+getCost(result) + "\nPath = "+ Arrays.deepToString(result));
		return null;
	}
	public  int[][] recursiveSolve(int[][] path, int[] newNode, int depth){
		//System.out.println("Depth = "+depth);
		//System.out.print("     Current = "+newNode[0]+" "+newNode[1]+" "+newNode[2]);
		//System.out.println(" "+ Arrays.deepToString(path));
		//If a solution isn't found by MAXDEPTH
		if(depth >= MAXDEPTH -1){
			//System.out.println("MAXDEPTH");
			path[depth][3] = 9999999;
			return path;
		}
		//if move isn't valid
		if(newNode[0] > 12 || newNode[1] > 8 || newNode[2] > 3 ||
		newNode[0] < 0 || newNode[1] < 0 || newNode[2] < 0){
			//System.out.println("ERROR" + Arrays.toString(path[depth]));
			path[depth][3] = 9999999;
			return path;
		}
		//Add the new node to the path
		path[depth] = Arrays.copyOf(newNode, 4);
		//System.out.println("Added"+Arrays.toString(path[depth])); 
		
		//If solution, return path
		if(path[depth][0] + path[depth][1] + path[depth][2] == 1){
			if(getCost(path) > 34) System.out.println("Cost = " + getCost(path) + "    :" +Arrays.deepToString(path) );
			return path;
		}
		
		int[][] best = new int[MAXDEPTH][4];
		best[depth][3] = newNode[3] + 12;
		int[][] tester = new int[MAXDEPTH][4];
		//System.out.println("Path = " + Arrays.toString(path[depth]));
		//System.out.println("New Node = " + Arrays.toString(path[depth]));
		int[] insert = new int[4];
		int temp;
		
		//Do valid moves
		//Tap bucket 1
		
		insert[0] = 12;
		insert[1] = newNode[1];
		insert[2] = newNode[2];
		insert[3] = newNode[3] + 12 - newNode[0];
		tester = recursiveSolve(path, insert, depth+1);
		
		if(getCost(best) >= getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			best = tester;
		}
		//tap bucket 2
		insert[0] = newNode[0];
		insert[1] = 8;
		insert[2] = newNode[2];
		insert[3] = newNode[3] + 8 - newNode[1];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//tap bucket 3
		insert[0] = newNode[0];
		insert[1] = newNode[1];
		insert[2] = 3;
		insert[3] = newNode[3] + 3 - newNode[2];
		tester = recursiveSolve(path, insert, depth+1);
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//System.out.println("Insert = " + Arrays.toString(insert));
		//empty bucket 3 onto the ground
		insert[0] = newNode[0];
		insert[1] = newNode[1];
		insert[2] = 0;
		insert[3] = newNode[3] + newNode[2];
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		tester = recursiveSolve(path, insert, depth+1);
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
	
		//empty bucket 2 onto the ground
		insert[0] = newNode[0];
		insert[1] = 0;
		insert[2] = newNode[2];
		insert[3] = newNode[3] + newNode[1];
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		tester = recursiveSolve(path, insert, depth+1);
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		
		//empty bucket 1 onto the ground
		insert[0] = 0;
		insert[1] = newNode[1];
		insert[2] = newNode[2];
		insert[3] = newNode[3] + newNode[0];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//Fill bucket 2 from Bucket 1 
		insert[0] = newNode[0] - 8 + newNode[1];
		insert[1] = 8;
		insert[2] = newNode[2];
		insert[3] = newNode[3] +  8 - newNode[1];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//fill bucket 3 from bucket 1
		insert[0] = newNode[0] - 3 + newNode[2];
		insert[1] = newNode[1];
		insert[2] = 3;
		insert[3] = newNode[3] + 3 - newNode[2];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//fill bucket 3 from bucket 2
		insert[0] = newNode[0];
		insert[1] = newNode[1] - 3 + newNode[2];
		insert[2] = 3;
		insert[3] = newNode[3] + 3 - newNode[2];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		//Do nothing
		insert[0] = insert[1] = insert[2] = 0;
		insert[3] = newNode[3];
		tester = recursiveSolve(path, insert, depth+1);
		
		//System.out.println("Insert = " + Arrays.toString(insert));
		if(getCost(best) > getCost(tester)){
			for (int i = 0; i < 3; i++) {
				best[i] = Arrays.copyOf(tester[i], tester[i].length);
			}
		}
		return best;
	}
	int getCost(int[][] path){
		int n = 0;
		int cost = 0;
		while(n < MAXDEPTH){
			if (path[n][3] > cost && path[n][3] != 9999999)cost = path[n][3];
			n++;
		}
		return cost;
	}
}