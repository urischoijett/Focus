import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	public static ArrayList<State> history = new ArrayList<State>();
	
	Node parent;
	Node[] children;
	private State s;
	
	
	public Node(State state){
		s = state;
	}
	
	public State getState(){
		return s;
	}
	
	private boolean isValid(State testState){
		for (int i=0; i<Node.history.size(); i++){
			if (State.equals(testState, Node.history.get(i))){
				return false;
			}
		}
		return true;	
	}
	
	public void expandNode(){
		
		
		
	}
	
	
};
