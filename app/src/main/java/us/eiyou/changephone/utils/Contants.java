package us.eiyou.changephone.utils;

import android.os.Environment;

/**
 * Created by Au on 2015/10/19.
 */
public class Contants {
    public static final String SD_HOSTS = Environment.getExternalStorageDirectory().getPath()+"/eiyou.us/system/hosts";
    public static final String IN_HOSTS = "/etc/hosts";
    public static final String IN_build = "/system/build.prop";
    public static final String SD_build =  Environment.getExternalStorageDirectory().getPath()+"/eiyou.us/system/build.prop";
    public static final String SD_temp_build =  Environment.getExternalStorageDirectory().getPath()+"/eiyou.us/system/temp_build.prop";
    public static final String adUrl="http://au0.github.io/ad/adUrl.html";
    public static final String adVideoUrl="http://au0.github.io/ad/ad_player.html";
}
