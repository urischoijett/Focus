import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Controller {
	public Gui 	 boardGui;
	private Node currNode, testNode;
	boolean activePlayer = true;
	
	public Controller(Gui g){
		boardGui = g;
		g.ctrl = this;
		
		init();
	}
	
	
	public void init(){
		currNode = new Node(new State());
		currNode.getState().randomStart();
//		currNode.getState().sampleStart();
		boardGui.setDisplay(currNode.getState());
//		currNode.expandNode(false);

	}
	public void playGame(){
//		boolean activePlayer = true; //green goes first
		Node nextNode;

		while(currNode.hasLegalMove(activePlayer)){
			
			if(activePlayer){
				nextNode = getGreenMove(currNode);
			} else {
				nextNode = getRedMove(currNode);
			}
			System.out.println("moving " +activePlayer);
			boardGui.setDisplay(nextNode.getState()); //send to gui
			activePlayer = !activePlayer;			  //switch active player
			currNode = nextNode;
		}
		
	}
	public void testMove(){
		Node nextNode;
		if (currNode.hasLegalMove(activePlayer)){
			if(activePlayer){
				nextNode = getGreenMove(currNode);
			} else {
				nextNode = getRedMove(currNode);
				
			}
			boardGui.setDisplay(nextNode.getState()); //send to gui
			activePlayer = !activePlayer;			  //switch active player
			currNode = nextNode;
		} else {
			System.out.print("Game over, winner is ");
			if (currNode.getState().getGreenScore()> currNode.getState().getRedScore()){
				System.out.print("green player");
			} else {
				System.out.print("red player");
			}
		}
		
	}
	
	private Node getGreenMove(Node currState){
		Node newMove;
		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;
		int depth = 2;
		int bestScore = Integer.MIN_VALUE;
		
		ArrayList<Node> futures = currState.getChildren(true);
		HashMap<Integer, Node> minimaxMoves = new HashMap<Integer, Node>(); // how to generate hashmap with minimax key values
		
		for(int i=0; i<futures.size(); i++){
			minimaxMoves.put(greenMin(futures.get(i), alpha, beta, depth), futures.get(i));
		}
		
		bestScore = Collections.max(minimaxMoves.keySet());
		
//		Iterator<Integer> itr = minimaxMoves.keySet().iterator();
//		while (itr.hasNext()){
//			if () 
//		}
		//select random move with top score?
		newMove = minimaxMoves.get(bestScore);
		
		return newMove;
	}
	//http://people.scs.carleton.ca/~oommen/Courses/COMP4106Winter17/AICh04IntelGamePlaying.pdf
	private int greenMax (Node n, int a, int b, int depth){
		int bestScore = Integer.MIN_VALUE;
		
		ArrayList<Node> futures = n.getChildren(true);
		
		if (depth==0 || !n.hasLegalMove(true)){ // cutoff conditions
			return n.greenMiniMaxScore();
		}
		for (int i=0; i<futures.size(); i++){
			bestScore = Integer.max(bestScore, greenMin(futures.get(i), a, b, depth-1));
			if (bestScore >= b){
				return bestScore;
			}
			a = Integer.max(a, bestScore);
		}
		
		return bestScore;
	}
	private int greenMin (Node n, int a, int b, int depth){
		int bestScore = Integer.MAX_VALUE;
		ArrayList<Node> futures = n.getChildren(true);
		
		if (depth==0 || !n.hasLegalMove(false)){
			return n.greenMiniMaxScore();
		}
		
		for (int i=0; i<futures.size(); i++){
			bestScore = Integer.min(bestScore, greenMax(futures.get(i), a, b, depth-1));
			if (bestScore <= a){
				return bestScore;
			}
			b = Integer.min(b, bestScore);
		}
		return bestScore;
	}
	
	private Node getRedMove(Node currState){
		Node newMove;
		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;
		int depth = 0;
		int bestScore = Integer.MIN_VALUE;
		
		ArrayList<Node> futures = currState.getChildren(false);
		HashMap<Integer, Node> minimaxMoves = new HashMap(); // how to generate hashmap with minimax key values
		
		for(int i=0; i<futures.size(); i++){
			minimaxMoves.put(redMin(futures.get(i), alpha, beta, depth), futures.get(i));
		}
		
		bestScore = Collections.max(minimaxMoves.keySet());
		
//		Iterator<Integer> itr = minimaxMoves.keySet().iterator();
//		while (itr.hasNext()){
//			if () 
//		}
		
		newMove = minimaxMoves.get(bestScore);
		
		return newMove;
	}
	//http://people.scs.carleton.ca/~oommen/Courses/COMP4106Winter17/AICh04IntelGamePlaying.pdf
	private int redMax (Node n, int a, int b, int depth){
		int bestScore = Integer.MIN_VALUE;
		
		ArrayList<Node> futures = n.getChildren(false);
		
		if (depth==0 || !n.hasLegalMove(true)){ // cutoff conditions
			return n.redMiniMaxScore();
		}
		for (int i=0; i<futures.size(); i++){
			bestScore = Integer.max(bestScore, redMin(futures.get(i), a, b, depth-1));
			if (bestScore >= b){
				return bestScore;
			}
			a = Integer.max(a, bestScore);
		}
		
		return bestScore;
	}
	private int redMin (Node n, int a, int b, int depth){
		int bestScore = Integer.MAX_VALUE;
		ArrayList<Node> futures = n.getChildren(true);
		
		if (depth==0 || !n.hasLegalMove(true)){
			return n.redMiniMaxScore();
		}
		
		for (int i=0; i<futures.size(); i++){
			bestScore = Integer.min(bestScore, redMax(futures.get(i), a, b, depth-1));
			if (bestScore <= a){
				return bestScore;
			}
			b = Integer.min(b, bestScore);
		}
		return bestScore;
	}
	
};
