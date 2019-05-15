package com.luck.picture.lib.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by lenovo on 2018/12/10.
 */

public class DownLoadManager {


    /**
     * 下载文件
     * @param context
     * @param downloadUrl
     * @param fileName
     */
    public static void downLoad(Context context, String downloadUrl, String folderName, String fileName, OnDownloadListener l){

        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        // 设置通知的标题和描述
        request.setTitle(fileName);
        request.setDescription("文件下载");

        /*
         * 设置允许使用的网络类型, 可选值:
         *     NETWORK_MOBILE:      移动网络
         *     NETWORK_WIFI:        WIFI网络
         *     NETWORK_BLUETOOTH:   蓝牙网络
         * 默认为所有网络都允许
         */
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        // 设置下载文件的保存位置
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+folderName);
        if (!folder.exists()) folder.mkdirs();

        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+folderName+"/"+fileName);

        request.setDestinationUri(Uri.fromFile(saveFile));

        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        // DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // 将下载请求加入下载队列, 返回一个下载ID
        // long downloadId = manager.enqueue(request);

        // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
        // manager.remove(downloadId);

        long id = downloadManager.enqueue(request);

        // 注册广播监听系统的下载完成事件。
        downloadListener(context, id, fileName, l);

    }

    private static void downloadListener(Context context, final long Id, final String fileName, final OnDownloadListener l) {

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (ID == Id) {
                    if (l != null) {
                      //  Toast.makeText(context, "视频" + fileName + " 下载完成!",Toast.LENGTH_SHORT).show();
                        l.onSuccess();
                    }
                }
            }
        };

        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public interface OnDownloadListener{

        void onSuccess();

    }

}
