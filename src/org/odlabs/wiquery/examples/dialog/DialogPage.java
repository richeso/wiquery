package org.odlabs.wiquery.examples.dialog;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.core.javascript.JsScope;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.odlabs.wiquery.core.options.ListItemOptions;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.ui.core.JsScopeUiEvent;
import org.odlabs.wiquery.ui.dialog.Dialog;
import org.odlabs.wiquery.ui.dialog.DialogButton;

/**
 * DialogPage
 */
public class DialogPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private Dialog simpleDialog;
	private Dialog basicDialog;
	private Dialog advancedDialog;
	private FeedbackPanel feedback;
	private Form<String> form;
	private TextField<String> nameField;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public DialogPage(final PageParameters parameters) {
		super("Dialog example");
		
		AbstractDefaultAjaxBehavior formAjaxBehavior = new AbstractDefaultAjaxBehavior() {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void respond(AjaxRequestTarget target) {
				// Here, we retrieve data form the form of the advanced dialog
				form.onFormSubmitted();
				
				if(form.hasError()){
					// Error
					target.addComponent(feedback);
				} else {
					getSession().cleanupFeedbackMessages();
					target.prependJavascript("alert('Thank you [" + 
							nameField.getModelObject() + "]')");
					advancedDialog.close(target);
				}
			}
		};
		add(formAjaxBehavior);
		
		// Simple dialog
		simpleDialog = new Dialog("simpleDialog");
		add(simpleDialog);
		
		AjaxLink<String> openSimpleDialog = new AjaxLink<String>("openSimpleDialog") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				simpleDialog.open(target);
			}
		};
		add(openSimpleDialog);
		
		// Basic dialog
		basicDialog = new Dialog("basicDialog");
		basicDialog.setTitle("A title for my basic dialog");
		basicDialog.setWidth(450);
		ListItemOptions<DialogButton> buttons = new ListItemOptions<DialogButton>();
		buttons.add(new DialogButton("Close dialog", 
				JsScope.quickScope(basicDialog.close().render())));
		basicDialog.setButtons(buttons);
		basicDialog.add(new Image("imageContainer", 
				new ResourceReference(getApplication().getClass(), "wiquery.png")));
		add(basicDialog);
		
		AjaxLink<String> openBasicDialog = new AjaxLink<String>("openBasicDialog") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				basicDialog.open(target);
			}
		};
		add(openBasicDialog);
		
		// Advanced dialog
		form = new Form<String>("form");
		form.setOutputMarkupId(true);
		form.setMarkupId("form");
		
		nameField = new TextField<String>("nameField", new Model<String>());
		nameField.setRequired(true);
		form.add(nameField);
		
		advancedDialog = new Dialog("advancedDialog");
		advancedDialog.add(form);
		
		feedback = new FeedbackPanel("feedback", 
				new ContainerFeedbackMessageFilter(form));
		feedback.setOutputMarkupPlaceholderTag(true);
		
		advancedDialog.add(feedback);
		add(advancedDialog);
		
		ListItemOptions<DialogButton> buttonsAdv = new ListItemOptions<DialogButton>();
		buttonsAdv.add(new DialogButton("Cancel", 
				JsScope.quickScope(advancedDialog.close().render())));
		buttonsAdv.add(new DialogButton("Save", 
				JsScope.quickScope("wicketSubmitFormById('form','" +
						formAjaxBehavior.getCallbackUrl() + 
						"', null, null, null, null, null);")));
		advancedDialog.setButtons(buttonsAdv);
		advancedDialog.setTitle("Type your name please");
		advancedDialog.setCloseOnEscape(false);
		advancedDialog.setModal(true);
		advancedDialog.setHeight(250);
		advancedDialog.setWidth(450);
		advancedDialog.setOpenEvent(JsScopeUiEvent.quickScope(new JsStatement().
				self().chain("parents", "'.ui-dialog:first'").
				chain("find", "'.ui-dialog-titlebar-close'").
				chain("hide").render())); // When we open the dialog, we remove the close button
		// on the title :)
		
		AjaxLink<String> openAdvancedDialog = new AjaxLink<String>("openAdvancedDialog") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				advancedDialog.open(target);
			}
		};
		add(openAdvancedDialog);
	}
}
