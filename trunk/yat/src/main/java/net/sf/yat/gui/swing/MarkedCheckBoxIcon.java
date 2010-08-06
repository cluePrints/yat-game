package net.sf.yat.gui.swing;

import java.awt.Component;

import net.sourceforge.napkinlaf.util.NapkinIconFactory.CheckBoxIcon;

public class MarkedCheckBoxIcon extends CheckBoxIcon {
	public MarkedCheckBoxIcon() {
		super(15);
	}
	
	@Override
	protected boolean shouldUseMark(Component c) {
		return true;
	}
}
