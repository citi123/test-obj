package com.city.testobj.web;

import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.city.testobj.util.ApiResponse;
import com.city.testobj.util.BeanUtils;
import com.city.testobj.util.Constants;

@Controller
@RequestMapping("/huashengke")
public class HuaShengKeController {

	@RequestMapping("/test")
	@ResponseBody
	public ApiResponse<Map> test() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("datetime", LocalDateTime.now().toString());
		return ApiResponse.success(params);
	}

	@RequestMapping("/test2")
	public void pay(HttpServletRequest req, HttpServletResponse res, String outer_trade_no) {
		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		request.setReturnUrl("http://118.89.229.222:8080/test-obj-war/receive/response");
		request.setNotifyUrl("http://118.89.229.222:8080/test-obj-war/receive/notify");

		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo("city_out_"+outer_trade_no);
		model.setProductCode("FAST_INSTANT_TRADE_PAY");
		model.setTotalAmount("99");
		model.setSubject("city test pay 看看乱码不");
		model.setBody("this is test payment");
		model.setGoodsDetail(null);
		model.setPassbackParams("");

		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId("");
		extendParams.setHbFqNum("");
		extendParams.setHbFqSellerPercent("");
		// model.setExtendParams(extendParams);

		model.setGoodsType("0");
		model.setTimeoutExpress("90m");
		// model.setEnablePayChannels("");
		// model.setDisablePayChannels("");
		// auth_token
		model.setQrPayMode("2");
		model.setQrcodeWidth(300L);

		request.setBizModel(model);

		try {
			AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
			BeanUtils.printObj(response);

			String charset = req.getCharacterEncoding();
			res.setCharacterEncoding(charset);

			PrintWriter writer = res.getWriter();
			res.setContentType("text/html;charset=" + charset);
			writer.write(response.getBody());
			writer.flush();
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/test3")
	public void mobilePay(HttpServletRequest req, HttpServletResponse res) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		request.setReturnUrl("http://118.89.229.222:8080/test-obj-war/receive/response");
		request.setNotifyUrl("http://118.89.229.222:8080/test-obj-war/receive/notify");

		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody("this my phone test pay");
		model.setSubject("apple 16G");
		model.setOutTradeNo("city_mobile_2018032300001");
		model.setTimeoutExpress("90m");
		model.setTimeExpire("2018-03-24 23:23");
		model.setTotalAmount("20");
		// model.setAuthToken("");
		model.setProductCode("QUICK_WAP_WAY");
		model.setGoodsType("1");
		model.setPassbackParams(URLEncoder.encode("return me origin", "utf-8"));
		// model.setPromoParams("");
		// model.setExtendParams(null);
		// model.setEnablePayChannels("");
		// model.setDisablePayChannels("");
		// model.setStoreId("");
		// model.setQuitUrl("");
		// model.setExtUserInfo(null);

		request.setBizModel(model);

		try {
			AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
			PrintWriter writer = res.getWriter();
			res.setContentType("text/html;charset=utf-8");
			res.setCharacterEncoding("utf-8");
			writer.write(response.getBody());
			writer.flush();
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
