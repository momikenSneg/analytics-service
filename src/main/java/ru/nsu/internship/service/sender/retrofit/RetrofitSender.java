package ru.nsu.internship.service.sender.retrofit;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.sender.MessageSender;

@Component("retrofitSender")
public class RetrofitSender implements MessageSender {
    @Override
    public void send(Message message, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //TODO нужно ли закрывать соединение?
        ServiceMessage serviceMessage = retrofit.create(ServiceMessage.class);
        serviceMessage.send(message).enqueue(new Callback<>() {
            //TODO нормально обработать результат (лог мб подкрутить)
            @Override
            public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {
                if (response.code() == 200){
                    System.out.println("Все океюшки");
                } else {
                    System.out.println("Плохой код");
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                System.out.println("Все плохо");
            }
        });
    }
}
