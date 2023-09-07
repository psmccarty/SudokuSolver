run: SudokuTester.class
	java SudokuTester

SudokuTester.class: SudokuImage.java SudokuSolver.java SudokuTester.java
	javac SudokuTester.java	

clean:
	rm *.class
