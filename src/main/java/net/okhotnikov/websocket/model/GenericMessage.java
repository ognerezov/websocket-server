package net.okhotnikov.websocket.model;

public  class GenericMessage<T> extends  BasicMessage{
    public T data;

    public GenericMessage() {
    }

    public GenericMessage(MessageType type, T data) {
        this.type = type;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "type=" + type +
                ", data=" + data +
                "} " + super.toString();
    }
}
