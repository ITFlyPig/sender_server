package com.wangyuelin.app.message;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.wangyuelin.app.utils.TextUtil;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MessageSend {

	/**
	 * 时间戳接口配置
	 */
	public static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";
	/**
	 * 备用接口
	 * http://api.mysubmail.com/service/timestamp
	 * https://api.submail.cn/service/timestamp
	 * http://api.submail.cn/service/timestamp
	 */
	
	public static final String TYPE_MD5 = "md5";
	public static final String TYPE_SHA1 = "sha1";
   /**
    * API 请求接口配置
    */
	private static final String URL="https://api.mysubmail.com/message/send";
	/**
	 * 备用接口
	 * @param args
	 * http://api.mysubmail.com/message/send
	 * https://api.submail.cn/message/send
	 * http://api.submail.cn/message/send
	 */
	

	/**
	 * 获取时间戳
	 * @return
	 */
	private static String getTimestamp(){
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(TIMESTAMP);
		try{
			HttpResponse response = closeableHttpClient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			String jsonStr = EntityUtils.toString(httpEntity,"UTF-8");
			if(jsonStr != null){
				JSONObject json = JSONObject.fromObject(jsonStr);
				return json.getString("timestamp");
			}
			closeableHttpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

    /**
     * 发送短信
     * @param phoneNumber   发送的手机号
     * @param content       发送的内容，例子：【wyl】你好，你的验证码是：1234。
     * @return
     */
	public static boolean send(String phoneNumber, String content) {
        TreeMap<String, Object> requestData = new TreeMap<String, Object>();
        String appid = "35490";
        String appkey = "4ccbca103bbcbc5ef91da3b178c2b8de";
        String to = phoneNumber;
        String signtype = "md5";
        requestData.put("appid", appid);
        requestData.put("content", content);
        requestData.put("to", to);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        @SuppressWarnings("deprecation")
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8);
        for(Map.Entry<String, Object> entry: requestData.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof String){
                builder.addTextBody(key, String.valueOf(value),contentType);
            }
        }
        if(signtype.equals(TYPE_MD5) || signtype.equals(TYPE_SHA1)){
            String timestamp = getTimestamp();
            requestData.put("timestamp", timestamp);
            requestData.put("sign_type", signtype);
            String signStr = appid + appkey + RequestEncoder.formatRequest(requestData) + appid + appkey;
            System.out.println(signStr);
            builder.addTextBody("timestamp", timestamp);
            builder.addTextBody("sign_type", signtype);
            builder.addTextBody("signature", RequestEncoder.encode(signtype, signStr), contentType);
        }else{
            builder.addTextBody("signature", appkey, contentType);
        }
        /**
         * http post 请求接口
         * 成功返回 status: success,其中 fee 参数为短信费用 ，credits 参数为剩余短信余额
         * 详细的 API 错误日志请访问 SUBMAIL 官网 → 开发文档 → DEBUG → API 错误代码
         */
        HttpPost httpPost = new HttpPost(URL);
        httpPost.addHeader("charset", "UTF-8");
        httpPost.setEntity(builder.build());
        try{
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity != null){
                String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println(jsonStr);
                if (!TextUtil.isEmpty(jsonStr)) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonStr);
                    String statusStr = jsonObject.getString("status");
                    if (!TextUtil.isEmpty(statusStr) && statusStr.equals("success")) {
                        return true;
                    }

                }
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获得短信的内容
     * @param code
     * @return
     */
    public static String getMessage(String code) {
	    return "【wyl】您好，您的验证码是：" + code + "。";

    }

}
