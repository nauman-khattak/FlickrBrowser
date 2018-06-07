package khattak.nauman.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener{
    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener{
        void OnItemClick(View view, int positon);
        void OnItemLongClick(View view, int position);
    }
    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: start");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null){
                    Log.d(TAG, "onSingleTapUp: calling listener.OnItemClick");
                    mListener.OnItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
//                return super.onSingleTapUp(e);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: start");
//                super.onLongPress(e);
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null){
                    //listener is MainActivity bcz it implemented the interface...dig deep into this
                    Log.d(TAG, "onLongPress: calling listener.OnLongPress");
                    mListener.OnItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    /*this method will be called whenever any touch event happens on RecyclerView whether its a tap,
    double tap, swipe or whatever*/
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");

//        return super.onInterceptTouchEvent(rv, e);
        /*if we return true the recycler view no longer scrolls through the list because returning
        true means that we've intercepted the touch event and told the system that we've handled every
        single event so nothing else needs to be done.
        If we handle a particular touch event then we can return true so that nothing else does
        but if we don't handle it we should return false so that whatever else is listening can do
        its stuff*/
//        return true;
        if (mGestureDetector != null){
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned "+result);
            return result;
        }
        else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }
    }

}
