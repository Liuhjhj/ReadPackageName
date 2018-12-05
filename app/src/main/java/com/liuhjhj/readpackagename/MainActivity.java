package com.liuhjhj.readpackagename;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PackageManager mPackageManager;
    private ListView mListView;
    public static ArrayList<App> application;

    public void init() {
        mPackageManager = this.getPackageManager();
        mListView = findViewById(R.id.app_main);
        ImageButton imageButton = findViewById(R.id.search_button);
        imageButton.setOnClickListener(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        get();
    }

    public void get() {
        application = new ArrayList<>();
        List<PackageInfo> appList = mPackageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : appList){
            App apps = new App();
            apps.setAppPackageName(packageInfo.packageName);
            apps.setAppName((String) mPackageManager.getApplicationLabel(packageInfo.applicationInfo));
            apps.setAppIcon(mPackageManager.getApplicationIcon(packageInfo.applicationInfo));
            application.add(apps);
        }
        AppAdapter mAppAdapter = new AppAdapter(
                this, application);
        mListView.setAdapter(mAppAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button :
                Intent intent = new Intent(this,Search.class);
                startActivity(intent);
                break;

                default:
                    break;
        }
    }
}
