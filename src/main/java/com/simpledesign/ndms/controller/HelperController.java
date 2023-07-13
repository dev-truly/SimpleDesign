package com.simpledesign.ndms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelperController {
    @GetMapping("/helper")
    public String getHepler() {
        return "views/helper";
    }

    @GetMapping("/ndms-default-info-update/helper")
    public ModelAndView defaultInfoUpdateHelper(
            ModelAndView mv) {
        mv.setViewName("views/ndms_default_info_update");
        return mv;
    }
}
