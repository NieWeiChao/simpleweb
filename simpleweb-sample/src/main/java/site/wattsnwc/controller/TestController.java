package site.wattsnwc.controller;

import site.wattsnwc.server.annotation.Controller;
import site.wattsnwc.server.annotation.RequestMapping;
import site.wattsnwc.server.annotation.RequestParam;
import site.wattsnwc.server.context.Context;

/**
 * msg
 *
 * @author watts
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/one1/")
    public void one1() {
        Context.getContext().response().setHttpContent("test 聂伟超 content one");
    }

    @RequestMapping("/one")
    public void one(@RequestParam("p1") String p1) {
        Context.getContext().response().setHttpContent("test 聶偉超 content one");
    }

    @RequestMapping("/two")
    public void two(String p2, Context context) {
        Context.getContext().response().setHttpContent("test 聶偉超 content two");
    }
}
