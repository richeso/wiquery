package org.odlabs.wiquery.examples.themeroller;

import java.util.ArrayList;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.examples.WicketApplication;
import org.odlabs.wiquery.ui.tabs.Tabs;
import org.odlabs.wiquery.ui.themes.WiQueryCoreThemeResourceReference;

/**
 * ThemeRollerPage
 */
public class ThemeRollerPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private DropDownChoice<WiQueryCoreThemeResourceReference> themeSelect;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ThemeRollerPage(final PageParameters parameters) {
		super("Theme Roller example");
		
		ArrayList<WiQueryCoreThemeResourceReference> themes = new ArrayList<WiQueryCoreThemeResourceReference>();
		themes.add(new WiQueryCoreThemeResourceReference("black-tie"));
		themes.add(new WiQueryCoreThemeResourceReference("blitzer"));
		themes.add(new WiQueryCoreThemeResourceReference("cupertino"));
		themes.add(new WiQueryCoreThemeResourceReference("excite-bike"));
		themes.add(new WiQueryCoreThemeResourceReference("humanity"));
		themes.add(new WiQueryCoreThemeResourceReference("fusion"));
		themes.add(new WiQueryCoreThemeResourceReference("redmond"));
		themes.add(new WiQueryCoreThemeResourceReference("ui-darkness"));
		themes.add(new WiQueryCoreThemeResourceReference("ui-lightness"));
		themes.add(new WiQueryCoreThemeResourceReference("vader"));
		
		themeSelect = new DropDownChoice<WiQueryCoreThemeResourceReference>(
				"themeSelect", 
				new Model<WiQueryCoreThemeResourceReference>(),
				themes,
				new ChoiceRenderer<WiQueryCoreThemeResourceReference>("name"));
		themeSelect.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				((WicketApplication) Application.get()).setTheme(themeSelect.getModelObject());
				setResponsePage(ThemeRollerPage.this);
			}
		});
		add(themeSelect);
		
		// wiQuery examples
		Tabs tabs = new Tabs("tabs");
		add(tabs);
	}
}
