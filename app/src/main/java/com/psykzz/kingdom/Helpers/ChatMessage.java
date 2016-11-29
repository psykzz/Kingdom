package com.psykzz.kingdom.Helpers;

/**
 * Created by PsyKzz on 29/11/2016.
 */
public class ChatMessage {
    public Boolean local;
    public String message;

    public ChatMessage(String message,  boolean local) {
        super();
        this.local = local;
        this.message = message;
    }
}

