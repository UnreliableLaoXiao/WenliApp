package com.example.updateapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.updateapp.dialog.UpdateDialog;
import com.example.updateapp.entity.Version;
import com.example.updateapp.minterface.EndInterface;
import com.example.updateapp.minterface.NetInterface;
import com.example.updateapp.net.XhOkHttp;
import com.example.updateapp.util.FileUtil;
import com.example.updateapp.util.VersionUtil;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by machenike on 2018/10/16.
 * 发送请求
 */

public class UpdateApp {

    private    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    endInterface.Failed(0);
                    break;
                case 1:
                    FileUtil.install(activity);
                    break;
                case 2:
                    showDownload();
                    break;
                case 3:
                {
                    show.setText("正在下载...");
                    progressBar.setProgress(mprogress);
                }
                break;
                case 5:{
                    if (dialog_showresut != null)
                    dialog_showresut.dismiss();
                    endInterface.Failed(5);
                }
            }
        }
    };

    private  EndInterface endInterface;
    private   Activity activity;
    private   Version version;
    private   ProgressBar progressBar;
    private   TextView show;
    private   View v;
    private   int mprogress;
    private Dialog dialog_showresut;


    public UpdateApp(Activity activity,EndInterface endInterface) {
        this.endInterface = endInterface;
        this.activity = activity;
    }

    long enterTime;

    public  void initUpdateApp( String url) {
        FileUtil.delDownLaodFile(activity);
        LayoutInflater layout= LayoutInflater.from(activity);
        v = layout.inflate(R.layout.download_progress_view, null);
        progressBar= v.findViewById(R.id.progress);
        progressBar.setMax(100);
        show= v.findViewById(R.id.showprogress);
        if(FileUtil.verifyStoragePermissions(activity)){
            useOkHttpGetVersion(url);
        }
    }

    private   void useOkHttpGetVersion(@NonNull String url){
        enterTime = System.currentTimeMillis();
        XhOkHttp.GetVersion(url, activity, new NetInterface() {
            @Override
            public void complied(Object o) {
                version = (Version) o;
                if (!version.getVersion().equals(VersionUtil.getVerName(activity))){
                    handler.sendEmptyMessage(2);
                    Log.d("version", "complied: "+version.getVersion().equals(VersionUtil.getVerName(activity)));
                }
                else
                {
                    handler.sendEmptyMessageDelayed(5,2000);
                }
            }
            @Override
            public void progress(int mprogress) {

            }

            @Override
            public void fail() {
                    handler.sendEmptyMessageDelayed(5,2000);
            }
        });
    }

    private   void showDownload() {
        UpdateDialog.showUpDate(activity,version, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File file = new File(FileUtil.getFilePath(activity),"download.apk");
                dialog.dismiss();
                dialog_showresut = UpdateDialog.showLoad(activity,v);
                useOkhttpDownFile(version.getUrl());

            }
        });
    }

    private void useOkhttpDownFile(@NonNull String url){
        XhOkHttp.DownFile(url,activity, new NetInterface() {
            @Override
            public void complied(Object o) {
                show.setText("下载完成，等待安装");
                progressBar.setProgress(100);
                handler.sendEmptyMessage(1);
            }
            @Override
            public void progress(int progress) {
                mprogress = progress;
                handler.sendEmptyMessage(3);
            }
            @Override
            public void fail() {
                show.setText("下载失败,请联系管理员检查服务器状态！！！");
                handler.sendEmptyMessageDelayed(0,2000);
            }
        });
    }
}
