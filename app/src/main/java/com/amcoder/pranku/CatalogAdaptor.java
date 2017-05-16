package com.amcoder.pranku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CatalogAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public CatalogAdaptor(Context pContext) {
        mContext = pContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.prank_catalog_item, parent, false);
        return new PrankCatalogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class PrankCatalogViewHolder extends RecyclerView.ViewHolder {
        public PrankCatalogViewHolder(View v) {
            super(v);
        }
    }
}
