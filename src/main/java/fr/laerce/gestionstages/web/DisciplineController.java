package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.domain.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  12/02/2018.
 *
 * @author fred
 */

@Controller
public class DisciplineController {

    @Autowired
    DisciplineRepository repoDiscipline;



    @GetMapping("/discipline/liste")
    public String liste(Model model){
        model.addAttribute("disciplines", repoDiscipline.findAll());
        return "disciplineliste";
    }

    @GetMapping("/discipline/form")
    public String formGet(Model model){
        model.addAttribute("discipline", new Discipline());
        return "disciplineform";
    }

    @PostMapping("/discipline/update")
    public String formPost(@ModelAttribute Discipline discipline){
        System.out.println(discipline);
        repoDiscipline.save(discipline);
        return "redirect:/discipline/liste";
    }

    @GetMapping("/discipline/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        repoDiscipline.deleteById(id);
        return "redirect:/discipline/liste";
    }

    @GetMapping("/discipline/modif/{id}")
    public ModelAndView modif(@PathVariable("id")Long id, ModelMap model){
        Optional<Discipline> discipline = repoDiscipline.findById(id);
        System.out.println("-----> MODIF   "+discipline.get());
        model.addAttribute("discipline",discipline.get());
        return new ModelAndView("disciplineform", model);
    }

}
