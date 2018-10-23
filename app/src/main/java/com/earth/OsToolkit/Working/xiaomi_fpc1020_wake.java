package com.earth.OsToolkit.Working;


import android.content.Context;

import java.io.File;

import static com.earth.OsToolkit.Working.BaseClass.BaseIndex.XIAOMI_FPC1020_WAKEUP;

public class xiaomi_fpc1020_wake {

    /*public static int runOn(Context context){
        int copy = copyAssets2Cache(context,XIAOMI_FPC1020_WAKEUP_ON);
        if (copy != 0) {
            String path = context.getCacheDir() + File.separator + XIAOMI_FPC1020_WAKEUP_ON;
            try {
                Process process = Runtime.getRuntime().exec("chmod a+x " + path);
                process = Runtime.getRuntime().exec(new String[]{"su -c",". " + path});
                return 1;
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }*/

    public static int run(Context context){
        String path = context.getAssets() + XIAOMI_FPC1020_WAKEUP;
        File file = new File(path);
        if (file.exists()) {
            try {
                Process process = Runtime.getRuntime().exec("chmod a+x " + path);
                process = Runtime.getRuntime().exec(new String[]{"su -c", ". " + path});
                return 1;
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }


}
