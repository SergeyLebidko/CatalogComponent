package catalogcomponent;

import catalogcomponent.dataelements.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        GUI gui = (GUI) context.getBean("gui");
        ActionHandler actionHandler = (ActionHandler) context.getBean("actionHandler");

        UniCatalogPane uniCatalogPane = new UniCatalogPane(Product.class, null);
        gui.setUniCatalogPane(uniCatalogPane);
        actionHandler.setUniCatalogPane(uniCatalogPane);

        gui.showGui();

        ((ClassPathXmlApplicationContext) context).close();
    }

}
