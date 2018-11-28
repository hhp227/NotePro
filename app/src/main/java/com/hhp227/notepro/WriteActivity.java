package com.hhp227.notepro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {
    EditText editText;
    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editText = (EditText) findViewById(R.id.memoEditText);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        Intent intent = getIntent();
        final int code = intent.getIntExtra("code", 0);
        String memo = intent.getStringExtra("memo");
        switch(code) {
            case MainActivity.REQUEST_CODE_ADD :
                button1.setText("추가");
                button2.setText("닫기");
                break;
            case MainActivity.REQUEST_CODE_SET :
                button1.setText("수정");
                button2.setText("삭제");
                editText.setText(memo);
                break;
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                intent.putExtra("memo", text);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code == MainActivity.REQUEST_CODE_SET) {
                    setResult(RESULT_FIRST_USER);
                }
                finish();
            }
        });
    }
}
