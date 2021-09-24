package com.cn.lgf.module.main;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.cn.lgf.common.aspect.permission.annotations.NeedPermission;
import com.cn.lgf.common.crash.CrashDataBase;
import com.cn.lgf.common.crash.CrashHandler;
import com.cn.lgf.common.crash.entity.CrashEntity;
import com.cn.lgf.common.databinding.DataBindingActivity;
import com.cn.lgf.common.databinding.DataBindingConfig;
import com.cn.lgf.common.http.HttpLibrary;
import com.cn.lgf.module.main.http.AdHttpModel;
import com.cn.lgf.module.main.http.VideoDownloadModel;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.module.main
 * @ClassName: MainActivity
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/24 4:08 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/24 4:08 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@NeedPermission(permissions = {Manifest.permission.CALL_PHONE})
public class MainActivity extends DataBindingActivity {
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void initViewModel() {
        mainActivityViewModel = getActivityViewModel(MainActivityViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mainActivityViewModel);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpLibrary.getInstance().init(this,null);
        AdHttpModel adHttpModel=new AdHttpModel();
        adHttpModel.startRequest();
        VideoDownloadModel videoDownloadModel=new VideoDownloadModel(this);
        videoDownloadModel.startDownload();
        mainActivityViewModel.name.set("1111");
        CrashEntity entity=new CrashEntity();
        entity.crashInfo="crashInfo with 123";
        entity.deviceInfo="deviceInfo with 111111";
        entity.createTime= new Date();

        CrashEntity entity1=new CrashEntity();
        entity1.crashInfo="crashInfo with 数据2";
        entity1.deviceInfo="deviceInfo with 数据2";
        entity1.createTime= new Date();

        CrashDataBase.getInstance(getApplication()).crashDao().insertCrash(entity,entity1);
        CrashHandler.getInstance().init(getApplication());
        final TextView textView = findViewById(R.id.tv_title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Button button = new Button(MainActivity.this);
//                button = null;
//                button.setText("1111");
               List<CrashEntity> crashEntitys= CrashDataBase.getInstance(getApplication()).crashDao().queryAllCrashEntities();
               if(crashEntitys!=null){

               }
            }
        });
//        VideoView videoView=findViewById(R.id.video);
//        videoView.setVideoPath(String.valueOf(Uri.fromFile(new File(this.getCacheDir().getPath() + File.separator + "本森超跑.mp4"))));
//        Log.i("VideoDownloadModel", "path=" + this.getCacheDir().getPath() + File.separator + "本森超跑.mp4");
//        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
