package com.example.wheatherbot.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class WheatherService {

    @Value("${wheather.key}")
    private String key;
    @Value("${wheather.url}")
    private String url;

    public SendMessage handleUpdate(Update update) {
        SendMessage sendMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            sendMessage = handleInputMessage(message);
        }

        return sendMessage;
    }

    public SendMessage handleInputMessage(Message message) {
        long chatId = message.getFrom().getId();
        String userCity = message.getText();

        String urlContent = getContent(userCity);
        String replyMessage;

        if (urlContent.length() == 0) {
            replyMessage = userCity + " not found";
        } else {
            JSONObject obj = new JSONObject(urlContent);
            replyMessage = String.format(
                    "Temperature: %.2f\nFeels like: %.2f\nPressure: %.2f\nHumidity: %.2f",
                    obj.getJSONObject("main").getDouble("temp"),
                    obj.getJSONObject("main").getDouble("feels_like"),
                    obj.getJSONObject("main").getDouble("temp_max"),
                    obj.getJSONObject("main").getDouble("humidity")
            );
        }

        return new SendMessage(chatId, replyMessage);
    }

    public String getContent(String userCity) {
        String urlAddress = url + userCity + "&appid=" + key + "&units=metric";
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            bufferedReader.close();
        } catch(Exception e) {
            System.out.println(userCity + " not found");
        }

        return content.toString();
    }
}
