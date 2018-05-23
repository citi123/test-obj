package com.city.testobj.web;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.city.testobj.util.ErWeiMa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.city.testobj.util.ApiResponse;
import com.city.testobj.util.BeanUtils;
import com.city.testobj.util.Constants;

@Controller
@RequestMapping("/huashengke")
public class HuaShengKeController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HuaShengKeController.class);
	
	@RequestMapping("/test")
	@ResponseBody
	public ApiResponse<Map> test() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("datetime", LocalDateTime.now().toString());
		return ApiResponse.success(params);
	}

	@RequestMapping("/test2")
	public void pay(HttpServletRequest req, HttpServletResponse res, String out_trade_no) {
		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		request.setReturnUrl("http://118.89.229.222:8080/test-obj-war/receive/response");
		request.setNotifyUrl("http://118.89.229.222:8080/test-obj-war/receive/notify");

		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo("city_out_"+out_trade_no);
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
		model.setTimeoutExpress("5m");
		// model.setEnablePayChannels("");
		// model.setDisablePayChannels("");
		// auth_token
		model.setQrPayMode("2");
		model.setQrcodeWidth(300L);

		request.setBizModel(model);
		LOGGER.info(JSON.toJSONString(model));
		try {
			AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
			BeanUtils.printObj(response);

			String charset = req.getCharacterEncoding();
			res.setCharacterEncoding(charset);

			PrintWriter writer = res.getWriter();
			res.setContentType("text/html;charset=" + charset);
			writer.write(response.getBody());
			writer.flush();
			LOGGER.info(response.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/billQuery")
	@ResponseBody
	public ApiResponse<AlipayTradeQueryResponse> billQuery(HttpServletRequest req, HttpServletResponse res,String outer_trade_no) throws Exception{
		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(outer_trade_no);
		request.setBizModel(model);
		LOGGER.info(JSON.toJSONString(model));
		AlipayTradeQueryResponse response = alipayClient.execute(request);

		BeanUtils.printObj(response);

		return ApiResponse.success(response);
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
		LOGGER.info(JSON.toJSONString(model));
		try {
			AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
			PrintWriter writer = res.getWriter();
			res.setContentType("text/html;charset=utf-8");
			res.setCharacterEncoding("utf-8");
			writer.write(response.getBody());
			writer.flush();
			LOGGER.info(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping("/test4")
	public void scanPay(HttpServletRequest req, HttpServletResponse res,String out_trade_no) throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

		request.setNotifyUrl("http://139.199.165.21:8080/test-obj-war/jsp/receive/notify");

		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setOutTradeNo("out_trade_no" + out_trade_no);
		// model.setSellerId("");
		model.setTotalAmount("100");
		// model.setDiscountableAmount("");
		model.setSubject("Iphone6 16G");
		// model.setGoodsDetail(null);
		model.setBody("Iphone6 16G");
		// model.setOperatorId("");
		// model.setStoreId("");
		// model.setDisablePayChannels("");
		// model.setEnablePayChannels("");
		// model.setTerminalId("");
		// model.setExtendParams(null);
		model.setTimeoutExpress("5m");
		// model.setBusinessParams("");
		request.setBizModel(model);

		LOGGER.info(JSON.toJSONString(model));

		AlipayTradePrecreateResponse response = alipayClient.execute(request);

		BeanUtils.printObj(response);

		if(response.isSuccess()){
			BufferedImage bufferedImage = ErWeiMa.create(response.getQrCode(),"utf-8");

			OutputStream os = res.getOutputStream();
			ImageIO.write(bufferedImage, "png", os);
			os.flush();
			os.close();
		}else{
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/html;charset=utf-8");
			PrintWriter writer = res.getWriter();
			writer.write("失败了");
			writer.flush();
			writer.close();
		}

	}


}
