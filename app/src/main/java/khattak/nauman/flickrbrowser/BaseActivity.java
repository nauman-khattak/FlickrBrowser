package khattak.nauman.flickrbrowser;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    //in java constant variables are always in uppercase
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activateToolbar(boolean enableHome){
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null){
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null){
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }


}
