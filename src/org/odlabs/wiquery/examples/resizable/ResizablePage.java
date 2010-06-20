package org.odlabs.wiquery.examples.resizable;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.resizable.ResizableBehavior;

/**
 * ResizablePage
 */
public class ResizablePage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ResizablePage(final PageParameters parameters) {
		super("Resizable example");
	
		WebMarkupContainer divContainer = new WebMarkupContainer("divContainer");
		divContainer.add(new ResizableBehavior());
		add(divContainer);
		
		WebMarkupContainer imageParentContainer = new WebMarkupContainer("imageParentContainer");
		imageParentContainer.add(new ResizableBehavior());
		add(imageParentContainer);
		
		Image imageContainer = new Image("imageContainer", 
				new ResourceReference(getApplication().getClass(), "wiquery.png"));
		imageParentContainer.add(imageContainer);
		
		WebMarkupContainer textAreaContainer = new WebMarkupContainer("textAreaContainer");
		textAreaContainer.add(new ResizableBehavior());
		add(textAreaContainer);
	}
}
