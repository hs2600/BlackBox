// MenuFrame.java
// CSU Dominguez Hills / CSC 123
// Extra credit 3 (Black Box Game)
// Written by Horacio Santoyo
// 12/03/16

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Create the menu
public class MenuFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuFrame(BlackBox c){
		// Construct menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createSizeMenu());
	}

	// Creates the File menu.
	public JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createFileResetItem());
		menu.add(createFilePracticeItem());		
		menu.add(createFileExitItem());
		return menu;
	}

	public JMenu createSizeMenu(){
		JMenu menu = new JMenu("Hint");
		menu.add(endGame());
		menu.add(showBumpers());
		menu.add(hideBumpers());
		menu.add(clearGuess());
		return menu;		
	}

	// Creates the File->Exit menu item and sets its action listener.
	public JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}

	public JMenuItem createFileResetItem(){
		JMenuItem item = new JMenuItem("New game");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.resetBoard();
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}	

	public JMenuItem createFilePracticeItem(){
		JMenuItem item = new JMenuItem("New practice game");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.practiceBoard();
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}		

	public JMenuItem endGame(){
		JMenuItem item = new JMenuItem("Reveal and end game");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.endGame();
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}

	public JMenuItem showBumpers(){
		JMenuItem item = new JMenuItem("Show bumpers");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.hint(true);
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}

	public JMenuItem hideBumpers(){
		JMenuItem item = new JMenuItem("Hide bumpers");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.hint(false);
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}	

	public JMenuItem clearGuess(){
		JMenuItem item = new JMenuItem("Clear guesses");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				BlackBox.clearGuess();
			}
		}
		ActionListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
}