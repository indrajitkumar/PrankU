/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.amcoder.pranku.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.eventhelper.EventHelper;
import com.amcoder.pranku.views.EditDeletePopUP;
import com.philips.cdp.uikit.customviews.UIKitRadioButton;
import com.philips.cdp.uikit.drawable.VectorDrawable;

import java.util.List;

public class AddressSelectionAdapter extends RecyclerView.Adapter<AddressSelectionAdapter.AddressSelectionHolder> {
    private Context mContext;
    private List<AddressFields> mAddresses;

    private EditDeletePopUP mPopUP;
    private int mSelectedIndex;
    private Drawable mOptionsDrawable;
    AddressFields mAddressFields;

    private int mOptionsClickPosition = -1;


    public AddressSelectionAdapter(final Context context, final List<AddressFields> addresses) {
        mContext = context;
        mAddresses = addresses;
        mSelectedIndex = 0;
        initOptionsDrawable();
    }

    void initOptionsDrawable() {
        mOptionsDrawable = VectorDrawable.create(mContext, R.drawable.options_icon_5x17);
    }

    @Override
    public AddressSelectionHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.address_selection_item, null);
        return new AddressSelectionHolder(view);
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    @Override
    public void onBindViewHolder(final AddressSelectionHolder holder, final int position) {
        AddressFields address = mAddresses.get(position);
        holder.name.setText(address.getFirstName() + " " + address.getLastName());
        holder.address.setText(PrankUtility.formatAddress(address.getFormattedAddress()));
        holder.options.setImageDrawable(mOptionsDrawable);

        //Update payment options buttons
        updatePaymentButtonsVisibility(holder.paymentOptions, position);

        //bind options: edit, delete menu
        bindOptionsButton(holder.optionLayout, position);

        //bind toggle button
        setToggleStatus(holder.toggle, position);
        bindToggleButton(holder, holder.toggle);

        //bind deliver to address
        bindDeliverToThisAddress(holder.deliverToThisAddress,address);

        //bind add new address
        bindAddNewAddress(holder.addNewAddress);
    }

    private void bindAddNewAddress(final Button newAddress) {
        newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                EventHelper.getInstance().notifyEventOccurred(Constant.ADD_NEW_ADDRESS);
            }
        });
    }

    private void bindDeliverToThisAddress(Button deliver, AddressFields pAddressFields) {
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                EventHelper.getInstance().notifyEventOccurred(Constant.DELIVER_TO_THIS_ADDRESS);
            }
        });
        mAddressFields = pAddressFields;
    }

    public void onStop() {
        if (mPopUP != null && mPopUP.isShowing()) {
            mPopUP.dismiss();
        }
    }

    public AddressFields getAddressForDelivery(){
        return mAddressFields;
    }
    private void updatePaymentButtonsVisibility(final ViewGroup paymentOptions, final int position) {
        if (mSelectedIndex == position) {
            paymentOptions.setVisibility(View.VISIBLE);
        } else {
            paymentOptions.setVisibility(View.GONE);
        }
    }

    private void setToggleStatus(final UIKitRadioButton toggle, final int position) {
        if (mSelectedIndex == position) {
            toggle.setChecked(true);
        } else {
            toggle.setChecked(false);
        }
    }

    private void bindToggleButton(final AddressSelectionHolder holder, final UIKitRadioButton toggle) {
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mSelectedIndex = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    public int getSelectedPosition() {
        return mSelectedIndex;
    }

    public int getOptionsClickPosition() {
        return mOptionsClickPosition;
    }


    private void bindOptionsButton(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOptionsClickPosition = position;
                boolean disableDelete = false;
                if (mAddresses.size() == 1) {
                    disableDelete = true;
                }
                mPopUP = new EditDeletePopUP(mContext, v, disableDelete);
                mPopUP.show();
            }
        });
    }

    public void setAddresses(final List<AddressFields> data) {
        mSelectedIndex = 0;
        mAddresses = data;
    }

    public class AddressSelectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button deliverToThisAddress;
        Button addNewAddress;

        TextView name;
        TextView address;

        UIKitRadioButton toggle;
        ImageView options;

        ViewGroup paymentOptions;
        ViewGroup optionLayout;

        public AddressSelectionHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            address = (TextView) view.findViewById(R.id.tv_address);
            toggle = (UIKitRadioButton) view.findViewById(R.id.rbtn_toggle);
            options = (ImageView) view.findViewById(R.id.img_options);
            paymentOptions = (ViewGroup) view.findViewById(R.id.payment_options);
            deliverToThisAddress = (Button) view.findViewById(R.id.btn_deliver_to_this_address);
            addNewAddress = (Button) view.findViewById(R.id.btn_add_new_address);
            optionLayout = (ViewGroup) view.findViewById(R.id.options_layout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mSelectedIndex = getAdapterPosition();
            setToggleStatus(toggle, getAdapterPosition());
            notifyDataSetChanged();
        }
    }
}