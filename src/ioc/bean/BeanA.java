package ioc.bean;



/**
 * Created by lenovo on 2017/4/14.
 */
public class BeanA {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private  String name;

    public BeanB getB() {
        return b;
    }

    public void setB(BeanB b) {
        this.b = b;
    }

    private BeanB b;
}
