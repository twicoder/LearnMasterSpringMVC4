package masterspringmvc.controller.profile;

import masterspringmvc.utils.USLocalDateformatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by I302636 on 3/13/2017.
 */
@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String displayProfile(Model model){
        model.addAttribute("profileForm",new ProfileForm());
        return "profile/profilePage";
    }

    @RequestMapping(value="/profile", method= RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        System.out.println("save ok" + profileForm);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateformatter.getPattern(locale);
    }
}
