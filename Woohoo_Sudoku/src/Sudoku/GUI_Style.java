package Sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

//															class: GUI_Style
//-----------------------------------------------------------------------------
/**	
*	handles styles for every GUI component 
*/
public class GUI_Style
{
	private String _title;		//	string to be displayed
	private Rectangle _rect;	//	controls position and size 
	private Font _font;			//	controls font type, style, and size
	private Color _back_color;	//	controls background color
	private Color _fore_color;	//	controls foreground color
	
	
	//												GUI_Style: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	creates a new GUI_Style
	 * 	@param title	:	string to be displayed
	 * 	@param rect		:	controls position and size
	 * 	@param font		:	controls font type, style, and size
	 * 	@param back		:	controls background color
	 * 	@param fore		:	controls foreground color
	 */
	public GUI_Style(String title, Rectangle rect, Font font, Color back, Color fore)
	{
		Set_title(title);
		Set_rect(rect);
		Set_font(font);
		Set_back_color(back);
		Set_fore_color(fore);
	}
	
	
	//													GUI_Style: getters
	//-------------------------------------------------------------------------
	public String Get_title() {	return _title; }
	public Rectangle Get_rect() { return _rect; }
	public Font Get_font() { return _font; }
	public Color Get_back_color() { return _back_color; }
	public Color Get_fore_color() { return _fore_color; }
	
	
	//													GUI_Style: setters
	//-------------------------------------------------------------------------
	public void Set_title(String title) { this._title = title; }
	public void Set_rect(Rectangle rect) { this._rect = rect; }
	public void Set_font(Font font) { this._font = font; }
	public void Set_back_color(Color back_color) { this._back_color = back_color; }
	public void Set_fore_color(Color fore_color) { this._fore_color = fore_color; }
}
