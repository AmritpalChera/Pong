//Author: Amritpal Chera
//Date: 2019-06-24
//Description: This code implements all classes for the Pong game. It updates the content on the screen and what keys are pressed
import javax.swing.*;
//import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

public class Pong implements Runnable, KeyListener{
	
	//runs the entire code. 
	//first creates a pong game and then calls the run method which repeats forever
	public static void main (String[]args) {
		
		//************************CHANGE NUMBER OF POINTS HERE***********************
		int points =5; //********************
		Pong pong = new Pong(points);
		
		pong.run();
		
	}
	int height = 550; //height of the screen
	int width  = 900; //width of the screen
	//String key = "none"; //testing purposes...indicates what key is being pressed
	Thread thread; //runs a seperate thread
	JFrame frame; //window for where the game is played
	HumanPaddle p1; //player 1 (paddle on right-side)
	ball b1; //ball
	HumanPaddle p2; //player 2 (paddle on left-side)
	boolean collide; //true if ball collided with paddle
	int count; //keeps count of how many time the collision took place
	double speedBump =1;//number by which the x-velocity of the ball is increases (sequential)
	int p1Point=0;
	int p2Point =0;
	int pointslimit; //what the game is up to
	int win=-1; //indicates winner, -1 is no winner, 1 is player 1 as winner and 2 indicates player 2 won
	boolean enter; //if enter is pressed 
	boolean pointt; //true if any player got a point
	boolean start; //indicates if the game has started 
	
	public Pong(int points) { //constructor for the game
		frame = new JFrame ("PONG"); //names the window PONG
		frame.setSize(width,height); //sets the size of the window to the set size above
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets the program to terminate when you close the game window
		frame.addKeyListener(this); //implements keyListener to detect what keys are pressed and released
		HumanPaddle.setheight(height); //sends the height of screen to humanpaddle
		HumanPaddle.setwidth(width); //sends the width of screen to humanpaddle
		p1 = new HumanPaddle(2); //creates a new human paddle on the right side (player 1)
		b1 =new ball(height, width); //creates a ball and also sends the size of the screen to ball class
		p2 = new HumanPaddle(1); //creates a second bat on the left side
		frame.add(new Drawing());  //add the interface onto the screen
		//frame.setUndecorated(true);
		frame.setVisible(true); //makes the interface visible by the user
		start=false; //set the start of the game to false
		pointslimit=points; //set the limit on how much the game is up to
	}
	
	
	
	//updates the screen with the content
	class Drawing extends JComponent{
		
		//method that updates the data on the screen
		public void paint(Graphics g) {
			
			//SET THE BACKGROUND TO BLACK
			g.setColor(Color.black); 
			g.fillRect(0, 0, width, height);
			
			//UPDATE THE LOCATION OF THE OBJECTS
			p1.draw(g);
			b1.draw(g);
			p2.draw(g);
			
			
			//DISPLAY THE SCORE
			Font largeSerifFont = new Font ("Serif", Font.PLAIN,20);
			g.setFont(largeSerifFont);
			g.drawString("Player 1",(width/2)+width/4-20,25);
			g.drawString("Player 2",width/4-20,25);
			g.drawString(String.valueOf(p1Point),(width/2)+width/4,50);
			g.drawString(String.valueOf(p2Point),(width/(4)),50);
			
			//IF A PLAYER WON
			if (win!=-1) {
				largeSerifFont = new Font ("Serif", Font.PLAIN,40);
				g.setFont(largeSerifFont);
				g.setColor(Color.red);
				g.drawString("GAME OVER",(width/2)-120,height/2+50);
				g.drawString("WINNER IS PLAYER "+String.valueOf(win),(width/2)-190,height/2+90);
				
			}
			//only for debugging purposes
			//g.drawString(key, 200, 200);
			
			//IF A PLAYER HASN'T WON AND GAME ISN'T OVER, DISPLAY DATA BEFORE NEXT ROUND
			if (!start && win==-1) {
				g.drawString("Press Enter to continue", width/2-90, height/2+30);
				g.drawString("Player 1-->Arrow UP and DOWN keys", width/2-150, height/2+55);
				g.drawString("Player 2-->W and S keys", width/2-90, height/2+80);
				g.drawString("Game Upto "+String.valueOf(pointslimit)+" points", width/2-70, height/2+105);
				
			}
			
		}
	}

	
	//excutes the code for all the classes over and over again
	//set of instructions for the game
	public void run() {
		// TODO Auto-generated method stub
		
		//boolean loop = true;//control variable for the while loop
		while (true) { 
			
			if (start) {
				
				p1.move(); //alter player 1's paddle's movement according to key press
				
				b1.move();//alter ball's movement according to the set of conditions in method move in ball.
				
				p2.move();//alter player 2's paddle's movement according to key press
				
				collide = b1.checkcollision(p1,p2);//check if the ball collides with any of the paddles
				
				//if collision took place, increase the count by 1
				if (collide) {
					count++;
					//increase the x-velocity of the ball on every other collision
					if (count%2==0) {	
						//only increase the speed if its less than or equal to 5
						if (Math.abs(b1.getxVel())<=5) {
							if (b1.getxVel()>0)
								b1.setxVel((double)b1.getxVel()+speedBump); //increase speed by the speedBump amount indicated above in the positive direction
							else
								b1.setxVel((double)b1.getxVel()-speedBump); //increase speed by the speedBump amount indicated above in the negative direction
						}
					}
					
				
				}
				
				//update the screen with the newest content and positioning of objects
				
				updatePoint();
				
			}
			frame.repaint();
			
			try {
				//wait for 10ms before re-executing the code. 
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 * KEY CODES:
	 * left = 37
		up = 38
		right = 39
		down = 40
		w=87
		s=83
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//key = String.valueOf(e.getKeyCode());
		
		//set the acceleration to true according to the keys pressed
		
		if (e.getKeyCode() == 38) { //if key pressed is Arrow UP
			p1.setupAccel(true);
		}
		else if (e.getKeyCode() == 40) { //if key pressed is Arrow DOWN
			p1.setdownAccel(true);
		}
		if (e.getKeyCode() == 87) { //if key pressed is Arrow UP
			p2.setupAccel(true);
		}
		else if (e.getKeyCode() == 83) { //if key pressed is Arrow DOWN
			p2.setdownAccel(true);
		}
		
		else if (e.getKeyCode() == 10) { //if key pressed is Enter
			start=true; //start the game on press of Enter
		}
	}

	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//set the acceleration to false according to the keys released
		if (e.getKeyCode() == KeyEvent.VK_UP) { //if key pressed is Arrow UP
			p1.setupAccel(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) { //if key pressed is Arrow DOWN
			p1.setdownAccel(false);
		}
		
		if (e.getKeyCode() == 87) { //if key pressed is Arrow UP
			p2.setupAccel(false);
		}
		else if (e.getKeyCode() == 83) { //if key pressed is Arrow DOWN
			p2.setdownAccel(false);
		}
		
	}

	
	
	//returns -1 if no winner, 1 if player 1 wins, 2 if player 2 wins
	public void updatePoint() {
		win =-1;
		//if the ball goes out of bounds on the left side
		if (b1.getX()<0) {
			b1.reset();
			start=false;
			p1Point+=1; //give player 1 the point
			if (p1Point==pointslimit) {
				win=1;
			}
			
		}
		//if ball goes out of bounds on the right side
		else if (b1.getX()>width) {
			b1.reset();
			start=false;
			p2Point++; //give player 2 the point
			if (p2Point==pointslimit) {
				win=2;
			}
		}
		
	}
	
	

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
