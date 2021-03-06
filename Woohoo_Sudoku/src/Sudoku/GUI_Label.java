package Sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

//															enum: GUI_Label
//-----------------------------------------------------------------------------
/**	
*	controls the labels to be used
*/
public enum GUI_Label 
{
	GAME_TITLE	(0, new GUI_Style("SUDOKU", 
					new Rectangle(45, 40, 400, 70),
					new Font(Font.SANS_SERIF, Font.BOLD, 70), 
					null, 
					new Color(255, 255, 255))),
						
	NEW_GAME	(1, new GUI_Style("new game: ",
					new Rectangle(25, 130, 100, 20), 
					new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
					null, 
					new Color(255, 255, 255))),
						
	TIMER		(2, new GUI_Style("00:00", 
					new Rectangle(155, 160, 200, 100), 
					new Font(Font.SANS_SERIF, Font.BOLD, 40), 
					null, 
					new Color(255, 255, 255)));

	
	private final int _id;			//	must be unique, ascending, starting from 0
	private GUI_Style _style;		//	style to be applied to component
	
	
	//											GUI_Label: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	groups all properties for components into an enum for easier management
	 * 	@param id		:	must be unique, ascending, starting from 0
	 * 	@param style	:	style to be applied to component
	 */
	private GUI_Label(int id, GUI_Style style)
	{
		this._id = id;
		Set_style(style);
	}
	

	//										GUI_Label: getters / setters
	//-------------------------------------------------------------------------
	public int Get_id() { return _id; } 
	public GUI_Style Get_style() { return _style; }
	public void Set_style(GUI_Style style) { this._style = style; }

}
