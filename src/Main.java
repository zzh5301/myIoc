import ioc.bean.BeanA;
import ioc.bean.BeanB;
import ioc.core.BeanFactory;
import ioc.core.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        BeanFactory ac = new ClassPathXmlApplicationContext("/applicationContext.xml");
        BeanA a = (BeanA) ac.getBean("beanA");
        BeanA a1 = (BeanA) ac.getBean("beanA");

        BeanB b = (BeanB) ac.getBean("beanB");
        BeanB b1 = (BeanB) ac.getBean("beanB");
        System.out.println(a.getB());

        System.out.println("a==a1 : "+(a==a1));
        System.out.println("b==b1 : "+(b==b1));

    }
}
