package us.eiyou.changephone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import us.eiyou.changephone.R;
import us.eiyou.changephone.utils.Contants;
import us.eiyou.changephone.utils.FileUtils;

public class BrandActivity extends AppCompatActivity {
    EditText et_manufacturer,et_model;
    Button b_sure,b_ad;
    ImageButton ib_back;
    WebView wv_load_brand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        init();
        b_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKB();
                wv_load_brand.setVisibility(View.VISIBLE);
                String manufacturer = et_manufacturer.getText().toString();
                String model = et_model.getText().toString();
                File SD_buildFile = new File(Contants.SD_build);
                File IN_buildFile = new File(Contants.IN_build);
                File SD_temp_buildFile = new File(Contants.SD_temp_build);
                try {
                    if (!SD_buildFile.exists() || FileUtils.getFileSizes(SD_buildFile) <= 1024) {
                        FileUtils.fileWriteWithOverwrite(SD_buildFile, FileUtils.fileRead(IN_buildFile));
                    } else {
                        FileUtils.fileWriteWithOverwrite(SD_temp_buildFile, FileUtils.fileRead(SD_buildFile)
                                .replace("ro.product.manufacturer=", "ro.product.manufacturer=" + manufacturer + "\n#")
                                .replace("ro.product.model=", "ro.product.model=" + model + "\n#"));
                    }
                    catItoS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WaitAsyncTask waitAsyncTask = new WaitAsyncTask(wv_load_brand);
                waitAsyncTask.execute();
                Toast.makeText(getApplicationContext(), "修改完成！", Toast.LENGTH_LONG).show();
            }
        });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        b_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdActivity.class));
            }
        });
    }

    private void init() {
        et_manufacturer=(EditText)findViewById(R.id.et_manufacturer);
        et_model=(EditText)findViewById(R.id.et_model);
        b_sure = (Button) findViewById(R.id.b_sure);
        ib_back=(ImageButton)findViewById(R.id.ib_back);
        wv_load_brand=(WebView)findViewById(R.id.wv_load_brand);
        wv_load_brand.loadUrl("file:///android_asset/load_brand.html");
        wv_load_brand.setVisibility(View.GONE);
        ib_back.getBackground().setAlpha(121);
        b_ad=(Button)findViewById(R.id.b_ad);
    }
    private void catItoS() throws IOException, InterruptedException {
        Process process=Runtime.getRuntime().exec("su");
        DataOutputStream dataOutputStream=new DataOutputStream(process.getOutputStream());
        dataOutputStream.writeBytes("mount -o remount rw /system;\n");
        if((new File(Contants.SD_temp_build).exists())){
            dataOutputStream.writeBytes("cat " + Contants.SD_temp_build + ">" + Contants.IN_build + ";\n");
        }else {
            Toast.makeText(getApplicationContext(),"再试一次！",Toast.LENGTH_LONG).show();
        }
        dataOutputStream.writeBytes("exit\n");
        dataOutputStream.flush();
        process.waitFor();
    }
    private void closeKB(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
class WaitAsyncTask extends AsyncTask<Void,Void,Void>{
    WebView webView;
    WaitAsyncTask(WebView webView){
        this.webView=webView;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(777);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        webView.setVisibility(View.GONE);
    }
}
