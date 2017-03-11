import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Node {
	
	public static ArrayList<State> history = new ArrayList<State>();
	
	ArrayList<Node> children = new ArrayList<Node>();
	private State s;
	public boolean expanded = false;
//	Node parent;
	
	public Node(State state){
		s = state;
	}
//	public Node(State state, Node par){
//		s = state;
//		parent = par;
//	}
	
	public Node clone(){
		Node newNode = new Node(new State(this.getState()));
		
		return newNode;
	}
	
	public int greenMiniMaxScore(){  //green tries to acquire max number of controlling tiles
		int score = 0;
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				if (s.getSquareContent(i, j).peekFirst()== null){
					continue;
				}
				if (s.getSquareContent(i, j).peekFirst()){
					score++;
				} else 
					score--;
			}
		}
		return score;
	}
	
	public int redMiniMaxScore(){ //red tries to earn the most points
		int score = s.getRedScore()-s.getGreenScore();
		return score;
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
	
	public boolean hasLegalMove(boolean player){ //use only after expanding
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				if (s.getSquareContent(i, j).peekFirst()== null){
					continue;
				}
				if (s.getSquareContent(i, j).peekFirst() == player){
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<Node> getChildren(boolean player){
		//TODO fix scores
		ArrayList<Node> childNodes = new ArrayList<Node>();
		State 				newState;
		ArrayDeque<Boolean> currStack;
		Node 				nextNode;
		
		expanded = true;
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){  //for each square
				
				currStack = s.getSquareContent(i, j);
				
				if(currStack.peekFirst() == null){
					continue;	//skip square if empty
				}
				
				if (currStack.peekFirst() == player){ 			// if active player controls it
					for(int t_mov=1; t_mov < currStack.size()+1; t_mov++){  //number of tiles to move
						for(int d_mov=1; d_mov < t_mov+1; d_mov++){			//number of spaces to move
							
							//move up
							if (j<7 && !State.outOfBounds(i, j+d_mov)){
								newState = new State(s);
								newState.moveStack(player, i, j, i, j+d_mov, t_mov);
//								if (isValid(newState)){
									nextNode = new Node(newState);
									childNodes.add(nextNode);
//									history.add(newState);									
//								}
							}
							
							//move right
							if (i<7 && !State.outOfBounds(i+d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i+d_mov, j, t_mov);
//								if (isValid(newState)){
									nextNode = new Node(newState);
									childNodes.add(nextNode);
//									history.add(newState);
//								}
							}
							
							//move down
							if (j>0 && !State.outOfBounds(i, j-d_mov)){
								newState = new State(s);
								newState.moveStack(player, i, j, i, j-d_mov, t_mov);
//								if (isValid(newState)){
									nextNode = new Node(newState);
									childNodes.add(nextNode);
//									history.add(newState);
//								}
							}
							
							//move left				
							if (i>0 && !State.outOfBounds(i-d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i-d_mov, j, t_mov);
//								if (isValid(newState)){
									nextNode = new Node(newState);
									childNodes.add(nextNode);
//									history.add(newState);
//								}
							}
						}
					}
				} 
			}
		}
//		System.out.println("adding "+childNodes.size()+" new nodes");		
		return childNodes;
	}
	public int expandNode(boolean player){ //true for green moves, false for red
		State 				newState;
		ArrayDeque<Boolean> currStack;
		Node 				nextNode;
		
		expanded = true;
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
									nextNode = new Node(newState);
									children.add(nextNode);
									history.add(newState);									
								}
							}
							
							//move right
							if (i<7 && !State.outOfBounds(i+d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i+d_mov, j, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState);
									children.add(nextNode);
									history.add(newState);
								}
							}
							
							//move down
							if (j>0 && !State.outOfBounds(i, j-d_mov)){
								newState = new State(s);
								newState.moveStack(player, i, j, i, j-d_mov, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState);
									children.add(nextNode);
									history.add(newState);
								}
							}
							
							//move left				
							if (i>0 && !State.outOfBounds(i-d_mov, j)){
								newState = new State(s);
								newState.moveStack(player, i, j, i-d_mov, j, t_mov);
								if (isValid(newState)){
									nextNode = new Node(newState);
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
