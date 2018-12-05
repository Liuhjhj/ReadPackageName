package com.liuhjhj.readpackagename;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.logging.Filter;

import static com.liuhjhj.readpackagename.R.drawable.ic_back;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private EditText mEditText;
    private AppAdapter appAdapter;
    private ImageButton mImageButton;

    public void init(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        appAdapter = new AppAdapter(this,MainActivity.application);
        mImageButton = findViewById(R.id.back);
        mImageButton.setImageResource(R.drawable.ic_back);
        mImageButton.setOnClickListener(this);
        mListView = findViewById(R.id.app_search);
        mListView.setAdapter(appAdapter);
        mListView.setTextFilterEnabled(true);
        mEditText = findViewById(R.id.search_bar);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                appAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

                default:
                    break;
        }
    }
}
