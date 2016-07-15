package com.shenjianli.shenlib.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by edianzu on 2016/7/14.
 */
public class BaseAdapter<T> extends android.widget.BaseAdapter{

    private List<T> mAdapterData;

    public BaseAdapter(){

    }

    @Override
    public int getCount() {
        if(null != mAdapterData){
            return mAdapterData.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if(null != mAdapterData){
            return mAdapterData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
