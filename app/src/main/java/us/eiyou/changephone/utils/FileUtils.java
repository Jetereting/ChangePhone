package us.eiyou.changephone.utils;

import java.io.*;

/**
 * Created by Au on 2015/8/31.
 */
public class FileUtils {

    public static void fileWirteWithAdd(File file, String youWantToAdd) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        String oldContent=fileRead(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(oldContent + youWantToAdd);
        bufferedWriter.close();
    }

    public static void fileWriteWithOverwrite(File file, String youWantToOverwrite) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(youWantToOverwrite);
        bufferedWriter.close();
    }

    public static String fileRead(File file) throws IOException {
        String result = "",temp;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        while ((temp=bufferedReader.readLine()) != null) {
            result += (temp+"\n");
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return result;
    }
    public static void fileReplace(File file,String oldString,String newString) throws IOException {
        FileUtils.fileWriteWithOverwrite(file,FileUtils.fileRead(file).replace(oldString,newString));
    }
    public static long getFileSizes(File f) throws Exception{//取得文件大小
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }
}
