package com.city.testobj.thirdpay.alipay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.city.testobj.util.Constants;
import com.google.gson.stream.JsonWriter;

public class Test {

	public static void main2(String[] args) {
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo("city_out_20180323000002");

		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizModel(model);

		AlipayClient alipayClient = new DefaultAlipayClient(Constants.AliPayConfig.SERVER_URL,
				Constants.AliPayConfig.APP_ID, Constants.AliPayConfig.MERCHAT_PRIVATE_KEY,
				Constants.AliPayConfig.FORMAT, Constants.AliPayConfig.CHARSET_UTF8,
				Constants.AliPayConfig.ALI_PUBLIC_KEY, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		try {
			AlipayTradeQueryResponse re = alipayClient.execute(request);
			System.out.println(new JSONWriter().write(re, true));
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// main2(null);
	}

	public static void main(String[] args) {
		String url = "https://openapi.alipaydev.com/gateway.do";
		// String url =
		// "http://118.89.229.222:8080/test-obj-war/receive/notify";

		Map<String, String> data = new HashMap<String, String>();
		data.put("out_trade_no", "city_out_20180323000002");
		// data.put("trade_no", "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", Constants.AliPayConfig.APP_ID);
		params.put("method", "alipay.trade.query");
		params.put("format", Constants.AliPayConfig.FORMAT);
		params.put("charset", Constants.AliPayConfig.CHARSET_UTF8);
		params.put("sign_type", Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

		params.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")));
		params.put("version", "1.0");
		params.put("app_auth_token", "");
		params.put("biz_content", new JSONWriter().write(data, true));

		try {

			String sign = AlipaySignature.rsa256Sign(AlipaySignature.getSignContent(params),
					Constants.AliPayConfig.MERCHAT_PRIVATE_KEY, Constants.AliPayConfig.CHARSET_UTF8);

			params.put("sign", sign);
			/*
			 * HttpClient client = HttpClientBuilder.create().build(); HttpPost
			 * post = new HttpPost(url);
			 * 
			 * List<NameValuePair> list = new ArrayList<NameValuePair>();
			 * for(Map.Entry<String, String> entry : params.entrySet()){
			 * if(entry.getValue() != null &&
			 * "".equals(entry.getValue().trim())){ list.add(new
			 * BasicNameValuePair(entry.getKey(), entry.getValue())); } }
			 * list.add(new BasicNameValuePair("sign", sign)); HttpEntity
			 * reqEntity = new UrlEncodedFormEntity(list, "utf-8");
			 * post.setEntity(reqEntity); HttpResponse response
			 * =client.execute(post);
			 * 
			 * String message = EntityUtils.toString(response.getEntity(),
			 * "utf-8");
			 * 
			 * System.out.println(message);
			 */

			// signStr = URLEncoder.encode(signStr,
			// Constants.AliPayConfig.CHARSET_UTF8);

			URL _url = new URL(url);

			HttpURLConnection connection = null;
			if (url.startsWith("https")) {
				HttpsURLConnection conn = (HttpsURLConnection) _url.openConnection();
				conn.setSSLSocketFactory(socketFactory);
				conn.setHostnameVerifier(verifier);
				connection = conn;
			} else {
				connection = (HttpURLConnection) _url.openConnection();
			}

			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept-Charset", "utf-8");
			// connection.setRequestProperty("Accept",
			// "text/xml,text/javascript,text/html");
			// connection.setRequestProperty("User-Agent", "aop-sdk-java");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			OutputStream os = connection.getOutputStream();
			os.write(buildParam(params, Constants.AliPayConfig.CHARSET_UTF8)
					.getBytes(Constants.AliPayConfig.CHARSET_UTF8));
			os.flush();
			os.close();

			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}
			String result = writer.toString();
			is.close();

			System.out.println(result);

			JSONObject obj = JSONObject.parseObject(result);

			String responseName = params.get("method").replace(".", "_") + "_response";
			int start = result.indexOf(responseName) + responseName.length() + 2;
			int end = result.indexOf("\"sign\"") - 1;

			String singStr = result.substring(start, end);
			System.out.println(singStr);

			String response_sign = obj.getString("sign");

			boolean b = AlipaySignature.rsaCheck(singStr, response_sign, Constants.AliPayConfig.ALI_PUBLIC_KEY,
					Constants.AliPayConfig.CHARSET_UTF8, Constants.AliPayConfig.SIGN_TYPE.RSA2.name());

			System.out.println(b);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String buildParam(Map<String, String> map, String charset) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() == null || "".equals(entry.getValue().trim())) {
				continue;
			}
			sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), charset)).append("&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private static SSLContext ctx = null;

	private static HostnameVerifier verifier = null;

	private static SSLSocketFactory socketFactory = null;

	private static class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}

	static {

		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());

			ctx.getClientSessionContext().setSessionTimeout(15);
			ctx.getClientSessionContext().setSessionCacheSize(1000);

			socketFactory = ctx.getSocketFactory();
		} catch (Exception e) {

		}

		verifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return false;// 默认认证不通过，进行证书校验。
			}
		};

	}

	public static void main1(String[] args) {
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo("");
		model.setProductCode("");
		model.setTotalAmount("");
		model.setSubject("");
		model.setBody("");
		model.setGoodsDetail(null);
		model.setPassbackParams("");
		model.setExtendParams(null);
		model.setGoodsType("");
		model.setTimeoutExpress("");
		model.setEnablePayChannels("");
		model.setDisablePayChannels("");
		// model.setauth_token
		model.setQrPayMode("");
		model.setQrcodeWidth(300L);

		String s = new JSONWriter().write(model, true);
		System.out.println(s);

		AlipayTradePagePayRequest re;

	}

}
