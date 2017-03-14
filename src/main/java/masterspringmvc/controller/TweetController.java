package masterspringmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I302636 on 3/13/2017.
 */
@Controller
public class TweetController {

    @RequestMapping("/")
    public String home(){
        return "searchPage";
    }

    @RequestMapping(value="/postSearch",method= RequestMethod.POST)
    public String postsearch(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String search = request.getParameter("search");
        if(search.toLowerCase().contains("struts")){
            redirectAttributes.addFlashAttribute("error","Try using spring instead!");
            return "redirect:/";
        }

        redirectAttributes.addAttribute("search",search);
        return "redirect:result";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue="masterStringMVC4") String search,Model model){
        List<String> tweets = new ArrayList<>();
        tweets.add("This is tweet 1");
        tweets.add("This is tweet 2");
        tweets.add("This is tweet 3");
        tweets.add("This is tweet 4");
        model.addAttribute("tweets",tweets);

        List<String> searchResults=new ArrayList<>();
        searchResults.add("Search Baidu");
        searchResults.add("Search Google");
        searchResults.add("Sogou search");

        model.addAttribute("search",search);

        return "resultPage";
    }

}
