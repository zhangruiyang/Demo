package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public interface ICategoryService {
    /**
     * 添加类别
     */

    public ServerResponse addCategory(Category category);
    /**
     * 修改类别
     * categoryid
     * categoryName
     * categoryurl
     */

    public ServerResponse updateCategory(Category category);
    /**
     * 查看平级类别
     */

    public ServerResponse getCategoryById(@PathVariable("category") Integer categoryId);
    /**
     * 查看平级类别
     */

    public ServerResponse deepCategory(@PathVariable("category") Integer categoryId);
    /**
     * 根据id查询类别
     */
    public ServerResponse<Category> selectCategory(Integer categoryId);
}
