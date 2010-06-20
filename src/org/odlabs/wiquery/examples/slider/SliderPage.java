package org.odlabs.wiquery.examples.slider;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.core.DefaultJsScopeUiEvent;
import org.odlabs.wiquery.ui.slider.Slider;
import org.odlabs.wiquery.ui.slider.Slider.Orientation;

/**
 * SliderPage
 */
public class SliderPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private AbstractDefaultAjaxBehavior sliderBehavior;
	private CheckBox orientation;
	private Integer sliderValue;
	private Label sliderLabel;
	private Slider slider;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SliderPage(final PageParameters parameters) {
		super("Slider example");
		
		sliderValue = 0;
		
		sliderBehavior = new AbstractDefaultAjaxBehavior() {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void respond(AjaxRequestTarget target) {
				// We get the slider value
				sliderValue = Integer.parseInt(this.getComponent().
						getRequest().getParameter("sliderValue"));
				
				target.addComponent(sliderLabel);
			}
		};
		add(sliderBehavior);
	
		slider = new Slider("slider", 0, 100);
		slider.setChangeEvent(
				new DefaultJsScopeUiEvent(
						"wicketAjaxGet('" + sliderBehavior.getCallbackUrl(true)
						+ "&sliderValue='+" + Slider.UI_VALUE
						+ ", null,null, function() {return true;})"));
		add(slider);
		
		sliderLabel = new Label("sliderLabel", new PropertyModel<SliderPage>(this, "sliderValue"));
		sliderLabel.setOutputMarkupPlaceholderTag(true);
		add(sliderLabel);
		
		orientation = new CheckBox("orientation", new Model<Boolean>(true));
		orientation.setOutputMarkupPlaceholderTag(true);
		orientation.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				slider.setValue(sliderValue);
				
				if(orientation.getModelObject()){
					slider.setOrientation(Orientation.HORIZONTAL);
				} else {
					slider.setOrientation(Orientation.VERTICAL);
				}
				
				target.addComponent(slider);
			}
		});
		add(orientation);
	}
	
	public Integer getSliderValue() {
		return sliderValue;
	}
}
