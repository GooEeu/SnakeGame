package pkproject;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class SnakeYard {
	static final int ROWS=50;		//行数
	static final int COLS=50;		//列数
	static final int CELLSIZE=10;	//小格尺寸
	//用来计算YardPanel的尺寸。
	static Snake snake=new Snake();
	private static final int ORIGINFREQUENCY=200;//ms, upgrading frequency.
	
	
	static Yard YARDS[][]=new Yard[ROWS][COLS];
	static boolean haveBonusApple=false; 
	static Yard bonusYard;
	static int bonusGone;
	static int score;
	
	static Highscorelist hscl=new Highscorelist();
	static int highscore=hscl.getHighscore(0).getScore();
	static boolean isHighestscore;
	static String highscorePlayer=hscl.getHighscore(0).getName();
	
	static boolean restart;
	static boolean pause;
	
	static JLabel scoreLabel;
	static JLabel bonusLabel;
	static JLabel highscoreLabel;
	static JLabel pauseLabel;
	private JFrame snakeFrame;
	//static Highscorelist highscorelist=new Highscorelist();
	
	public static void main(String[] args) {
		
		
		do{	//while(restart);
		SnakeYard window = new SnakeYard();
		window.snakeFrame.setVisible(true);
		SnakeInit();
		restart=false;
		pause=true;
		int frequency=ORIGINFREQUENCY;
		do{ //while(!GameEnds
			//try{
				if(!pause){				// I meant to use "synchronized" to do this, however I fo-
										// und only 0.5% of CPU is used to do this.
					snake.updateDirection();
					RunSnake();
					frequency=(int)(ORIGINFREQUENCY*1.3/Math.log10((score+500)/25));
					pauseLabel.setText("<html><p>press Space to Pause <br> </p></html>");
				} else {
					pauseLabel.setText("<html><p>press Space or <br> Arrows to start</p></html>");
				}
				//will become faster when higher score, twice so fast when arriving 10000.
				System.out.print(frequency);
				System.out.println(pause);
				//Thread.sleep(frequency);//1000ms = 1sec
				//}catch(InterruptedException e){
				//}
			
		}while(!GameEnds());
		int i;
		if(hscl.checkHighscore(score)){
			System.out.println("is Highscore");
			String hscplr;
			int j=JOptionPane.showConfirmDialog(null, "You are Highscore, do you want us to remember you?", "Congratulations",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (j==0){
				hscplr = JOptionPane.showInputDialog( "Please enter your name" );
				
			} else {
				hscplr="a stranger";
			}
			hscl.updateHighscore(new Highscore(hscplr, score));
			if(isHighestscore){
			highscoreLabel.setText( highscore  +" by "+highscorePlayer );
			}
			i=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Restart",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
			
		} else {
			System.out.println("is not highscore");
			i=JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Restart",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if(i==0){
			restart=true;
		} 
		}while(restart);
	}

	/**
	 * This is to determin if the Game will end.
	 * In the snake game, the game ends when
	 * =:	the snake hits a wall,
	 * OR
	 * =:	the snake eats itself.
	 * @return	if the game ends next turn
	 */
	private static boolean GameEnds() {
		boolean GE=true;
		int frequency=ORIGINFREQUENCY;
		frequency=(int)(ORIGINFREQUENCY*1.3/Math.log10((score+500)/25));
		try{
			Thread.sleep(frequency);
		} catch(InterruptedException e){
		} finally {
			snake.updateDirection();
			Point nH=snake.nextHead();
			
			//0 for empty, 1 for snake(dead), 2 for apple, 3 for wall(dead)
			int nHStts=YARDS[(int) nH.getX()][(int) nH.getY()].getStatus();
			
			
			if(nHStts!=1&&nHStts!=3){
				GE=false;
			}
		}
		return GE;
	}

	private static void SnakeInit() {
		
		snake.setHead(new Point(25,25));
		snake.setTail(new Point(24,25));
		snake.setDirection(3);
		Yard hY=YARDS[snake.getHead().x][snake.getHead().y];
		Yard tY=YARDS[snake.getTail().x][snake.getTail().y];
		hY.setStatus(1);
		hY.setDirection(3);
		tY.setStatus(1);
		tY.setDirection(3);
		setApple();
		bonusGone=0;
		score=0;
		//highscore=0;
		isHighestscore=false;
	}
	
	/**
	 * RunSnake() will Check if the Snake ates an Apple	
	 * =:If it is an Apple 								
	 * snake will groth one Yard langer					
	 * OR												
	 * =:If it is Empty
	 * snake won't growth
	 */
	private static void RunSnake() {
		
		/*
		 * Check nextHead 属性: 墙/蛇; 空白; 果子.
		 =:如果是果子
		 * 往 Head 那一格存储direction  
		 * nextHead 变成新 Head, 上色.
		 =:如果是空, 还要
		 * Tail位置那一个格子存储了 (应该存储了) 下一个格子的位置，寻找新Tail. updateTail
		 * Tail 褪色.
		 */
		Yard nHY=YARDS[snake.nextHead().x][snake.nextHead().y];
		Yard hY=YARDS[snake.getHead().x][snake.getHead().y];
		Yard tY=YARDS[snake.getTail().x][snake.getTail().y];
		switch(nHY.getStatus()){//0 for empty, 1 for snake(dead), 2 for apple, 3 for wall(dead).
			case 0:
				
				hY.setDirection(snake.getDirection());
				snake.setHead(snake.nextHead());
				snake.updateTail(tY.getDirection());
				
				tY.setStatus(0);
				
				nHY.setStatus(1);
				setBonus();
				score=score+1;
				break;
			case 2:
				
				hY.setDirection(snake.getDirection());
				snake.setHead(snake.nextHead());
				nHY.setStatus(1);
				setApple();
				setBonus();
				
				score=score+100;
				break;
			case 4:
				
				hY.setDirection(snake.getDirection());
				snake.setHead(snake.nextHead());
				nHY.setStatus(1);
				
				score=score+50*bonusGone+200;
				haveBonusApple=false;
				bonusGone=0;
				bonusLabel.setText("");
				
				break;
		}
		if(score>highscore){
			isHighestscore=true;
			highscore=score;
			highscoreLabel.setText( highscore  +" by YOU!" );
		}
		//get an new Apple
		if(haveBonusApple){
			if(bonusGone>=1){
				bonusGone--;
			} else {
				haveBonusApple=false;
				bonusYard.setStatus(0);
				bonusLabel.setText("");
			}
		}
		scoreLabel.setText("Your Score :"+score);
		
	}
	/**
	 * Set a bonused apple.
	 */
	private static void setBonus() {
		
		if((Math.random()*100)<=1 && haveBonusApple==false && score>1000){
		boolean appleSet=false;
		do{
			bonusYard=YARDS[(int)(Math.random()*49+1)][(int)(Math.random()*49+1)];
			if(bonusYard.getStatus()==0){
				bonusYard.setStatus(4);
				appleSet=true;
				haveBonusApple=true;
				bonusGone=(int)(Math.random()*20+40);
				
				bonusLabel.setText("<html><body><p>There is a bonus ! <br>Go and get it as <strong>fast</strong> as you can !</p></body></html>");
			}
		}while(!appleSet);
		}
	}

	private static void setApple() {
		
		boolean appleSet=false;
		do{
			Yard aY=YARDS[(int)(Math.random()*49+1)][(int)(Math.random()*49+1)];
			if(aY.getStatus()==0){
				aY.setStatus(2);
				appleSet=true;
				
			}
		}while(!appleSet);
	}

	/**
	 * Create the application.
	 */
	public SnakeYard() {
		SYinit();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void SYinit() {
		snakeFrame = new JFrame();
		snakeFrame.setTitle("Snake");
		snakeFrame.setBounds(100, 100, (int)(ROWS*CELLSIZE*1.5), COLS*CELLSIZE+100);
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeMoveListener SML=new SnakeMoveListener(this);
		snakeFrame.addKeyListener(SML);
		
		JPanel panel = new JPanel();
		snakeFrame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		
		JPanel Yardpanel = new JPanel();
		Yardpanel.setBounds(207, 29, ROWS*CELLSIZE, COLS*CELLSIZE);
		panel.add(Yardpanel);
		Yardpanel.setLayout(null);

		/*
		 * YARDS is where the snake runs and the apple shows.
		 * we remember where the head of the snake is and where the tail is.
		 * the snake don't move, but their head become black and tail become white.
		 * every part of the snake remembers where the next part is, so the end of 
		 * 
		 * 
		 */
		
				
		for(int r=0 ;r<ROWS;r++){
			for(int c=0;c<COLS;c++){
				YARDS[r][c]=new Yard(r,c);
				YARDS[r][c].setBounds(r*CELLSIZE,c*CELLSIZE, CELLSIZE, CELLSIZE);
				if(r==0||r==ROWS-1||c==0||c==COLS-1){
					YARDS[r][c].setStatus(3);
				} else {
					YARDS[r][c].setStatus(0);
				}
				YARDS[r][c].upgrade();
				Yardpanel.add(YARDS[r][c]);
			}
		}
		
		scoreLabel = new JLabel("Your Score: "+ score);
		bonusLabel = new JLabel("");
		highscoreLabel = new JLabel( highscore  +" by "+highscorePlayer );
		highscoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pauseLabel=new JLabel("<html><p>press Space or <br> Arrows to start</p></html>");
		
		scoreLabel.setBounds(15, 30, 180, 18);
		panel.add(scoreLabel);
		bonusLabel.setBounds(15, 130, 180, 56);
		panel.add(bonusLabel);
		highscoreLabel.setBounds(15, 80, 180, 18);
		panel.add(highscoreLabel);
		pauseLabel.setBounds(13, 400, 180, 56);
		panel.add(pauseLabel);
		
		JLabel lblNewLabel = new JLabel("Highest Score:");
		lblNewLabel.setBounds(15, 60, 180, 18);
		panel.add(lblNewLabel);
		

	}
}
/**
 * YARDS is where the snake runs and the apple shows.
 * we remember where the head of the snake is and where the tail is.
 * the snake don't move, but their head become black and tail become white.
 * every part of the snake remembers where the next part is, so the end of 
 * 
 * @author Guyiyu
 */
class Yard extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point position;
	//private boolean isSnake;
	private int status;//0 for empty, 1 for snake, 2 for apple, 3 for wall.
	private int direction;
	
	Yard(int x, int y){
		super();
		this.position=new Point(x,y);
	}
	
	/**
	 * update the color, based on which status this Yard is.
	 */
	public void upgrade(){
		switch( this.status){//0 for empty, 1 for snake, 2 for apple, 3 for wall.=: 4 for Bonus Apple??
		case 0:this.setBackground(Color.WHITE);break;
		case 1:this.setBackground(Color.BLACK);break;
		case 2:this.setBackground(Color.ORANGE);break;
		case 3:this.setBackground(Color.GRAY);break;
		case 4:this.setBackground(Color.RED);break;
		}
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	/**
	 * 0 for empty,
	 * 1 for snake,
	 * 2 for apple.
	 * 3 for wall.
	 * @return the status of this Yard
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		this.upgrade();
	}
	
	@Override
	public String toString(){
		return "Yard@"+this.position;
	}
}
/**
 * Let the snake change direction when Arrow Keys pressed
 * 
 */
class SnakeMoveListener implements KeyListener{
	SnakeMoveListener(SnakeYard SY){
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.print("按下个键");
		//System.out.print(SnakeYard.pause);
        if (e.getKeyCode() == KeyEvent.VK_DOWN &&SnakeYard.snake.getDirection()!=0 ) {  
           SnakeYard.snake.setDirection(1);
           SnakeYard.pause=false;  
        } else if (e.getKeyCode() == KeyEvent.VK_UP &&SnakeYard.snake.getDirection()!=1 ) {  
           SnakeYard.snake.setDirection(0);
           SnakeYard.pause=false; 
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT &&SnakeYard.snake.getDirection()!=2 ) {  
           SnakeYard.snake.setDirection(3);
           SnakeYard.pause=false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT &&SnakeYard.snake.getDirection()!=3 ) {  
           SnakeYard.snake.setDirection(2);
           SnakeYard.pause=false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE ){
        	SnakeYard.pause = !SnakeYard.pause;
        }
        //System.out.println(SnakeYard.pause);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
}

