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
    private int M1  = 1024 * 1024 ;
    @RequestMapping("/one")
    public void one(String p1){
        System.out.println("p1::::::"+p1);
        int s = M1;
        System.out.println(s);
        Context.getContext().response().setHttpContent("test 聶偉超 content one");
    }

    @RequestMapping("/two")
    public void two(String p2,Context context){
        System.out.println("p2::::::"+p2);
        Context.getContext().response().setHttpContent("test 聶偉超 content two");
    }
}