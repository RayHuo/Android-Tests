package com.rayhuo.todolist.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayhuo.todolist.R;

/**
 * Created by rayhuo on 13/11/2015.
 */
public class MoodItemViewActivity extends Activity{

    private Bundle args = null;
    private ImageView moodViewImage = null;
    private TextView moodViewContent = null;
    private TextView moodViewDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mood_item_view_layout);

        initView();
        prepareListener();
    }


    private void initView() {
        args = getIntent().getExtras();

        moodViewImage = (ImageView) findViewById(R.id.mood_item_view_image);
        moodViewContent = (TextView) findViewById(R.id.mood_item_view_content);
        moodViewDate = (TextView) findViewById(R.id.mood_item_view_date);

        Bitmap bitmap = getCompressBitmap(args.getString("mood_image"));
        moodViewImage.setImageBitmap(bitmap);

        moodViewContent.setText(args.getString("mood_content"));
        moodViewDate.setText(args.getString("mood_date"));

    }

    private void prepareListener() {
        
    }


    private Bitmap getCompressBitmap(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);

        DisplayMetrics disMet = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disMet);
        int width = disMet.widthPixels;     // ÆÁÄ»¿í¶È£¨ÏñËØ£©
        int height = disMet.heightPixels;   // ÆÁÄ»¸ß¶È£¨ÏñËØ£©

        int heightDiv = opts.outHeight / height;
        int widthDiv = opts.outWidth / width;

        opts.inSampleSize = heightDiv > widthDiv ? heightDiv : widthDiv;

        if(opts.inSampleSize > 1) {
            opts.outHeight = height;
            opts.outWidth = width;
        }

        opts.inPurgeable = true;
        opts.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }
}
