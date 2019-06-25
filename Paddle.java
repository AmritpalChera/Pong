//Author: Amritpal Chera
//Date: 2019-06-24
//Description: This class stores all the necessary methods in order for the Paddles to move. 
import java.awt.Graphics;


//creates an interface for communication purposes in HumanPaddle
public interface Paddle {
	public void draw (Graphics g);
	public void move();
	public int getY();
	public int getyVel();
 
}
