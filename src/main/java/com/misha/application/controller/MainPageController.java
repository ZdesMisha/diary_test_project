package com.misha.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by misha on 17.11.16.
 */
@Controller
public class MainPageController {

        private static final Logger LOG = LoggerFactory.getLogger(MainPageController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index() {
         LOG.info("Requesting main page...");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }
}
