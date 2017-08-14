package ioc.config.parsing;

import ioc.config.Bean;
import ioc.config.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/13.
 */
public class ConfigurationManager {
    /**
     * 根据指定的路径读取配置文件
     *
     */

    public static Map<String,Bean> getBeanConfig(String path){

        Map<String,Bean> result=new HashMap<>();

        SAXReader reader=new SAXReader();

        InputStream is=ConfigurationManager.class.getResourceAsStream(path);

        Document doc=null;
        try{
            doc=reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("加载配置文件出错");
        }
        String xpath="//bean";
        List<Element> beanNodes=doc.selectNodes(xpath);
        for(Element ele:beanNodes){
            Bean bean=new Bean();
            bean.setName(ele.attributeValue("name"));
            bean.setClassName(ele.attributeValue("class"));
            String scope=ele.attributeValue("scope");
            if(scope!=null&&scope.trim().length()>0)
                bean.setScope(scope);
            List<Element> propNodes=ele.elements("property");
            if(propNodes!=null){
                for(Element prop:propNodes){
                    Property p=new Property();
                    p.setName(prop.attributeValue("name"));
                    p.setValue(prop.attributeValue("value"));
                    p.setRef(prop.attributeValue("ref"));
                    bean.getProperties().add(p);
                }
            }
            result.put(bean.getName(),bean);

        }
        return result;

    }

}
