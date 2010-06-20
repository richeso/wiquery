package org.odlabs.wiquery.examples.themedecorator;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.odlabs.wiquery.core.commons.CoreJavaScriptResourceReference;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.themes.ThemeUiHelper;
import org.odlabs.wiquery.ui.themes.WiQueryCoreThemeResourceReference;
import org.odlabs.wiquery.ui.themes.ThemeUiHelper.IconEnum;

/**
 * ThemeRollerPage
 */
public class ThemeDecoratorPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private WebMarkupContainer buttonOver;
	
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ThemeDecoratorPage(final PageParameters parameters) {
		super("Theme Decorator with the fusion theme");
		
		add(new AbstractBehavior() {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				
				// Add needed resources
				response.renderJavascriptReference(CoreJavaScriptResourceReference.get());
				response.renderCSSReference(
						new WiQueryCoreThemeResourceReference("fusion"));
				response.renderOnDomReadyJavascript(ThemeUiHelper.hover(buttonOver).render().toString());
			}
		});
		
		// Decorate your components
		WebMarkupContainer buttonCorned = new WebMarkupContainer("buttonCorned");
		ThemeUiHelper.buttonRounded(buttonCorned);
		add(buttonCorned);
		
		WebMarkupContainer buttonCornedFocused = new WebMarkupContainer("buttonCornedFocused");
		ThemeUiHelper.buttonRoundedFocused(buttonCornedFocused);
		add(buttonCornedFocused);
		
		buttonOver = new WebMarkupContainer("buttonOver");
		ThemeUiHelper.buttonRounded(buttonOver);
		add(buttonOver);
		
		WebMarkupContainer divTitle = new WebMarkupContainer("divTitle");
		ThemeUiHelper.titleComponent(divTitle);
		add(divTitle);
		
		WebMarkupContainer divRounded = new WebMarkupContainer("divRounded");
		ThemeUiHelper.componentRounded(divRounded);
		add(divRounded);
		
		WebMarkupContainer highlightedText = new WebMarkupContainer("highlightedText");
		ThemeUiHelper.highlightedText(highlightedText);
		add(highlightedText);
		
		WebMarkupContainer errorText = new WebMarkupContainer("errorText");
		ThemeUiHelper.errorText(errorText);
		add(errorText);
		
		WebMarkupContainer overlay = new WebMarkupContainer("overlay");
		ThemeUiHelper.overlayComponent(overlay);
		add(overlay);
		
		WebMarkupContainer closethick = new WebMarkupContainer("closethick");
		ThemeUiHelper.iconComponent(closethick, IconEnum.CLOSETHICK);
		add(closethick);
		
		WebMarkupContainer calendar = new WebMarkupContainer("calendar");
		ThemeUiHelper.iconComponent(calendar, IconEnum.CALENDAR);
		add(calendar);
	}
}
