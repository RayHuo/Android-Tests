package com.ray.todolist.utils;

import com.ray.todolist.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmActivity extends Activity {
	// ����MediaPlayer����  
    private MediaPlayer alarmMusic;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        // ����ָ�����֣���Ϊ֮����MediaPlayer����  
        alarmMusic = MediaPlayer.create(this, R.raw.getup);  
        // ����Ϊѭ������  
        alarmMusic.setLooping(true);  
        // ��������  
        alarmMusic.start();  
        // ����һ���Ի���  
        new AlertDialog.Builder(AlarmActivity.this).setTitle("����")  
                .setMessage("�������ˣ����������棡")  
                .setPositiveButton("ȷ��", new OnClickListener() {  
  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        // ֹͣ���ֲ���  
                        alarmMusic.stop();  
                        // ������Activity  
                        AlarmActivity.this.finish();  
                    }  
                }).show();  
    }  
}
