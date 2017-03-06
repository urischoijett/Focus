
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Random;

public class State {
	
	private ArrayDeque<Boolean>[][] state = new ArrayDeque[8][8];
	private int redScore, greenScore;
	
	//constructors
	public State() {
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j] = new ArrayDeque<Boolean>();
			}
		}
	}
	public State(ArrayDeque<Boolean>[][] s){
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j] = s[i][j].clone();
			}
		}
	}
	
	//methods - get/set
	public ArrayDeque<Boolean> getSquareContent(int x, int y){
		return state[x][y].clone();
	}
	public void setSquareContent(int x, int y, ArrayDeque<Boolean> nc){
		state[x][y] = nc.clone();
	}
	public int getRedScore(){
		return redScore;
	}
	public int getGreenScore(){
		return greenScore;
	}
	public ArrayDeque<Boolean>[][] getState(){
		return state;
	}
	
	//methods - other
	public static boolean equals(State s1, State s2){		//TEST MEEEEEEEEE
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				if(!s1.getState()[i][j].equals(s2.getState()[i][j])){
					return false;
				}
			}
		}
		return true;
	}
	public void moveStack(boolean player, int x1, int y1, int x2, int y2, int n){
		//source:(x1,y1), destination:(x2,y2), num_moved: n

		if(n>state[x1][y1].size()){
			System.out.println("trying to move too many tiles");
			return;
		}
		
		//move stack 1 onto stack 2
		ArrayDeque<Boolean> movers = new ArrayDeque<Boolean>();
		for (int i=0; i<n; i++){
			movers.addFirst(state[x1][y1].pop());
		}
		for (int i=0; i<n; i++){
			state[x2][y2].addFirst(movers.pop());
		}
//		 && !
		//score any points
		while(state[x2][y2].size()>5){
			if (state[x2][y2].pollLast()){
				if (!state[x2][y2].peekFirst()){
					redScore++;
				} 
			} else {
				if (state[x2][y2].peekFirst()){
					greenScore++;
				}
			}
		}

	}
	
	public void randomStart(){
		//Set scores to 0-0 and place a single chip in each stack of a random color
		
		Random rand = new Random();
		redScore   = 0;
		greenScore = 0;
		
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j].clear();
				if(i>0 && i<7 && j>0 && j<7){
					state[i][j].add(rand.nextBoolean());
					state[i][j].add(rand.nextBoolean());
					state[i][j].add(rand.nextBoolean());
				}
			}
		}
	}
	
};
