package wso2.integration.server.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class UserController {

    @RequestMapping(value ="/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public Principal getUserr(Principal principal) {
        return principal;
    }
}
