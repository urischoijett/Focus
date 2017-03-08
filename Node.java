import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	public static ArrayList<State> history = new ArrayList<State>();
	
	Node parent;
	ArrayList<Node> children = new ArrayList<Node>();
	private State s;
	
	
	public Node(State state){
		s = state;
	}
	public Node(State state, Node par){
		s = state;
		parent = par;
	}
	
	public State getState(){
		return s;
	}
	
	private boolean isValid(State testState){
		for (int i=0; i<Node.history.size(); i++){
			if (State.stateEquals(testState, Node.history.get(i))){
				return false;
			}
		}
		return true;	
	}
	public boolean hasLegalMove(){ //use only after expanding
		if (children.size() == 0){
			return false;
		}
		return true;
	}
	public int expandNode(boolean player){ //true for green moves, false for red
		State 				newState;
		ArrayDeque<Boolean> currStack;
		Node 				nextNode;
		
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){  //for each square
				
				currStack = s.getSquareContent(i, j);
				
				if(currStack.peekFirst() == null){
					continue;	//skip square if empty
				}
				
				if (currStack.peekFirst() == player){ // if active player controls it
					for(int t_mov=1; t_mov < currStack.size()+1; t_mov++){  //number of tiles to move
						for(int d_mov=1; d_mov < t_mov+1; d_mov++){			//number of spaces to move
							
							//move up
							if (j<7 && !State.outOfBounds(i, j+d_mov)){
								newState = new State(s);
								newState.moveStack(player, i, j, i, j+d_mov, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState, this);
									children.add(nextNode);
									history.add(newState);									
								}
							}
							
							//move right
							if (i<7 && !State.outOfBounds(i+d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i+d_mov, j, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState, this);
									children.add(nextNode);
									history.add(newState);
								}
							}
							
							//move down
							if (j>0 && !State.outOfBounds(i, j-d_mov)){
								newState = new State(s);
								newState.moveStack(player, i, j, i, j-d_mov, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState, this);
									children.add(nextNode);
									history.add(newState);
								}
							}
							
							//move left				
							if (i>0 && !State.outOfBounds(i-d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i-d_mov, j, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState, this);
									children.add(nextNode);
									history.add(newState);
								}
							}
						}
					}
				} 
			}
		}
		System.out.println("adding "+children.size()+" new nodes");
		return children.size();
	}
	
	
};
