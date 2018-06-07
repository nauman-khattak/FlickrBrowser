package khattak.nauman.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {
    private static final String TAG = "MainActivity";
    private static final String baseUrl = "https://api.flickr.com/services/feeds/photos_public.gne";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        //callback: is displayed automatically here because its the first paramether in GetFlickrJsonData constructor
        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, baseUrl, "en-us", true);
        //        getFlickrJsonData.executeOnSameThread("mountain");
        getFlickrJsonData.execute("mountain");
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        Log.d(TAG, "onOptionsItemSelected() returned: returned");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if (status == DownloadStatus.OK) {
            mFlickrRecyclerViewAdapter.loadNewData(data);
        } else {
            //download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status" + status);
        }
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void OnItemClick(View view, int position) {
        Log.d(TAG, "OnItemClick: starts");
        Toast.makeText(MainActivity.this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemLongClick(View view, int position) {
        Log.d(TAG, "OnItemLongClick: starts");
//        Toast.makeText(MainActivity.this, "Long tap at position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
//        Photo class should be serialized to work in putExtra()
        intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }
}
