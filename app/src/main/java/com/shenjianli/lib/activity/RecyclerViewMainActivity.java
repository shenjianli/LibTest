package com.shenjianli.lib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shenjianli.lib.R;
import com.shenjianli.lib.engine.recyclerview.chat.ChatActivity;
import com.shenjianli.lib.engine.recyclerview.multi.MultiActivity;
import com.shenjianli.lib.engine.recyclerview.single.SingleActivity;


public class RecyclerViewMainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SingleActivity.class);
        switch (view.getId()) {
            case R.id.single_adapter:
                intent.putExtra("type", "adapter");
                startActivity(intent);
                break;
            case R.id.single_touch:
                intent.putExtra("type", "touch");
                startActivity(intent);
                break;
            case R.id.single_support:
                intent.putExtra("type", "support");
                startActivity(intent);
                break;
            case R.id.chat_btn:
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case R.id.multi_btn:
                startActivity(new Intent(this, MultiActivity.class));
                break;
        }
    }
}
