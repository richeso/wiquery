package org.odlabs.wiquery.examples.sortable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.sortable.SortableAjaxBehavior;
import org.odlabs.wiquery.ui.sortable.SortableBehavior;

/**
 * SortablePage
 */
public class SortablePage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SortablePage(final PageParameters parameters) {
		super("Sortable example");
		

	    List<String> values1 = Arrays.asList(
	    		"Sort 1 - Value 1", 
	    		"Sort 1 - Value 2",
	    		"Sort 1 - Value 3",
	    		"Sort 1 - Value 4",
	    		"Sort 1 - Value 5");
	    
	    List<String> values2 = Arrays.asList(
	    		"Sort 2 - Value 1", 
	    		"Sort 2 - Value 2",
	    		"Sort 2 - Value 3",
	    		"Sort 2 - Value 4",
	    		"Sort 2 - Value 5");
	    
	    ListView<String> listView = new ListView<String>("listView", values1) {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
			 */
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("item", item.getModel()));
				item.setOutputMarkupId(true);
			}
	    };
	    
	    WebMarkupContainer sortableWicket = new WebMarkupContainer("sortableWicket");
	    SortableBehavior sortableBehavior = new SortableBehavior();
	    sortableBehavior.setConnectWith(".connectedSortable");
		sortableWicket.add(sortableBehavior);
	    sortableWicket.add(listView);
	    add(sortableWicket);
	    
	    ListView<String> listAjaxView = new ListView<String>("listAjaxView", values2) {
	    	private static final long serialVersionUID = 1L;
	    	
			/* (non-Javadoc)
			 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
			 */
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("itemAjax", item.getModel()));
				item.setOutputMarkupId(true);
			}
	    };
	    
	    WebMarkupContainer sortableAjaxWicket = new WebMarkupContainer("sortableAjaxWicket");
	    SortableAjaxBehavior sortableAjaxBehavior = new SortableAjaxBehavior() {
	    	private static final long serialVersionUID = 1L;
	    	
			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.sortable.SortableAjaxBehavior#onReceive(org.apache.wicket.Component, int, org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onReceive(Component sortedComponent, int index, 
					Component parentSortedComponent,
					AjaxRequestTarget ajaxRequestTarget) {
				ajaxRequestTarget.appendJavascript("alert('received  : " 
						+ sortedComponent.getMarkupId() +" - " 
						+ sortedComponent.getDefaultModelObject().toString()  
						+" - index : " + index + "')");
				ajaxRequestTarget.appendJavascript("alert('received from  : " 
						+ parentSortedComponent.getMarkupId() 
						+ "')");
			}

			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.sortable.SortableAjaxBehavior#onUpdate(org.apache.wicket.Component, int, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onUpdate(Component sortedComponent, int index,
					AjaxRequestTarget ajaxRequestTarget) {
				ajaxRequestTarget.appendJavascript("alert('updated  : " 
						+ sortedComponent.getMarkupId() +" - " 
						+ sortedComponent.getDefaultModelObject().toString()
						+" - index : " + index + "')");
			}

			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.sortable.SortableAjaxBehavior#onRemove(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onRemove(Component sortedComponent,
					AjaxRequestTarget ajaxRequestTarget) {
				ajaxRequestTarget.appendJavascript("alert('removed  : " 
						+ sortedComponent.getMarkupId() 
						+" - " 
						+ sortedComponent.getDefaultModelObject().toString()  
						+"')");
			}
	    };
	    sortableAjaxBehavior.getSortableBehavior().setConnectWith(".connectedSortable");
		sortableAjaxWicket.add(sortableAjaxBehavior);
	    sortableAjaxWicket.add(listAjaxView);
	    add(sortableAjaxWicket);
	}
}
