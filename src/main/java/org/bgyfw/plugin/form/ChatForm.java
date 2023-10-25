package org.bgyfw.plugin.form;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.wm.ToolWindow;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatForm {
    private JTextArea question;
    private JTextArea answer;
    private JPanel panel1;
    private JButton submit;
    private JScrollPane questionScroll;
    private JScrollPane answerScroll;

    private ToolWindow toolWindow;
    //private JSONArray completionParamList;

    private final String URL = "http://10.194.94.3:8000";

    private int questionNum = 3;

    private AtomicInteger lock = new AtomicInteger(0);

    public ChatForm(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        //completionParamList = new JSONArray();
        panel1.setLayout(null);
        //panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setBounds(0,0, 500, 800);

        /*question.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == KeyEvent.VK_ENTER){
                    completionsRequest();
                }
            }
        });*/
        submit.addActionListener(e -> {
            String content = question.getText();
            completionsRequest(content);
        });

        questionScroll.setBorder(new LineBorder(Color.black));
        questionScroll.setBounds(10, 10, 300, 100);
        //question.setBackground(new Color(144, 238, 144));
        questionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setViewportView(question);
        panel1.add(questionScroll);

        submit.setBounds(320, 30, 100, 40);
        //submit.setBackground(new Color(255, 228, 181));
        panel1.add(submit);

        answer.setEditable(false);
        answerScroll.setBorder(new LineBorder(Color.black));
        answerScroll.setBounds(10, 120, 420, 500);
        //answer.setBackground(new Color(210, 0, 80, 255));
        answerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setViewportView(answer);
        panel1.add(answerScroll);

    }

    public void completionsRequest(String content) {
        if(StringUtils.isEmpty(content)){
            return;
        }
        boolean isLock = lock.compareAndSet(0, 1);
        if(isLock){
            try {
                modelRequest(content);
            } catch (Exception e) {
                lock.set(0);
                e.printStackTrace();
            }
        }else{
            try {
                String message = new String("正在运行，请稍后".getBytes("gbk"), StandardCharsets.UTF_8);
                String title = new String("提示".getBytes("gbk"), StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        question.setText("");
    }


    public JPanel getPanel1() {
        return panel1;
    }

    public void setQuestionText(String text){
        question.setText(text);
    }

    public ToolWindow getToolWindow() {
        return toolWindow;
    }

    private void modelRequest(String content) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject data = new JSONObject();
        data.put("model", "chatglm2-6b");
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", content);
        JSONArray completionParamList = new JSONArray();
        completionParamList.add(message);
        data.put("messages", completionParamList);
        data.put("temperature", 0.5);
       /* data.put("frequency_penalty", 0);
        data.put("presence_penalty", 0);*/
        data.put("max_tokens", 1000);
        /*data.put("stop", "null");*/
        data.put("stream", true);

        RequestBody body = RequestBody.create(mediaType, data.toString());
        Request request = new Request.Builder()
                .url(URL + "/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        answer.setText("");

        new Thread(() -> {
            String line;
            try {
                while ((line = response.body().source().readUtf8Line()) != null) {
                    if (line.equals("data: [DONE]")) {
                        break;
                    } else if (line.startsWith("data: ")) {
                        line = line.substring(6);
                        JSONObject responseJson = JSONObject.parseObject(line);
                        if (responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").containsKey("content")) {
                            answer.append(responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").getString("content"));
                            answer.setCaretPosition(answer.getText().length());
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                lock.set(0);
            }

        }).start();
    }
}
