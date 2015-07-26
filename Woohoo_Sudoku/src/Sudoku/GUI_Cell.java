package Sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

//															enum: GUI_Cell
//-----------------------------------------------------------------------------
/**	
*	list of all GUI_Styles possible for a cell
*/
public enum GUI_Cell 
{
	NORMAL	(0, new GUI_Style(null, 
				new Rectangle(405, 40, 40, 25),
				new Font(Font.SANS_SERIF, Font.PLAIN, 10), 
				new Color(100, 100, 100), 
				new Color(255, 255, 255))),
						
	HINT	(1, new GUI_Style(null, 
				new Rectangle(405, 40, 40, 25),
				new Font(Font.SANS_SERIF, Font.BOLD, 10), 
				new Color(50, 50, 200), 
				new Color(255, 255, 255))),
				
	GOOD	(2, new GUI_Style(null, 
				new Rectangle(405, 40, 40, 25),
				new Font(Font.SANS_SERIF, Font.BOLD, 10), 
				new Color(50, 150, 50), 
				new Color(255, 255, 255))),
						
						
	ERROR	(3, new GUI_Style(null, 
				new Rectangle(405, 40, 40, 25), 
				new Font(Font.SANS_SERIF, Font.BOLD, 10), 
				new Color(200, 50, 50), 
				new Color(255, 255, 255))),
				
				
	ACTIVE	(4, new GUI_Style(null, 
				new Rectangle(405, 360, 40, 25), 
				new Font(Font.SANS_SERIF, Font.BOLD, 10), 
				new Color(100, 100, 100), 
				new Color(255, 255, 255))),
				
	CLICKED	(5, new GUI_Style(null, 
				new Rectangle(405, 360, 40, 25), 
				new Font(Font.SANS_SERIF, Font.BOLD, 10), 
				new Color(50, 50, 50), 
				new Color(255, 255, 255)));

	
	private final int _id;			//	must be unique, ascending, starting from 0
	private GUI_Style _style;		//	style to be applied to component
	
	
	//												GUI_Cell: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	groups all properties for components into an enum for easier management
	 * 	@param id		:	must be unique, ascending, starting from 0
	 * 	@param style	:	style to be applied to cell
	 */
	private GUI_Cell(int id, GUI_Style style)
	{
		this._id = id;
		Set_style(style);
	}
	

	//											GUI_Cell: getters / setters
	//-------------------------------------------------------------------------
	public int Get_id() { return _id; } 
	public GUI_Style Get_style() { return _style; }
	public void Set_style(GUI_Style style) { this._style = style; }
}
