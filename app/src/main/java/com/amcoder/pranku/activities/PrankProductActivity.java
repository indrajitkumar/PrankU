package com.amcoder.pranku.activities;

import android.os.Bundle;

import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.fragment.AddressSelectionFragment;
import com.amcoder.pranku.fragment.BaseFragment;
import com.amcoder.pranku.fragment.ShippingAddressFragment;

import java.io.Serializable;

import static com.amcoder.pranku.Utility.Constant.DELIVER_PRODUCT;

/**
 * Created by philips on 6/6/17.
 */

public class PrankProductActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Serializable serializableExtra = (getIntent().getSerializableExtra(DELIVER_PRODUCT));
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DELIVER_PRODUCT, serializableExtra);

        if (PrankUtility.getAddressList() == null || PrankUtility.getAddressList().size() == 0) {
            addFragment(ShippingAddressFragment.createInstance(bundle, BaseFragment.AnimationType.NONE), ShippingAddressFragment.TAG);
        } else
            addFragment(AddressSelectionFragment.createInstance(bundle, BaseFragment.AnimationType.NONE), AddressSelectionFragment.TAG);
    }


}
