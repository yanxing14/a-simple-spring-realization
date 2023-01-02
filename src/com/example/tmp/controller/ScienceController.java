package com.example.tmp.controller;

import com.example.tmp.ModelAndView;
import com.example.tmp.RequestMapping;

@RequestMapping("/science")
public class ScienceController {

    @RequestMapping("/math")
    public ModelAndView handleMath() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sample.jsp");
        mav.addObject("title", "Mathematicians");
        mav.addObject("path", "/science/math");
        mav.addObject("method", "get");
        mav.addObject("params", "null");
        mav.addObject("type", "Mathematicians");
        mav.addObject("content", "Archimedes, Gauss, Newton, Euler, Euclid, Poincare");
        return mav;
    }

    @RequestMapping("/physics")
    public ModelAndView handlePhysics() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sample.jsp");
        mav.addObject("title", "Physicists");
        mav.addObject("path", "/science/physics");
        mav.addObject("method", "get");
        mav.addObject("params", "null");
        mav.addObject("type", "Physicists");
        mav.addObject("content", "Einstein, Newton, Planck, Faraday, Maxwell, Feynman");
        return mav;
    }
}
