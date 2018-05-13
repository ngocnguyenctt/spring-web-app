package sale.management.app.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author ngoc on 02/05/2018
 * @subject sale-management-app
 */

@Controller
public class CommonController
{
    /**
     * set status field into form
     *
     * @return Map
     */
    @ModelAttribute("statusList")
    public Map<String, String> setStatus()
    {
        Map<String, String> statusList = new TreeMap<>();
        statusList.put("AV", "Active");
        statusList.put("DE", "Deleted");
        statusList.put("UA", "UnActive");
        return statusList;
    }

    /**
     * get flash message.
     *
     * @param message String
     * @param args    Object[]
     * @return flash message
     */
    String getMessage(MessageSource messageSource, String message, Object[] args)
    {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}
