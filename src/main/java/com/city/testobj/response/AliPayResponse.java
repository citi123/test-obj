package com.city.testobj.response;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.city.testobj.util.Constants;

public class AliPayResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(AliPayResponse.class);

	public static void main(String[] args) throws AlipayApiException {
		//deal_notify();
		// deal_response();

		unsign();
	}

	static void unsign() throws AlipayApiException {
		Map<String,String> map = new HashMap<>();

		map.put("gmt_create","2018-04-24 15:35:25");
		map.put("charset","utf-8");
		map.put("seller_email","xqfgew5490@sandbox.com");
		map.put("subject","Iphone6 16G");
		map.put("sign","Wu/GkP0jhqOf3TUng+EnL3LP+GGkqOf+Rf1E7+ZenO8aHsy+4abvUbuXSrl+QEhs/+6pNY4d0d6oPQu289Xmc7AuQUk9noK3QihXaMerMbT8FmzS6am7ZuUdynb7v0MJWF5GKVFhfdxhBtAee2+gG7oqIsFhszPbAVim640LyLG1e2mzl3+/6fFFq8EACEadVH+4kGqXA8g+1PUncWKrbaeyRAk1A/XSEKmSB4HCnvYM4Y70d+qq0tjKuDaB6hJ0mXbtm9+50onRUUKLkZhD7ZHpWcR73Gm9lA4Byav9Ad5+h26FQXbxifwzfpIZ1OMjo8YswSXxuT3WITNKRoKFkg==");
		map.put("body","Iphone6 16G");
		map.put("buyer_id","2088102175908333");
		map.put("invoice_amount","100.00");
		map.put("notify_id","c4cfa1346bd79116395159fc9106d35ijq");
		map.put("fund_bill_list","[{\"amount\":\"100.00\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
		map.put("notify_type","trade_status_sync");
		map.put("trade_status","TRADE_SUCCESS");
		map.put("receipt_amount","100.00");
		map.put("app_id","2016091000479403");
		map.put("buyer_pay_amount","100.00");
		map.put("sign_type","RSA2");
		map.put("seller_id","2088102174993653");
		map.put("gmt_payment","2018-04-24 15:35:43");
		map.put("notify_time","2018-04-24 15:35:43");
		map.put("version","1.0");
		map.put("out_trade_no","out_trade_no2018042400001");
		map.put("total_amount","100.00");
		map.put("trade_no","2018042421001004330200436982");
		map.put("auth_app_id","2016091000479403");
		map.put("buyer_logon_id","vvg***@sandbox.com");
		map.put("point_amount","0.00");

		TreeMap<String,String> params = new TreeMap<>(map);

		for(Map.Entry<String,String> entry : params.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		boolean result = AlipaySignature.rsaCheckV1(params, Constants.AliPayConfig.ALI_PUBLIC_KEY,
				Constants.AliPayConfig.CHARSET_UTF8, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());
		System.out.println(result);
	}

	public static void deal_response() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("total_amount", "99.00");
		params.put("timestamp", "2018-03-23 23:31:46");
		params.put("sign",
				"tb99y3IkUKgjyvJE96av6VhTHIGyKL+/aO+ArXlhRPpSKIjnDDGScZXrGJMF9Hn8bQBQ7jhEPA/a+uLFFr+/S9iyO37L1Qr7BYJht2QRHj/LVpilSsjQpHOVCxe3MTVR7PVDzp7Wg+Ng8vtmAlQ9Zt34TVlo0pz9m8/xsG0tTBLznF5CjN4S8natfnpjDvznpjcQj0tqXUm3FKoPalB1XZpPs7Ul32p+MFiWpmB/3sdO20dY+f7Ts4Y0Pu/+92r9IDHEVh/9/3CVo9ua8OaWNWzuqSlepLngrgyZ8egsC6EXFtWbg9FdgncNSY/cX/JNlPRNNugAtEcBV7VKEYWOzA==");
		params.put("trade_no", "2018032321001004330200428208");
		params.put("sign_type", "RSA2");
		params.put("auth_app_id", "2016091000479403");
		params.put("charset", "utf-8");
		params.put("seller_id", "2088102174993653");
		params.put("method", "alipay.trade.page.pay.return");
		params.put("app_id", "2016091000479403");
		params.put("out_trade_no", "city_out_20180323000002");
		params.put("version", "1.0");
		
		

		
		try {
			boolean result = AlipaySignature.rsaCheckV1(params, Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.CHARSET_UTF8, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());
			System.out.println("验签结果:" + result);
			
			JsonConverter converter = new JsonConverter();
			AlipayTradePagePayResponse response = converter.fromJson(params, AlipayTradePagePayResponse.class);
			outObj(response);
		} catch (AlipayApiException e) {
			LOGGER.info("验签异常", e);
		}
	}

	public static void deal_notify() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("gmt_create", "2018-03-23 23:31:20");
		params.put("charset", "utf-8");
		params.put("gmt_payment", "2018-03-23 23:31:37");
		params.put("notify_time", "2018-03-23 23:31:38");
		params.put("subject", "city test pay");
		params.put("sign",
				"W8c6I+a/wr6KKXr7QI3xQ/EZt/UyYPxkQKrEP67Y6B+NtjaJw4q+1O/oT7nQoNo8CfWmrh3qvCYbV0CyVsrWfjUvlmU6LCSwTza7NX/E0zh0iWnUJmAk9aYOZFgpzAnR6wPuUloANgy4yB5iVPHDzNfsAfLgRKU/c7tdGA6+YWfEK9fwNHfF44/1ljHCLoVT4UfjISAfextBZqujNLS+edhT41dCXo7MPWd3FSPEWZhHPFkGDTWeOHpLdCkMWjUEiFfYHOL5owO25BChGU3vqaQHBypUv7EYszAVI/LJIlTzJ4GuZ83st5k1hnv9N94RvBrqxkw0t9sg2ezZCs5FXg==");
		params.put("buyer_id", "2088102175908333");
		params.put("body", "this is test payment");
		params.put("invoice_amount", "99.00");
		params.put("version", "1.0");
		params.put("notify_id", "ec927a0254595aba028f00dd449008cijq");
		params.put("fund_bill_list", "[{\"amount\":\"99.00\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
		params.put("notify_type", "trade_status_sync");
		params.put("out_trade_no", "city_out_20180323000002");
		params.put("total_amount", "99.00");
		params.put("trade_status", "TRADE_SUCCESS");
		params.put("trade_no", "2018032321001004330200428208");
		params.put("auth_app_id", "2016091000479403");
		params.put("receipt_amount", "99.00");
		params.put("point_amount", "0.00");
		params.put("app_id", "2016091000479403");
		params.put("buyer_pay_amount", "99.00");
		params.put("sign_type", "RSA2");
		params.put("seller_id", "2088102174993653");

		try {
			boolean result = AlipaySignature.rsaCheckV1(params, Constants.AliPayConfig.ALI_PUBLIC_KEY,
					Constants.AliPayConfig.CHARSET_UTF8, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());
			System.out.println("验签结果:" + result);

			JsonConverter converter = new JsonConverter();
			
			AlipayTradePayResponse response = converter.fromJson(params, AlipayTradePayResponse.class);
			
			
			
			
			outObj(response);
		} catch (AlipayApiException e) {
			LOGGER.info("验签异常", e);
		}
	}

	private static void outObj(Object obj) {
		if (obj == null) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		try {
			Class<?> clazz = obj.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					int mod = field.getModifiers();
					if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
						continue;
					}
					field.setAccessible(true);
					Object val = field.get(obj);
					/*
					 * if (val instanceof String) { String value = (String) val;
					 * if (StringUtils.isNotEmpty(value)) { value = new
					 * String(value.getBytes("ISO8859-1"), "utf-8"); }
					 * field.set(obj, value); }
					 */
					if (val != null) {
						sb.append(field.getName() + ":" + val.toString()).append("\n");
					} else {
						sb.append(field.getName() + ":").append("\n");
					}
				}
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(sb.toString());
	}
}
