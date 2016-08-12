package com.shenjianli.lib.engine.home.bean;

import java.util.List;

/**
 * Created by edianzu on 2016/8/12.
 */
public class SecKillBean {
    private String btn_more;
    /**
     * action : http://m.mall.icbc.com.cn/mobile/mobileSeckill/mobileSeckillDetail.jhtml?actyId=0000005868&fromType=index
     * discount : 6.9折
     * endTime : 2016-08-12 16:00:00
     * imgUrl : http://image2.mall.icbc.com.cn/images/10003629/1416904866553_2_1.jpg
     * prodname : 41度丛台酒白瓷瓶 500ml 浓香型   冀派经典白酒 宴请自饮
     * productname :
     * promotionPrice : ￥19.90
     * startTime : 2016-08-12 15:00:00
     * systemTime : 2016-08-12 11:23:48
     */

    private List<ProductBean> product;

    public String getBtn_more() {
        return btn_more;
    }

    public void setBtn_more(String btn_more) {
        this.btn_more = btn_more;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }
}
