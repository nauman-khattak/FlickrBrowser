package khattak.nauman.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Muhammad NaumanTariq on 5/14/2018.
 */
enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
};

//AsyncTask<Params, Progress, Result>
class GetRawData extends AsyncTask<String, Void, String>  {
    private static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete{
        void onDownloadComplete(String data, DownloadStatus status);
    }


    public GetRawData(OnDownloadComplete callback) {
        mDownloadStatus = DownloadStatus.IDLE;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d(TAG, "onPostExecute: The parameter is " + s);
        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder result;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response code was " + responseCode);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = new StringBuilder();
            //            String line;
            //            while((line = reader.readLine()) != null){
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: invalid URL" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data" + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception, Needs permission?" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: error closing stream" + e.getMessage());
                }
            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
