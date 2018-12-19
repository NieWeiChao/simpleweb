package site.wattsnwc.server.scanner;

import org.reflections.Reflections;
import site.wattsnwc.server.annotation.Controller;
import site.wattsnwc.server.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * msg
 *
 * @author watts
 */
public class ControllerScanner {
    private ControllerScanner(){}
    private static final Set<Object> CONTROLLERS = new HashSet<>(16);

    private static final ConcurrentHashMap<String,Method> MAPPINGS = new ConcurrentHashMap<>(128);

    public static void scanner() {
        Reflections reflections = new Reflections("site.wattsnwc.controller");
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
        CONTROLLERS.addAll(classesList);
        classesList.forEach(aClass -> {
            RequestMapping classMapping = aClass.getAnnotation(RequestMapping.class);
            String classUrl = classMapping==null?"":classMapping.value();
            Stream.of(aClass.getMethods()).forEach(method -> {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String methodUrl = requestMapping==null?"":requestMapping.value();
                if(requestMapping!=null){
                    MAPPINGS.put(classUrl+methodUrl,method);
                }
            });
        });
    }

    public static Method getMethod(String url){
        return MAPPINGS.get(url);
    }
}
