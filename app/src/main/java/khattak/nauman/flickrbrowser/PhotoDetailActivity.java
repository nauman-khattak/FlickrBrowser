package khattak.nauman.flickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
        activateToolbar(true);
        //getIntent() retrieves the intent that started this activity
        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null){
            TextView photoTitle = (TextView) findViewById(R.id.photo_title);

            Resources resources = getResources();

            //            photoTitle.setText("Title: "+photo.getTitle());
            photoTitle.setText(resources.getString(R.string.photo_title_text, photo.getTitle()));

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            //            photoTags.setText("Tags: "+photo.getTags());
            photoTags.setText(resources.getString(R.string.photo_tags_text, photo.getTags()));


            TextView author = (TextView) findViewById(R.id.photo_author);
            author.setText(photo.getAuthor());

            ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
            Picasso.get().load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }
    }

}
