package net.sf.yat.gui.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	@UiField
	TabLayoutPanel mainPanel;

	@UiField
	Label lbType;

	@UiField
	Label lbConcept;

	public MainView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		mainPanel.selectTab(1);
	}
}
