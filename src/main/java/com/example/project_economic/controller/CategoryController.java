package com.example.project_economic.controller;

import com.example.project_economic.entity.CategoryEntity;
import com.example.project_economic.service.CategoryService;
import com.example.project_economic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(path = "/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private  ProductController productController;
    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") CategoryEntity categoryEntity, Model model, Principal principal){
        CategoryEntity categoryEntity1=new CategoryEntity(categoryEntity.getName());
        try{
            this.categoryService.save(categoryEntity1);

        }catch (Exception exception){
            model.addAttribute("error","error");
        }
        model.addAttribute("allcategories",this.categoryService.findAll());
        model.addAttribute("countProductByCategory",productController.countProuductDtos());

        return "home/addnew";
    }

    @GetMapping("/edit/")
    public String show_form_updateCategory(@RequestParam("id") Long categoryId,Model model){
        model.addAttribute("edit_category",categoryId);
        model.addAttribute("allcategories",this.categoryService.findAll());
        model.addAttribute("category",new CategoryEntity());
        model.addAttribute("categoryEdit",this.categoryService.findById(categoryId));
        model.addAttribute("countProductByCategory",productController.countProuductDtos());
        return "home/addnew";
    }
    @PostMapping("/update/")
    public String updateCategory(@ModelAttribute("category") CategoryEntity categoryUpdate,@RequestParam("id") Long id,Model model){
        try{
            this.categoryService.update(categoryUpdate,id);
            model.addAttribute("category",new CategoryEntity());
        }catch (Exception exception){
            model.addAttribute("error","error");
        }
        model.addAttribute("allcategories",this.categoryService.findAll());
        model.addAttribute("countProductByCategory",productController.countProuductDtos());

        return "home/addnew";
    }

    @GetMapping("/update/delete/")
    public String deleteCategory(Model model,@RequestParam("id") Long id){
        this.categoryService.deleteById(id);
        model.addAttribute("allcategories",this.categoryService.findAll());
        model.addAttribute("countProductByCategory",productController.countProuductDtos());
        model.addAttribute("category",new CategoryEntity());
        return "home/addnew";
    }
    @GetMapping("/update/active/")
    public String activeCategory(Model model,@RequestParam("id") Long id){
        this.categoryService.enableById(id);
        model.addAttribute("allcategories",this.categoryService.findAll());
        model.addAttribute("countProductByCategory",productController.countProuductDtos());
        model.addAttribute("category",new CategoryEntity());
        return "home/addnew";
    }
}
