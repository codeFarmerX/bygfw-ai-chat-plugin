package org.bgyfw.plugin.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.jsoup.Connection;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OKHttp tool
 */
public final class OkHttpUtils {

    private final static OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            //.proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10808)))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).build();

    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");



    /**
     * post请求
     * @param url 接口地址
     * @param bodyParams body信息
     * @param headerMap header信息
     * @return 结果
     */
    public static String httpPost(String url,Object bodyParams, Map<String, String> headerMap){
        RequestBody body = RequestBody.create(APPLICATION_JSON, JSONObject.toJSONString(bodyParams));

        Request.Builder requestBuilder = new Request.Builder().url(url).method(Connection.Method.POST.name(), body);
        return getResult(headerMap, requestBuilder);
    }

    private static String getResult(Map<String, String> headerMap, Request.Builder requestBuilder) {
        String result = null;
        if(headerMap != null && !headerMap.isEmpty()){
            headerMap.forEach(requestBuilder::addHeader);
        }
        Request request = requestBuilder.build();
        try {
            Response response = CLIENT.newCall(request).execute();
            if(response != null && response.body() != null){
                byte[] b = response.body().bytes();
                result = new String(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://sandbox.primetrust.com/v2/uploaded-documents";
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("contact-id", "36cf9c1a-a75c-494c-bccd-274b0654c7ed");
        bodyParams.put("description", "test");
        bodyParams.put("label", "test upload");
        bodyParams.put("public", "false");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdXRoX3NlY3JldCI6IjVjOTM0ZDliLTgwNTItNGVmYS05ZjVmLTFjMTFlOGQyMGJjMSIsInVzZXJfZ3JvdXBzIjpbXSwibm93IjoxNjA5NzU0MzI3LCJleHAiOjE2MTAzNTkxMjd9.U0tOq-yfMo4ylgMWEKtXnd7BHsYSXOFurypfGrZ3JFc");

        File file=new File("E:\\testFile\\4.png");

        //String str = sendFromDataPostRequest(url, bodyParams,  headerMap, file,"file", "testFile.png");
        System.out.println("------:"+file.getName());
    }
}
