package com.store_management_system.sms.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.store_management_system.sms.model.*;
import com.store_management_system.sms.repository.FeedbackRepository;
import com.store_management_system.sms.service.UserService;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    UserService userService;

    @GetMapping("/feedback/create/{id}")
    public String createFeedback(Model model,@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
           Feedback feedback =new Feedback();
           feedback.setOrderId(id);
           LocalDate currdate=LocalDate.now();
           feedback.setFdate(currdate);
            model.addAttribute("feedback", feedback);
            return "createFeedback";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to get create feedback page."+e.getMessage());
            return "redirect:/customers";
        }
        
    }
    @PostMapping("/feedback/create")
    public String postCreateFeedback(Model model,@ModelAttribute Feedback feedback, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
            feedbackRepository.save(feedback);
            return "redirect:/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to create Feedback "+e.getMessage());
            model.addAttribute("feedback", feedback);
            return "createFeedback";
        }
    }

    @GetMapping("/feedback/view/{id}")
    public String viewFeedback(Model model,@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
            Feedback feedback=feedbackRepository.findByOrderId(id);
            model.addAttribute("feedback", feedback);

            return "viewfeedback";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to get feedback page."+e.getMessage());
            return "redirect:/customers";
        }
    }

    @PostMapping("/update/feedback/{id}")
    public String updateFeedback(Model model,@PathVariable Long id,@ModelAttribute Feedback feedback, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
            feedbackRepository.save(feedback);
            return "redirect:/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to update feedback page."+e.getMessage());
            model.addAttribute("feedback",feedback);
            return "viewfeedback";
        }
    }

    @PostMapping("/delete/feedback/{id}")
    public String deleteFeedback(Model model,@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
            feedbackRepository.deleteByOrderId(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to delete."+e.getMessage());
        
        }
        
        return "redirect:/customers";
    }
}
