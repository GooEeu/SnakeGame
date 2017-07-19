package pkproject;

public class Highscore {
	private String name;
	private int score;
	
	
	Highscore(){
		this.name="nobody";
		this.score=0;
	}
	/**
	 * 
	 * @param name name of the player who achieved this score
	 * @param score the score he acchieved in the game
	 */
	Highscore(String name,int score){
		this.name=name;
		this.score=score;
	}
	/**
	 * 
	 * @return name of the player
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 
	 * @return score
	 */
	public int getScore(){
		return this.score;
	}
	
	@Override
	public String toString(){
		return this.name+","+this.score;
	}
}
