package org.odlabs.wiquery.examples.draggable;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.draggable.DraggableAjaxBehavior;
import org.odlabs.wiquery.ui.draggable.DraggableBehavior;
import org.odlabs.wiquery.ui.draggable.DraggableContainment;
import org.odlabs.wiquery.ui.draggable.DraggableRevert;
import org.odlabs.wiquery.ui.draggable.DraggableAjaxBehavior.DraggableEvent;
import org.odlabs.wiquery.ui.draggable.DraggableContainment.ContainmentEnum;

/**
 * DraggablePage
 */
public class DraggablePage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private CheckBox revertPosition;
	private CheckBox containmentConstraint;
	private DraggableBehavior draggableBehavior;
	private DraggableAjaxBehavior draggableAjaxBehavior;
	private WebMarkupContainer dragPanel;
	private WebMarkupContainer dragAjaxPanel;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public DraggablePage(final PageParameters parameters) {
		super("Draggable example");
		
		draggableBehavior = new DraggableBehavior();
		
		dragPanel = new WebMarkupContainer("dragPanel");
		dragPanel.add(draggableBehavior);
		add(dragPanel);
		
		// Options
		revertPosition = new CheckBox("revertPosition", new Model<Boolean>(false));
		revertPosition.setOutputMarkupPlaceholderTag(true);
		revertPosition.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				draggableBehavior.setRevert(new DraggableRevert(revertPosition.getModelObject()));				
				target.addComponent(dragPanel);
			}
		});
		add(revertPosition);
		
		containmentConstraint = new CheckBox("containmentConstraint", new Model<Boolean>(false));
		containmentConstraint.setOutputMarkupPlaceholderTag(true);
		containmentConstraint.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if(containmentConstraint.getModelObject()){
					draggableBehavior.setContainment(new DraggableContainment(
							ContainmentEnum.PARENT));
				} else {
					draggableBehavior.setContainment(new DraggableContainment(
							ContainmentEnum.WINDOW));
				}
				
				target.addComponent(dragPanel);
			}
		});
		add(containmentConstraint);
		
		// Ajax
		draggableAjaxBehavior = new DraggableAjaxBehavior(DraggableEvent.STOP) {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.draggable.DraggableAjaxBehavior#onDrag(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onDrag(Component component, AjaxRequestTarget ajaxRequestTarget) {
				// Nothing
			}

			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.draggable.DraggableAjaxBehavior#onStart(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onStart(Component component, AjaxRequestTarget ajaxRequestTarget) {
				// Nothing
			}

			/* (non-Javadoc)
			 * @see org.odlabs.wiquery.ui.draggable.DraggableAjaxBehavior#onStop(org.apache.wicket.Component, org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onStop(Component draggedComponent,
					AjaxRequestTarget ajaxRequestTarget) {
				ajaxRequestTarget.appendJavascript("alert('drag stop');");
			}
		};
		draggableAjaxBehavior.getDraggableBehavior().setContainment(
				new DraggableContainment(ContainmentEnum.PARENT));
		
		dragAjaxPanel = new WebMarkupContainer("dragAjaxPanel");
		dragAjaxPanel.add(draggableAjaxBehavior);
		add(dragAjaxPanel);
	}
}
