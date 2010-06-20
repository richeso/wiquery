package org.odlabs.wiquery.examples;

import groovy.lang.GroovyShell;

import java.net.URL;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.Session;
import org.odlabs.wiquery.ui.themes.IThemableApplication;
import org.odlabs.wiquery.ui.themes.WiQueryCoreThemeResourceReference;
import org.odlabs.wiquery.utils.WiQueryWebApplication;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see war.Start#main(String[])
 */
public class WicketApplication extends WiQueryWebApplication implements IThemableApplication {
	
	// current theme
	private ResourceReference theme;
	
	// Groovy Shell
	private GroovyShell groovyShell;
	
	/**
	 * Constructor
	 */
	public WicketApplication() {
		super();
		theme = new WiQueryCoreThemeResourceReference("fusion");
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	protected void init() {
		super.init();
		groovyShell = new GroovyShell();
		
		ClassLoader myClassLoader = Thread.currentThread().getContextClassLoader();
		URL file = myClassLoader.getResource("groovy/security/groovy.policy");
		System.setProperty("java.security.policy", file.toString());
		System.setSecurityManager(new SecurityManager());
	}
	
	/**
	 * @return the Groovy Shell
	 */
	public GroovyShell getGroovyShell() {
		return groovyShell;
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	public void setTheme(ResourceReference theme) {
		this.theme = theme;
	}
	
	public ResourceReference getTheme(Session session) {
		return theme;
	}

}
