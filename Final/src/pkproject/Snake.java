package pkproject;

import java.awt.Point;
/**
 * This class remembers:
 * Where the Snake's head is,
 * Where the Snake's tail is,
 * Where the Snake's current direction is to.
 * 
 * This class calculates,
 * Where next tail will be,
 * where next head will be.
 * 
 * @author Guyiyu
 *
 */
public class Snake {
	private int direction;
	private Point Head;
	private Point Tail;
	//private int length=2;
	private int nextdirection;
	
	/**
	 * 
	 * @param head the snake's initial head
	 * @param tail the snake's initial tail
	 * @param direction the snake's initial direction
	 */
	public Snake(Point head,Point tail,int direction){
		this.setHead(head);
		this.setTail(tail);
		this.setDirection(direction);
	}
		
	public Snake() {
		this(new Point(25,25), new Point(24,25), 3);
	}
	
	public Snake(int direction){
		this(new Point(25,25), new Point(24,25), direction);
	}
	
	public Snake(Point head,Point tail){
		this(head,tail,3);
	}
	

	/**
	 * 
	 * @return the snake's current direction
	 */
	
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Note, direction <B>won't</B> be change until next refresh.
	 * UP=0, DOWN=1, LEFT=2, RIGHT=3.
	 * @param direction the snake's <B>NEXT</B> direction.
	 */
	public void setDirection(int direction) {
		this.nextdirection = direction;
	}
	/**
	 * 
	 * @return Point where the head is.
	 */
	public Point getHead() {
		return Head;
	}
	
	public void setHead(Point head) {
		Head = head;
	}
	/**
	 * 
	 * @return Point where the tail is.
	 */
	public Point getTail() {
		return Tail;
	}

	public void setTail(Point tail) {
		Tail = tail;
	}


	
	public void updateTail(int direction){
		Point nT=this.Tail;
		switch(direction){
		case 0:nT = new Point(this.Tail.x,this.Tail.y-1);break;
		case 1:nT = new Point(this.Tail.x,this.Tail.y+1);break;
		case 2:nT =  new Point(this.Tail.x-1,this.Tail.y);break;
		case 3:nT =  new Point(this.Tail.x+1,this.Tail.y);break;
		}
		this.setTail(nT);
	}
	
	public void updateDirection(){
		this.direction=this.nextdirection;
	}
	/**
	 * Calculates where next Head will be at.
	 * @return Point where next head will be at.
	 */
	public Point nextHead(){
		
		switch(this.direction){
		case 0:return new Point(this.Head.x,this.Head.y-1);
		case 1:return new Point(this.Head.x,this.Head.y+1);
		case 2:return new Point(this.Head.x-1,this.Head.y);
		case 3:return new Point(this.Head.x+1,this.Head.y);
		}
		System.out.println("Error with nextHead");//should finish before switch sentence ends.
		return this.getHead();
	}

	public int getNextdirection() {
		return nextdirection;
	}

	public void setNextdirection(int nextdirection) {
		this.nextdirection = nextdirection;
	}


}
