package com.example.tmp;

import java.lang.reflect.Method;
import java.util.HashMap;

public class RequestMappingInfo {
    private Object controllerObject;
    private HashMap<String, Method> map = new HashMap<>();//路径和对应的ModelAndView方法

    public Object getControllerObject() {
        return controllerObject;
    }

    public void setControllerObject(Object controllerObject) {
        this.controllerObject = controllerObject;
    }

    public HashMap<String, Method> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Method> map) {
        this.map = map;
    }
}
