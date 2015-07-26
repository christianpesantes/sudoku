package Sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

//															enum: GUI_ComboBox
//-----------------------------------------------------------------------------
/**	
*	controls the comboboxes to be used
*/
public enum GUI_ComboBox
{						
	INPUT_FILE		(0, new GUI_Style(null, 
						new Rectangle(95, 130, 185, 25), 
						new Font(Font.SANS_SERIF, Font.PLAIN, 12), 
						new Color(100, 100, 100), 
						new Color(255, 255, 255)));

	
	private final int _id;			//	must be unique, ascending, starting from 0
	private GUI_Style _style;		//	style to be applied to component
	
	
	//											GUI_ComboBox: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	groups all properties for components into an enum for easier management
	 * 	@param id		:	must be unique, ascending, starting from 0
	 * 	@param style	:	style to be applied to component
	 */
	private GUI_ComboBox(int id, GUI_Style style)
	{
		this._id = id;
		Set_style(style);
	}
	

	//										GUI_ComboBox: getters / setters
	//-------------------------------------------------------------------------
	public int Get_id() { return _id; } 
	public GUI_Style Get_style() { return _style; }
	public void Set_style(GUI_Style style) { this._style = style; }
}
