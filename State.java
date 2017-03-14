
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
	public State(State s){
		redScore   = s.getRedScore();
		greenScore = s.getGreenScore();
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j] = s.getSquareContent(i, j).clone();
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
	public static boolean outOfBounds(int x, int y){ //test this
		if (x<0 || x>7 || y<0 || y>7) { //outside the board
			return true;
		}
		if (x==0 && y<2 || x==0 && y>5){ //column 1
			return true;
		}
		if (x==1 && y<1 || x==1 && y>6){ //column 2
			return true;
		}
		if (x==6 && y<1 || x==6 && y>6){ //column 7
			return true;
		}
		if (x==7 && y<2 || x==7 && y>5){ //column 8
			return true;
		}
		return false;
	}
	
	//methods - other
	public static boolean stateEquals(State s1, State s2){//compares 2 full states for equality
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				if(!State.stackEquals(s1.getState()[i][j],s2.getState()[i][j])){
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean stackEquals(ArrayDeque<Boolean> s1, ArrayDeque<Boolean> s2){ //helper for stateEquals, compares individual stacks of chips
		if (s1.size() != s2.size()){ //if different # of elements, false
			return false;
		}
		Iterator<Boolean> s1_itr = s1.iterator();
		Iterator<Boolean> s2_itr = s2.iterator();
		while (s1_itr.hasNext() && s2_itr.hasNext()){ //if any 2 elements are diff, false
			if (s1_itr.next() != s2_itr.next()){
				return false;
			}
		} // else must be true
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
		int numGreenTiles=0, numRedTiles=0;;
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j].clear();
				if(i>0 && i<7 && j>0 && j<7){
					if (rand.nextBoolean()){
						if(numGreenTiles < 18){
							state[i][j].add(true);
							numGreenTiles++;
						} else {
							state[i][j].add(false);
							numRedTiles++;
						}
					}  else {
						if (numRedTiles< 18){
							state[i][j].add(false);
							numRedTiles++;
						} else {
							state[i][j].add(true);
							numGreenTiles++;
						}	
					} 
//					state[i][j].add(rand.nextBoolean());
//					state[i][j].add(rand.nextBoolean());
//					state[i][j].add(rand.nextBoolean());
//					state[i][j].add(rand.nextBoolean());
				}
			}
		}
	}
	
	public void sampleStart(){
		//Set scores to 0-0 and place a single chip in each stack of a random color
		redScore   = 0;
		greenScore = 0;
		
		for (int i=0; i<8; i++){
			for (int j =0; j<8; j++){
				state[i][j].clear();
				if(i>0 && i<7 && j>0 && j<7){
					state[i][j].add(true);
//					if(i%4>2 || i%4==0){
//						state[i][j].add(true);
//					} else {
//						state[i][j].add(false);
//					}
				}
			}
		}
	}

};
