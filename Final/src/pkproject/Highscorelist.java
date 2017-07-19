package pkproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Guyiyu
 */
public class Highscorelist {
	private static Highscore[] highscorelist =new Highscore[10];
	
	/**
	 * Highscorelist() will automaticly read "src/highscore"(Without Encryption)
	 * If there is no such a file, we will save 10 highscores with the name "nobody"
	 */
	public Highscorelist()  {
		
		BufferedReader bufr=null;
		try {
			bufr = new BufferedReader(new FileReader("src/highscore"));	
			for(int i=0;i<10;i++){
				String str=null;
				String[] splitStr=null;
				str=bufr.readLine();
				splitStr=str.split(",");
				highscorelist[i]=new Highscore
						(splitStr[0],Integer.parseInt(splitStr[1]));
			}
			//System.out.println("Readed");
		}catch (IOException e) {
			//e.printStackTrace();
			for(int i=0;i<10;i++){
				highscorelist[i]=new Highscore();
			}
			if(!(e instanceof FileNotFoundException)){
				e.printStackTrace();
			}
			save();
		}
	}
	/**
	 * save this highscorelist to "src/Highscore"
	 */
	private void save() {
		try {
			BufferedWriter bufw=new BufferedWriter(new FileWriter("src/Highscore") );
			for(int i=0;i<10;i++){
				bufw.write(highscorelist[i].toString());
				bufw.newLine();
				//System.out.println(highscorelist[i].toString());
			}
			bufw.flush();
			bufw.close();
		} catch (IOException e) {}
	}

	/**
	 * check if this score is a highscore,
	 * when it is you can use updateHighscore to update it.
	 * 
	 * @param score the score in the game
	 * @return Boolean isHighscore
	 */
	public boolean checkHighscore(int score){
		boolean isHighscore=false;
		if(score>highscorelist[9].getScore()) {
			isHighscore=true;
		}
		return isHighscore;
	}
	/**
	 * saving  this highscore to the highscore list
	 * Automaticly put it in the right place
	 * @param highscore the Highscore you want to add in the list
	 */
	public void updateHighscore(Highscore highscore){
		String name=highscore.getName();
		int score=highscore.getScore();
		for(int i=8;i>=0;i--){
			if (highscorelist[i].getScore()>score){
				highscorelist[i+1]=new Highscore(name,score);
			} else if(i!=0) {
				highscorelist[i+1]=highscorelist[i];
			} else {
				highscorelist[1]=highscorelist[0];
				highscorelist[0]=new Highscore(name,score);
			}	
		}
		save();
	}
	/**
	 * 
	 * @param i the i-th best player, i should be between 0 and 9
	 * @return the stored i-th Highscore
	 * @see Highscore
	 */
	public Highscore getHighscore(int i){
		if(i>=0 && i<=9){
			return highscorelist[i];
		} else {
			return new Highscore();
		}
	}
}


	
	
