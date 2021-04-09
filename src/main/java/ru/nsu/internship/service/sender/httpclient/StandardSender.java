package ru.nsu.internship.service.sender.httpclient;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.sender.MessageSender;

import java.io.IOException;

@Component("standardSender")
public class StandardSender implements MessageSender {
    @Override
    public void send(Message message, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(new Gson().toJson(message));
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        //TODO логировать
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == 200){
                System.out.println("Все крута");
            } else {
                System.out.println("Код плохой");
            }
        } catch (IOException e){
            System.out.println("Все плоха");
        }
    }
}
