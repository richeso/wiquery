package org.odlabs.wiquery.examples;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ResourceLink;

public class AbstractExamplePage extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param title
	 *            Title of the example page
	 */
	public AbstractExamplePage(String title) {
		super();
		
		add(new Label("exampleTitle", title));
		add(new BookmarkablePageLink<HomePage>("mainPageLink", HomePage.class));
		
		ResourceLink<String> javaLink = new ResourceLink<String>("downloadJavaLink", 
				new ResourceReference(getClass(), getClass().getSimpleName() + ".java.txt"));
		add(javaLink);
		ResourceLink<String> htmlLink = new ResourceLink<String>("downloadHtmlLink", 
				new ResourceReference(getClass(), getClass().getSimpleName() + ".html.txt"));
		add(htmlLink);
		
		javaLink.setVisible(isVisibleDownloadLinks());
		htmlLink.setVisible(isVisibleDownloadLinks());
	}
	
	/**
	 * @return true if we want to insert the download links
	 */
	public Boolean isVisibleDownloadLinks() {
		return true;
	}
}
