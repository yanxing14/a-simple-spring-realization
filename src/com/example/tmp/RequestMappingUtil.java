package com.example.tmp;

import java.lang.reflect.Method;
import java.util.HashMap;

public class RequestMappingUtil {
    public static RequestMappingInfo getRequestMappingInfo(Class<?> cls) throws InstantiationException, IllegalAccessException {
        RequestMappingInfo rmInfo = new RequestMappingInfo();
        // 处理 cls 所代表的类，填充 rmInfo 对象
        rmInfo.setControllerObject(cls.newInstance());
        //path1：art或science
        String path1=cls.getAnnotation(RequestMapping.class).value();
        Method methods[]= cls.getMethods();
        Method methods2[]=new Method[methods.length];
        String[] path2=new String[methods.length];
        int index=0;
        //method是所有方法，这里面有RequestMapping注解的放进method2数组里
        for (int i=0;i<methods.length;i++){
            if(methods[i].isAnnotationPresent(RequestMapping.class)) {
                path2[index] = methods[i].getAnnotation(RequestMapping.class).value();
                methods2[index]=methods[i];
                index++;
            }
        }
        //路径和方法的对应
        HashMap<String,Method> mapInfo=new HashMap<>();
        for(int i=0;i<methods.length;i++){
            mapInfo.put(path1+path2[i],methods2[i]);
        }
        rmInfo.setMap(mapInfo);
        return rmInfo;
    }
}
