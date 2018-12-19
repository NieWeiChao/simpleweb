package site.wattsnwc.controller;

import site.wattsnwc.server.annotation.Controller;
import site.wattsnwc.server.annotation.RequestMapping;
import site.wattsnwc.server.context.Context;

/**
 * msg
 *
 * @author watts
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/one")
    public void one(){
        Context.getContext().response().setHttpContent("test 聶偉超 content one");
    }

    @RequestMapping("/two")
    public void two(){
        Context.getContext().response().setHttpContent("test 聶偉超 content two");
    }
}
