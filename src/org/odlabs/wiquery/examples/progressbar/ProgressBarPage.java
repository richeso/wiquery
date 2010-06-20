package org.odlabs.wiquery.examples.progressbar;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.progressbar.ProgressBar;

/**
 * ProgressBarPage
 */
public class ProgressBarPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket component
	private ProgressBar progressBar;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ProgressBarPage(final PageParameters parameters) {
		super("ProgressBar example");
		
		progressBar = new ProgressBar("progressBar");
		progressBar.setValue(0);
		add(progressBar);
		
		AjaxLink<String> incrementButton = new AjaxLink<String>("incrementButton"){
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				progressBar.increment(target);
			}
		};
		
		AjaxLink<String> decrementButton = new AjaxLink<String>("decrementButton"){
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				progressBar.decrement(target);
			}
		};
		
		add(incrementButton);
		add(decrementButton);
	}
}
