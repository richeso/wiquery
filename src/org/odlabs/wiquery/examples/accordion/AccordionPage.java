package org.odlabs.wiquery.examples.accordion;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.accordion.Accordion;

/**
 * AccordionPage
 */
public class AccordionPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private Accordion accordion;
	private CheckBox autoHeight;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public AccordionPage(final PageParameters parameters) {
		super("Accordion example");

		accordion = new Accordion("accordion");
		add(accordion);
		
		// Options
		autoHeight = new CheckBox("autoHeight", new Model<Boolean>(true));
		autoHeight.setOutputMarkupPlaceholderTag(true);
		autoHeight.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				accordion.setAutoHeight(autoHeight.getModelObject());				
				target.addComponent(accordion);
			}
		});
		add(autoHeight);
	}
}
