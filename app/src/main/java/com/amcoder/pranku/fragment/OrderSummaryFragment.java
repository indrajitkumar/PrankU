package com.amcoder.pranku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.adapters.OrderProductAdapter;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.model.Product;

import java.util.ArrayList;

/**
 * Created by philips on 6/9/17.
 */

public class OrderSummaryFragment extends BaseFragment implements View.OnClickListener {
    private OrderProductAdapter mAdapter;
    private Button mBtnPayNow;
    private Button mBtnCancel;
    private Context mContext;
    public static final String TAG = OrderSummaryFragment.class.getName();

    public static OrderSummaryFragment createInstance(Bundle args, AnimationType animType) {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        args.putInt(Constant.EXTRA_ANIMATIONTYPE, animType.ordinal());
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_summary_fragment, container, false);

        mBtnPayNow = (Button) rootView.findViewById(R.id.btn_paynow);
        mBtnCancel = (Button) rootView.findViewById(R.id.btn_cancel);

        mBtnPayNow.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        Bundle bundle = getArguments();
        Product product = null;
        AddressFields addressFields = null;

        if (bundle.containsKey(Constant.DELIVER_PRODUCT) && bundle.containsKey(Constant.DELIVER_TO_THIS_ADDRESS)) {
            product = (Product) bundle.getSerializable(Constant.DELIVER_PRODUCT);
            addressFields = (AddressFields) bundle.getSerializable(Constant.DELIVER_TO_THIS_ADDRESS);
        }

        RecyclerView mOrderListView = (RecyclerView) rootView.findViewById(R.id.order_summary);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mOrderListView.setLayoutManager(layoutManager);
        ArrayList<Product> arrayList = new ArrayList<>();
        arrayList.add(product);
        mAdapter = new OrderProductAdapter(mContext, arrayList, addressFields);
        // updateCartOnResume();
        mOrderListView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleAndBackButtonVisibility(R.string.order_summary, true);
    }

    @Override
    public void onClick(View v) {

    }

}
