package Sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

//															enum: GUI_Button
//-----------------------------------------------------------------------------
/**	
*	controls the buttons to be used
*/
public enum GUI_Button 
{
	RANDOM 		(0, new GUI_Style("random", 
					new Rectangle(290, 130, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	PAUSE		(1, new GUI_Style("pause", 
					new Rectangle(120, 270, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	RESTART 	(2, new GUI_Style("restart", 
					new Rectangle(120, 305, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	HELP		(3, new GUI_Style("help", 
					new Rectangle(210, 270, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	SOLVE		(4, new GUI_Style("solve", 
					new Rectangle(210, 305, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	CREDITS		(5, new GUI_Style("credits", 
					new Rectangle(120, 340, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255))),
						
	QUIT		(6, new GUI_Style("quit", 
					new Rectangle(210, 340, 80, 25), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					new Color(100, 100, 100), 
					new Color(255, 255, 255)));

	
	private final int _id;			//	must be unique, ascending, starting from 0
	private GUI_Style _style;		//	style to be applied to component
	
	
	//											GUI_Button: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	groups all properties for components into an enum for easier management
	 * 	@param id		:	must be unique, ascending, starting from 0
	 * 	@param style	:	style to be applied to component
	 */
	private GUI_Button(int id, GUI_Style style)
	{
		this._id = id;
		Set_style(style);
	}
	

	//										GUI_Button: getters / setters
	//-------------------------------------------------------------------------
	public int Get_id() { return _id; } 
	public GUI_Style Get_style() { return _style; }
	public void Set_style(GUI_Style style) { this._style = style; }

}