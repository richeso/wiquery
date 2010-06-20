package org.odlabs.wiquery.examples.datepicker;

import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.core.events.Event;
import org.odlabs.wiquery.core.events.MouseEvent;
import org.odlabs.wiquery.core.events.WiQueryEventBehavior;
import org.odlabs.wiquery.core.javascript.JsScope;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.datepicker.DateOption;
import org.odlabs.wiquery.ui.datepicker.DatePicker;
import org.odlabs.wiquery.ui.datepicker.DatePickerNumberOfMonths;
import org.odlabs.wiquery.ui.datepicker.DatePicker.ShowOnEnum;

/**
 * DatePickerPage
 */
public class DatePickerPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private DatePicker<Date> datePicker;
	private TextField<Short> selectableDate;
	private TextField<Short> numberOfMonths;
	private CheckBox showButtonPanel;
	private CheckBox showButtonImage;
	private WebMarkupContainer datePanel;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public DatePickerPage(final PageParameters parameters) {
		super("DatePicker example");
		
		datePanel = new WebMarkupContainer("datePanel");
		datePanel.setOutputMarkupPlaceholderTag(true);
		
		datePicker = new DatePicker<Date>("datePicker");
		datePicker.setButtonImage("http://jqueryui.com/demos/datepicker/images/calendar.gif");
		datePicker.setButtonImageOnly(true);
		datePanel.add(datePicker);
		
		WebMarkupContainer openDialog = new WebMarkupContainer("openDialog");
		openDialog.setOutputMarkupPlaceholderTag(true);
		openDialog.add(new WiQueryEventBehavior(new Event(MouseEvent.CLICK) {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.core.events.Event#callback()
			 */
			@Override
			public JsScope callback() {
				return JsScope.quickScope(datePicker.show().render());
			}
		}));
		datePanel.add(openDialog);
		add(datePanel);
		
		Form<String> form = new Form<String>("form");
		add(form);
		
		selectableDate = new TextField<Short>("selectableDate", 
				new Model<Short>(), Short.class);
		form.add(selectableDate);
		
		numberOfMonths = new TextField<Short>("numberOfMonths", 
				new Model<Short>(), Short.class);
		form.add(numberOfMonths);
		
		showButtonPanel = new CheckBox("showButtonPanel", new Model<Boolean>(false));
		form.add(showButtonPanel);
		
		showButtonImage = new CheckBox("showButtonImage", new Model<Boolean>(false));
		form.add(showButtonImage);
		
		AjaxSubmitLink dateButton = new AjaxSubmitLink("dateButton", form){
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
			 */
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(selectableDate.getModelObject() != null){
					datePicker.setMinDate(new DateOption(new Short("-" + selectableDate.getModelObject())));
					datePicker.setMaxDate(new DateOption(selectableDate.getModelObject()));
				}
				
				if(numberOfMonths.getModelObject() != null){
					datePicker.setNumberOfMonths(new DatePickerNumberOfMonths(numberOfMonths.getModelObject()));
				}
				
				datePicker.setShowButtonPanel(showButtonPanel.getModelObject());
				//datePicker.setButtonImageOnly(showButtonImage.getModelObject());
				
				if(showButtonImage.getModelObject()) {
					datePicker.setShowOn(ShowOnEnum.BOTH);
				} else {
					datePicker.setShowOn(ShowOnEnum.FOCUS);
				}
				
				target.addComponent(datePanel);
			}
		};
		form.add(dateButton);
	}
}
