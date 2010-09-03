package net.sf.yat.gui.gwt.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ColorListBox extends Composite implements HasClickHandlers {

	private static ColorListBoxUiBinder uiBinder = GWT
			.create(ColorListBoxUiBinder.class);

	interface ColorListBoxUiBinder extends UiBinder<Widget, ColorListBox> {
	}
	
	List<String> colors;

	@UiField
	PushButton btn;
	
	String currentColor;
	boolean nullAllowed;

	public ColorListBox(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
		initWidget(uiBinder.createAndBindUi(this));		
		setAllowedColors(getAllColors());
		setColor(null);
	}

	public static LinkedList<String> getAllColors() {
		return new LinkedList<String>(Arrays.asList("#0000FF", "#00FF00", "#FF0000", "#FFFF00", "#FF00FF", "#00FFFF", "#C0C0C0"));
	}
	
	public void setAllowedColors(List<String> colors) {
		this.colors = colors;
	}
	
	public void setColor(String color) 
	{
		btn.setHTML("<tt><span style='background-color:"+color+";color:"+color+"'>&nbsp;</span>▼</tt>");
		currentColor=color;
	}
	
	public String getColor()
	{
		return currentColor;
	}
	
	@UiHandler(value="btn")
	void onClick(ClickEvent evt) 
	{
		final PopupPanel popUp = new PopupPanel();
		popUp.setAutoHideEnabled(true);
		VerticalPanel vPanel = new VerticalPanel();
		if (nullAllowed) {
			Anchor a = new Anchor();
			a.setHTML("<span style='background-color:#000000;color:#FFFFFF'><tt>&nbsp;?&nbsp;</tt></span>");
			vPanel.add(a);
			a.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					popUp.hide();
					setColor(null);
					manager.fireEvent(event);
				}
			});
		}
		
		for (String color : colors) {
			final String fColor = color;
			Anchor btn1 = new Anchor();
			btn1.setHTML("<span style='background-color:"+color+";color:"+color+"'><tt>&nbsp;&nbsp;&nbsp;</tt></span>");
			btn1.addClickHandler(new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
					popUp.hide();
					setColor(fColor);
					manager.fireEvent(event);
				}
			});
			vPanel.add(btn1);
		}	
		
		
		popUp.add(vPanel);
		popUp.setPopupPosition(this.getAbsoluteLeft(), this.getAbsoluteTop());
		popUp.show();
	}
	
	private HandlerManager manager = new HandlerManager(this);
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return manager.addHandler(ClickEvent.getType(), handler);
	}
}
