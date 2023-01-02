package com.example.tmp;

import java.util.HashMap;

public class ModelAndView {
    private HashMap<String, String> model = new HashMap<>();
    private String viewName;

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void addObject(String key, String value) {
        model.put(key, value);
    }

    public HashMap<String, String> getModel() {
        return model;
    }
}
