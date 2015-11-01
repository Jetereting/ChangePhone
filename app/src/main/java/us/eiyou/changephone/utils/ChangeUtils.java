package us.eiyou.changephone.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Au on 2015/10/19.
 */
public class ChangeUtils {
    public static void mkdir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/eiyou.us/system");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void writeHosts() {
        final File file = new File(Contants.SD_HOSTS);
        try {
            FileUtils.fileWriteWithOverwrite(file, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            HtmlParse.hosts(file);
            SystemUtils.root();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
