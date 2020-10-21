package com.dustinkendall.htmltemplater;

/**
 * Created by Dustin Kendall on 10/20/20.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by Dustin Kendall on 5/26/20.
 */
public class Html {
    private StringBuilder htmlTemplate;
    private String htmlContent;
    private static final String prefix = "\\$\\{";
    private static final String suffix = "\\}";
    private static final String scopeDelimiter = "\\.";
    private HashMap<String, Object> objectHashMap;
    public Html(InputStream htmlTemplate) throws IOException {
        this();
        BufferedReader reader = new BufferedReader(new InputStreamReader(htmlTemplate));
        String line = null;
        while((line = reader.readLine())!=null){
            this.htmlTemplate.append(line);
        }
    }

    public Html(String htmlTemplate){
        this();
        this.htmlTemplate.append(htmlTemplate);
    }

    public Html(){
        htmlTemplate = new StringBuilder();
        htmlContent = "";
        objectHashMap = new HashMap<>();
    }

    public Html put(String varName, Object object){
        objectHashMap.put(varName,object);
        return this;
    }


    private boolean isPrimitiveInstance(Object object){
        if(object instanceof String){
            return true;
        } else if(object instanceof Integer){
            return true;
        } else if(object instanceof Long){
            return true;
        } else if(object instanceof Double){
            return true;
        }else if (object instanceof Float){
            return true;
        }else {
            return false;
        }
    }

    private void insertFields(String varName, Object object){

        if (isPrimitiveInstance(object)){
            htmlContent = htmlContent.replaceAll(prefix+varName+suffix,object.toString());
        }else if(object instanceof Html){
            htmlContent = htmlContent.replaceAll(prefix+varName+suffix,((Html) object).render());
        }
        else {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String reName = prefix + varName + scopeDelimiter + field.getName() + suffix;
                if(htmlContent.contains(reName.replace("\\",""))) {
                    try {

                        field.setAccessible(true);
                        Object obj = field.get(object);
                        String val = (obj != null) ? obj.toString() : "";
                        htmlContent = htmlContent.replaceAll(reName, val);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            //Are able to invoke 0 parameter public declared methods within HTML template. This
            //excludes static, abstract, and synchronized methods.
            Method[] methods = object.getClass().getDeclaredMethods();
            for(Method method : methods){
                String reName = prefix + varName + scopeDelimiter + method.getName() + "\\(\\)" + suffix;
                int mod = method.getModifiers();
                //can invoke non-static public getter methods with zero arguments
                if(method.getName().startsWith("get")) {
                    if (!Modifier.isStatic(mod) && Modifier.isPublic(mod) &&
                            !Modifier.isAbstract(mod) && !Modifier.isSynchronized(mod)) {

                        if (method.getParameterTypes().length == 0) {
                            if (htmlContent.contains(reName.replace("\\",""))){
                                try {
                                    method.setAccessible(true);
                                    //0 parameters take null args
                                    Object obj = method.invoke(object, (Object[]) null);
                                    String val = (obj != null) ? obj.toString() : "";
                                    htmlContent = htmlContent.replaceAll(reName, val);

                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String render(){
        if(htmlTemplate !=null && htmlTemplate.length()>0 && objectHashMap.size() > 0) {
            htmlContent = htmlTemplate.toString();
            for (String key : objectHashMap.keySet()) {
                insertFields(key, objectHashMap.get(key));
            }
        }
        return htmlContent;
    }
}
