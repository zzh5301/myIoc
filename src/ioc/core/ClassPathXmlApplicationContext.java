package ioc.core;

import ioc.config.Bean;
import ioc.config.Property;
import ioc.config.parsing.ConfigurationManager;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/4/13.
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    //存放配置文件信息
    private Map<String,Bean> config;

    //存放bean对象容器
    private Map<String,Object> context= new HashMap<>();


    public ClassPathXmlApplicationContext(String path){

        //读取配置文件中bean信息
        config= ConfigurationManager.getBeanConfig(path);

        if(config!=null){
            for(Map.Entry<String,Bean> e:config.entrySet()){

                String beanName=e.getKey();
                Bean bean=e.getValue();

                if(bean.getScope().equals(Bean.SINGLETON)){
                    Object beanObj=createBeanByConfig(bean);
                    context.put(beanName,beanObj);
                }
            }
        }


    }

    private Object createBeanByConfig(Bean bean){

        Class clazz=null;

        Object beanObject=null;

        try{
            clazz=Class.forName(bean.getClassName());
            beanObject=clazz.newInstance();
            List<Property> properties=bean.getProperties();

            for (Property property:properties){
                Map<String,Object> params=new HashMap<>();
                if(property.getValue()!=null){
                    params.put(property.getName(),property.getValue());
                    BeanUtils.populate(beanObject,params);
                }else if(property.getRef()!=null){
                    Object ref=context.get(property.getRef());
                    if(ref==null){
                        ref=createBeanByConfig(config.get(property.getRef()));
                    }
                    params.put(property.getName(),ref);
                    BeanUtils.populate(beanObject,params);
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return beanObject;


    }

    @Override
    public Object getBean(String name) {
        Bean bean=config.get(name);
        Object beanObj=null;
        if(bean.getScope().equals(Bean.SINGLETON)){

            beanObj=context.get(name);
        }else if(bean.getScope().equals(Bean.PROTOTYPE)){


            beanObj=createBeanByConfig(bean);
        }
        return beanObj;
    }
}
