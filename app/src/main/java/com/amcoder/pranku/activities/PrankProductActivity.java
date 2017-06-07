package com.amcoder.pranku.activities;

import android.os.Bundle;

import com.amcoder.pranku.fragment.BaseFragment;
import com.amcoder.pranku.fragment.ShippingAddressFragment;

/**
 * Created by philips on 6/6/17.
 */

public class PrankProductActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(ShippingAddressFragment.createInstance(new Bundle(), BaseFragment.AnimationType.NONE), ShippingAddressFragment.TAG);

    }


}
