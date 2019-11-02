package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.vo.ProductDetailVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

public interface IProductService {
    public ServerResponse addOrUpdate(Product product);
    public ServerResponse updateStatus(Product product);

    /**
     * 后台商品搜索
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse search(String productName,
                                 Integer productId,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 后台商品详情
     * @param ProductId
     * @return
     */
    public ServerResponse<ProductDetailVo> detail(Integer ProductId);
    /**
     * 后台商品详情
     * @param ProductId
     * @return
     */
    public ServerResponse<Product> findProductByProductId( Integer ProductId);

    /**
     * 根据商品的id查询商品的库存
     * */
    public ServerResponse<Product> findProductById(Integer productId);
    /**
     * 扣库存
     */
    public ServerResponse reduceStock(Integer productId,Integer stock);



}
