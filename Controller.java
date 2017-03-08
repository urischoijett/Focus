
public class Controller {
	public Gui 	 boardGui;
	private Node currNode, testNode;
	int i=0;
	
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
		
		while (currNode.expandNode(activePlayer) > 0){ //expand node for active player and make sure it has available moves
			//select a move to make
			
			//make it
			
			
			
			
			
			boardGui.setDisplay(currNode.getState()); //send to gui
			activePlayer = !activePlayer;			  //switch active player
		}
		
		if (activePlayer){
			System.out.println("No legal moves left for green");
		} else {
			System.out.println("No legal moves left for red");
		}
		
	}
	public void testMove(){
		
		boardGui.setDisplay(currNode.children.get(i).getState());
		i++;
	}
};
