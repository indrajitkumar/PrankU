package com.amcoder.pranku.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.NetworkImageLoader;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.model.Product;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by philips on 6/9/17.
 */

public class OrderProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = OrderProductAdapter.class.getSimpleName();
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private final Context mContext;
    private ArrayList<Product> mProductList;
    private AddressFields mAddressFields;
    private ImageLoader mImageLoader;

    public OrderProductAdapter(Context pContext, ArrayList<Product> pProductList, AddressFields pAddressFields) {
        mContext = pContext;
        mProductList = pProductList;
        mAddressFields = pAddressFields;
        mImageLoader = NetworkImageLoader.getInstance(mContext).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_footer_item, parent, false);
            return new FooterOrderSummaryViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_shopping_item, parent, false);
            return new OrderProductHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mProductList.size() == 0) return;
        if (holder instanceof OrderProductHolder) {
            OrderProductHolder orderProductHolder = (OrderProductHolder) holder;
//            String imageURL = mProductList.get(0).getImageURL();
//            ImageLoader mImageLoader = NetworkImageLoader.getInstance(mContext)
//                    .getImageLoader();
//            orderProductHolder.mNetworkImage.setImageUrl(imageURL, mImageLoader);
//            orderProductHolder.mTvProductName.setText(mProductList.get(0).getTitle());
//            String price = mProductList.get(0).getPrice();
            String imageURL = mProductList.get(0).getUrls().get(0);
            String discountedPrice = mProductList.get(0).getDiscountPrice();
            String formattedPrice = mProductList.get(0).getPrice();

            orderProductHolder.mTvProductName.setText(mProductList.get(0).getTitle());
            orderProductHolder.mNetworkImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.no_icon));
            orderProductHolder.mTvtotalPrice.setText(formattedPrice);
            //orderProductHolder.mTvQuantity.setText(mProductList.get(0).get);
            orderProductHolder.mTvQuantity.setText("1");
            getNetworkImage(orderProductHolder, imageURL);
        } else {
            FooterOrderSummaryViewHolder footerHolder = (FooterOrderSummaryViewHolder) holder;
            footerHolder.mTitleDeliveryAddress.setText(R.string.shipping_address);
            footerHolder.mTitleVat.setText(R.string.vat);
            // footerHolder.mDeliveryPrice.setText();
        }
    }

    private void getNetworkImage(OrderProductHolder orderProductHolder, String imageURL) {
        mImageLoader.get(imageURL, ImageLoader.getImageListener(orderProductHolder.mNetworkImage,
                R.drawable.no_icon, android.R.drawable
                        .ic_dialog_alert));
        orderProductHolder.mNetworkImage.setImageUrl(imageURL, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mProductList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType= " + position);
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private Product getLastValidItem() {
        return mProductList.get(mProductList.size() - 1);
    }

    private boolean isPositionFooter(int position) {
        return position == mProductList.size();
    }

    public class OrderProductHolder extends RecyclerView.ViewHolder {
        TextView mTvProductName;
        NetworkImageView mNetworkImage;
        TextView mTvQuantity;
        TextView mTvtotalPrice;

        public OrderProductHolder(final View itemView) {
            super(itemView);
            mTvProductName = (TextView) itemView.findViewById(R.id.tv_productName);
            mNetworkImage = (NetworkImageView) itemView.findViewById(R.id.iv_product_image);
            mTvQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            mTvtotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
        }
    }

    public class FooterOrderSummaryViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleDeliveryAddress;
        TextView mShippingFirstName;
        TextView mShippingAddress;
        TextView mTitleDelivery;
        TextView mDeliveryPrice;
        TextView mTitleTotalPrice;
        TextView mTotalPrice;
        TextView mTitleVat;
        TextView mVatValue;
        TextView mVatInclusive;
        View mDeliveryView;
        ImageView mEditIcon;

        public FooterOrderSummaryViewHolder(View itemView) {
            super(itemView);
            mTitleDeliveryAddress = (TextView) itemView.findViewById(R.id.tv_title_delivery_address);
            mShippingFirstName = (TextView) itemView.findViewById(R.id.tv_shipping_first_name);
            mShippingAddress = (TextView) itemView.findViewById(R.id.tv_shipping_address);
            mTitleDelivery = (TextView) itemView.findViewById(R.id.tv_delivery);
            mDeliveryPrice = (TextView) itemView.findViewById(R.id.tv_delivery_price);
            mTitleTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_lable);
            mTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
            mTitleVat = (TextView) itemView.findViewById(R.id.tv_vat);
            mVatValue = (TextView) itemView.findViewById(R.id.tv_vat_price);
            mVatInclusive = (TextView) itemView.findViewById(R.id.tv_vat_inclusive);
            mDeliveryView = (View) itemView.findViewById(R.id.delivery_view);
            mEditIcon = (ImageView) itemView.findViewById(R.id.edit_icon);
        }
    }
}
