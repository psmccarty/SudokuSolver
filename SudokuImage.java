import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Shows the work done by SudokuSolver with a graphical window so it can be understood by humans
 * 
 * @author Patrick McCarty
 *
 */
public class SudokuImage {
  
  public static ArrayList<JLabel> labels; // stores all numbers of the puzzle
  public static Border border; // the border each square
  public static JFrame frame; // the window that will display everything
  
  /**
   * Sets up the Sudoku puzzle so the graphics can be implemented appropriately
   * @param nums the array representing the puzzle
   */
  public static void setUpPuzzle(int[] nums) {
    labels = new ArrayList<JLabel>(); // stores each label. A label is one cell in the puzzle
    border = BorderFactory.createLineBorder(Color.black, 2); // the boarder each cell will have
    frame = new JFrame(); // the frame where labels will be placed
    
    frame.setTitle("Sudoku Solver");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(810, 810);
    frame.setLayout(new GridLayout(9, 9)); // 9 by 9 grid layout just like a Sudoku puzzle
    
    for(int i = 0; i < nums.length; i++) { // loop through the entire puzzle and create labels 
      labels.add(new JLabel());
      if(nums[i] == 0) {
        labels.get(i).setText(" "); // value of zero means it is an empty square on the puzzle
      }
      else {
        labels.get(i).setText(Integer.toString(nums[i]));
      }
      
      labels.get(i).setBorder(border);
      labels.get(i).setHorizontalAlignment(JLabel.CENTER);
      labels.get(i).setFont(new Font("Arial", Font.PLAIN, 25));
      frame.add(labels.get(i));
    }
    frame.setVisible(true);
  }
  
  /**
   * Updates the label associated with the inputed index. Sets the text to its new number and
   * changes the background if necessary
   * 
   * @param indx index of the label
   */
  public static void updatePuzzle(int indx) {
    try {
      Thread.sleep(10); // slow down the program so it can be seen by a human
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // the value that this label has is 0 so it should be blank and be colored red
    if(SudokuSolver.getVal(indx) == 0) {
      labels.get(indx).setText(" ");
      labels.get(indx).setBackground(Color.red);
      labels.get(indx).setOpaque(true);
    }
    // the value that this label has is 10 so it should show 10 and be colored red
    else if(SudokuSolver.getVal(indx) == 10) {
      labels.get(indx).setText(Integer.toString(SudokuSolver.getVal(indx)));
      labels.get(indx).setBackground(Color.red);
      labels.get(indx).setOpaque(true);
    }
    // the value is a number between 1-9 so it should show the number and be colored green
    else {
      labels.get(indx).setText(Integer.toString(SudokuSolver.getVal(indx)));
      labels.get(indx).setBackground(Color.green);
      labels.get(indx).setOpaque(true);
    }
  }
}
