/**
 * File Name: HttpTools.java
 * Date: 2019-12-16 18:28:06
 */
package me.belucky.easytool.http;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;

/**
 * Description: 
 * @author shenzulun
 * @date 2019-12-16
 * @version 1.0
 */
public class HttpTools {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		HttpTools.post();
	}
	
	public static void post() throws UnsupportedEncodingException {
		OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
										.connectTimeout(30000, TimeUnit.MILLISECONDS)
										.readTimeout(30000, TimeUnit.MILLISECONDS)
										.build();
		String transData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ebank><reqHead><custNo>2000002557</custNo><userId>000000</userId><tranCode>100007</tranCode><serialNo>20191216174238040010</serialNo><reqDate>20191216</reqDate><reqTime>174238001</reqTime></reqHead><reqBody><iBatchOrderList><row><orderNo>201912161742380400100</orderNo><payAccount>201000190354763</payAccount><payAccountName>临平南大街72号</payAccountName><recAccount>101014862981321</recAccount><recAccountName>亚历山大</recAccountName><recBankType>0</recBankType><payAmount>1.0</payAmount><payUse>本行转账测试</payUse><payRem></payRem><tranMate>0</tranMate></row><row><orderNo>201912161742380400101</orderNo><payAccount>201000190354763</payAccount><payAccountName>临平南大街72号</payAccountName><recAccount>6222021207004589545</recAccount><recAccountName>工行测试</recAccountName><recAccountOpenBank>中国工商银行有限公司天津西苑支行</recAccountOpenBank><recBankType>1</recBankType><payAmount>1.01</payAmount><payUse>跨行转账测试</payUse><payRem></payRem><unionBankNo>102110087163</unionBankNo><tranMate>0</tranMate></row></iBatchOrderList></reqBody></ebank>";
		String url = "http://127.0.0.1:7071/bce/yq.do";
		RequestBody requestBody = new FormBody.Builder()
		        .add("tranCode", "100007")
		        .addEncoded("transData", URLEncoder.encode(transData, "UTF-8"))
		        .addEncoded("url", URLEncoder.encode(url, "UTF-8"))
		        .build();
		Request request = new Request.Builder()
		        .url("http://127.0.0.1:9999")
		        .post(requestBody)
		        .build();
		
		System.out.println("start");
		long start = System.currentTimeMillis();
		Call call = okHttpClient.newCall(request);
		Response response = null;
		try {
			response = call.execute();
	    	System.out.println(response.protocol() + " " +response.code() + " " + response.message());
	    	Headers headers = response.headers();
	    	for (int i = 0; i < headers.size(); i++) {
	    		System.out.println(headers.name(i) + ":" + headers.value(i));
	    	}
	    	byte[] arr = response.body().bytes();
	    	System.out.println(new String(arr, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("M: " + e.getMessage());
			if(response != null) {
				System.out.println(response.protocol() + " " +response.code() + " " + response.message());
			}
		} finally {
			if(response != null) {
				response.close();
			}
		}
		System.out.println("end");
		System.out.println(System.currentTimeMillis() - start);
	}
}
