package com.neuedu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.vo.ProductDetailVo;
import com.neuedu.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ICategoryService iCategoryService;

    @Autowired
    ProductMapper productMapper;
    @Value("${Demo.imageHost}")
    private String imageHost;



    @Override
    public ServerResponse addOrUpdate(Product product) {
        if (product == null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //subimages 1.png,2.png
        //设置商品的主图 sub_images->100jpg
        String sub_image=product.getSubImages();
        if (sub_image!= null&&!sub_image.equals("")){
          String[] sub_imageArr=sub_image.split(",");
          if (sub_imageArr.length>0){
              //设置商品的主图
              product.setMainImage(sub_imageArr[0]);
          }
        }


        Integer productId = product.getId();
        if (productId == null) {
            int result = productMapper.insert(product);
            if (result <= 0) {
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "添加失败");
            } else {
                return ServerResponse.serverResponseBySuccess();
            }

        } else {
            int result = productMapper.updateByPrimaryKey(product);
            if (result <= 0) {
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "更新失败");
            } else {
                return ServerResponse.serverResponseBySuccess();
            }
        }

    }

    @Override
    public ServerResponse updateStatus(Product product) {
        if (product == null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Integer productId = product.getId();

        if (productId != null) {
            int result = productMapper.updateStatus(product);
            if (result <= 0) {
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "更改商品状态失败");
            }
        }
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {

        if (productName!=null){
            productName="%"+productName+"%";
        }
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Product> productList=productMapper.findProductByIdName(productId, productName);

        List<ProductListVO> productListVOS= Lists.newArrayList();
        for (Product product:productList
             ) {
            //product-->productlistVo
            ProductListVO productListVO=assembleProductListVO(product);
            productListVOS.add(productListVO);
        }
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        if (productId == null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.serverResponseBySuccess();
        }
        ProductDetailVo productDetailVo=assembleProductDetailVO(product);
        return ServerResponse.serverResponseBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<Product> findProductByProductId(Integer ProductId) {
        if (ProductId == null) {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        Product product=productMapper.selectByPrimaryKey(ProductId);
        if (product==null){
            return ServerResponse.serverResponseBySuccess();
        }

        return ServerResponse.serverResponseBySuccess(product);
    }


    @Override
    public ServerResponse<Product> findProductById(Integer productId) {
        if (productId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id不能为空");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品不存在");
        }

        return ServerResponse.serverResponseBySuccess(product);
    }

    @Override
    public ServerResponse reduceStock(Integer productId, Integer stock) {
        if (productId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品id不能为空");
        }
        if (stock==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"库存参数不能为空");
        }
        int result=productMapper.reduceProductStock(productId, stock);
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"扣库存失败");
        }

        return ServerResponse.serverResponseBySuccess();
    }

    private ProductDetailVo assembleProductDetailVO(Product product){





        ProductDetailVo productDetailVO=new ProductDetailVo();

        productDetailVO.setCategoryId(product.getCategoryId());

        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));

        productDetailVO.setDetail(product.getDetail());

        productDetailVO.setImageHost(imageHost);

        productDetailVO.setName(product.getName());

        productDetailVO.setMainImage(product.getMainImage());

        productDetailVO.setId(product.getId());

        productDetailVO.setPrice(product.getPrice());

        productDetailVO.setStatus(product.getStatus());

        productDetailVO.setStock(product.getStock());

        productDetailVO.setSubImages(product.getSubImages());

        productDetailVO.setSubtitle(product.getSubtitle());

        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));

        //Category category= categoryMapper.selectByPrimaryKey(product.getCategoryId());
        ServerResponse<Category> serverResponse=iCategoryService.selectCategory(product.getCategoryId());
        Category category=serverResponse.getData();
        if(category!=null){

            productDetailVO.setParentCategoryId(category.getParentId());
        }

        return productDetailVO;

    }


    private ProductListVO assembleProductListVO(Product product){

        ProductListVO productListVO=new ProductListVO();

        productListVO.setId(product.getId());

        productListVO.setCategoryId(product.getCategoryId());

        productListVO.setMainImage(product.getMainImage());

        productListVO.setName(product.getName());

        productListVO.setPrice(product.getPrice());

        productListVO.setStatus(product.getStatus());

        productListVO.setSubtitle(product.getSubtitle());



        return  productListVO;

    }
}