package catalogcomponent;

import catalogcomponent.dataelements.Product;
import catalogcomponent.filters.ProductFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        GUI gui = (GUI) context.getBean("gui");
        ActionHandler actionHandler = (ActionHandler) context.getBean("actionHandler");

        UniTree uniTree = new UniTree(Product.class, new ProductFilter());
        gui.setUniTree(uniTree);
        actionHandler.setUniTree(uniTree);

        gui.showGui();

        ((ClassPathXmlApplicationContext) context).close();
    }

}
