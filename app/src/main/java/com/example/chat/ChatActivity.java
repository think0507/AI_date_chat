package com.example.chat;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

public class ChatActivity extends Activity {
    private LinearLayout chatLayout;
    private EditText inputField;
    private Button sendButton;
    private Button changeButton;

    private boolean isSelfBubble = true; // 기본적으로 자신의 말풍선으로 시작

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatLayout = findViewById(R.id.chat_layout);
        inputField = findViewById(R.id.input_field);
        sendButton = findViewById(R.id.send_button);
        changeButton = findViewById(R.id.change_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBubble();
            }
        });
    }

    private void sendMessage() {
        String message = inputField.getText().toString();
        if (!message.isEmpty()) {
            addMessageToChatArea(message, isSelfBubble);
            inputField.setText("");
            // 여기서 실제로 상대방에게 메시지를 전송하거나 처리하는 로직을 추가해야 합니다.
        }
    }

    private void addMessageToChatArea(String message, boolean isSelf) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setBackgroundResource(R.drawable.chat_bubble); // 9-patch 이미지로 배경 설정
        textView.setPadding(20, 10, 20, 10); // 원하는 패딩 값으로 조정
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(10, 10, 10, 10); // 원하는 마진 값으로 조정

        // 말풍선 정렬 설정 (자신의 말풍선인 경우 오른쪽, 상대방의 말풍선인 경우 왼쪽)
        if (isSelf) {
            layoutParams.gravity = Gravity.END; // 오른쪽으로 정렬
        } else {
            layoutParams.gravity = Gravity.START; // 왼쪽으로 정렬
        }

        textView.setLayoutParams(layoutParams);

        chatLayout.addView(textView);
    }

    private void toggleBubble() {
        isSelfBubble = !isSelfBubble;
        String buttonText = isSelfBubble ? "Change to Friend's Bubble" : "Change to My Bubble";
        changeButton.setText(buttonText);
    }
}