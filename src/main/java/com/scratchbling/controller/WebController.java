package com.scratchbling.controller;

import com.scratchbling.model.Item;
import com.scratchbling.model.ItemSize;
import com.scratchbling.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private ItemRepository repository;

    @ModelAttribute
    public void populateModel(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("items", repository.findAll(pageable));
        model.addAttribute("sizes", ItemSize.values());
        model.addAttribute("search", "");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String consumerInterface(Pageable pageable, Model model) {

        model.addAttribute("title", "Scratch bling consumer page");

        return "consumer";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("title", "Admin Panel");

        model.addAttribute("item", new Item());

        model.addAttribute("action", "create");

        model.addAttribute("search", "");


        return "admin";
    }

    @PostMapping(path = "/admin", params = {"action=create"})
    public String createItem(Item item, RedirectAttributes model) {

        repository.save(item);
        model.addFlashAttribute("message", "Item created successfully");
        return "redirect:/web/admin";
    }

    @PostMapping(path = "/admin", params = {"action=delete"})
    public String deleteItem(@RequestParam Long id, RedirectAttributes model) {

        repository.deleteById(id);
        model.addFlashAttribute("message", "Item deleted successfully");
        return "redirect:/web/admin";
    }

    @PostMapping(path = "/admin", params = {"action=edit"})
    public String editItem(@RequestParam Long id, Model model) {
        model.addAttribute("title", "Admin Panel");
        model.addAttribute("action", "submit-edit");

        Optional<Item> optionalItem = repository.findById(id);

        model.addAttribute("item", optionalItem.orElse(new Item()));

        return "admin";
    }

    @PostMapping(path = "/admin", params = {"action=submit-edit"})
    public String submitEditItem(Item item, RedirectAttributes model) {
        repository.save(item);
        model.addFlashAttribute("message", "Item updated successfully");
        return "redirect:/web/admin";
    }

    @GetMapping(path = "/admin", params = {"action=search"})
    public String searchItem(@RequestParam String search, @PageableDefault Pageable pageable, Model model) {
        model.addAttribute("title", "Admin Panel");
        model.addAttribute("item", new Item());

        model.addAttribute("action", "create");
        model.addAttribute("search", search);

        Page<Item> items = repository.findByNameIgnoreCaseContaining(search, pageable);

        model.addAttribute("items", items);

        return "admin";
    }
}
