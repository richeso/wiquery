package org.odlabs.wiquery.examples.droppable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.droppable.DroppableAjaxBehavior;
import org.odlabs.wiquery.ui.sortable.SortableBehavior;

/**
 * SortablePage
 */
public class DroppablePage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Properties :
	private HashMap<String, Integer> selection;
	
	// Wicket component
	private Label selectionLabel;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public DroppablePage(final PageParameters parameters) {
		super("Sortable example");
		
		selection = new HashMap<String, Integer>();
		
	    List<Fruit> values1 = Arrays.asList(
	    		new Fruit("Apple"), 
	    		new Fruit("Banana"), 
	    		new Fruit("Kiwi"), 
	    		new Fruit("Orange"));
	    
	    ListView<Fruit> listView = new ListView<Fruit>("listView", values1) {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
			 */
			@Override
			protected void populateItem(ListItem<Fruit> item) {
				item.add(new Image("item", item.getModelObject().getResourceReference()));
				item.setOutputMarkupId(true);
			}
	    };
	    
	    WebMarkupContainer sortableWicket = new WebMarkupContainer("sortableWicket");
	    SortableBehavior sortableBehavior = new SortableBehavior();
	    sortableBehavior.setConnectWith(".connectedSortable");
		sortableWicket.add(sortableBehavior);
	    sortableWicket.add(listView);
	    add(sortableWicket);
	 
	    // Add the Ajax drop zone
	    WebMarkupContainer dropZone = new WebMarkupContainer("dropZone");
	    dropZone.add(new DroppableAjaxBehavior() {
	    	private static final long serialVersionUID = 1L;
	    	
			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.droppable.DroppableAjaxBehavior#onDrop(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void onDrop(Component droppedComponent,
					AjaxRequestTarget ajaxRequestTarget) {
				
				if(droppedComponent != null){
					ListItem<Fruit> item = (ListItem<Fruit>)droppedComponent;
					Fruit fruit = item.getModelObject();
					Integer nb = 1;
					if(selection.containsKey(fruit.getName())){
						nb = selection.get(fruit.getName()) + 1;
					}
					selection.put(fruit.getName(), nb);
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("<ul>");
					for(String key : selection.keySet()){
						buffer.append("<li>");
						buffer.append(selection.get(key));
						buffer.append(" " + key + "(s)");
						buffer.append("</li>");
					}
					buffer.append("</ul>");
					selectionLabel.setDefaultModelObject(buffer.toString());
					ajaxRequestTarget.addComponent(selectionLabel);
				}				
			}
		});
	    dropZone.add(new Image("cartImage", new ResourceReference(DroppablePage.class, "cart.jpg")));
	    add(dropZone);
	    
	    selectionLabel = new Label("selectionLabel", new Model<String>());
	    selectionLabel.setOutputMarkupPlaceholderTag(true);
	    selectionLabel.setEscapeModelStrings(false);
	    add(selectionLabel);
	}
	
	/**
	 * Bean of a fruit
	 * @author Julien
	 *
	 */
	private static class Fruit extends Object implements Serializable {
		private static final long serialVersionUID = 1L;
		
		// Properties
		private String name;
		
		/**
		 * Constructor
		 * @param name Name fo the fruit
		 */
		public Fruit(String name) {
			super();
			this.setName(name);
		}
		
		/**
		 * @return the name of the fruit
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * @return the ResourceReference of the fruit
		 */
		private ResourceReference getResourceReference() {
			return new ResourceReference(DroppablePage.class, 
					getName().toLowerCase() + ".jpg");
		}

		/**
		 * Set's the name of the fruit
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}	
	}
}
