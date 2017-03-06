
public class Controller {
	public Gui 	 boardGui;
	private Node currNode, testNode;
	
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
	}
	
	public void testMove(){
		currNode.getState().moveStack(true, 2, 2, 2, 3, 1);
		boardGui.setDisplay(currNode.getState());
		
		testNode = new Node(new State());
		testNode.getState().sampleStart();
		State.stateEquals(testNode.getState(), currNode.getState());
		
	}
};
