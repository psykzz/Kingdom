package com.psykzz.turnbasechat;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.psykzz.turnbasechat.Helpers.LocalDiscovery;

import java.util.ArrayList;


/**
 * Created by PsyKzz on 26/11/2016.
 */

public class DiscoveryAdaptor implements ListAdapter {

    LocalDiscovery mLocalDiscovery;
    private Activity mActivity;

    public DiscoveryAdaptor(Activity context, ListView mListView, LocalDiscovery localDiscovery) {
        super();
        mActivity = context;
        mLocalDiscovery = localDiscovery;
        ArrayList<String> discoveries = mLocalDiscovery.getRecentDiscoveries();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }

    @Override
    public int getCount() {
        return mLocalDiscovery.getRecentDiscoveries().size();
    }

    @Override
    public Object getItem(int i) {
        return mLocalDiscovery.getRecentDiscoveries().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return mActivity.findViewById(R.id.discItem);
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mLocalDiscovery.getRecentDiscoveries().size() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
