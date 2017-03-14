package masterspringmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I302636 on 3/13/2017.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam(defaultValue="masterSpringMVC") String search, Model model){
        List<String> tweets = new ArrayList<>();
        tweets.add("Hello World");
        tweets.add("This is my test!");
        tweets.add("God will help you!");
        tweets.add("Java EE");
        tweets.add("Design pattern");
        tweets.add(search);
        List<String> searchResults=new ArrayList<>();
        searchResults.add("Search Baidu");
        searchResults.add("Search Google");
        searchResults.add("Sogou search");


        model.addAttribute("tweets",tweets);
        model.addAttribute("search",search);

        return "resultPage";
    }
}
