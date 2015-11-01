package us.eiyou.changephone.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.List;

import us.eiyou.changephone.R;
import us.eiyou.changephone.utils.Contants;

public class AdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }


    public void b_buy(View view) {
        choiceBrowserToVisitUrl(Contants.adUrl);
    }

    public void b_show(View view) {
        choiceBrowserToVisitUrl(Contants.adVideoUrl);
    }

    public void ib_back(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void choiceBrowserToVisitUrl(String url) {
        boolean existUC = false, existOpera = false, existQQ = false, existDolphin = false, existSkyfire = false, existSteel = false, existGoogle = false;
        String ucPath = "", operaPath = "", qqPath = "", dolphinPath = "", skyfirePath = "", steelPath = "", googlePath = "";
        PackageManager packageMgr = getPackageManager();
        List<PackageInfo> list = packageMgr.getInstalledPackages(0);
        for (int i = 0; i < list.size(); i++) {
            PackageInfo info = list.get(i);
            String temp = info.packageName;
            if (temp.equals("com.uc.browser")) {
                // 存在UC
                ucPath = temp;
                existUC = true;
            } else if (temp.equals("com.tencent.mtt")) {
                // 存在QQ
                qqPath = temp;
                existQQ = true;
            } else if (temp.equals("com.opera.mini.android")) {
                // 存在Opera
                operaPath = temp;
                existOpera = true;
            } else if (temp.equals("mobi.mgeek.TunnyBrowser")) {
                dolphinPath = temp;
                existDolphin = true;
            } else if (temp.equals("com.skyfire.browser")) {
                skyfirePath = temp;
                existSkyfire = true;
            } else if (temp.equals("com.kolbysoft.steel")) {
                steelPath = temp;
                existSteel = true;
            } else if (temp.equals("com.android.browser")) {
                // 存在GoogleBroser
                googlePath = temp;
                existGoogle = true;
            }
        }
        if (existUC) {
            gotoUrl(ucPath, url, packageMgr);
        } else if (existOpera) {
            gotoUrl(operaPath, url, packageMgr);
        } else if (existQQ) {
            gotoUrl(qqPath, url, packageMgr);
        } else if (existDolphin) {
            gotoUrl(dolphinPath, url, packageMgr);
        } else if (existSkyfire) {
            gotoUrl(skyfirePath, url, packageMgr);
        } else if (existSteel) {
            gotoUrl(steelPath, url, packageMgr);
        } else if (existGoogle) {
            gotoUrl(googlePath, url, packageMgr);
        } else {
            doDefault();
        }
    }

    private void gotoUrl(String packageName, String url,
                         PackageManager packageMgr) {
        try {
            Intent intent;
            intent = packageMgr.getLaunchIntentForPackage(packageName);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // 在1.5及以前版本会要求catch(android.content.pm.PackageManager.NameNotFoundException)异常，该异常在1.5以后版本已取消。
            e.printStackTrace();
        }
    }

    private void doDefault() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Contants.adUrl));
        startActivity(intent);
    }
}
