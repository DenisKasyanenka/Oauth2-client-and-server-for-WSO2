package wso2.integration.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AppController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${wso2SessionTerminateUrl}")
	private String wso2SessionTerminateUrl;
	
    @RequestMapping("/")
    public String greeting(Model model) {
        model.addAttribute("wso2SessionTerminateUrl", wso2SessionTerminateUrl);
        return "index";
    }
    
    /**
     * NOTE that this method assumes that the user performing this action is authenticated.
     * This is implied by the implementation, noting it here in addition to this.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
