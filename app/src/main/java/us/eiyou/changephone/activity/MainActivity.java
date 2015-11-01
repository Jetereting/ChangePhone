package us.eiyou.changephone.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import us.eiyou.changephone.R;
import us.eiyou.changephone.utils.ChangeUtils;

public class MainActivity extends AppCompatActivity {

    Button b_hosts, b_brand, b_ad;
    WebView wv_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        b_hosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "正在翻 ，正在翻....", Toast.LENGTH_LONG).show();
                wv_loading.setVisibility(View.VISIBLE);
                WriteHostsAsyncTask writeHostsAsyncTask = new WriteHostsAsyncTask(wv_loading,b_hosts);
                writeHostsAsyncTask.execute();
            }
        });
        b_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BrandActivity.class));
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
        b_hosts = (Button) findViewById(R.id.b_hosts);
        b_brand = (Button) findViewById(R.id.b_brand);
        b_ad = (Button) findViewById(R.id.b_ad);
        wv_loading = (WebView) findViewById(R.id.wv_loading);
        wv_loading.loadUrl("file:///android_asset/loading.html");
        ChangeUtils.mkdir();
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}

class WriteHostsAsyncTask extends AsyncTask<Integer, Integer, String> {
    WebView webView;
    Button button;
    WriteHostsAsyncTask(WebView webView,Button button) {
        this.webView = webView;
        this.button=button;
    }

    @Override
    protected String doInBackground(Integer... params) {
        ChangeUtils.writeHosts();
        int a=2;
        while (a<8){
            try {
                Thread.sleep(222);
            } catch (InterruptedException e) {
                e.printStackTrace();
        }
            publishProgress(a);
            a++;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(values[0]>=5){
            webView.loadUrl("file:///android_asset/load.html");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        webView.setVisibility(View.GONE);
    }
}