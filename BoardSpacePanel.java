import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")

public class BoardSpacePanel extends JPanel{
	
	public static enum options {OUTOFBOUNDS, EMPTY, RED, GREEN};
	public options squareDisplay;
	private ArrayDeque<Boolean> stack= new ArrayDeque<Boolean>();
	
	public BoardSpacePanel(){
		this.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK, Color.BLACK));
		squareDisplay = options.EMPTY;
	
	}
	
	public BoardSpacePanel(int[] xy){
		this.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK, Color.BLACK));
		JLabel address = new JLabel(xy[0]+", "+ xy[1]);
		this.add(address);
	}
	
//	public BoardSpacePanel(options content){ //
//		squareDisplay = content;
//		stack = new ArrayDeque<Boolean>();
//		
//		switch(content) {
//			case OUTOFBOUNDS:
//				squareDisplay = options.OUTOFBOUNDS;
//				this.setBackground(Color.DARK_GRAY);
//				
//			case EMPTY:
//				squareDisplay = options.EMPTY;
//			
//			case RED:
//				squareDisplay = options.RED;
//				stack.add(false);
//				
//			case GREEN:
//				squareDisplay = options.GREEN;
//				stack.add(true);
//		}
//	}
	
	public void updateContent(ArrayDeque<Boolean> c){
		if (c.isEmpty()){
			squareDisplay = options.EMPTY;
		} else if (c.getFirst())  {
			squareDisplay = options.GREEN;
		} else if (!c.getFirst()) {
			squareDisplay = options.RED;
		}
		stack = c.clone();
		updateDisplay();
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Iterator<Boolean> itr = stack.descendingIterator();
		int i=5;
		
		while (itr.hasNext()){
			if (itr.next()){
				g2.setColor(Color.GREEN);
			} else {
				g2.setColor(Color.RED);
			}
			Shape chip = new Ellipse2D.Float(this.getWidth()/2-15, (this.getHeight()/5)*i-12, 30, 10);
			g2.fill(chip);
			i--;
		}
	}
	
	public void setOutofBounds(){
		squareDisplay = options.OUTOFBOUNDS;
		updateDisplay();
	}
	
	private void updateDisplay(){
		//display controlling color
		switch(squareDisplay) {
			case OUTOFBOUNDS:
				this.setBackground(Color.DARK_GRAY);
				
			case EMPTY:
				squareDisplay = options.EMPTY;
				
			case RED:
				
				
			case GREEN:
		}
		repaint();
		//display stack
	}
	
};
