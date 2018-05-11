package com.xiaohei.auser.wenliapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import com.xiaohei.auser.wenliapp.entity.Version;

/**
 * Created by Auser on 2018/4/10.
 */

public class UpdateDialog {

    public static void showUpDate(Activity activity , Version version, DialogInterface.OnClickListener listener_ok) {

        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("更新提示");
        builder.setMessage("新版本："+version.getVersion()+"\n"+
                           "更新内容："+version.getDescript());
        builder.setPositiveButton("确定", listener_ok);
        builder.setCancelable(false);
        builder.create().show();
    }

    public static void showLoad(Activity activity,View v){
        Dialog dialog=new Dialog(activity);
        dialog.setContentView(v);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN/2,
                WindowManager.LayoutParams.FLAG_FULLSCREEN/3);
        dialog.setCancelable(false);
        dialog.show();
    }
}
