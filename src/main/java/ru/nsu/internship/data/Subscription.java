package ru.nsu.internship.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private String message;
    private List<String> urls;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        Collections.sort(urls);
        Collections.sort(that.urls);
        return message.equals(that.message) &&
                urls.equals(that.urls);
    }

    @Override
    public int hashCode() {
        Collections.sort(urls);
        return Objects.hash(message, urls);
    }
}
