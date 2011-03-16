package ${package};

import java.util.Date;

import fiftyfive.wicket.js.JavaScriptDependency;
import fiftyfive.wicket.link.HomeLink;
import org.apache.wicket.Component;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import static fiftyfive.wicket.util.Shortcuts.*;

/**
 * Base class for all pages. Provides markup for the HTML5 doctype.
 * <p>
 * Also exposes a {@link #getBody} method to subclasses that can be used
 * to add <code>id</code> or <code>class</code> attributes to the
 * {@code <body>} element. For example, to add an {@code id}, do this:
 * <pre class="example">
 * getBody().setMarkupId("myId");</pre>
 * <p>
 * To add a CSS class (using {@link fiftyfive.wicket.util.Shortcuts#cssClass}):
 * <pre class="example">
 * getBody().add(cssClass("myClass"));</pre>
 */
public abstract class BasePage extends WebPage
{
    private WebMarkupContainer body;
    
    public BasePage()
    {
        this(null);
    }
    
    public BasePage(PageParameters params)
    {
        super(params);
        
        // Set up <head> elements
        add(new HomeLink("home-link"));
        
        // Set up JS
        add(new JavaScriptDependency(BasePage.class));
        
        // Set up CSS
        add(cssResource("styles/screen.css"));
        add(cssConditionalResource("IE", "styles/ie.css"));
        add(cssPrintResource("styles/print.css"));
        
        // Allow subclasses to register CSS classes on the body tag
        WebMarkupContainer body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        add(body);
        
        body.add(new DebugBar("debug"));

        // Copyright year in footer
        body.add(DateLabel.forDatePattern("year", Model.of(new Date()), "yyyy"));
        
        // From now on add() will add to body instead of page
        this.body = body;
    }
    
    /**
     * Return a component that represents the {@code <body>} of the page.
     * Use this to add CSS classes or set the markup ID for styling purposes.
     */
    public WebMarkupContainer getBody()
    {
        return this.body;
    }

    /**
     * When subclasses of BasePage add components to the page, in reality
     * they need to be added as children of the {@code <body>} container.
     * This implementation ensures the page hierarchy is correctly enforced.
     * 
     * @return {@code this} to allow chaining
     */
    @Override
    public BasePage add(Component... childs)
    {
        for(Component c : childs)
        {
            // Wicket automatically translates <head> into an
            // HtmlHeaderContainer and adds it to the page. Make sure this
            // is registered as a direct child of the page itself, not the
            // body.
            if(null == this.body || c instanceof HtmlHeaderContainer)
            {
                super.add(c);
            }
            // Everything else goes into the <body>.
            else
            {
                this.body.add(c);
            }
        }
        return this;
    }
}