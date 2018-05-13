package sale.management.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ngoc on 08/05/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping("/user")
public class UserController extends CommonController
{
    @GetMapping
    public ModelAndView index()
    {
        return new ModelAndView("user");
    }
}
