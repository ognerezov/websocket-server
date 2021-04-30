package net.okhotnikov.websocket.model;

public  class GenericMessage<T> {
    public MessageType type;
    public T data;

    public GenericMessage() {
    }

    public GenericMessage(MessageType type, T data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
