
public class Controller {
	public Gui 	 boardGui;
	private Node currNode;
	
	public Controller(Gui g){
		boardGui = g;
		g.ctrl = this;
		
		init();
	}
	
	
	public void init(){
		currNode = new Node(new State());
		currNode.getState().randomStart();
		boardGui.setDisplay(currNode.getState());
	}
	
	public void testMove(){
		currNode.getState().moveStack(true, 2, 2, 2, 3, 1);
		boardGui.setDisplay(currNode.getState());
	}
};
