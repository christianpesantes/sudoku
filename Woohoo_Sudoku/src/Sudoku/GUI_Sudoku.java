package Sudoku;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//															class: GUI_Sudoku
//-----------------------------------------------------------------------------
/**	
*	controls GUI and handles events
*/
public class GUI_Sudoku extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;	//	[constant] required for serialize
	
	public static final String _END_OF_GAME_MSG = "Thanks for playing!";	// 	[constant] bye bye message
	
	public static final String _WINDOW_TITLE = "Team Woohoo: Sudoku";	// 	[constant] window's title
	public static final int _WINDOW_WIDTH = 870;						//	[constant] window's width
	public static final int _WINDOW_HEIGHT = 450;						//	[constant] window's height
	public static final Color _BKGRND_COLOR = new Color(50, 50, 50);	//	[constant] window's background color
	
	public static final String [] _TEAM_MEMBERS = {"Parita Patel", 		// [constant] team members' names
													"Autumnn Wooten", 
													"Michelle George", 
													"Christian Pesantes Torres"};
	
	private static JLabel [] _labels = new JLabel [GUI_Label.values().length];		//	for all labels in the game
	private static JButton [] _buttons = new JButton [GUI_Button.values().length];	//	for all buttons in the game
	
	private static JComboBox<String> _combox_file;		//	for input combobox
	private static int _file_index_selected;			//	index of file chosen
	
	private static JButton [] [] _btn_game_grid = new JButton [Grid._ROWS] [Grid._COLS];	//	buttons for grid
	
	private static JButton [] _btn_input = new JButton [Grid._MAX_CELL_VALUE];			// 	buttons for input
	private static GUI_Cell [] _input_styles = new GUI_Cell [Grid._MAX_CELL_VALUE];		// 	styles for input buttons
	
	private static javax.swing.Timer _timer;	//	clock to display as a label
	
	private static int _second = 0;		//	keeps track of seconds
	private static int _minute = 0;		//	keeps track of minutes
	
	private static int _PENATLY_FOR_HELP = 20;	//	[constant] penalty for help (in seconds)
	
	
	//												GUI_Sudoku: constructor
	//-------------------------------------------------------------------------
	/**
	 *	adds elements to the window; defines handlers
	 */
	public GUI_Sudoku()
	{
		//														window setup
		//---------------------------------------------------------------------
		setTitle(_WINDOW_TITLE);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize(_WINDOW_WIDTH, _WINDOW_HEIGHT);
		setLayout(null);
		getContentPane().setBackground(_BKGRND_COLOR);
		
		//														game setup
		//---------------------------------------------------------------------
		_file_index_selected = Grid.Setup_Game();
		
		_timer = new javax.swing.Timer(1000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{ _labels[GUI_Label.TIMER.Get_id()].setText(Add_Time(0,1));	}
		});
		

		//													components setup
		//---------------------------------------------------------------------
		for(GUI_Label lbl : GUI_Label.values())  add(Setup_Label(lbl));
		for(GUI_Button btn : GUI_Button.values()) add(Setup_Button(btn));
		add(Setup_ComboBox());
		
		for(int i=0; i<Grid._MAX_CELL_VALUE; i++)
		{ 
			add(Setup_Input_Button(i));
			_btn_input[i].addActionListener(this);
		}
		for(int i=0; i<Grid._ROWS; i++) 
		{ 
			for(int j=0; j<Grid._COLS; j++) 
			{ 
				add(Setup_Game_Grid(i,j)); 
				_btn_game_grid[i][j].addActionListener(this);
			} 
		} 
		
		if(Grid.Is_Full()) 
		{
			if(_timer.isRunning()) _timer.stop();
			JOptionPane.showMessageDialog(new JFrame(), _END_OF_GAME_MSG,
					_WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		//									ActionListener: GUI_Button.QUIT
		//---------------------------------------------------------------------
		_buttons[GUI_Button.QUIT.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				if(JOptionPane.showConfirmDialog(rootPane, "quit game?", _WINDOW_TITLE, 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) ==JOptionPane.OK_OPTION) 
				{ System.exit(0); } 
			}
		});
		
		
		//									ActionListener: GUI_Button.RANDOM
		//---------------------------------------------------------------------
		_buttons[GUI_Button.RANDOM.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				if(_timer.isRunning())
				{
					if(JOptionPane.showConfirmDialog(rootPane, "start a new random game?", _WINDOW_TITLE, 
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==JOptionPane.OK_OPTION) 
					{ 
						Grid.Generate_Random_Game();
						
						_timer.stop();
						_labels[GUI_Label.TIMER.Get_id()].setText(Set_Time(0, 0));
					}
				}
				else Grid.Generate_Random_Game();
				
				
				for(int i=0; i<Grid._ROWS; i++)
				{
					for(int j=0; j<Grid._COLS; j++)
					{
						Update_Game_Grid_Button(i, j);
					}
				}
				
				Check_For_End_Of_Game();
			}
		});
		

		//									ActionListener: GUI_Button.CREDITS
		//---------------------------------------------------------------------
		_buttons[GUI_Button.CREDITS.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				JOptionPane.showMessageDialog(rootPane, _TEAM_MEMBERS,
						_WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		//									ActionListener: GUI_Button.SOLVE
		//---------------------------------------------------------------------
		_buttons[GUI_Button.SOLVE .Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 				
				if(JOptionPane.showConfirmDialog(rootPane, "solve current game?", _WINDOW_TITLE, 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==JOptionPane.OK_OPTION) 
				{ 
					if(_timer.isRunning()) _timer.stop();
					
					for(int i=0; i<Grid._ROWS; i++)
					{
						for(int j=0; j<Grid._COLS; j++)
						{
							Grid.Set_Cell_User_Value(Grid.Get_Cell(i, j).Get_calculated_value(), i, j);
							Grid.Set_Cell_Style(GUI_Cell.GOOD, i, j);
							if(Grid.Get_Cell(i, j).Is_immutable()) Grid.Set_Cell_Style(GUI_Cell.HINT, i, j);
							Update_Game_Grid_Button(i, j);
						}
					}
					
					Check_For_End_Of_Game();
					
				} 
			}
		});
		
		
		//									ActionListener: GUI_Button.HELP
		//---------------------------------------------------------------------
		_buttons[GUI_Button.HELP.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 				
				if(Grid.Solve_Random_Cell())
				{
					if(!_timer.isRunning()) _timer.start();
					_labels[GUI_Label.TIMER.Get_id()].setText(Add_Time(0, _PENATLY_FOR_HELP));
					
					for(int i=0; i<Grid._ROWS; i++)
					{
						for(int j=0; j<Grid._COLS; j++)
						{
							Update_Game_Grid_Button(i, j);
						}
					}
					
					Check_For_End_Of_Game();
				}
				
			}
		});
		
		
		//									ActionListener: GUI_Button.PAUSE
		//---------------------------------------------------------------------
		_buttons[GUI_Button.PAUSE.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 				
				if(_timer.isRunning())
				{
					_timer.stop();
					JOptionPane.showMessageDialog(rootPane, "game paused!", _WINDOW_TITLE, 
							JOptionPane.INFORMATION_MESSAGE);
					_timer.start();
				}
				else
				{
					JOptionPane.showMessageDialog(rootPane, "game paused!", _WINDOW_TITLE, 
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		
		//									ActionListener: GUI_Button.RESTART
		//---------------------------------------------------------------------
		_buttons[GUI_Button.RESTART.Get_id()].addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 				
				if(JOptionPane.showConfirmDialog(rootPane, "restart game?", _WINDOW_TITLE, 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==JOptionPane.OK_OPTION) 
				{ 
					_labels[GUI_Label.TIMER.Get_id()].setText(Set_Time(0, 0)); 
					if(_timer.isRunning()) _timer.stop();
					
					Grid.Reset_Game();

					for(int i=0; i<Grid._ROWS; i++)
					{
						for(int j=0; j<Grid._COLS; j++)
						{
							Update_Game_Grid_Button(i, j);
						}
					}	
					Check_For_End_Of_Game();
				}
			}
		});
		
		
		//							ActionListener: GUI_ComboBox.INPUT_FILE
		//---------------------------------------------------------------------
		_combox_file.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				JComboBox<?> cb = (JComboBox<?>) arg0.getSource();
				Grid.Read_File(Grid.Get_Input_Files()[cb.getSelectedIndex()]);
				Grid.Solve_Grid(0);
				
				for(int i=0; i<Grid._ROWS; i++)
				{
					for(int j=0; j<Grid._COLS; j++)
					{
						Update_Game_Grid_Button(i, j);
					}
				}	
				
				_labels[GUI_Label.TIMER.Get_id()].setText(Set_Time(0, 0)); 
				if(_timer.isRunning()) _timer.stop();
				
				Check_For_End_Of_Game();
			}
		});
		
	}
	
	//											GUI_Sudoku: actionPerformed
	//-------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e)
	{
		if(!_timer.isRunning()) _timer.start();
			
        for(int i=0; i<Grid._MAX_CELL_VALUE; i++)
        {
        	if(e.getSource() == _btn_input[i])
        	{
        		Grid.Set_input(i+1);
        		New_Input_Button_Selected(i);
        	}
        }
        
        
        for (int i=0; i<Grid._ROWS; i++)
        {
        	for (int j=0; j<Grid._COLS; j++)
        	{
        		if(e.getSource() == _btn_game_grid[i][j])
        		{	
        			if(Grid.Get_Cell(i, j).Is_immutable()) return;
        			
        			Grid.Set_Cell_User_Value(Grid.Get_input(), i, j);
        			
        			if (Grid.Get_input() == Grid.Get_Cell(i, j).Get_calculated_value())
        				Grid.Set_Cell_Style(GUI_Cell.GOOD, i, j);
        			
        			else 
        				Grid.Set_Cell_Style(GUI_Cell.ERROR, i, j);
        			
        			Update_Game_Grid_Button(i, j);
        		}
        	}
        }
        Check_For_End_Of_Game();
    }
	
	//										GUI_Sudoku: Check_For_End_Of_Game
	//-------------------------------------------------------------------------
	private static void Check_For_End_Of_Game()
	{
		if(Grid.Is_Full()) 
		{
			if(_timer.isRunning()) _timer.stop();
			JOptionPane.showMessageDialog(new JFrame(), _END_OF_GAME_MSG,
					_WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//											GUI_Sudoku: Setup_Label
	//-------------------------------------------------------------------------
	/**
	 * 	handles all labels for the game
	 * 	@param lbl		:	send the type of label along with the definitions
	 *	@return			:	updated component and ready to be added
	 */
	private static JLabel Setup_Label(GUI_Label lbl)
	{
		_labels[lbl.Get_id()] = new JLabel(lbl.Get_style().Get_title());
		_labels[lbl.Get_id()].setBounds(lbl.Get_style().Get_rect());
		Update_Label(lbl);
		return _labels[lbl.Get_id()];
	}
	
	
	//											GUI_Sudoku: Update_Label
	//-------------------------------------------------------------------------
	/**
	 * 	updates font and foreground color for labels
	 * 	@param lbl	:	specifies the label to be updated
	 */
	private static void Update_Label(GUI_Label lbl)
	{
		_labels[lbl.Get_id()].setFont(lbl.Get_style().Get_font());
		_labels[lbl.Get_id()].setForeground(lbl.Get_style().Get_fore_color());
	}
	
	
	//											GUI_Sudoku: Setup_Button
	//-------------------------------------------------------------------------
	/**
	 * 	handles all buttons for the game
	 * 	@param lbl		:	send the type of button along with the definitions
	 *	@return			:	updated component and ready to be added
	 */
	private static JButton Setup_Button(GUI_Button btn)
	{
		_buttons[btn.Get_id()] = new JButton(btn.Get_style().Get_title());
		_buttons[btn.Get_id()].setBounds(btn.Get_style().Get_rect());
		Update_Button(btn);
		return _buttons[btn.Get_id()];
	}
	
	
	//											GUI_Sudoku: Update_Button
	//-------------------------------------------------------------------------
	/**
	 * 	updates the font, background color, and foreground color
	 * 	@param btn	:	specifies the button to be updated
	 */
	private static void Update_Button(GUI_Button btn)
	{
		_buttons[btn.Get_id()].setFont(btn.Get_style().Get_font());
		_buttons[btn.Get_id()].setBackground(btn.Get_style().Get_back_color());
		_buttons[btn.Get_id()].setForeground(btn.Get_style().Get_fore_color());
	}
	
	
	//											GUI_Sudoku: Setup_ComboBox
	//-------------------------------------------------------------------------
	/**
	 * 	handles all comboboxes for the game
	 * 	@param lbl		:	send the type of combobox along with the definitions
	 *	@return			:	updated component and ready to be added
	 */
	private static JComboBox<?> Setup_ComboBox()
	{
		_combox_file = new JComboBox<String>(Grid.Get_Input_Files());
		_combox_file.setSelectedIndex(_file_index_selected);
		_combox_file.setBounds(GUI_ComboBox.INPUT_FILE.Get_style().Get_rect());
		Update_ComboBox();
		return _combox_file;
	}
	
	//											GUI_Sudoku: Update_ComboBox
	//-------------------------------------------------------------------------
	/**
	 * 	updates combobox's font, background color, and foreground color
	 */
	private static void Update_ComboBox()
	{
		_combox_file.setFont(GUI_ComboBox.INPUT_FILE.Get_style().Get_font());
		_combox_file.setBackground(GUI_ComboBox.INPUT_FILE.Get_style().Get_back_color());
		_combox_file.setForeground(GUI_ComboBox.INPUT_FILE.Get_style().Get_fore_color());
	}
	
	
	//											GUI_Sudoku: Setup_Input_Button
	//-------------------------------------------------------------------------
	/**
	 * 	handles input buttons
	 * 	@param i	:	index of the array to be worked with (display will be i+1)
	 * 	@return		:	updated button and ready to be added
	 */
	private static JButton Setup_Input_Button(int i)
	{
		_input_styles[i] = GUI_Cell.ACTIVE;
		if (i==Grid.Get_input()-1)  _input_styles[i] = GUI_Cell.CLICKED;
		
		_btn_input[i] = new JButton(Integer.toString(i+1));
		Rectangle r = (Rectangle) _input_styles[i].Get_style().Get_rect().clone();
		r.x += i*45;
		if(i>=6) r.x += 10;
		else if(i>=3) r.x += 5;
		_btn_input[i].setBounds(r);
		Update_Input_Button(i);
		return _btn_input[i];
	}
	
	
	//										GUI_Sudoku: Update_Input_Button
	//-------------------------------------------------------------------------
	/**
	 * 	updates font, background color, and background color
	 * 	@param i	:	index of input button to be updated
	 */
	private static void Update_Input_Button(int i)
	{
		_btn_input[i].setFont(_input_styles[i].Get_style().Get_font());
		_btn_input[i].setBackground(_input_styles[i].Get_style().Get_back_color());
		_btn_input[i].setForeground(_input_styles[i].Get_style().Get_fore_color());
	}
	
	
	//									GUI_Sudoku: New_Input_Button_Selected
	//-------------------------------------------------------------------------
	/**
	 * 	sets all input buttons to default style; except for the one clicked
	 * 	@param clicked	:	index for the input button clicked
	 */
	private static void New_Input_Button_Selected(int clicked)
	{
		for(int i=0; i<Grid._MAX_CELL_VALUE; i++)
		{
			_input_styles[i] = GUI_Cell.ACTIVE;
			if(i==clicked) _input_styles[i] = GUI_Cell.CLICKED;
				
			Update_Input_Button(i);
		}
	}
	
	
	//											GUI_Sudoku: Setup_Game_Grid
	//-------------------------------------------------------------------------
	/**
	 * 	setups buttons for the game's grid
	 * 	@param i	:	index for row	
	 * 	@param j	:	index for column
	 * 	@return		:	an updated button ready to be used
	 */
	private static JButton Setup_Game_Grid(int i, int j)
	{
		Grid.Set_Cell_Style(GUI_Cell.NORMAL, i, j);
		if(Grid.Get_Cell(i, j).Is_immutable()) Grid.Set_Cell_Style(GUI_Cell.HINT, i, j);
		
		_btn_game_grid[i][j] = new JButton();
		Rectangle r = (Rectangle) Grid.Get_Cell(i, j).Get_Style().Get_style().Get_rect().clone();
		r.x += j*45;
		r.y += i*30;
		if(j>=6) r.x += 10;
		else if(j>=3) r.x += 5;
		if(i>=6) r.y += 10;
		else if(i>=3) r.y += 5;
		_btn_game_grid[i][j].setBounds(r);
		Update_Game_Grid_Button(i, j);
		return _btn_game_grid[i][j];
	}
	
	
	//									GUI_Sudoku: Update_Game_Grid_Button
	//-------------------------------------------------------------------------
	/**
	 * 	update buttons for the game's grid
	 * 	@param i	:	index for row	
	 * 	@param j	:	index for column
	 */
	private static void Update_Game_Grid_Button(int i, int j)
	{
		_btn_game_grid[i][j].setText(Grid.Get_Print_Value(i, j));
		_btn_game_grid[i][j].setFont(Grid.Get_Cell(i, j).Get_Style().Get_style().Get_font());
		_btn_game_grid[i][j].setBackground(Grid.Get_Cell(i, j).Get_Style().Get_style().Get_back_color());
		_btn_game_grid[i][j].setForeground(Grid.Get_Cell(i, j).Get_Style().Get_style().Get_fore_color());
	}
	
	
	//													GUI_Sudoku: Get_Time
	//-------------------------------------------------------------------------
	/**
	 * 	format used: 00:00
	 * 	@return	:	returns string representation of the time
	 */
	public static String Get_Time()
	{
		String min = new String();
		if(_minute < 10) min+="0";
		min += Integer.toString(_minute);
		
		String sec = new String();
		if(_second < 10) sec+="0";
		sec += Integer.toString(_second);
		
		return min + ":" + sec;
	}
	
	//													GUI_Sudoku: Add_Time
	//-------------------------------------------------------------------------
	/**
	 * 	adds to the current time (no negative numbers are allowed)
	 * 	@param min	:	minutes to be added (no limit)
	 * 	@param sec	:	seconds to be added (will add to minutes if over 59)
	 * 	@return		:	returns Get_Timer()
	 */
	public static String Add_Time(int min, int sec)
	{
		if(min < 0) min = 0;
		if(sec < 0) sec = 0;
		
		_minute += min;
		_second += sec;
		
		while(_second > 59) 
		{
			_minute++;
			_second -= 60;
		}
		
		return Get_Time();
	}
	
	//													GUI_Sudoku: Set_Time
	//-------------------------------------------------------------------------
	/**
	 * 	sets time (no negative numbers are allowed)
	 * 	@param min	:	new value for minutes
	 * 	@param sec	:	new value for seconds
	 * 	@return		:	returns Get_Timer()
	 */
	public static String Set_Time(int min, int sec)
	{
		if(min < 0) min = 0;
		if(sec < 0) sec = 0;
		
		_minute = min;
		_second = sec;
		
		return Get_Time();
	}
}
