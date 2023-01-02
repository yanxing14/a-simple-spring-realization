package com.example.tmp.controller;

import com.example.tmp.ModelAndView;
import com.example.tmp.RequestMapping;

@RequestMapping("/art")
public class ArtController {

    @RequestMapping("/painting")
    public ModelAndView handlePainting() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sample.jsp");
        mav.addObject("title", "Painters");
        mav.addObject("path", "art/painting");
        mav.addObject("method", "get");
        mav.addObject("params", "null");
        mav.addObject("type", "Painters");
        mav.addObject("content", "Michelangelo, Da Vinci, Rubens, Rembrandt, Ingres, Cezanne");
        return mav;
    }

    @RequestMapping("/writing")
    public ModelAndView handleWriting() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sample.jsp");
        mav.addObject("title", "Writers");
        mav.addObject("path", "art/writing");
        mav.addObject("method", "get");
        mav.addObject("params", "null");
        mav.addObject("type", "Writers");
        mav.addObject("content", "Shakespeare, Dante, Homer, Tolstoy, Chaucer, Dickens");
        return mav;
    }
}
