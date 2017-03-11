import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gui extends JFrame{
	
	public  Controller ctrl;
	private JPanel contentPane;
	private JLabel redScoreLabel, redScore, greenScoreLabel, greenScore;
	private JButton resetButton, moveButton, playButton;
	private BoardSpacePanel[][] gameBoard = new BoardSpacePanel[8][8];
	
	private State display;
	
	
	
	public Gui(){
		setResizable(false);
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(new GridBagLayout());
		setContentPane(contentPane);
		GridBagConstraints c = new GridBagConstraints();
			
		
		//game board
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 1;
		
		for (int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				int[] id = {i, j};
				gameBoard[i][j] = new BoardSpacePanel(id);
				c.gridx      = i+1;
				c.gridy      = 7-j;
				contentPane.add(gameBoard[i][j], c);
			}
		}
		
		//red label
		redScoreLabel = new JLabel("Red");
		c.gridx      = 0;
		c.gridy      = 0;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(redScoreLabel, c);
		
		//red score
		redScore = new JLabel("0");
		c.gridx      = 0;
		c.gridy      = 1;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(redScore, c);
		
		//green label
		greenScoreLabel = new JLabel("Green");
		c.gridx      = 9;
		c.gridy      = 0;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(greenScoreLabel, c);
		
		//red score
		greenScore = new JLabel("0");
		c.gridx      = 9;
		c.gridy      = 1;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 0;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(greenScore, c);
		
		//reset button
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	resetButtonHandler();}	
		});
		c.gridx      = 0;
		c.gridy      = 7;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 2;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(resetButton, c);
		
		//move button
		moveButton = new JButton("Move");
		moveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	moveButtonHandler();}	
		});
		c.gridx      = 9;
		c.gridy      = 6;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 2;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(moveButton, c);
		
		//play button
		playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	playButtonHandler();}
		});
		c.gridx      = 9;
		c.gridy      = 7;
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.weightx    = 1;
		c.weighty    = 1;
		c.fill       = 2;
		c.anchor	 = GridBagConstraints.CENTER;
		contentPane.add(playButton, c);
		
		setBoardBoundries();
		setVisible(true);
		
	}
	public void setDisplay(State s){
		display = s;
		update();
	}
	
	private void update(){
		redScore.setText(String.valueOf(display.getRedScore()));
		greenScore.setText(String.valueOf(display.getGreenScore()));
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				gameBoard[i][j].updateContent(display.getSquareContent(i, j));
			}
		}
		
	}
	private void resetButtonHandler(){
		ctrl.init();
	}
	
	private void moveButtonHandler(){
		ctrl.testMove();
	}
	
	private void playButtonHandler(){
		ctrl.playGame();
	}
	
	private void setBoardBoundries(){
		gameBoard[0][0].setOutofBounds();
		gameBoard[1][0].setOutofBounds();
		gameBoard[0][1].setOutofBounds();
		
		gameBoard[6][0].setOutofBounds();
		gameBoard[7][0].setOutofBounds();
		gameBoard[7][1].setOutofBounds();
		
		gameBoard[0][6].setOutofBounds();
		gameBoard[1][7].setOutofBounds();
		gameBoard[0][7].setOutofBounds();
		
		gameBoard[6][7].setOutofBounds();
		gameBoard[7][6].setOutofBounds();
		gameBoard[7][7].setOutofBounds();
	}
	
};
