package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.CheckEnum;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    IProductService productService;

     @Autowired
    CartMapper cartMapper;
    @Override
    public ServerResponse addProductToCart(Integer userId, Integer productId, Integer count) {
        //step1:参数的非空判断
        if (productId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品的id必传");
        }
        if (count==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品数量不能为零");
        }
        //step2:判断商品是否存在
         ServerResponse serverResponse=productService.findProductById(productId);
        if (!serverResponse.isSuccess()){//商品不存在
            return ServerResponse.serverResponseByError(serverResponse.getStatus(),serverResponse.getMsg());
        }else {
            Product product= (Product) serverResponse.getData();
            if (product.getStatus()<=0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"商品已售空");
            }
        }
        //判断商品是否在购物车
        Cart cart=cartMapper.findCartByUIdPId(userId, productId);
        if (cart==null){
            Cart cart1=new Cart();
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(CheckEnum.CART_PRODUCT_CHECK.getCheck());
           int result=cartMapper.insert(cart1);
           if (result<=0){
               return ServerResponse.serverResponseByError(ResponseCode.ERROR,"添加失败");
           }
        }else {
            cart.setQuantity(cart.getQuantity()+count);
            int result=cartMapper.updateByPrimaryKey(cart);
            if (result<=0){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新失败");
            }
        }
        //step4:封装购物车对象cartVO
         CartVO cartVO=getCartVO(userId);
        //step5:返回cartVO
        return ServerResponse.serverResponseBySuccess(cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO=getCartVO(userId);
        return ServerResponse.serverResponseBySuccess(cartVO);
    }

    @Override
    public ServerResponse update(Integer userId,Integer productId, Integer count) {
        //1.参数的判空
        if (productId == null || count==null){
          return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        //2.查询购物车中的商品
        Cart cart=cartMapper.findCartByUIdPId(userId,productId);
        if (cart!=null){
            //3.更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        //4.返回cartVo
        return ServerResponse.serverResponseBySuccess(getCartVO(userId));

    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //step1:参数非空校验
        if(productIds==null||productIds.equals("")){
            return ServerResponse.serverResponseByError("参数不能为空");
        }
        //step2:productIds-->List<Integer>

        List<Integer> productIdList=Lists.newArrayList();

        String[] productIdsArr=  productIds.split(",");

        if(productIdsArr!=null&&productIdsArr.length>0){

            for(String productIdstr:productIdsArr){

                Integer productId=Integer.parseInt(productIdstr);

                productIdList.add(productId);
            }
        }
        //step3:调用dao
        cartMapper.deleteByUseridAndProductIds(userId,productIdList);
        //step4:返回结果
        return ServerResponse.serverResponseBySuccess(getCartVO(userId));

    }

    @Override
    public ServerResponse<List<Cart>> findCartsByUseridCheck(Integer userId) {
        List<Cart> cartList=cartMapper.findCartsByUseridCheck(userId);
        return ServerResponse.serverResponseBySuccess(cartList);
    }

    @Override
    public ServerResponse deleteBatch(List<Cart> cartList) {
        if(cartList==null||cartList.size()==0){
            return ServerResponse.serverResponseByError("要删除的购物车商品不能为空");
        }
        int result=cartMapper.deleteBatch(cartList);
        if (result!=cartList.size()){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"购物车批量删除失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }


    private CartVO getCartVO(Integer userId){
        CartVO cartVO=new CartVO();
        //1.根据用户的id查询用户的购物信息
        List<Cart> cartList=cartMapper.findCartByUserId(userId);
        if (cartList==null||cartList.size()==0){
            return cartVO;
        }
        //定义购物车商品总价格
        BigDecimal cartTotalPrice=new BigDecimal("0");
        //2.List<cart>-->List<CartProductVO>
        List<CartProductVO> cartProductVOList= Lists.newArrayList();
        int limit_quantity=0;
        String limitQuantity=null;
        for (Cart cart:cartList
             ) {
            CartProductVO cartProductVO=new CartProductVO();
            cartProductVO.setId(cart.getId());
            cartProductVO.setUserId(userId);
            cartProductVO.setProductId(cart.getProductId());
            cartProductVO.setQuantity(cart.getQuantity());
            ServerResponse serverResponse=productService.findProductById(cart.getProductId());
            if (serverResponse.isSuccess()){
                Product product=(Product) serverResponse.getData();
                if (product.getStock()>=cart.getQuantity()){
                    limit_quantity=cart.getQuantity();
                    limitQuantity="LIMIT_NUM_SUCCESS";
                }else {
                    limit_quantity=product.getStock();
                    limitQuantity="LIMIT_NUM_FAIL";
                }
                cartProductVO.setQuantity(limit_quantity);
                cartProductVO.setLimitQuantity(limitQuantity);
                cartProductVO.setProductName(product.getName());
                cartProductVO.setProductSubtitle(product.getSubtitle());
                cartProductVO.setProductMainImage(product.getMainImage());
                cartProductVO.setProductPrice(product.getPrice());
                cartProductVO.setProductStatus(product.getStatus());
                cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue()
                        ,cart.getQuantity()*1.0));
                cartProductVO.setProductStock(product.getStock());
                cartProductVO.setProductChecked(cart.getChecked());
                cartProductVOList.add(cartProductVO);
                if (cart.getChecked()==CheckEnum.CART_PRODUCT_CHECK.getCheck()){
                    //商品被选中
                    cartTotalPrice=BigDecimalUtils.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());
                }
            }
        }
        cartVO.setCartProductVOList(cartProductVOList);
        //3.计算购物车的总价格
        cartVO.setCarttotalprice(cartTotalPrice);
        //4.判断是否全选
        Integer count=cartMapper.isAllChecked(userId);
        if (count==0){
            //全选
            cartVO.setIsallchecked(true);
        }else {
            cartVO.setIsallchecked(false);
        }
        //5。构建cartVO
        return cartVO;
    }
}
