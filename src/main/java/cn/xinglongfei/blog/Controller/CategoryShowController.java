package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.po.Category;
import cn.xinglongfei.blog.service.BlogService;
import cn.xinglongfei.blog.service.CategoryService;
import cn.xinglongfei.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/19
 */
@Controller
public class CategoryShowController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/categories/{id}")
    public String categories(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        List<Category> categories = categoryService.listCategoryTop(1000);
        if (id == -1) {
            id = categories.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setCategoryId(id);
        model.addAttribute("categories", categories);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeCagegoryId",id);
        return "categories";
    }
}
