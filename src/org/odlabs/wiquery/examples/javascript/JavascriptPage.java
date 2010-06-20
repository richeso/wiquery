package org.odlabs.wiquery.examples.javascript;

import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPostprocessingCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import org.odlabs.wiquery.core.javascript.JsQuery;
import org.odlabs.wiquery.core.javascript.JsScope;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.odlabs.wiquery.examples.AbstractExamplePage;
import org.odlabs.wiquery.examples.WicketApplication;

/**
 * JavascriptPage
 */
public class JavascriptPage extends AbstractExamplePage {

	private static final long serialVersionUID = 1L;
	
	// Wicket components
	private FeedbackPanel feedback;
	private Label jsGenerated;
	private Model<String> binding;
	
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public JavascriptPage(final PageParameters parameters) {
		super("Javascript binding");
		
		// Container to insert the wiQuery javascript binding
		Form<String> jsForm = new Form<String>("jsForm");
		add(jsForm);
		
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		jsForm.add(feedback);
		
		StringBuffer exampleJavaCode = new StringBuffer();
		exampleJavaCode.append("Options options = new Options();");
		exampleJavaCode.append("\n");
		exampleJavaCode.append("options.put(\"autoOpen\", false);");
		exampleJavaCode.append("\n");
		exampleJavaCode.append("\n");
		exampleJavaCode.append("return new JsQuery(component).$().chain(\"dialog\", \"open\", options.getJavaScriptOptions());");
		
		binding = new Model<String>(exampleJavaCode.toString());
		TextArea<String> jsCode = new TextArea<String>("jsCode", binding);
		jsCode.add(new StringValidator.MaximumLengthValidator(200));
		jsForm.add(jsCode);
		
		AjaxSubmitLink jsSubmit = new AjaxSubmitLink("jsSubmit") {
			private static final long serialVersionUID = 1L;
			
			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#getAjaxCallDecorator()
			 */
			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator() {
				AjaxPreprocessingCallDecorator pre = new AjaxPreprocessingCallDecorator(null){
					private static final long serialVersionUID = 1L;
					
					/* (non-Javadoc)
					 * @see org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator#decorateScript(java.lang.CharSequence)
					 */
					@Override
					public CharSequence decorateScript(CharSequence script) {
						return "this.disabled=true; this.value='Generating';" + script;
					}
				};
				
				AjaxPostprocessingCallDecorator post = new AjaxPostprocessingCallDecorator(pre) {
					private static final long serialVersionUID = 1L;

					/* (non-Javadoc)
					 * @see org.apache.wicket.ajax.calldecorator.AjaxPostprocessingCallDecorator#postDecorateOnSuccessScript(java.lang.CharSequence)
					 */
					@Override
					public CharSequence postDecorateOnSuccessScript(
							CharSequence script) {
						return script + "this.disabled=false; this.value='Generate';";
					}
				};
				return post;
			}

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#onError(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
			 */
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				
				target.addComponent(feedback);
			}

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
			 */
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					getSession().cleanupFeedbackMessages();
					
					StringBuffer groovyScript = new StringBuffer();
					groovyScript.append("import org.odlabs.wiquery.core.javascript.*;").append("\n");
					groovyScript.append("import org.odlabs.wiquery.core.javascript.helper.*").append("\n");
					groovyScript.append("import org.odlabs.wiquery.ui.core.*;").append("\n");
					groovyScript.append("import org.odlabs.wiquery.core.options.*;").append("\n");
					groovyScript.append(binding.getObject());
					
					GroovyCodeSource codeSource = new GroovyCodeSource(
							groovyScript.toString(), "wiQueryScript", "/serverCodeBase/restrictedClient");
					
					GroovyShell groovyShell = ((WicketApplication) getApplication()).getGroovyShell();
					groovyShell.setVariable("component", jsGenerated);
					Object returnObject = groovyShell.evaluate(codeSource);
					
					if(returnObject instanceof JsStatement 
							|| returnObject instanceof JsQuery
							|| returnObject instanceof JsScope){
						
						String render = null;
						
						if(returnObject instanceof JsStatement) {
							render = ((JsStatement) returnObject).render().toString();
							
						} else if(returnObject instanceof JsQuery) {
							render = ((JsQuery) returnObject).getStatement().render().toString();
							
						} else {
							render = ((JsScope) returnObject).render().toString();
						}
						
						jsGenerated.setDefaultModelObject(render);
						
					} else {
						error("The Java code must return a JsQuery or a JsStatement or a JsScope");
						jsGenerated.setDefaultModelObject("");
					}
					
				} catch (Exception e) {
					error("Generation error / Bad syntax");
					jsGenerated.setDefaultModelObject("");
					e.printStackTrace();
				}
				
				target.addComponent(feedback);
				target.addComponent(jsGenerated);
			}
		};
		jsForm.add(jsSubmit);

		// Container to display generatd javascript
		jsGenerated = new Label("jsGenerated", new Model<String>());
		jsGenerated.setOutputMarkupPlaceholderTag(true);
		add(jsGenerated);
	}

	/* (non-Javadoc)
	 * @see org.odlabs.wiquery.examples.AbstractExamplePage#isVisibleDownloadLinks()
	 */
	@Override
	public Boolean isVisibleDownloadLinks() {
		return false;
	}
}
