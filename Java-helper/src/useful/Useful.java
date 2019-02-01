package useful;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Useful {
	////////////////
	//Constants//
	////////////////
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	////////////////
	  //Methods//
	////////////////
	/**
	 * Find fibonacci number on specified place in the sequence
	 *
	 * @param place	Specify the place in the sequence (e.g. if place equals 10 the method will return 55)
	 * @return      The fibonacci number in the specified place
	 */
	public static long findFibonacciAt (long place) {

		long value = place;

		double Gs1 = Math.pow(1.618034, value);
		double Gs2 = Math.pow(-0.618034, value);
		double root = Math.sqrt(5);

		double num = ((Gs1-Gs2)/root);

		return (Math.round(num));

	}
	/**
	 * Generate <i>java.awt.Color</i> variable with random (r, g, b) parameters
	 * @return Random <i>Color</i> variable
	 */
	public static Color randomColor() {

		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();

		Color fieldColor = new Color(r, g, b);

		return fieldColor;
	}
	
	public static int randomInt(int range) {
		Random rand = new Random();
		if(range!=0) {return rand.nextInt(range);}
		else {return rand.nextInt();}
	}
	public static int randomInt(int rangeMin, int rangeMax) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		if(rangeMin!=0 || rangeMax!=0) {return rand.nextInt(rangeMax - rangeMin)+rangeMin;}
		else {return rand.nextInt();}
	}
	/**
	 * Centers your Frame on screen
	 * Does not use frame.setLocationRelativeTo(null)
	 * @param frame The name of your <i>Frame</i> variable
	 */		
	public static void placeAtCenter(Frame frame) {
		Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
		int Dx = (int) screenSize.getWidth();
		int Dy = (int) screenSize.getHeight();
		frame.setLocation(Dx/2 - frame.getSize().width/2, Dy/2 - frame.getSize().height/2-40);

	}
	/**
	 * Centers your Frame on screen
	 * Does the same thing as frame.setLocationRelativeTo(null)
	 * @param frame The name of your <i>Frame</i> variable
	 */
	public static void center(Frame frame) {
		frame.setLocationRelativeTo(null);
	}
	/**
	 * Centers your Frame on screen and resizes if (idk why i added this)
	 * Does not use frame.setLocationRelativeTo(null)
	 * @param frame The name of your <i>Frame</i> variable
	 * @param width Desired width
	 * @param height Desired height
	 */
	public static void centerAndResize(Frame frame, int width, int height) {
		Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
		int Dx = (int) screenSize.getWidth();
		int Dy = (int) screenSize.getHeight();
		frame.setBounds(new Rectangle(Dx/2 - frame.getSize().width/2, Dy/2 - frame.getSize().height/2-40, width, height));

	}
	/**
	 * Returns your ip adress
	 * 
	 * @param site Optional. A site that will ONLY contain your ip or any other string.
	 * if set to 'null', the function reaches "https://api.ipify.org/" to get the ip.
	 */
	public static String getIp(String site) throws Exception {
		String tmp = null;
		try {
			if(site = null) throw new Exception();
			
			Scanner sc = new Scanner(new URL(site).openStream(),"UTF-8");
			tmp=sc.next();
			sc.close();
			return tmp;
		} catch (Exception e1) {
			try {
				Scanner sc1 = new Scanner(new URL("https://api.ipify.org/").openStream(),"UTF-8");
				tmp=sc1.next();
				sc1.close();
				return tmp;
			} catch (Exception e2) {
				throw new Exception("The site is probably unavalable!  "+ e2.getStackTrace());
			}
		}
	}
	/**
	 * Add input tip on your TextField
	 * @param field field to add tip
	 * @param tip the tip to add
	 */	
	public static void setFieldTip (final JTextField field, final String tip) {
		field.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (field.getText().isEmpty()) {
					field.setText(tip);
				}else return;
			}
			public void focusGained(FocusEvent e) {
				System.out.println(field.getText());
				if(field.getText().equals(tip)) {
					field.setText(null);
				}
			}
		});
	}
	/**
	 * Sets text color of JLabel to red and
	 * als sets text to provided string
	 * @param label Labet to use
	 * @param text Desired text
	 */
	public static void errorLabel(JLabel label, String text) {
		label.setForeground(Color.RED);
		label.setText(text);
	}
	/**
	 * Sets text color of JLabel to green and
	 * als sets text to provided string
	 * @param label Labet to use
	 * @param text Desired text
	 */
	public void successLabel(JLabel label, String text) {
		label.setForeground(Color.GREEN);
		label.setText(text);
	}
	
	/**
	 * Probably the most useful function
	 * 
	 * Does the same as Thread.sleep(int millis), but keeps your code cleaner
	 * as you don't need to catch an InterruptedException.
	 * @param millis Amount of milliseconds to sleep
	 */
	public static void easySleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.err.println("The thread was interrupted!\nIf it was done on purpose, please use regular sleep method");
		}
	}
	/**
	 * Probably the most useful function
	 * 
	 * Does the same as Thread.sleep(long millis, int nanos), but keeps your code cleaner
	 * as you don't need to catch an InterruptedException.
	 * @param millis Amount of milliseconds to sleep
	 * @param nanos Amount of nanoseconds to sleep
	 */
	public static void easySleep(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.err.println("The thread was interrupted!\nIf it was done on purpose, please use regular sleep method");
		}
	}
}
