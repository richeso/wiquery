package org.odlabs.wiquery.examples.tabs;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.tabs.Tabs;

/**
 * ProgressBarPage
 */
public class TabsPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private CheckBox enableTabTwo;
	private CheckBox enableTabSix;
	private Tabs tabs;
	private WebMarkupContainer hiddenTab6;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public TabsPage(final PageParameters parameters) {
		super("Tabs example");

		tabs = new Tabs("tabs");
		add(tabs);
		
		hiddenTab6 = new WebMarkupContainer("hiddenTab6");
		hiddenTab6.setOutputMarkupPlaceholderTag(true);
		hiddenTab6.setVisible(false);
		
		Image imageContainer = new Image("imageContainer", 
				new ResourceReference(getApplication().getClass(), "wiquery.png"));
		
		hiddenTab6.add(imageContainer);		
		add(hiddenTab6);
		
		// Add some possible actions
		enableTabTwo = new CheckBox("enableTabTwo", new Model<Boolean>(true));
		enableTabTwo.setOutputMarkupPlaceholderTag(true);
		enableTabTwo.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if(enableTabTwo.getModelObject()){
					tabs.enable(target, 1);
				} else {
					tabs.disable(target, 1);
				}
			}
		});
		add(enableTabTwo);
		
		enableTabSix = new CheckBox("enableTabSix", new Model<Boolean>(false));
		enableTabSix.setOutputMarkupPlaceholderTag(true);
		enableTabSix.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				hiddenTab6.setVisible(enableTabSix.getModelObject());
				target.addComponent(hiddenTab6);
				
				if(enableTabSix.getModelObject()){
					tabs.add(target, 5, "Tab 6", hiddenTab6);
				} else {
					tabs.remove(target, 5);
				}
			}
		});
		add(enableTabSix);
	}
}
