package khattak.nauman.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mPhotoList = photoList;
        mContext = context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by layout manager whenever new view is requested
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    //this method is called by the recycler view when it wants new data to be stored in a viewHolder so that it can display it
    // or more precisely
    //called by the layout manager when it wants new data in an existing row
    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotoList.get(position);
        Log.d(TAG, "onBindViewHolder: "+photoItem.getTitle()+" ---> "+position);
        Picasso.get().load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageThumbnail);

        holder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.size() : 0);
    }

    //when search query changes this method will be called to load new data
    void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageThumbnail;
        TextView title;
        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: inside Construcotr");
            this.imageThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
