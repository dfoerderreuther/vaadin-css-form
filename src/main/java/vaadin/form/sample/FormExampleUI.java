package vaadin.form.sample;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class FormExampleUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = FormExampleUI.class, widgetset = "vaadin.form.sample.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("myform");
        layout.setMargin(true);
        setContent(layout);

        addCustomLayout(layout);
    }

    private void addCustomLayout(Layout layout) {

        TextField name = new TextField("Name");
        name.setRequired(true);
        name.setImmediate(true);

        layout.addComponent(formLine(name));
        layout.addComponent(formLine(new TextField("Strasse")));

        HorizontalLayout plzOrt = new HorizontalLayout();
        plzOrt.setCaption("Postleitzahl / Ort");
        TextField plz = new TextField();
        plz.addStyleName("plz");
        TextField ort = new TextField();
        ort.addStyleName("ort");
        plzOrt.addComponent(plz);
        plzOrt.addComponent(ort);

        layout.addComponent(formLine(plzOrt));

        TextField langesLabel = new TextField("Textfield mit langem Label. So lang, dass es sogar umbricht.");
        layout.addComponent(formLine(langesLabel));

        layout.addComponent(formLine(new TextField("Land")));

        layout.addComponent(formLine(new TextArea("Mehrzeilige Eingabe")));
        layout.addComponent(formLine(new TextField("Kurz")));

        HorizontalLayout bewertung = new HorizontalLayout();
        bewertung.setStyleName("bewertung");
        bewertung.addComponent(new Label("unwichtig"));
        OptionGroup options = new OptionGroup();
        for (int i = 0; i < 5; i++) {
            options.addItem(i);
            options.setItemCaption(i, "");
        }
        bewertung.addComponent(options);
        bewertung.addComponent(new Label("wichtig"));

        layout.addComponent(formLine(bewertung));

    }

    private CustomLayout formLine(Component component) {
        CustomLayout custom = new CustomLayout("formLine");
        custom.addComponent(component, "input");
        return custom;
    }

}
