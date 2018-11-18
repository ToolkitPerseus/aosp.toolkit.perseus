package com.earth.OsToolkit.Working.BaseClass;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@SuppressWarnings("all")
public class Copy {
    public static int copyAssets2Cache(Context context, String fileName){
        File file = new File(context.getCacheDir().getAbsolutePath()
                + File.separator
                + fileName);
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1)
                    fos.write(buffer,0,len);
                fos.flush();
                inputStream.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int setScriptPermission(Context context, String fileName) {
        String path = context.getCacheDir().getAbsolutePath() + File.separator + fileName;
        File file = new File(path);

        if (file.exists()) {
            file.setReadable(true);
            file.setWritable(true);
            file.setExecutable(true);
            if (file.canExecute())
                return 1;
            else
                return 0;
        } else {
            return -1;
        }

        /*
        Process process;
        try {
            if (new File(path).exists()) {
                 process = Runtime.getRuntime().exec(new String[]{"su -c ","chmod 777 " + path});
                 if (process.waitFor() == 0) {
                     return 1;
                 } else {
                     return 0;
                 }
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        */
    }

}