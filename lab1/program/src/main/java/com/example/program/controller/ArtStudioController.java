package com.example.program.controller;

import com.example.program.service.UploadingVideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ArtStudioController {
    private final UploadingVideoService uploadingService;

    public ArtStudioController(UploadingVideoService uploadingService) {
        this.uploadingService = uploadingService;
    }

    @GetMapping("/home")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "upload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if(file != null){
            uploadingService.upload(file);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        }

        return "redirect:/home";
    }
}
