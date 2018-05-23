package com.city.testobj.response;

import com.city.testobj.thirdpay.wxpay.MyWXPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

import java.util.Map;

/**
 * Created by City Mo on 2018/4/26.
 */
public class WxResponse {
    public static void main(String[] args) throws Exception {
        String xmlStr = "<xml>\n" +
                "  <appid><![CDATA[wxab8acb865bb1637e]]></appid>\n" +
                "  <bank_type><![CDATA[CMB_CREDIT]]></bank_type>\n" +
                "  <cash_fee><![CDATA[1]]></cash_fee>\n" +
                "  <device_info><![CDATA[WEB]]></device_info>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[11473623]]></mch_id>\n" +
                "  <nonce_str><![CDATA[dfa7a63fc7b742b38aedff443cece42c]]></nonce_str>\n" +
                "  <openid><![CDATA[oHkLxtwK5T_ZOZlEOXYh_wi1SVCk]]></openid>\n" +
                "  <out_trade_no><![CDATA[out_trade_no2018042400002]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[9523FA24ABEBA2ACFE051B4F7EE5CFB9C5EC8A3EB507C2EAA54B09A60C789331]]></sign>\n" +
                "  <time_end><![CDATA[20180424154437]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <trade_type><![CDATA[NATIVE]]></trade_type>\n" +
                "  <transaction_id><![CDATA[4200000098201804245167073994]]></transaction_id>\n" +
                "</xml>";
        Map<String,String> map = WXPayUtil.xmlToMap(xmlStr);

        MyWXPayConfig config = MyWXPayConfig.getInstance();
        WXPay wxpay = new WXPay(config);
        System.out.println(wxpay.isPayResultNotifySignatureValid(map));
        System.out.println(wxpay.isResponseSignatureValid(map));
    }
}
