package Sudoku;

//																class: Cell
//-----------------------------------------------------------------------------
/**	
 *	stores information for a single cell
 */
public class Cell 
{
	
	private boolean _immutable;		// 	TRUE for hints
	private int _user_value;		//	keeps track of user-entered value
	private int _calculated_value;	//	valid value calculated by the game
	private GUI_Cell _style;		//	render style to be used (definitions at GUI_Cell.java)

	
	//													Cell: constructor
	//-------------------------------------------------------------------------
	/**
	 * 	Default style for the cell is GUI_Cell.NORMAL unless it is a hint
	 * 	@param is_immutable		:	TRUE for hints
	 * 	@param user_value		:	0 unless it is a hint
	 * 	@param calculated_value	:	0 unless it is a hint
	 */
	public Cell(boolean immutable, int user_value, int calculated_value)
	{
		Set_immutable(immutable);
		Set_user_value(user_value);
		Set_calculated_value(calculated_value);
		
		Set_Style(GUI_Cell.NORMAL);
		if(Is_immutable()) Set_Style(GUI_Cell.HINT);
	}
	
	
	//												Cell: getters / setters
	//-------------------------------------------------------------------------
	
	public boolean Is_immutable() {	return _immutable; }
	public void Set_immutable(boolean immutable) { this._immutable = immutable; }

	public int Get_user_value() { return _user_value; }
	public void Set_user_value(int user_value) { this._user_value = user_value; }

	public int Get_calculated_value() { return _calculated_value; }
	public void Set_calculated_value(int calculated_value) { this._calculated_value = calculated_value; }
	
	public GUI_Cell Get_Style() { return _style; }
	public void Set_Style(GUI_Cell style) { this._style = style; }

		
	
	//														Cell: toString
	//-------------------------------------------------------------------------
	/**	
	 *	prints _user_value only 
	 */
	public String toString() { 	return " " + Get_user_value() + " "; }
}
