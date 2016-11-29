package com.psykzz.kingdom;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psykzz.kingdom.Helpers.Discovery;

public class DiscoveryAdaptor extends RecyclerView.Adapter<DiscoveryAdaptor.ViewHolder> {
    private ArrayList<Discovery> mDataset;
    private DiscoverActivity mActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
//        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.main_text);
//            txtFooter = (TextView) v.findViewById(R.id.secondary_text);
        }
    }

    public void add(int position, Discovery item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Discovery item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DiscoveryAdaptor(DiscoverActivity activity) {
        mActivity = activity;
        mDataset = mActivity.mLocalDiscovery.mRecentDiscoveries;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DiscoveryAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.discovery_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Discovery disc = mDataset.get(position);
        holder.itemView.setTag(disc);  // save the item
        holder.txtHeader.setText(mDataset.get(position).getEndpointName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
