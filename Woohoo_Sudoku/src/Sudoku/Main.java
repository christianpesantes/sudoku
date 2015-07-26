package Sudoku;

//																class: Main
//-----------------------------------------------------------------------------
/**	
 *	runs the game
 */
public class Main 
{
	//															Main: main
	//-------------------------------------------------------------------------
	/**
	 * 	starts sudoku game
	 *	@param args	:	not used
	 */
	public static void main(String[] args) 
	{
		GUI_Sudoku sudoku = new GUI_Sudoku();
		sudoku.setVisible(true);
	}
}
