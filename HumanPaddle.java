//Author: Amritpal Chera
//Date: 2019-06-24
//Description: This class deals with the movement of paddles according to the pressed keys on the keyboard. 
import java.awt.Color;
import java.awt.Graphics;

public class HumanPaddle implements Paddle {
	int y, yVel; // y: the y coordinate of the paddle. yVel; speed the paddle moves at
	boolean upAccel, downAccel; //boolean values, true if the paddle accelerates in the right direction, otherwise false
	int player, x;//player is to indicate what player it is(1 or 2). 'x' indicates the x coordinates of the paddle
	
	double friction =0.8; //slows the racket down by 20% each cycle.
	
	//the height and the width of the screen
	static int appheight;
	static int appwidth;
	
	
	public HumanPaddle(int player) { //constructor for a paddle
		upAccel = false; //paddle should not be accelerating up
		downAccel = false;//paddle should not be accelerating down
		y=(appheight/2)-40; //centre point for the paddle. Since the total height of applet is 500 and paddle is 80units in lenght. 500/2 - 40 = 210. 
		yVel =0; //initial movement is 0
		
		//if the racket being made is for player 1; indicates left side of the applet 
		if(player==1) {
			x=20; //indicates the left most side of the bat. which is going to be 20 units awasy from left side
		}
		
		//player 2 gets the right side of the applet
		if (player==2) {
			x=appwidth-40; //both paddles must be equally placed so the leftmost side of right racket most be 40units away from right side
		}
		
		
	}
	
	//getting for paddle velocity
	public int getyVel() {
		return (int)yVel;
	}
	
	//setter for the height of the screen
	public static void setheight(int h) {
		appheight=h;
	}
	//setter for the width of the screen
	public static void setwidth(int h) {
		appwidth=h;
	}
	
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.white);
		g.fillRect(x,(int)y, 20, 80); //set the width of paddle to 20 units and height to 80 units
		
		//only for debugging purposes
		/*
		if (upAccel) {
			g.drawString("UP", 150, 50);
		}
		else if (downAccel) {
			g.drawString("DOWN", 150, 50);
		}
		else if (!downAccel && !upAccel) {
			g.drawString("NONE", 150, 50);
		}
		*/
		//g.drawString(String.valueOf(appheight),120,100);
		
		//value of the y coordinate of paddle
		//g.drawString(String.valueOf(y), 120, 50);
	}


	public void move() {
		// TODO Auto-generated method stub
		
		//true if up key is pressed
		if (upAccel) {
			yVel-=2; //since (0,0) is the top left of screen, going up means subtracting coordinate values
		}
		
		//true if down key is pressed
		else if (downAccel) { //to go down we must add values to the paddle coordinates
			yVel+=2;
		}
		else if (!upAccel && !downAccel) {
			yVel*=friction; //multiply it by a friction coeffcient so it slows the paddle when its not accelerating. 
		}
		
		
		//limit the rate of change of y-coordinate to max of 5
		if (yVel>5) {
			yVel=5;
		}
		
		//limit the rate of change of y-coordinate to min of -5
		else if (yVel<-5) {
			yVel=-5;
		}
		
		y+=yVel; //alter the y coordinate according to yVel;
		
		//Limit the y- coordinates of paddle so it does not go off the screen
		//if bat has reached the top, don't let it go past that point
		if (y<0) {
			y=0;
		}
		
		//if bottom has reached the bottom, don't let it go past that point
		else if (y>appheight-118) {
			y=appheight-118;
		}
	}


	//returns the top-left y coordinate of the paddle
	public int getY() {
		// TODO Auto-generated method stub
		return (int)y;
	}
	
	//setter for upAccel
	//upAccel true is up key is pressed, otherwise false
	public void setupAccel(boolean n) {
		upAccel = n;
	}
	
	//setter for down acceleration 
	//downAccel true is down key is pressed, otherwise false
	public void setdownAccel(boolean n) {
		downAccel = n;
	}
	
	

}
