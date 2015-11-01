package us.eiyou.changephone.utils;

import java.io.DataOutputStream;

/**
 * Created by Au on 2015/10/19.
 */
public class SystemUtils {
    public static void root() throws Exception {
        Process process=Runtime.getRuntime().exec("su");
        DataOutputStream dataOutputStream=new DataOutputStream(process.getOutputStream());
        dataOutputStream.writeBytes("mount -o remount rw /system;\n");
        dataOutputStream.writeBytes("cat "+Contants.SD_HOSTS+">"+Contants.IN_HOSTS+";\n");
        dataOutputStream.writeBytes("exit\n");
        dataOutputStream.flush();
        process.waitFor();
    }
}
