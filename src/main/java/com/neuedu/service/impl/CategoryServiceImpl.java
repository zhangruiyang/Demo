package com.neuedu.service.impl;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ServerResponse addCategory(Category category) {
        if (category==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        int result=categoryMapper.insert(category);
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加品类失败");
        }


        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse updateCategory(Category category) {
        if (category==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数不能为空");

        }
        if (category.getId()==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"类别id不能为空");
        }
        int result=categoryMapper.updateByPrimaryKey(category);
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新品类失败");
        }

        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse getCategoryById(Integer categoryId) {
        if (categoryId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"id不能为空");
        }
        List<Category> categoryList=categoryMapper.selectCategoryById(categoryId);


        return ServerResponse.serverResponseBySuccess(categoryList,"成功");
    }

    @Override
    public ServerResponse deepCategory(Integer categoryId) {
        if (categoryId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"id不能为空");
        }
        Set<Category> categorySet = Sets.newHashSet();
        //递归查询
        Set<Category> categorySet1=findALLChildCategory(categorySet,categoryId);
         //平级子类根据id 所以转成Integer
        Set<Integer> categoryIds=Sets.newHashSet();
        Iterator<Category> iterator=categorySet1.iterator();
        while (iterator.hasNext()){
          Category category=iterator.next();
          categoryIds.add(category.getId());
        }


        return ServerResponse.serverResponseBySuccess(categoryIds);
    }

    @Override
    public ServerResponse<Category> selectCategory(Integer categoryId) {
       if (categoryId==null){
           return ServerResponse.serverResponseByError(ResponseCode.ERROR,"类别id不能为空");
       }
        Category category=categoryMapper.selectByPrimaryKey(categoryId);

        return ServerResponse.serverResponseBySuccess(category);
    }

    public Set<Category> findALLChildCategory(Set<Category> categorySet,Integer CategoryId){
       //查看categoryId的类别信息
        Category category=categoryMapper.selectByPrimaryKey(CategoryId);
        if (category!=null){
            categorySet.add(category);
        }
        //查看categoryId的评级子类
        List<Category> categoryList=categoryMapper.selectCategoryById(CategoryId);
        if (categoryList!=null&&categoryList.size()>0){
            for (Category category1:categoryList
                 ) {
                findALLChildCategory(categorySet,category1.getId());
            }
        }

         return categorySet;
    }
}
