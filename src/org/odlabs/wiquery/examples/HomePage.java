package org.odlabs.wiquery.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.odlabs.wiquery.examples.accordion.AccordionPage;
import org.odlabs.wiquery.examples.datepicker.DatePickerPage;
import org.odlabs.wiquery.examples.dialog.DialogPage;
import org.odlabs.wiquery.examples.draggable.DraggablePage;
import org.odlabs.wiquery.examples.droppable.DroppablePage;
import org.odlabs.wiquery.examples.javascript.JavascriptPage;
import org.odlabs.wiquery.examples.progressbar.ProgressBarPage;
import org.odlabs.wiquery.examples.resizable.ResizablePage;
import org.odlabs.wiquery.examples.selectable.SelectablePage;
import org.odlabs.wiquery.examples.slider.SliderPage;
import org.odlabs.wiquery.examples.sortable.SortablePage;
import org.odlabs.wiquery.examples.tabs.TabsPage;
import org.odlabs.wiquery.examples.themedecorator.ThemeDecoratorPage;
import org.odlabs.wiquery.examples.themeroller.ThemeRollerPage;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {
		super();
		
		add(new BookmarkablePageLink<ThemeRollerPage>("themeRollerLink", ThemeRollerPage.class));
		add(new BookmarkablePageLink<ThemeDecoratorPage>("themeDecoratorLink", ThemeDecoratorPage.class));
		add(new BookmarkablePageLink<JavascriptPage>("javascriptLink", JavascriptPage.class));
		
		add(new BookmarkablePageLink<AccordionPage>("accordionLink", AccordionPage.class));
		add(new BookmarkablePageLink<DatePickerPage>("datePickerLink", DatePickerPage.class));
		add(new BookmarkablePageLink<DialogPage>("dialogLink", DialogPage.class));
		add(new BookmarkablePageLink<ProgressBarPage>("progressBarLink", ProgressBarPage.class));
		add(new BookmarkablePageLink<SliderPage>("sliderLink", SliderPage.class));
		add(new BookmarkablePageLink<TabsPage>("tabsLink", TabsPage.class));
		
		add(new BookmarkablePageLink<DraggablePage>("draggableLink", DraggablePage.class));
		add(new BookmarkablePageLink<DroppablePage>("droppableLink", DroppablePage.class));
		add(new BookmarkablePageLink<ResizablePage>("resizableLink", ResizablePage.class));
		add(new BookmarkablePageLink<SelectablePage>("selectableLink", SelectablePage.class));
		add(new BookmarkablePageLink<SortablePage>("sortableLink", SortablePage.class));
	}
}
