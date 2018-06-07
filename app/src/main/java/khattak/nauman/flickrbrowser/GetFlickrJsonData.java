package khattak.nauman.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrJsonData";
    private final OnDataAvailable mCallback;
    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;
//    private boolean onSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        Log.d(TAG, "GetFlickrJsonData: Inside Constructor");
        mBaseURL = baseURL;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallback = callback;
    }

    /*void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: start");

        String destinationUri = createUri(searchCriteria);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);

        Log.d(TAG, "executeOnSameThread: ends");
    }*/

    @Override
    protected void onPostExecute(List<Photo> photos) {
        super.onPostExecute(photos);
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(strings[0]);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return mPhotoList;
    }

    private String createUri(String searchCriteria) {
        Log.d(TAG, "createUri: starts");

        return Uri.parse(mBaseURL).buildUpon().appendQueryParameter("tags", searchCriteria).appendQueryParameter("tagmode", mMatchAll ? "all" : "any").appendQueryParameter("lang", mLanguage).appendQueryParameter("format", "json").appendQueryParameter("nojsoncallback", "1").build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: dwonlaod status ="+status);

        if (status == DownloadStatus.OK){
            mPhotoList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < itemsArray.length() ; i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");
                    //m falls under JSONObject media in fetched JSON data
                    //this photo is just a low resolution preview photo
                    //m	small, 240 on longest side, this image will be used to display in recycler view
                    String photoUrl = jsonPhoto.getJSONObject("media").getString("m");
                    //b	large, 1024 on longest side, this image will be displayed in a separate activity upon clicking image in recycler view
                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    Photo photo = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photo);
//                    Log.d(TAG, "onDownloadComplete: "+ photo.toString());
                }
                mCallback.onDataAvailable(mPhotoList, status);
            } catch (JSONException e){
                Log.d(TAG, "onDownloadComplete: Error processing JSON data "+e.getMessage());
                e.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}
