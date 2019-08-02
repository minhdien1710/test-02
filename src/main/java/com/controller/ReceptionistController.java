package com.controller;

import com.model.Receptionist;
import com.model.ReceptionistForm;
import com.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/receptionist")
@PropertySource("classpath:global_config_app.properties")

public class    ReceptionistController {
    @Autowired
    private ReceptionistService receptionistService;
    @Autowired
    Environment environment;

    @GetMapping("/list*")
    public ModelAndView findAll() {
        List<Receptionist> receptionistList = receptionistService.findAll();
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionist", receptionistList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveReceptionist(@ModelAttribute ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();

        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Receptionist receptionistObject = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge()
                , receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);

        receptionistService.addReceptionist(receptionistObject);
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        modelAndView.addObject("message", "add successfully");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView showEditForm(@PathVariable int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(), receptionist.getAge(),
                receptionist.getAddress(), receptionist.getHobby(), null);
        ModelAndView modelAndView = new ModelAndView("/receptionist/update");
        modelAndView.addObject("receptionistForm", receptionistForm);
        modelAndView.addObject("receptionist", receptionist);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateReceptionist(@ModelAttribute("receptionist") ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload");
        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Receptionist receptionist = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(),
                receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);
        receptionistService.updateReceptionist(receptionist.getId(), receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/update");
        modelAndView.addObject("receptionistForm", receptionistForm);
        modelAndView.addObject("message", "Update receptionist successfully");
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") int id) {
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                    receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
            ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
            modelAndView.addObject("receptionist", receptionist);
            modelAndView.addObject("receptionistForm", receptionistForm);
            return modelAndView;
        } else {
            return null;
        }

    }

    @PostMapping("/delete")
    public String deleteReceptionist(@ModelAttribute("receptionistForm") Receptionist receptionist) {
        receptionistService.removeReceptionist(receptionist.getId());
        return "receptionist/list";
    }

    @GetMapping("/detail/{id}")
    public ModelAndView viewReceptionist(@PathVariable int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                receptionist.getAge(), receptionist.getHobby(), receptionist.getAddress(), null);
        ModelAndView modelAndView = new ModelAndView("/receptionist/detail");
        modelAndView.addObject("receptionistForm", receptionistForm);
        modelAndView.addObject("receptionist", receptionist);
        return modelAndView;

    }
    @GetMapping("/search")
    public ModelAndView Search(@RequestParam("search") String name) {
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionists", receptionistService.findByName(name));
        return modelAndView;
    }
}

