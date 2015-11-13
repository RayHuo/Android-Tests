package com.rayhuo.todolist.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rayhuo.todolist.R;
import com.rayhuo.todolist.db.DataBaseHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMoodActivity extends Activity {

    private DataBaseHelper mDBHelper = null;
    private SQLiteDatabase mSQLDB = null;
    private final String TABLENAME = "moods";

    private ImageView goback = null;
    private ImageView saveBtn = null;
    private TextView moodDate = null;
    private RatingBar moodRating = null;
    private EditText moodContent = null;
    private ImageView moodImage = null;
    private ImageView addMoodBtn = null;

    private String imagePath = "";      // 记录图片保存的路径。无论何种方式获取图片，都另存一份copy到指定目录下
    private Uri imageUri = null;        // 图片的 Uri

    private static final int TAKE_PHOTO = 1;         // 相机拍照
    private static final int CAPTURE_PHOTO = 2;      // 图库获取照片
    private static final int reqheight = 200;       // 目标高度
    private static final int reqwidth = 400;        // 目标宽度

    private static final String[] getPhotoChoice = new String[] {"拍照", "图库", "取消"};

    private SimpleDateFormat FULL_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_mood);

        initDataBase();
        initView();
        prepareListener();
    }


    private void initDataBase() {
        mDBHelper = new DataBaseHelper(this);
        mSQLDB = mDBHelper.getWritableDatabase();
    }


    private void initView() {
        goback = (ImageView) findViewById(R.id.new_mood_back);
        saveBtn = (ImageView) findViewById(R.id.new_mood_save);
        moodDate = (TextView) findViewById(R.id.new_mood_date);
        moodContent = (EditText) findViewById(R.id.new_mood_content);
        moodImage = (ImageView) findViewById(R.id.new_mood_image);
        addMoodBtn = (ImageView) findViewById(R.id.new_mood_add_image);


        String default_date = DATE_FORMAT.format(new Date());
        moodDate.setText(default_date);
    }


    private void prepareListener() {
        goback.setOnClickListener(goBackListener);
        saveBtn.setOnClickListener(saveListener);
        addMoodBtn.setOnClickListener(chooseImageListener);
    }


    private OnClickListener goBackListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            finish();
        }
    };


    private OnClickListener saveListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            ContentValues cv = new ContentValues();
            cv.put("content", moodContent.getText().toString());
//            cv.put("content", "测试内容，挺好的");
            cv.put("image", imagePath);
            cv.put("mark_time", DATE_FORMAT.format(new Date()));

            mSQLDB.insert(TABLENAME, null, cv);

            finish();   // 返回到上级页面
        }
    };


    private OnClickListener chooseImageListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // 弹窗让用户选择使用图库或相机
            new AlertDialog.Builder(AddMoodActivity.this).setTitle("选择获取方式")
                    .setItems(getPhotoChoice,
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    switch (which) {
                                        case 0:
                                            captureImageFromCamera();   // 拍照
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            captureImageFromLocal();    // 图库
                                            dialog.dismiss();
                                            break;
                                        case 2:
                                            dialog.dismiss();
                                            break;
                                    }

                                }
                            }
                    ).show();
        }
    };


    // 从本地图库获取照片
    private void captureImageFromLocal() {
        // 启动图库
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CAPTURE_PHOTO);
    }

    // 拍照获取照片
    private void captureImageFromCamera() {

        // 以时间戳作为图片名字
        Date date = new Date(System.currentTimeMillis());
        String filename = FULL_TIME_FORMAT.format(date) + ".jpg";

        File photo = null;
        String state = Environment.getExternalStorageState().toString();
        // 有SD卡
        if(state.equals(Environment.MEDIA_MOUNTED)) {
            photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), filename);
        }
        // 无SD卡
        else {
            photo = new File(Environment.getDataDirectory(), filename);
        }

        // 若存在同名文件，覆盖掉。事实上，以时间戳来命名，同名的情况基本不会出现
        try {
            if(photo.exists()) {
                photo.delete();
            }
            photo.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(photo);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }


    /*
    * 获取图片后处理行为
    * @requestCode  获取图片的方式
    * @resultCode   获取图片的结果：成功与否
    * @data         获取得到的图片结果
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    try {
                        // 图片压缩
                        BitmapFactory.Options options = compressOptions(imageUri);
                        if(options == null) {
                            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 压缩完图片后，用于显示
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, options);
                        moodImage.setVisibility(View.VISIBLE);
                        moodImage.setImageBitmap(bitmap);
                        addMoodBtn.setVisibility(View.INVISIBLE);

                        imagePath = imageUri.getPath(); // 保存到数据库中的地址
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;

            case CAPTURE_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    // 压缩图片
                    BitmapFactory.Options options = compressOptions(picturePath);
                    if(options == null) {
                        Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    moodImage.setVisibility(View.VISIBLE);
                    moodImage.setImageBitmap(BitmapFactory.decodeFile(picturePath, options));
                    addMoodBtn.setVisibility(View.INVISIBLE);

                    imagePath = picturePath;    // 保存到数据库中的地址
                }
                break;
            default:
                break;
        }
    }


    /*
    * 图片压缩函数
    * */
    private BitmapFactory.Options compressOptions(Object sources) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;      // 只获取图片的宽高，不生成图片，不占内存

        try {
            // 从图库获取
            if(sources instanceof String) {
                BitmapFactory.decodeFile((String)sources, options);
            }
            if(sources instanceof Uri) {
                BitmapFactory.decodeStream(getContentResolver().openInputStream((Uri)sources), null, options);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        // 根据分辨率对资源进行压缩，减少OOM，这里边的目标高度和宽度不能太小，否则图片会失真。centerCrop本身还会做一层压缩的
        if (options.outWidth >= reqwidth && reqwidth != 0) {
            options.inSampleSize = options.outWidth / reqwidth;
            options.outHeight = (int)((double)options.outHeight * ((double)reqwidth / (double)options.outWidth));
            options.outWidth = reqwidth;
        }
        else {
            options.inSampleSize = 1;
        }

        options.inPurgeable = true;
        options.inJustDecodeBounds = false;

        return options;
    }



    @Override
    // 捕捉返回键动作，然后结束程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 在主页按返回键，直接退出程序。
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mSQLDB.close();
        super.onDestroy();
    }

}
