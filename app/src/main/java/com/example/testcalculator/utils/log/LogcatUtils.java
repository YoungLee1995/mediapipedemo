package com.example.testcalculator.utils.log;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * log
 */
public class LogcatUtils {
    private static LogcatUtils logcatUtils;
    private String filePath;
    //logcat文件名称
    String fileName = "logcatData.log";
    //单例模式
    public static synchronized LogcatUtils newInstance() {
        if(logcatUtils == null)
            logcatUtils = new LogcatUtils();
        return logcatUtils;
    }

    public void init(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath() + "/";
        }else{
            filePath = Environment.getExternalStorageDirectory().getPath() + "/";
        }
        File file = new File(filePath + fileName);
        if (file.exists()) {
            //如果当前初始化的时候存在该文件  那就删除掉之后重新创建
            Boolean isDeleteSuccess = file.delete();
            Log.v("LogcatUtils","文件是否删除成功:"+isDeleteSuccess);
        }
        Log.v("LogcatUtils",filePath);
    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent) {
        //生成文件夹之后，再生成文件，不然会出
        //makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\n";
        try {
            ///storage/emulated/0/logInfo/
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("LogcatUtils", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("LogcatUtils", "Error on write File:" + e);
        }
    }
    //生成文件
    /*public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    //生成文件夹
    public void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }*/
}
