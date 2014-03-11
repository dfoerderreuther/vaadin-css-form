package vaadin.form.sample;

import com.google.common.base.Optional;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class FormExampleUI extends UI
{


    private FieldGroup fieldGroup;

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

        BeanItem<MyBean> beanItem = new BeanItem<MyBean>(new MyBean());
        fieldGroup = new FieldGroup(beanItem);

        addCustomLayout(layout);
    }

    private void addCustomLayout(Layout layout) {
        layout.addComponent(formLine(beanAttribute("Name", "name")));
        layout.addComponent(formLine(beanAttribute("Strasse", "street")));
        layout.addComponent(formLine(plzOrt("Postleitzahl / Ort")));
        layout.addComponent(formLine(beanAttribute("Textfield mit langem Label. So lang, dass es sogar umbricht. Und das sogar gleich zwei mal.", "message")));
        layout.addComponent(formLine(beanAttribute("Land", "country")));
        layout.addComponent(formLine(beanAttribute(Optional.<Component>absent(), "Mehrzeilige Eingabe", "message2", Optional.<Class<? extends AbstractTextField>>of(TextArea.class))));
        layout.addComponent(formLine(rating()));
    }

    private Field<?> beanAttribute(String name, String propertyName, String... styleNames) {
        return beanAttribute(Optional.<Component>absent(), name, propertyName, Optional.<Class<? extends AbstractTextField>>absent(), styleNames);
    }

    private Field<?> beanAttribute(Optional<Component> component, String name, String propertyName, Optional<Class<? extends AbstractTextField>> fieldType, String... styleNames) {
        AbstractTextField field = (AbstractTextField) (fieldType.isPresent() ? fieldGroup.buildAndBind(name, propertyName, fieldType.get()) : fieldGroup.buildAndBind(name, propertyName));
        field.addValidator(new MyBeanValidator(component.isPresent() ? component.get() : field, MyBean.class, propertyName));
        field.setNullRepresentation("");
        for (String styleName : styleNames) {
            field.addStyleName(styleName);
        }
        return field;
    }

    public static class MyBeanValidator extends BeanValidator {
        public Component component;

        public MyBeanValidator(Component component, Class<?> beanClass, String propertyName) {
            super(beanClass, propertyName);
            this.component = component;
        }

        @Override
        public void validate(Object value) throws InvalidValueException {
            try {
                super.validate(value);
            } catch (InvalidValueException e) {
                component.addStyleName("error");
                throw e;
            }
            component.removeStyleName("error");
        }
    }

    private HorizontalLayout plzOrt(String name) {
        HorizontalLayout plzOrt = new HorizontalLayout();
        plzOrt.setCaption(name);
        Field<?> plz = beanAttribute(Optional.<Component>of(plzOrt), "", "zip", Optional.<Class<? extends AbstractTextField>>absent(), "plz");
        plzOrt.addComponent(plz);
        Field<?> ort = beanAttribute("Ort", "city", "ort");
        plzOrt.addComponent(ort);
        return plzOrt;
    }

    private HorizontalLayout rating() {
        HorizontalLayout rating = new HorizontalLayout();
        rating.setStyleName("bewertung");
        rating.addComponent(new Label("unwichtig"));
        OptionGroup options = new OptionGroup();
        for (int i = 0; i < 5; i++) {
            options.addItem(i);
            options.setItemCaption(i, "");
        }
        rating.addComponent(options);
        rating.addComponent(new Label("wichtig"));
        return rating;
    }

    private CustomLayout formLine(Component component) {
        CustomLayout custom = new CustomLayout("formLine");
        custom.addComponent(component, "input");
        return custom;
    }

    private Validator testValidator(final Component component) {
        return new Validator() {
            @Override
            public void validate(Object o) throws InvalidValueException {
                if (((String)o).trim().length() < 2) {
                    component.addStyleName("error");
                    throw new InvalidValueException(String.format("%s ist invalid", component.getCaption()));
                } else {
                    component.removeStyleName("error");
                }

            }
        };
    }

}
