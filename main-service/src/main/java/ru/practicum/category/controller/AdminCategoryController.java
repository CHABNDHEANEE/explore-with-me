package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@PathVariable Long catId, @Valid @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.updateCategory(catId, newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }
}
