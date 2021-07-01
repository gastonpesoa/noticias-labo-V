package com.example.noticias;

public enum EMessages {
    NOTICIA(0), IMAGEN(1), URLRSS(2);

    private final int value;
    private EMessages(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
