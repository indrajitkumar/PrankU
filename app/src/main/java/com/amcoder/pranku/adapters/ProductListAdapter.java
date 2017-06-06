package com.amcoder.pranku.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.Utility.NetworkImageLoader;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.eventhelper.EventHelper;
import com.amcoder.pranku.model.Product;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.shamanland.fonticon.FontIconTextView;

import java.util.ArrayList;


public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ImageLoader mImageLoader;
    private Context mContext;

    private ArrayList<Product> mProductList = new ArrayList<>();
    private Product mSelectedProduct;

    public ProductListAdapter(Context pContext, ArrayList<Product> pProductList) {
        mContext = pContext;
        mProductList = pProductList;
        mImageLoader = NetworkImageLoader.getInstance(mContext).getImageLoader();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.product_list_item, parent, false);
        return new ProductListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Product product = mProductList.get(position);
        ProductListViewHolder productHolder = (ProductListViewHolder) holder;

        String imageURL = product.getUrls().get(0);
        String discountedPrice = product.getDiscountPrice();
        String formattedPrice = product.getPrice();

        productHolder.mProductName.setText(product.getTitle());
        productHolder.mProductImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.no_icon));
        productHolder.mPrice.setText(formattedPrice);
        productHolder.mProductDiscription.setText(product.getDescription());

        if (discountedPrice == null || discountedPrice.equalsIgnoreCase("")) {
            productHolder.mDiscountedPrice.setVisibility(View.GONE);
            productHolder.mPrice.setTextColor(PrankUtility.getThemeColor(mContext));
        } else if (formattedPrice != null && discountedPrice.equalsIgnoreCase(formattedPrice)) {
            productHolder.mPrice.setVisibility(View.GONE);
            productHolder.mDiscountedPrice.setVisibility(View.VISIBLE);
            productHolder.mDiscountedPrice.setText(discountedPrice);
        } else {
            productHolder.mDiscountedPrice.setVisibility(View.VISIBLE);
            productHolder.mDiscountedPrice.setText(discountedPrice);
            productHolder.mPrice.setPaintFlags(productHolder.mPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        getNetworkImage(productHolder, imageURL);

        productHolder.mArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setTheProductDataForDisplayingInProductDetailPage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    private void getNetworkImage(final ProductListViewHolder productListViewHolder,
                                 final String imageURL) {
        mImageLoader.get(imageURL, ImageLoader.getImageListener(productListViewHolder.mProductImage,
                R.drawable.no_icon, android.R.drawable
                        .ic_dialog_alert));
        productListViewHolder.mProductImage.setImageUrl(imageURL, mImageLoader);
    }

    private void setTheProductDataForDisplayingInProductDetailPage(int position) {
        mSelectedProduct = mProductList.get(position);
        EventHelper.getInstance().notifyEventOccurred(Constant.IAP_LAUNCH_SHIPPING_ADDRESS);
    }

    private class ProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        NetworkImageView mProductImage;
        TextView mProductName;
        TextView mProductDiscription;
        TextView mPrice;
        FontIconTextView mArrow;
        TextView mDiscountedPrice;

        ProductListViewHolder(View itemView) {
            super(itemView);
            mProductImage = (NetworkImageView) itemView.findViewById(R.id.image);
            mProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            mProductDiscription = (TextView) itemView.findViewById(R.id.tv_ctn);
            mPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mArrow = (FontIconTextView) itemView.findViewById(R.id.arrow);
            mDiscountedPrice = (TextView) itemView.findViewById(R.id.tv_discounted_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            boolean isSelected = this.itemView.isSelected();
            this.itemView.setSelected(!isSelected);
            this.itemView.setBackgroundColor(isSelected ? Color.TRANSPARENT : ContextCompat.getColor(this.itemView.getContext(), R.color.divider));
            setTheProductDataForDisplayingInProductDetailPage(getAdapterPosition());
        }
    }
}
