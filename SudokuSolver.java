import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Solves a 9-by-9 Sudoku puzzle
 * 
 * @author Patrick McCarty
 *
 */
public class SudokuSolver {
  private static HashMap<Integer, ArrayList<Integer>> rows; // contains all 9 rows of the board
  private static HashMap<Integer, ArrayList<Integer>> cols; // contains all 9 cols of the board
  private static HashMap<Integer, ArrayList<Integer>> blocks; // contains all 9 blocks of the board
  private static int[] vals; // the array that is the actual puzzle
  private static ArrayList<Integer> missingNums; // the blank squares that need to be filed in
  
  /**
   * Sets up the puzzle in a way so it can be solved
   * 
   * @param arr the Sudoku puzzle in the form of a 1 dimensional array going left to right and 
   * starting from the top and making its way to the bottom. Row 1 column 1 is the first element 
   * in the array and row 9 column 9 is the last element in the array
   */
  private static void boardSetup(int[] arr) {
    rows = new HashMap<Integer, ArrayList<Integer>>();
    cols = new HashMap<Integer, ArrayList<Integer>>();
    blocks = new HashMap<Integer, ArrayList<Integer>>();
    missingNums = new ArrayList<Integer>();
    vals = Arrays.copyOf(arr, arr.length);
    
    // populates rows, cols and blocks with keys 0-8 and values of empty Arraylists in each key
    for(int i = 0; i < 9; i++) {
      rows.put(i, new ArrayList<Integer>());
      cols.put(i, new ArrayList<Integer>());
      blocks.put(i, new ArrayList<Integer>());
    }
    
    // loops through the contents of input array and adds it to its speciffic row, col and block
    for(int i = 0; i < arr.length; i++) {
      if(arr[i] != 0) {
        rows.get(i/9).add(arr[i]);
        cols.get(i%9).add(arr[i]);
        blocks.get(findBlock(i)).add(arr[i]);
      }
      if(arr[i] == 0) {
        missingNums.add(i);
      }
    }
  }
  
  /**
   * Checks if the number trying to be placed in the given index is already present in the row, 
   * column or block
   * 
   * @param indx the index in the vals array
   * @param val the number being tried
   * @return true if the number can be placed at that index, false otherwise
   */
  public static boolean checkPossible(int indx, int val) {
    if(val == 0) {
      return false;
    }
    int row = indx / 9;
    int col = indx % 9;
    int block = findBlock(indx);
    // check if the number can be placed at that spot on the board
    if(rows.get(row).contains(val) || cols.get(col).contains(val) 
        || blocks.get(block).contains(val)) {
      return false;
    }
    return true;
  }
  
  /**
   * Finds which 3-by-3 block the given index belongs to. Blocks are read left to right 
   * then going to the next line
   * 
   * @param i index of the number
   * @return the block from 0-8 that it belongs too
   */
  private static int findBlock(int i) {
    int m = i % 9;
    int d = i / 9;
    if(m < 3 && d < 3) {
      return 0;
    }
    else if(m < 6 && d < 3) {
      return 1;
    }
    else if(m < 9 && d < 3) {
      return 2;
    }
    else if(m < 3 && d < 6) {
      return 3;
    }
    else if(m < 6 && d < 6) {
      return 4;
    }
    else if(m < 9 && d < 6) {
      return 5;
    }
    else if(m < 3 && d < 9) {
      return 6;
    }
    else if(m < 6 && d < 9) {
      return 7;
    }
    else if(m < 9 && d < 9) {
      return 8;
    }
    else {
      throw new NoSuchElementException(); // a problem occured
    }
  }
  
  /**
   * Updates the values of the Hashmaps that keep track of what numbers are in which rows, columns 
   * and blocks using the new values of the vals array
   */
  private static void updateVals() {
    rows.clear();
    cols.clear();
    blocks.clear();
    
    for(int i = 0; i < 9; i++) {
      rows.put(i, new ArrayList<Integer>());
      cols.put(i, new ArrayList<Integer>());
      blocks.put(i, new ArrayList<Integer>());
    }
    
    for(int i = 0; i < vals.length; i++) {
      if(vals[i] != 0) {
        rows.get(i / 9).add(vals[i]);
        cols.get(i % 9).add(vals[i]);
        blocks.get(findBlock(i)).add(vals[i]);
      }
    }
  }
  
  /**
   * Prints the solved Sudoku puzzle in an easy to interpret way
   */
  private static void printResult() {
    System.out.print(" -----------------");
    for(int i = 0; i < vals.length; i++) {
      if(i % 9 == 0) {
        System.out.println();
        System.out.print("|");
      }
      
      if(i % 9 == 8) {
        System.out.print(vals[i] + "|");
      }
      else {
        System.out.print(vals[i] + " ");
      }
    }
    System.out.println();
    System.out.print(" -----------------");
  }
  
  /**
   * Solves a 9-by-9 Sudoku puzzle
   * 
   * @param arr the Sudoku puzzle in the form of a 1 dimensional array
   * @param showWork whether or not the work done by the program is shown in a new window
   */
  public static void solve(int[] arr, boolean showWork) {
    boardSetup(arr); // set up the board so it can be solved
    
    if(showWork) {
      SudokuImage.setUpPuzzle(arr);
    }
    for(int i = 0; i < vals.length; i++) {
      updateVals(); // update what numbers are in which rows/columns/blocks
      if(i < 0) {
        break;
      }
      if(!missingNums.contains(i)) { // a number given by the puzzle. Move on to next iteration
        continue;
      }
      vals[i]++;
      
      if(showWork) {
        SudokuImage.updatePuzzle(i);
      }
      if(vals[i] > 9) { // no number can be placed at this spot
        vals[i] = 0; // reset this number
        if(showWork) {
          SudokuImage.updatePuzzle(i);
        }
        if(i == missingNums.get(0)) {
          System.out.println("unsolvable");
          break; // the first missing number cannot be other than zero so no solution is possible
        }
        else {
          i--;
          // go back to the previous missing number
          while(!missingNums.contains(i)) {
            if(i < 0) {
              break;
            }
            i--;
          }
          i--;
          continue;
        }
      }
      
      // find the first possible value for this position and update vals array
      while(!checkPossible(i, vals[i])) {
        vals[i]++;
        if(showWork) {
          SudokuImage.updatePuzzle(i);
        }
        if(vals[i] > 9) { // no possible number for this position
          vals[i] = 0;
          if(showWork) {
            SudokuImage.updatePuzzle(i); 
          }
          break;
        }
      }
      // go back to the previous missing number
      if(vals[i] == 0) {
        i--;
        while(!missingNums.contains(i)) {
          if(i < 0) {
            System.out.println("unsolvable");
            break;
          }
          i--;
        }
        i--;
        continue;
      }
    }
    System.out.println("done!");
    printResult();
  }
  
  /**
   * Gets the number at the specified index of the Sudoku board
   * 
   * @param indx index of the desired number
   * @return the contents of the board at the desired location
   */
  public static int getVal(int indx) {
    return vals[indx];
  }
}
