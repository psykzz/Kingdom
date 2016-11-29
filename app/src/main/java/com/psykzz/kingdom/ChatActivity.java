package com.psykzz.kingdom;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.psykzz.kingdom.Helpers.ChatArrayAdapter;
import com.psykzz.kingdom.Helpers.ChatMessage;

public class ChatActivity extends Activity {

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String deviceId = intent.getStringExtra("deviceId");

        // TODO: connect to the device.

        buttonSend = (Button) findViewById(R.id.btn_send);

        listView = (ListView) findViewById(R.id.message_view);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_right);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.message_content);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private void recieveChatMessage(ChatMessage msg) {
        // TODO: Register a listener here or something
        chatArrayAdapter.add(msg);
    }

    private boolean sendChatMessage() {
        if (chatText.getText().toString() == "") {
            return false;
        }
        chatArrayAdapter.add(new ChatMessage(chatText.getText().toString(), true));
        chatText.setText("");
        // TODO: actually send the message across the network
        return true;
    }
}
