package khattak.nauman.flickrbrowser;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

public class SearchActivity extends BaseActivity {

    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activateToolbar(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }
}
