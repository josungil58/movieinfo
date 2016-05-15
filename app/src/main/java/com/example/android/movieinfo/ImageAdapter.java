package com.example.android.movieinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Singhal on 10/31/2015.
 */
public class ImageAdapter extends ArrayAdapter<String> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int layoutId;
    private int imageViewID;

    public ImageAdapter(Context context, int layoutId, int imageViewID, ArrayList<String> urls) {
        super(context, 0, 0, urls);
        //http://neoroid.tistory.com/entry/Java-this-this-super-super-%EC%9D%98-%EC%9D%B4%ED%95%B4 참조
        // super()는 결국 ImageAdapter가 상속받은 ArrayAdapter의 생성자
        // super(context, 0. urls)로 해도 같은 결과

        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutId = layoutId;
        this.imageViewID = imageViewID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // public abstract View getView(int positin, View convertView, ViewGroup parent)
        // int position: the position of the item within the adapter's data set
        // convertView - the old view to reuse
        // parent - the parent that this view will eventually be attaced to.

        // getView()가 호출되면 기존의 view 객체(convertView)가 전달되는데, 이것이 null인지를 검사해서
        // null인 경우에는 새로운 ImageView객체를 생성해야 함

        View v = convertView;
       if (v == null) {
            v = mLayoutInflater.inflate(layoutId, parent, false);
        // public View inflate (int resource, ViewGroup root, boolean attachToRoot)
        // VireGroup - optional view to be the parent of the generatedd hierarchy
        // false - root is only used to create the correct subclass of Layout Params for the root view in the Xml
        }
        ImageView imageView = (ImageView) v.findViewById(imageViewID);
        String url = getItem(position);
        // in Adapter class, public abstract Object getItem(int position)
        // Get the data item associated with the specified position in the data set
        Picasso.with(context)
                .load(url)
        //        .resize(100,100)
        //        .centerCrop()
                .into(imageView);
        // http://square.github.io/picasso/ 참조
        return v;
    }
}
