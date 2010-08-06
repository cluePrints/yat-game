package net.sf.yat.gui;

import javax.swing.UIManager;

import net.sf.yat.gui.swing.GameGui;
import net.sourceforge.napkinlaf.NapkinLookAndFeel;

public class Launcher {
	public static void main(String[] args) throws Exception{
		UIManager.setLookAndFeel(NapkinLookAndFeel.class.getName());
		GameGui gui = new GameGui();		
	}
}
