package com.psykzz.turnbasechat;

import android.app.Activity;
import android.os.Bundle;

/*
Show the active chat
 */

public class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected String[] loadPreviousChat(String participantId) {
        String[] results = {
                "Message one",
                "message two"
        };
        return results;
    }
}
