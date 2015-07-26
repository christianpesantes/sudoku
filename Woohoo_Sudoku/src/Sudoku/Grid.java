package Sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

//																class: Grid
//-----------------------------------------------------------------------------
/**	
 *	process all the logic at grid level 
 */
public class Grid 
{
	
	public static final int _HINTS_IN_RANDOM_GAME = 9;		//	[constant] number of hints in a random game
	private static final int _MAX_ATTEMPTS_FOR_RANDOM_CELL = 30;	//	[constant] max attempts to choose random cell
	
	public static final int _ROWS = 9;	//	[constant] number of rows
	public static final int _COLS = 9;	//	[constant] number of columns
	
	public static final int _MAX_CELL_VALUE = 9;	//	[constant] max value for a cell 
	public static final int _MIN_CELL_VALUE = 0;	//	[constant] min value for a cell
		
	private static Cell [] [] _grid = new Cell [_ROWS] [_COLS];		//	grid for the game
	private static int _input = 1;									// 	value to be used as input
	
	private static final String _INPUT_DIRECTORY = "input";		//	[constant] name of folder for input
	private static final String [] _EMPTY_INPUT_FOLDER			//	[constant] message when input folder is empty
								= {"input folder is empty!"};
	
	private static LinkedList<String> _input_files = new LinkedList<String>();	//	list of files in input folder
	
	
	//												Grid: getters / setters
	//-------------------------------------------------------------------------
	public static int Get_input() { return _input; }
	public static void Set_input(int input) { Grid._input = input; }
	public static void Set_Cell_Style(GUI_Cell style, int i, int j) { _grid[i][j].Set_Style(style); }
	public static void Set_Cell_User_Value(int user, int i, int j) { _grid[i][j].Set_user_value(user); }
	
	
	
	//														Grid: Get_Cell
	//-------------------------------------------------------------------------
	/**
	 * 	returns cell at the specified location; throws error if indexes are out of bound
	 *	@param i	:	index for rows
	 * 	@param j	:	index for columns
	 * 	@return		:	cell located at given coordinates
	 * 	@throws ArrayIndexOutOfBoundsException	: when out of range
	 */
	public static Cell Get_Cell(int i, int j)
	{			
		return _grid[i][j];
	}

	//														Grid: Set_Cell
	//-------------------------------------------------------------------------
	/**
	 * 	sets the value of a cell at specified location
	 * 	@param c	:	cell to be added
	 * 	@param i	:	index for rows
	 * 	@param j	:	index for columns
	 * 	@throws ArrayIndexOutOfBoundsException	:	when out of range
	 */
	public static void Set_Cell(boolean immutable, int user, int calc, int i, int j)
	{
		_grid[i][j].Set_immutable(immutable);
		_grid[i][j].Set_user_value(user);
		_grid[i][j].Set_calculated_value(calc);
	}
	

	
	//											Grid: Check_Input_Folder
	//-------------------------------------------------------------------------
	/**
	 * 	checks the input folder for files
	 * 	@return	:	how many files in the input folder
	 */
	public static int Check_Input_Folder()
	{
		File root = new File(_INPUT_DIRECTORY);		
		File[] files = root.listFiles();
		
		if(files == null) return 0;
		if(files.length == 0) return 0;
		
		for(int i=0; i<files.length; i++) _input_files.add(files[i].toString());
		return files.length;
	}
	
	//											Grid: Get_Input_Files
	//-------------------------------------------------------------------------
	/**
	 * 	the list of files that could possibly contain puzzles for the game
	 * 	@return	:	returns a string [] with the list of files
	 */
	public static String [] Get_Input_Files()
	{
		if(_input_files.size() == 0) return _EMPTY_INPUT_FOLDER;
		
		String [] str = _input_files.toArray(new String[0]);
		return str;
	}
	
	
	//														Grid: Read_File
	//-------------------------------------------------------------------------
	/**
	 * 	reads a file, and if it is valid information, it is used to populate the grid
	 * 	@param file	:	file to be read
	 *  @return		:	TRUE if the file can be read
	 */
	public static boolean Read_File (String file)
	{
		Scanner scan = null;
		
		try { scan = new Scanner(new FileInputStream(file)); }
		catch (FileNotFoundException e) 
		{ return false; }
		
		String str = scan.next();
		if(str.length() != (Grid._COLS * Grid._ROWS)){ scan.close(); return false;}
		
		int k = 0;
		for(int i=0; i<Grid._ROWS; i++)
		{
			for(int j=0; j<Grid._COLS; j++)
			{			
				if(!Character.isDigit(str.charAt(k))) { scan.close(); return false; }
	
				int x = Character.digit(str.charAt(k++), 10);
				
				boolean flag = false;
				if(x != 0) flag = true;
				
				Grid._grid[i][j]=new Cell(flag, x, x);
			}
		}
		scan.close();
		return true;
	}
	
	//												Grid: Generate_Random_Game
	//-------------------------------------------------------------------------
	/**
	 * 
	 *  very first cell is generated randomly and then Solve_Grid is called 
	 *  to solve the rest of the grid generates a random grid; 
	 *  uses _HINTS_IN_RANDOM_GAME to determine how many hints to create
	 */
	public static void Generate_Random_Game()
	{
		Random rand = new Random();	
		
		for(int i=0; i<_ROWS; i++)
		{
			for(int j=0; j<_COLS; j++) 
			{
				_grid[i][j] = new Cell(false, 0, 0);
			}
		}
		
		_grid[0][0].Set_calculated_value(rand.nextInt(_MAX_CELL_VALUE - _MIN_CELL_VALUE + 1));
		//_grid[0][0].Set_user_value(_grid[0][0].Get_calculated_value());
		//_grid[0][0].Set_immutable(true);
		//_grid[0][0].Set_Style(GUI_Cell.HINT);
		Solve_Grid(0);
		
		for(int i = 0; i<_HINTS_IN_RANDOM_GAME; i++)
		{
			int x = rand.nextInt(_MAX_CELL_VALUE - _MIN_CELL_VALUE);
			int y = rand.nextInt(_MAX_CELL_VALUE - _MIN_CELL_VALUE);

			_grid[x][y].Set_immutable(true);
			_grid[x][y].Set_Style(GUI_Cell.HINT);
			_grid[x][y].Set_user_value(_grid[x][y].Get_calculated_value());
		}
	}
	
	//														Grid: Reset_Game
	//-------------------------------------------------------------------------
	/**
	 * 	sets all user values to zero and sets all styles to NORMAL; except for hints
	 */
	public static void Reset_Game()
	{
		for(int i=0; i<_ROWS; i++)
		{
			for(int j=0; j<_COLS; j++)
			{
				_grid[i][j].Set_Style(GUI_Cell.NORMAL);
				_grid[i][j].Set_user_value(0);
				
				if(_grid[i][j].Is_immutable())
				{
					_grid[i][j].Set_Style(GUI_Cell.HINT);
					_grid[i][j].Set_user_value(_grid[i][j].Get_calculated_value());
				}
			}
		}
	}
	
	//														Grid: Print_Grid
	//-------------------------------------------------------------------------
	/**	
	 * 	print the grid to console
	 */
	public static void Print_Grid()
	{
		for(int i=0; i<_ROWS; i++) 
		{
			for(int j=0; j<_COLS; j++)
			{ 
				System.out.println(" " + _grid[i][j] + " ");  
			}
			System.out.println();
		}
	}
	
	//													Grid: Get_Print_Value
	//-------------------------------------------------------------------------
	/**
	 * 	checks whether to display the user value or show an empty string
	 * 	@param i	:	index for row
	 * 	@param j	:	index for column
	 * 	@return		:	either an empty sting, or the user value
	 */
	public static String Get_Print_Value(int i, int j)
	{
		if(_grid[i][j].Get_Style().equals(GUI_Cell.NORMAL)) return "";
		return Integer.toString(_grid[i][j].Get_user_value());
	}
	
	
	//														Grid: Solve_Grid
	//-------------------------------------------------------------------------
	/**
	 *  uses brute force algorithm to solve the grid
	 *  if the current cell can not hold a number between 1 and 9 it will backtrack and solve from there
	 * 	attempts to solve the grid; will look for only one solution
	 * 	@param square	:	current cell being processed
	 * 	@return			:	returns TRUE if the grid was solved	
	 */
	public static boolean Solve_Grid(int square)
	{
		if(square >= 81) return true;
		
		int i = square / 9;
		int j = square % 9;

		if(_grid[i][j].Is_immutable()) return Solve_Grid(square + 1);
		if(_grid[i][j].Get_calculated_value() != 0) return Solve_Grid(square + 1);
		
		for(int temp = 1;temp <= 9;temp++)
		{
			if(Check_Solution(i,j,temp) == true)
			{
				_grid[i][j].Set_calculated_value(temp);
				if(Solve_Grid(square+1)) return true;
			}
		}

		_grid[i][j].Set_calculated_value(0);
		return false;
	}
	

	//													Grid: Check_Solution
	//-------------------------------------------------------------------------
	/**
	 *	calls each function to check the horizontal, vertical, and blocks 
	 *  if one of the functions fails it automatically fails all checks
	 * 	@param i	:	index for row
	 * 	@param j	:	index for column
	 * 	@param n	:	value to be it tested
	 * 	@return		:	TRUE if the value can be used
	 */
	public static boolean Check_Solution(int i, int j, int n)
	{
		if(!Check_Horizontal(i, j, n) || !Check_Vertical(i, j, n)  || !Check_Block(i, j, n)) return false;
		return true;
	}
	
	//													Grid: Check_Horizontal
	//-------------------------------------------------------------------------
	/**
	 * 	checks the entire row to verify if the a number can be used
	 * 	@param i	:	index for row
	 * 	@param j	:	index for column
	 * 	@param n	:	value to be tested
	 * 	@return		:	returns TRUE if the number can be used
	 */
	public static boolean Check_Horizontal(int i, int j, int n)
	{
		for(int x = 0; x<_COLS; x++)
		{
			if(x != j)
			{
				if(n == _grid[i][x].Get_calculated_value())	return false;
			}
		}
		return true;
	}
	
	//													Grid: Check_Vertical
	//-------------------------------------------------------------------------
	/**
	 *	checks the entire column to verify if the a number can be used
	 * 	@param i	:	index for row
	 * 	@param j	:	index for column
	 * 	@param n	:	value to be tested
	 * 	@return		:	TRUE of the value can be used
	 */
	public static boolean Check_Vertical(int i, int j, int n)
	{
		for(int x = 0; x<_ROWS; x++)
		{
			if(x != i)
			{
				if(n == _grid[x][j].Get_calculated_value())	return false;
			}
		}
		return true;
	}
	
	
	//													Grid: Current_Block
	//-------------------------------------------------------------------------
	/**
	 * 	checks if the value can be used within the current block
	 * 	@param x	:	index for rows
	 * 	@param y	:	index for columns
	 * 	@param minx	:	index for block in x
	 * 	@param miny	:	index for block in y
	 * 	@param temp	:	
	 * 	@return		:	TRUE if the value can be used
	 */
	public static boolean Current_Block(int x, int y, int minx, int miny, int temp)
	{
		for(int i = minx;i<minx+3;i++)
		{
			for(int j = miny; j<miny+3;j++)
			{
				if(x != i && y != j){
					if(temp == _grid[i][j].Get_calculated_value()) return false;
				}
			}
		}
		return true;
	}
	
	
	//													Grid: Check_Block
	//-------------------------------------------------------------------------
	/**
	 * 	checks the entire column to verify if the a number can be used
	 * 	@param x	:	index for row
	 * 	@param y	:	index for column
	 * 	@param temp	:	value to be tested
	 * 	@return		:	TRUE if it can be used
	 */
	public static boolean Check_Block(int x, int y, int temp)
	{
		int block_x = x / 3, block_y=y / 3;
		int minx = block_x * 3, miny = block_y * 3;
		
		return Current_Block(x,y,minx, miny, temp);
	}
	
	
	//														Grid: Setup_Game
	//-------------------------------------------------------------------------
	/**
	 *	first, it checks how many files are in the input folder; then it validates
	 *	the files, eliminating corrupted files, impossible games, etc; if all fails,
	 *	then it creates a random game
	 * 	@return	:	returns the index of the file to be used as the starting grid (typically zero)	
	 */
	public static int Setup_Game()
	{
		int num = Check_Input_Folder(); 
		if(num == 0) 
		{
			Generate_Random_Game(); 
			return num; 
		}
		
		ListIterator<String> itr = _input_files.listIterator();
		  
		while(itr.hasNext())
		{
			String file = itr.next();
			
			if(!Read_File(file))
			{ 
				itr.remove();
				continue;
			}

			if(!Solve_Grid(0)) 
			{
				itr.remove();
			}
			
		}
		
		if(_input_files.isEmpty())
		{ 
			Generate_Random_Game();
			return 0; 
		}
		
		Read_File(_input_files.get(0));
		Solve_Grid(0);

		return 0;
	}
	
	//												Grid: Solve_Random_Cell
	//-------------------------------------------------------------------------
	/**
	 * 	solves a random unsolved cell in the grid
	 * 	@return	:	returns TRUE if the action was possible
	 */
	public static boolean Solve_Random_Cell()
	{	
		Random rand = new Random();
		int attempts = 0;
		while (attempts < _MAX_ATTEMPTS_FOR_RANDOM_CELL )
		{
			int x= rand.nextInt(_MAX_CELL_VALUE);
			int y = rand.nextInt(_MAX_CELL_VALUE);
			attempts++;
		
			if (_grid[x][y].Get_Style().equals(GUI_Cell.HINT)) continue;
			if (_grid[x][y].Get_Style().equals(GUI_Cell.GOOD)) continue;  
		
			_grid[x][y].Set_user_value(_grid[x][y].Get_calculated_value());
			_grid[x][y].Set_Style(GUI_Cell.GOOD);
			return true;
		}
	
		for (int i=0;i<_ROWS;i++)
		{	
			for (int j=0;j<_COLS;j++)
			{
				if (_grid[i][j].Get_Style().equals(GUI_Cell.HINT)) continue;
				if (_grid[i][j].Get_Style().equals(GUI_Cell.GOOD)) continue;  
				
				_grid[i][j].Set_user_value(_grid[i][j].Get_calculated_value());
				_grid[i][j].Set_Style(GUI_Cell.GOOD);
				return true;
			}
		}

		return false;
}
	
	
	//															Grid: Is_Full
	//-------------------------------------------------------------------------
	/**
	 * 	checks if the grid has been completed
	 * 	@return	:	TRUE if there are no cells left marked as NORMAL
	 */
	public static boolean Is_Full()
	{
		for(int i = 0; i<_ROWS;i++)
		{
			for(int j = 0; j < _COLS;j++)
			{
				if(_grid[i][j].Get_Style().equals(GUI_Cell.NORMAL)) return false;
			}
		}
		return true;
	}
	
}
