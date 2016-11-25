package com.psykzz.turnbasechat;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

public class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
