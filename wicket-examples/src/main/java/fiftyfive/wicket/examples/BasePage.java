/*
 * Copyright 2009 55 Minutes (http://www.55minutes.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fiftyfive.wicket.examples;


import java.util.Calendar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;


/**
 * Base class for all pages. Provides markup for XHTML 1.0 Strict doctype.
 * <p>
 * Also exposes a <code>_body</code> variable to subclasses that can be used
 * to add <code>id</code> or <code>class</code> attributes to the
 * &lt;body&gt; element. For example, to add an <code>id</code>, do this:
 * <pre>
 * _body.setMarkupId("myId");
 * </pre>
 * To add a CSS class (using fiftyfive.wicket.util.Shortcuts.cssClass):
 * <pre>
 * _body.add(cssClass("myClass"));
 * </pre>
 */
public abstract class BasePage extends WebPage
{
    protected final WebMarkupContainer _body;
    
    public BasePage(PageParameters params)
    {
        super(params);
        add(_body = new WebMarkupContainer("body") {
            public boolean isTransparentResolver()
            {
                return true;
            }
        });
        _body.setOutputMarkupId(true);

        // Set the copyright <meta> content to the current calendar year.
        Calendar cal = Calendar.getInstance();
        WebComponent copyright = new WebComponent("copyright");
        copyright.add(new AttributeModifier("content", true,
            new Model("Copyright " + cal.get(Calendar.YEAR) + " 55 Minutes")));
        add(copyright);
    }
    
    public ExampleApplication getExampleApplication()
    {
        return (ExampleApplication) getApplication();
    }
    
    public ExampleSession getExampleSession()
    {
        return (ExampleSession) super.getSession();
    }
}