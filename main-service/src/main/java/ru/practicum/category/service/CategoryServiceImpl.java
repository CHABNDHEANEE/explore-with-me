package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.util.ObjectCheckExistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.category.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ru.practicum.category.service.CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final ObjectCheckExistence checkExistence;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Optional<Category> cat = categoryRepository.findByName(newCategoryDto.getName());
        if (cat.isPresent()) {
            throw new ConflictException(String.format("Category %s already exists",
                    newCategoryDto.getName()));
        }
        Category category = categoryRepository.save(CATEGORY_MAPPER.toCategory(newCategoryDto));
        return CATEGORY_MAPPER.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long catId, NewCategoryDto newCategoryDto) {
        Category category = checkExistence.getCategory(catId);
        Optional<Category> cat = categoryRepository.findByName(newCategoryDto.getName());
        if (cat.isPresent() && !category.getName().equals(newCategoryDto.getName())) {
            throw new ConflictException(String.format("Category %s already exists",
                    newCategoryDto.getName()));
        }
        category.setName(newCategoryDto.getName());
        return CATEGORY_MAPPER.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConflictException("You can't delete category with linked events");
        }
        checkExistence.getCategory(catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).stream()
                .map(CATEGORY_MAPPER::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CATEGORY_MAPPER.toCategoryDto(checkExistence.getCategory(catId));
    }
}
