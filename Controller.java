import java.util.HashMap;

public class Controller {
	public Gui 	 boardGui;
	private Node currNode, testNode;
	int i=0;
	boolean activePlayer = false;
	
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
		boolean activePlayer = true; //green goes first
		Node nextNode;

		while(currNode.hasLegalMove(activePlayer)){
			
			if(activePlayer){
				nextNode = getGreenMove();
			} else {
				nextNode = getRedMove();
			}
			System.out.println("moving " +activePlayer);
			boardGui.setDisplay(nextNode.getState()); //send to gui
			activePlayer = !activePlayer;			  //switch active player
			currNode = nextNode;
		}
		
	}
	
	private Node getGreenMove(Node currState){
		Node newMove;
		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;
		int depth = 2;
		
		HashMap<Integer, Node> bestMove = new HashMap();
		bestMove.putAll(greenMax(currState, alpha, beta, depth));
		
		return newMove;
	}
	//http://people.scs.carleton.ca/~oommen/Courses/COMP4106Winter17/AICh04IntelGamePlaying.pdf
	private HashMap<Integer, Node> greenMax (Node n, int a, int b, int depth){
		
		HashMap<Integer, Node> nodevals = new HashMap();
		int bestScore = Integer.MIN_VALUE;
		Node bestNode, tempNode;
		int tempScore;
		
		if (!n.expanded){
			n.expandNode(true);
		}
		
		if (depth==0 || !n.hasLegalMove(true)){
			nodevals.put(n.greenMiniMaxScore(), n);
			return nodevals;
		}
		
		for (int i=0; i<n.children.size(); i++){
			
		}
		
		
		return nodevals;
	}
	private HashMap<Integer, Node> greenMin (Node n, int a, int b, int depth){
		HashMap<Integer, Node> nodevals = new HashMap();
		return nodevals;
	}
	
	private Node getRedMove(){
		Node newMove = new Node(new State());
		
		
		return newMove;
	}
	
	
	public void testMove(){
		
		Node temp, temp_winner = null;
		currNode.expandNode(activePlayer);
		int max_val=-9999;
		int temp_val;
				
		if (activePlayer) { //greens turn
			for(int i=0; i<currNode.children.size(); i++){
				temp = currNode.children.get(i);
				temp.children.clear();
				temp.expandNode(!activePlayer);
				temp_val = temp.redMinChild();
				if (max_val < temp_val){
					max_val = temp_val;
					temp_winner = temp;
				}
			}
			
		} else {			//reds turn
			for(int i=0; i<currNode.children.size(); i++){
				temp = currNode.children.get(i);
				temp.children.clear();
				temp.expandNode(!activePlayer);
				temp_val = temp.redMinChild();
				if (max_val < temp_val){
					max_val = temp_val;
					temp_winner = temp;
				}
			}	
		}
		currNode = temp_winner;

		boardGui.setDisplay(currNode.getState()); //send to gui
		activePlayer = !activePlayer;			  //switch active player
		
		
		if (activePlayer){
			System.out.println("No legal moves left for green");
		} else {
			System.out.println("No legal moves left for red");
		}
	}
};
