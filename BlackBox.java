// BlackBox.java
// CSU Dominguez Hills / CSC 123
// Extra credit 3 (Black Box Game)
// Written by Horacio Santoyo
// 12/03/16
// TODO: Add sound effects and maybe 3d graphics

import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.geom.Ellipse2D;
//import java.awt.geom.Point2D;
import java.awt.Font;


public class BlackBox extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BlackBox(){
		addMouseListener(new MouseButtonListener());
        //addMouseMotionListener(new MouseMotionListener());
		start();
		resetBoard();
	}

	public static void main(String[] args) throws InterruptedException{
		BlackBox blackbox = new BlackBox();
		MenuFrame frame = new MenuFrame(blackbox);
		frame.setTitle("Black Box");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(LEFT+45+size*10, TOP+95+size*10);
		frame.setSize(580, 620);
		frame.add(blackbox);
		frame.setVisible(true);


/*
	    while(true){
	    Thread.sleep(1000);
		//frame.setSize(LEFT+45+gridSize*squareSize, TOP+85+gridSize*squareSize);
		System.out.println("Test!");
		}
*/
		
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.setFont(new Font("Serif", Font.BOLD, 20)); 

		g2.setColor(Color.white);
		g2.fillRect(1, 1, 600, 600);

		//Draw board
		g2.setColor(Color.black);
		g2.fillRect(LEFT+size, TOP+size, size*8, size*8); //inner black square

		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(1));
		g2.fillRect (LEFT+size, TOP, size*8, size); //top red bar
		g2.fillRect (LEFT+size, TOP+size*9, size*8, size); //bottom red bar
		g2.fillRect (LEFT, TOP+size, size, size*8); //left red bar
		g2.fillRect (LEFT+size*9, TOP+size, size, size*8); //right red bar

		g2.setColor(Color.white);
		//g2.setStroke(new BasicStroke(1));
		for(int i = 0; i < 9; i++)
			g2.drawLine(LEFT,TOP+size+size*i,LEFT+size+size*9,TOP+size+size*i); //white horizontal lines

		for(int i = 0; i < 9; i++)
			g2.drawLine(LEFT+size+size*i,TOP,LEFT+size+size*i,TOP+size+size*9); //white vertical lines

//		if(gameOver) result = "Game over.";

		//Result label
		g2.setColor(Color.black);
		if(!laserOn)
			g2.drawString(result, LEFT+size,TOP-10);
		else
			g2.drawString("Running...", LEFT+size,TOP-10);
		//Button labels
		for(int i = 0; i < 32; i++)
			g2.drawString(buttonArr[i].label, buttonArr[i].x,buttonArr[i].y);

		//Score label
		if(score > 0 || gameOver)
			g2.drawString("Score: " + score, LEFT+size*7,TOP-10);

		if(showLaser && laserOn){
		//Draw laser
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4));
		g2.drawRect (posx+17, posy+17, 5, 5); 
		g2.drawRect (posx+28, posy+17, 5, 5);
		g2.drawRect (posx+22, posy+22, 5, 5);
		g2.drawRect (posx+28, posy+28, 5, 5); 
		g2.drawRect (posx+17, posy+28, 5, 5);		
		}



		if(bumperSet == false){

			int xx = 0;
			int yy = 0;
			boolean duplicate = false;

			for(int i = 0; i < 4; i++){
				duplicate = true;
				while(duplicate == true){
					xx = rnd.nextInt(8) + 1;
					yy = rnd.nextInt(8) + 1;
					for(int j = 0; j < 4; j++){
						duplicate = false;
						if(xx == bumpArr[j] && yy == bumpArr[j+4]){
							duplicate = true;
							j = 4;
						}
					}
					if(duplicate == false){
						bumpArr[i] = xx;
						bumpArr[i+4] = yy;
					}
				}
			}

/*
			bumpArr[0] = 3;
			bumpArr[4] = 3;
			bumpArr[1] = 5;
			bumpArr[5] = 3;
			bumpArr[2] = 3;
			bumpArr[6] = 5;
			bumpArr[3] = 5;
			bumpArr[7] = 5;
*/

		bumperSet = true;
		if(practice) System.out.println("(" + bumpArr[0] + "," + bumpArr[4] + ")" + " (" + bumpArr[1] + "," + bumpArr[5] + ")" + " (" + bumpArr[2] + "," + bumpArr[6] + ")" + " (" + bumpArr[3] + "," + bumpArr[7] + ")");
		}

		Ellipse2D.Double bumper1 = new Ellipse2D.Double(LEFT+15+size*bumpArr[0], TOP+15+size*bumpArr[4], 20, 20);
		Ellipse2D.Double bumper2 = new Ellipse2D.Double(LEFT+15+size*bumpArr[1], TOP+15+size*bumpArr[5], 20, 20);
		Ellipse2D.Double bumper3 = new Ellipse2D.Double(LEFT+15+size*bumpArr[2], TOP+15+size*bumpArr[6], 20, 20);
		Ellipse2D.Double bumper4 = new Ellipse2D.Double(LEFT+15+size*bumpArr[3], TOP+15+size*bumpArr[7], 20, 20);

		Ellipse2D.Double bumper5 = new Ellipse2D.Double(LEFT+18+size*bumpGuessArr[0], TOP+18+size*bumpGuessArr[4], 15, 15);
		Ellipse2D.Double bumper6 = new Ellipse2D.Double(LEFT+18+size*bumpGuessArr[1], TOP+18+size*bumpGuessArr[5], 15, 15);
		Ellipse2D.Double bumper7 = new Ellipse2D.Double(LEFT+18+size*bumpGuessArr[2], TOP+18+size*bumpGuessArr[6], 15, 15);
		Ellipse2D.Double bumper8 = new Ellipse2D.Double(LEFT+18+size*bumpGuessArr[3], TOP+18+size*bumpGuessArr[7], 15, 15);

		if(showBumpers){
		g2.setColor(Color.green);
		g2.setStroke(new BasicStroke(20));
		g2.draw(bumper1);
		g2.draw(bumper2);
		g2.draw(bumper3);
		g2.draw(bumper4);
		g2.setColor(Color.black);
		}


		g2.setColor(Color.yellow);
		g2.setStroke(new BasicStroke(15));
		g2.draw(bumper5);
		g2.draw(bumper6);
		g2.draw(bumper7);
		g2.draw(bumper8);
		g2.setColor(Color.black);



		//Test rectangle
//		g2.setColor(Color.white);
//		g2.drawRect(1, 1, 600, 600);
//		g2.setColor(Color.gray);
//		g2.fillRect(1, 1, 600, 600);



		if(((posx < LEFT+size || posx > LEFT+8*size || posy < TOP+size || posy > TOP+8*size) && laserOn && counter >= size/2) || hit == 1){			
			if(hit == 0){
				int origButton = button;
				buttonArr[button-1].label = button + "";
				setButton(posx,posy);
				buttonArr[button-1].label = origButton + "";
				if(origButton == button){
					result = "Reflection at " + origButton;
					buttonArr[button-1].label = "R";
				}else
				result+=button;
			}
			laserOn = false;
			showLaser = false;
			score++;
			if(practice) System.out.println(result);
			hit = 0;
		}

		if(laserOn){
			if(counter >= size/2)
			{
				counter = 0;
				result = button + "-->";

				//1=North, 2=East, 3=South, 4=West
				//direction = rnd.nextInt(5) + 1;

				//Check for hits
				for(int i = 0; i < 4; i++)
					if((posx-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 3){ //Below
						if(practice) System.out.println("Bumper found at (" + (posx-LEFT)/size + "," + (posy+size-TOP)/size + ")");
						direction = 1;
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						hit = 1;
					}
				for(int i = 0; i < 4; i++)
					if((posx-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4] && direction == 1){ //Above
						if(practice) System.out.println("Bumper found at (" + (posx-LEFT)/size + "," + (posy-size-TOP)/size + ")");
						direction = 3;
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						hit = 1;						
					}
				for(int i = 0; i < 4; i++)
					if((posx+size-LEFT)/size == bumpArr[i] && (posy-TOP)/size == bumpArr[i+4] && direction == 2){ //Right
						if(practice) System.out.println("Bumper found at (" + (posx+size-LEFT)/size + "," + (posy-TOP)/size + ")");
						direction = 4;
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						hit = 1;						
					}					
				for(int i = 0; i < 4; i++)
					if((posx-size-LEFT)/size == bumpArr[i] && (posy-TOP)/size == bumpArr[i+4] && direction == 4){ //Left
						if(practice) System.out.println("Bumper found at (" + (posx-size-LEFT)/size + "," + (posy-TOP)/size + ")");
						direction = 2;
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						hit = 1;						
					}		

				//Check for deflection
				for(int i = 0; i < 4; i++){ //check below
					if((posx-LEFT-size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 3){ //Southwest
						direction = 2;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT+size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 3){ //Southeast
						direction = 4;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}						
				}

				for(int i = 0; i < 4; i++){ //check above
					if((posx-LEFT-size)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4] && direction == 1){ //Northwest
						direction = 2;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT+size)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4] && direction == 1){ //Northeast
						direction = 4;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}						
				}

				for(int i = 0; i < 4; i++){ //check right
					if((posx-LEFT+size)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4] && direction == 2){ //Northeast
						direction = 3;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT+size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 2){ //Southeast
						direction = 1;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT+size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 3){ //Southeast
						direction = 4;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}					
				}

				for(int i = 0; i < 4; i++){ //check left
					if((posx-LEFT-size)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4] && direction == 4){ //Northwest
						direction = 3;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT-size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 4){ //Southwest
						direction = 1;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
					if((posx-LEFT-size)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4] && direction == 3){ //Southwest
						direction = 2;
						if(practice) System.out.println("Deflection at (" + bumpArr[i] + "," + bumpArr[i+4] + ")");
					}
				}

				switch(direction){
					case 1:
						ew = 0;
						ns = -1;
						break;
					case 2:
						ew = 1;
						ns = 0;
						break;
					case 3:
						ew = 0;
						ns = 1;
						break;
					case 4:
						ew = -1;
						ns = 0;
						break;
				}

				if(showLaser && laserOn){
				//Draw laser
				g2.setStroke(new BasicStroke(4));
				g2.setColor(Color.red);
				g2.drawRect (posx+22, posy+22, 5, 5);
				g2.setColor(Color.black);
				}
			}
			counter++;
			posx+=ew*2;
			posy+=ns*2;
		}
	}
/*
	private class MouseMotionListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e){
			System.out.println("Dragged: " + e.getX() + "," + e.getY());
		}
		public void mouseMoved(MouseEvent e){
			System.out.println("Moved: " + e.getX() + "," + e.getY());
		}
	}
*/
	private class MouseButtonListener implements MouseListener
	{
		public void mouseClicked (MouseEvent e){
		if(!gameOver){
			resetPos();
			if(practice == true) showLaser = true;

			posx=e.getX()-LEFT-(e.getX()-LEFT)%size+LEFT;
			posy=e.getY()-TOP-(e.getY()-TOP)%size+TOP;

			if(e.getX() > LEFT+size && e.getX() < LEFT+9*size && e.getY() > TOP+size && e.getY() < TOP+9*size){

				boolean duplicate = false;
				correctGuesses=0;

				//Check for duplicate guess and remove if found
				for(int i = 0; i < 4; i++){
					if(posx/size == bumpGuessArr[i] && posy/size == bumpGuessArr[i+4]){
						bumpGuessArr[i] = -100;
						bumpGuessArr[i+4] = -100;
						duplicate = true;
						i = 4;
					}
				}

				if(!duplicate){
					for(int i = 0; i < 4; i++){
						if(bumpGuessArr[i] < 0){
							bumpGuessArr[i] = posx/size;
							bumpGuessArr[i+4] = posy/size;
							i = 4;
						}
					}
				}

				//Check for number of guesses
				guesses = 0;
				for(int i = 0; i < 4; i++){
					if(bumpGuessArr[i] > 0)
						guesses++;
				}

				//Check correct guesses
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 4; j++){
						if(bumpGuessArr[i] == bumpArr[j] && bumpGuessArr[i+4] == bumpArr[j+4])
							correctGuesses++;
					}
				}

				if(guesses == 4 && correctGuesses ==4) {
					endGame();
					result = "Game over. You win!";
					System.out.println("Game over, you guessed correctly! " + "Score: " + score);
				}
			}
			
			//left bar (1-8)
			if(e.getX() > LEFT && e.getX() < LEFT+size && e.getY() > TOP+size && e.getY() < TOP+9*size){
				setButton(posx,posy);
				//laserOn = true;
				direction = 2;
				ew = 1;
				for(int i = 0; i < 4; i++) // Check for hit on right
					if((posx+size-LEFT)/size == bumpArr[i] && (posy-TOP)/size == bumpArr[i+4]){
						if(practice) System.out.println("Bumper found at " + (posx-size-LEFT)/size + ", " + (posy-TOP)/size);
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						laserOn = false;
						showLaser = false;
						score++;
					}					
				for(int i = 0; i < 4; i++) // Check for reflection on right
					if(((posx+size-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4]) || ((posx+size-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4])){
						result = "Reflection at " + button;
						buttonArr[button-1].label = "R";
						laserOn = false;
						showLaser = false;
						score++;
					}					
			}

			//bottom bar (9-16)
			if(e.getX() > LEFT+size && e.getX() < LEFT+9*size && e.getY() > TOP+9*size && e.getY() < TOP+10*size){
				setButton(posx,posy);
				//laserOn = true;
				direction = 1;
				ns = -1;
				for(int i = 0; i < 4; i++) // Check for hit above
					if((posx-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4]){
						if(practice) System.out.println("Bumper found at " + (posx-LEFT)/size + ", " + (posy-size-TOP)/size);
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						laserOn = false;
						showLaser = false;
						score++;
					}				
				for(int i = 0; i < 4; i++) // Check for reflection above
					if(((posx-size-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4]) || ((posx+size-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4])){
						result = "Reflection at " + button;
						buttonArr[button-1].label = "R";
						laserOn = false;
						showLaser = false;
						score++;
					}					
			}

			//right bar (17-24)
			if(e.getX() > LEFT+9*size && e.getX() < LEFT+10*size && e.getY() > TOP+size && e.getY() < TOP+9*size){
				setButton(posx,posy);
				//laserOn = true;
				direction = 4;
				ew = -1;
				for(int i = 0; i < 4; i++) // Check for hit on left
					if((posx-size-LEFT)/size == bumpArr[i] && (posy-TOP)/size == bumpArr[i+4]){
						if(practice) System.out.println("Bumper found at " + (posx+size-LEFT)/size + ", " + (posy-TOP)/size);
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						laserOn = false;				
						showLaser = false;
						score++;
					}				
				for(int i = 0; i < 4; i++) // Check for reflection on left
					if(((posx-size-LEFT)/size == bumpArr[i] && (posy-size-TOP)/size == bumpArr[i+4]) || ((posx-size-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4])){
						result = "Reflection at " + button;
						buttonArr[button-1].label = "R";
						laserOn = false;
						showLaser = false;
						score++;						
					}					
			}

			//top bar (25-32)
			if(e.getX() > LEFT+size && e.getX() < LEFT+9*size && e.getY() > TOP && e.getY() < TOP+size){
				setButton(posx,posy);
				//laserOn = true;
				direction = 3;
				ns = 1;
				for(int i = 0; i < 4; i++) // Check for hit below
					if((posx-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4]){
						if(practice) System.out.println("Bumper found at " + (posx-LEFT)/size + ", " + (posy+size-TOP)/size);
						result = "Hit at " + button;
						buttonArr[button-1].label = "H";
						laserOn = false;
						showLaser = false;
						score++;						
					}
				for(int i = 0; i < 4; i++) // Check for reflection below
					if(((posx-size-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4]) || ((posx+size-LEFT)/size == bumpArr[i] && (posy+size-TOP)/size == bumpArr[i+4])){
						result = "Reflection at " + button;
						buttonArr[button-1].label = "R";
						laserOn = false;
						showLaser = false;
						score++;
					}					
			}
		}
		}
		public void mouseEntered (MouseEvent e){} // required by MouseListener interface but does nothing
		public void mouseExited (MouseEvent e){} // required by MouseListener interface but does nothing
		public void mouseReleased (MouseEvent e){} // required by MouseListener interface but does nothing
		public void mousePressed (MouseEvent e){} // required by MouseListener interface but does nothing
	}

	// Create a timer to generate an event (call to repaint())
	public void start(){
		class EventTimer implements ActionListener // Note: a class within a method
		{
			public void actionPerformed(ActionEvent event){
				repaint();
			}
		}
		EventTimer listener = new EventTimer();
		Timer t = new Timer(10, listener);
		t.start();
	}

	public static void resetPos(){
		posx = LEFT+size*size;
		posy = TOP;
		counter = 0;
		ew = 0;
		ns = 0;	
		laserOn = false;
		button = 1;
		direction = 0;
		hit = 0;
		tmpGuess = 0;
	}			

	public static void resetBoard(){
		resetPos();
		clearGuess();		
		score = 0;
		guesses = 0;
		result = "";
		correctGuesses = 0;
		for (int i = 0; i < buttonArr.length; i++)
     		buttonArr[i] = new Button();
		for(int i = 0; i < 8; i++)
			bumpArr[i] = 0;
		bumperSet = false;
		showBumpers = false;
		practice = false;
		gameOver = false;
	}

	public static void practiceBoard(){
		resetPos();
		resetBoard();
		showBumpers = true;
		practice = true;
	}

	public static void hint(boolean tf){
		showBumpers = tf;
	}				

	public static void endGame(){
		if(!gameOver){
			gameOver = true;
			showBumpers = true;
			score+=(tmpGuess-correctGuesses)*5+(4-tmpGuess)*5;			
		}
		showBumpers =  true;
		result = "Game over.";
		System.out.println("Game over. Score: " + score);
	}	

	public static void clearGuess(){
		for(int i = 0; i < 8; i++)
			bumpGuessArr[i] = -100;
		guesses = 0;
		correctGuesses = 0;		
	}

	private static class Button{
		Button(){
			label = "";
			x = 0;
			y = 0;
		}
		private String label;
		private int x;
		private int y;
	}

	public static void setButton(int xpos, int ypos){
		if(xpos/size == 0)
			button = ypos/size;
		if(ypos/size == 9)
			button = xpos/size+8;
		if(ypos/size == 0)
			switch(xpos/size){
				case 1:
					button = 32;
					break;
				case 2:
					button = 31;
					break;
				case 3:
					button = 30;
					break;
				case 4:
					button = 29;
					break;
				case 5:
					button = 28;
					break;
				case 6:
					button = 27;
					break;
				case 7:
					button = 26;
					break;
				case 8:
					button = 25;
					break;
			}

		if(xpos/size == 9)
			switch(ypos/size){
				case 1:
					button = 24;
					break;
				case 2:
					button = 23;
					break;
				case 3:
					button = 22;
					break;
				case 4:
					button = 21;
					break;
				case 5:
					button = 20;
					break;
				case 6:
					button = 19;
					break;
				case 7:
					button = 18;
					break;
				case 8:
					button = 17;
					break;
			}

		if(xpos+10 == buttonArr[button-1].x){
			laserOn = false;
		}
		else{
			laserOn = true;	
			buttonArr[button-1].x = xpos+10;
			buttonArr[button-1].y = ypos+30;
		}
	}	

	public static final int TOP = 30;
	public static final int LEFT = 30;
	public static int posx = 0;
	public static int posy = 0;
	public static int counter = 0;
	public static int direction = 0;
	public static int ew = 0;
	public static int ns = 0;
	public static int size = 50;
	public static int guesses = 0;
	public static int tmpGuess = 0;	
	public static int score = 0;
	public static int correctGuesses = 0;
	public static boolean bumperSet = false;
	public static boolean showBumpers = false;
	public static boolean showLaser = false;
	public static boolean laserOn = false;
	public static boolean practice = false;	
	public static boolean gameOver = false;
	Random rnd = new Random();
	public static int[] bumpArr = new int[8];
	public static int[] bumpGuessArr = new int[8];
	public static Button[] buttonArr = new Button[32];
	public static String result = "";
	public static int button = 1;
	public static int hit = 0;

}