package ru.argds.app.test;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
    private int priority;
    private long order;
    private String text;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return priority == message.priority &&
                order == message.order &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority, order, text);
    }

    @Override
    public String toString() {
        return "Message{" +
                "priority=" + priority +
                ", order=" + order +
                ", text='" + text + '\'' +
                '}';
    }
}
