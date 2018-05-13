package sale.management.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ngoc on 23/04/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping("/")
public class HomeController
{
    @GetMapping
    public String index()
    {
        return "home";
    }
}
