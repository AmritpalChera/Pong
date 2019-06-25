//Author: Amritpal Chera
//Date: 2019-06-24
//Description: This code paints the ball onto the screen and also deals with its interaction with the paddles

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ball {
	double xVel; // x velocity of the ball
	double yVel; // y velocity of the ball
	double x; // x-coordinate of the ball
	double y; // y-coordinate of the ball
	static int appheight, appwidth; // the height and width of the window
	Random rand = new Random(); // random value
	double changeConstant = 0.3; //constant for how much the paddle speed affects the ball speed. 
	double velocityVector; //combination of x and y velocity to create the final vector. 

	// constructor for the ball
	public ball(int h, int w) {
		
		// SET X-VELOCITY OF BALL
		xVel = rand.nextInt(3) + 1; // varies the x velocity from 1 to 3
		if (rand.nextInt(2) == 0) { // 50% chance for the ball to start from any side
			xVel = -xVel; // if 0 was drawn, reverse the direction of ball.
		}

		// SET Y-VELOCITY OF BALL; repeat process of setting x-velocity
		yVel = rand.nextInt(3) + 1; // varies the x velocity from 1 to 3
		if (rand.nextInt(2) == 0) { // 50% chance for the ball to start from any side
			yVel = -yVel; // if 0 was drawn, reverse the direction of ball.
		}

		// sets the height and width of the screen
		appheight = h;
		appwidth = w;
		
		//sets the x and y coordinates of the ball to middle of the screen
		x = appwidth/2.0;
		y = appheight/2.0;
	}

	// returns the x-coordinate of ball
	public int getX() {
		return (int) x;
	}

	// returns the y-coordinate of ball
	public int getY() {
		return (int) y;
	}
	
	//resets the ball
	public void reset() {
		x=appwidth/2.0;
		y=appheight/2.0;
		// SET X-VELOCITY OF BALL
		xVel = rand.nextInt(3) + 1; // varies the x velocity from 1 to 3
		if (rand.nextInt(2) == 0) { // 50% chance for the ball to start from any side
			xVel = -xVel; // if 0 was drawn, reverse the direction of ball.
		}

		// SET Y-VELOCITY OF BALL; repeat process of setting x-velocity
		yVel = rand.nextInt(3) + 1; // varies the x velocity from 1 to 3
		if (rand.nextInt(2) == 0) { // 50% chance for the ball to start from any side
			yVel = -yVel; // if 0 was drawn, reverse the direction of ball.
		}
	}

	// controls the movement of the ball
	public void move() {
		x += xVel;
		y += yVel;

		// if one reaches the top and bottom limit of the screen, reflect it in other
		// direction
		if (y < 10 || y > appheight - 48) {
			yVel = -yVel;
		}

	}
	
	//setter for xVel of the ball
	public void setxVel(double x) {
		xVel=x;
		
	}
	
	//getter for xVel of the ball
	public double getxVel() {
		return xVel;
	}
	
	
	//draws the ball on the graphics screen
	public void draw(Graphics g) {
		
		g.setColor(Color.white); // set the color of ball to white
		
		//if overall velocity is greater than 7, change ball color to yellow
		if (velocityVector>7) {
			g.setColor(Color.yellow);
		}
		
		//if overall velocity is greater than 5, change vall color to pink
		else if (velocityVector>5) {
			g.setColor(Color.pink);
		}
		g.fillOval((int) x - 10, (int) y - 10, 20, 20); // 20 by 20 oval, so the centre is going to be 10 and 10

		// for testing purposes
		//g.drawString(String.valueOf(yVel),150,150);
		
		//g.drawString(String.valueOf(velocityVector),100,150);
		
	}

	// checks if the ball collides with the paddle
	// if it does, reverse the x-velocity, hence movement
	public boolean checkcollision(Paddle p1, Paddle p2) {
		
		boolean col = false; //true if collision occured, otherwise false
		
		// left paddle collision
		// paddle 20units away from left, has width of 20 plus ball has radius of 10
		// if ball is in range of the left paddle width (Player 2)
		if (x < 50 && x > 40) {

			// compare y values
			// if the ball is in range of the y-location of the paddle
			if (y >= p2.getY() - 0.5 && y <= p2.getY() + 80.5) { // since the paddle is 80 units tall
				col=true;
				xVel = -xVel;
				//to make thing more dynamic, add paddle's velocity to the ball
				yVel+=p2.getyVel()*changeConstant;
				
	
				
			}

		}

		// right paddle collision
		// if ball is in range of the right paddle width (Player 1)
		else if (x > appwidth - 50 && x < appwidth - 40) { // same thing but from the right side
			// compare y values
			// if the ball is in range of the y-location of the paddle
			if (y >= p1.getY() - 0.5 && y <= p1.getY() + 80.5) {// since the paddle is 80 units tall
				col=true;
				xVel = -xVel;
				//to make thing more dynamic, add paddle's velocity to the ball
				yVel+=p1.getyVel()*changeConstant;
				//limit the yVel to 5
				
			}
		}
		/*
		//limit the y-velocity to max of 6
		if (yVel>6) {
			yVel=6;
		}
		//limit the y-velocity to max of -6
		else if (yVel<-6) {
			y=-6;
		}
		
		//limit the y-velocity to min of 1
		if (yVel<1 && yVel>-1) {
			yVel/=Math.abs(yVel);
		}
		*/
		//calculate the overall velocity of the ball
		calcVector();
		return col;
	}
	
	//this method calulates the overall velocity vector using the Pythagorean theorem
	public void calcVector() {
		velocityVector = Math.pow(xVel,2)+Math.pow(yVel,2);
		velocityVector = Math.sqrt(velocityVector);
	}
}
