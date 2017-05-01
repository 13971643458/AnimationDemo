package com.example.liuwenxiang.animationdemo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView star1 = null;
    private TextView tvShow = null;
    private CheckBox checkBox = null;
    private Button btUpdate = null;

    private AnimationDrawable drawable = null;

    private int i = 25;
    private int TIME = 1000;

    private String url = "https://timgsa.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        star1 = (ImageView) findViewById(R.id.star_1);
        tvShow = (TextView) findViewById(R.id.tvShow);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btUpdate = (Button) findViewById(R.id.bt_update);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    url = "https://timgsa.baidu.co";
                }else{
                    i = 25;
                    url = "https://timgsa.baidu.com";
                    btUpdate.setClickable(true);
                }
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mystart(star1);
                handler.postDelayed(runnable, TIME); //每隔1s执行
                GetDate();
            }
        });

    }

    public void mystart(View view) {
        // TODO Auto-generated method stub
        //播放逐帧动画
        drawable = (AnimationDrawable) view.getBackground();
        drawable.start();
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                i--;
                tvShow.setText("开始计时:" + Integer.toString(i));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                btUpdate.setClickable(false);
                if (i <= 0) {
                    Toast.makeText(MainActivity.this, "GetDate停止计时了哈，不要在走了...", Toast.LENGTH_SHORT).show();
                    drawable.stop();
                    handler.removeCallbacks(runnable);
                    return;
                }

                GetDate();
            } else if (msg.arg1 == 2) {
                btUpdate.setClickable(true);
                Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                drawable.stop();
                handler.removeCallbacks(runnable);

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);

            }
        }
    };

    private void GetDate() {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.arg1 = 1;
                message.obj = e.getMessage();
                mHandler.sendMessage(message);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String body = response.body().string();
                Message message = new Message();
                message.arg1 = 2;
                message.obj = body;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawable.stop();
        handler.removeCallbacks(runnable);
        i = 0;
    }
}

