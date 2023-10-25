package org.bgyfw.plugin.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OKStream {
    public static void main(String[] args) throws IOException {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input question:");
            String str = reader.readLine();
            if ("bye".equals(str)) {
                break;
            }
            OKStream okStream = new OKStream();
            okStream.chatWithGPT(str);
        }
    }

    public static void chatWithGPT(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", "system");
        jsonObject.put("content", "You are an AI assistant that helps people find information.");
        jsonArray.add(jsonObject);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("role", "user");
        jsonObject2.put("content",message);
        jsonArray.add(jsonObject2);
        data.put("messages", jsonArray);
        data.put("model", "chatglm2-6b");
        data.put("temperature", 0.5);
        data.put("top_p", 0.95);
        data.put("frequency_penalty", 0);
        data.put("presence_penalty", 0);
        data.put("max_tokens", 800);
        data.put("stop", "null");
        data.put("stream", true);
        RequestBody body = RequestBody.create(mediaType, data.toString());
        Request request = new Request.Builder()
                .url("http://10.194.94.3:8000/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String line;
        while ((line = response.body().source().readUtf8Line()) != null) {
            if (line.equals("data: [DONE]")) {
                System.out.println("\n[DONE]");
                break;
            } else if (line.startsWith("data: ")) {
                line = line.substring(6);
                JSONObject responseJson = JSONObject.parseObject(line);
                if (responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").containsKey("content")) {
                    System.out.print(responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").getString("content"));
                }
            }
        }
    }
}