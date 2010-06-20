package org.odlabs.wiquery.examples.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.core.JsScopeUiEvent;
import org.odlabs.wiquery.ui.selectable.SelectableAjaxBehavior;
import org.odlabs.wiquery.ui.selectable.SelectableBehavior;

/**
 * SelectablePage
 */
public class SelectablePage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SelectablePage(final PageParameters parameters) {
		super("Selectable example");
		
		List<String> values = Arrays.asList(
	    		"Value 1", 
	    		"Value 2",
	    		"Value 3",
	    		"Value 4",
	    		"Value 5");
	    
	    ListView<String> listView = new ListView<String>("listView", values) {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
			 */
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("item", item.getModel()));
			}
	    };
	    
	    WebMarkupContainer selectableWicket = new WebMarkupContainer("selectableWicket");
	    selectableWicket.setMarkupId("selectableWicket");
	    selectableWicket.add(listView);
	    
	    StringBuffer javascript = new StringBuffer();
	    javascript.append("var selected = new Array();");
	    javascript.append("jQuery.each($('#selectableWicket').children(\"*[class*='ui-selected']\")");
	    javascript.append(", function(){selected.push($(this).text());});");
	    javascript.append("$('#selectedResult').text(jQuery.unique(selected).toString());");
	    
	    SelectableBehavior selectableBehavior = new SelectableBehavior();
	    selectableBehavior.setStopEvent(JsScopeUiEvent.quickScope(javascript.toString()));
		selectableWicket.add(selectableBehavior);
		add(selectableWicket);
	    
	    // Ajax section
	    
	    ListView<String> listAjaxView = new ListView<String>("listAjaxView", values) {
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
	    
	    final Label ajaxSelectedResult = new Label("ajaxSelectedResult", new Model<String>());
	    ajaxSelectedResult.setOutputMarkupId(true);
	    add(ajaxSelectedResult);
	    
	    WebMarkupContainer selectableAjaxWicket = new WebMarkupContainer("selectableAjaxWicket");
	    selectableAjaxWicket.setMarkupId("selectableAjaxWicket");
	    selectableAjaxWicket.add(new SelectableAjaxBehavior(){
	    	private static final long serialVersionUID = 1L;
	    	
			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.selectable.SelectableAjaxBehavior#onSelection(org.apache.wicket.Component[], org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onSelection(Component[] components,
					AjaxRequestTarget ajaxRequestTarget) {
				StringBuffer buffer = new StringBuffer();
				for(Component c : components){
					buffer.append("[");
					buffer.append(c.getDefaultModelObject().toString());
					buffer.append("]");
				}
				
				ajaxSelectedResult.setDefaultModelObject(buffer.toString());
				ajaxRequestTarget.addComponent(ajaxSelectedResult);
			}
	    	
	    });
	    selectableAjaxWicket.add(listAjaxView);
	    add(selectableAjaxWicket);
	}
}
