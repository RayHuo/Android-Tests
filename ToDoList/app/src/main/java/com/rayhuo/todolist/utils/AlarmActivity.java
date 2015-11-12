package com.rayhuo.todolist.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.rayhuo.todolist.R;

public class AlarmActivity extends Activity {
    // 声明MediaPlayer对象
    private MediaPlayer alarmMusic;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载指定音乐，并为之创建MediaPlayer对象
        alarmMusic = MediaPlayer.create(this, R.raw.getup);
        // 设置为循环播放
        alarmMusic.setLooping(true);
        // 播放音乐
        alarmMusic.start();
        // 创建一个对话框
        new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
                .setMessage("闹钟响了，起床啦，懒虫！")
                .setPositiveButton("确定", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 停止音乐播放
                        alarmMusic.stop();
                        // 结束该Activity
                        AlarmActivity.this.finish();
                    }
                }).show();
    }  
}
