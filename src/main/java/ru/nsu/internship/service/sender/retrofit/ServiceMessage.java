package ru.nsu.internship.service.sender.retrofit;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ru.nsu.internship.data.Message;

import java.util.concurrent.CompletableFuture;

public interface ServiceMessage {
    @POST("/planetary/apod")
    @Headers("Content-Type: application/json")
    Call<Message> send(@Body Message message);
}
