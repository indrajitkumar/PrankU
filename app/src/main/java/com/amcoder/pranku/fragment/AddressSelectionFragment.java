/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.amcoder.pranku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.adapters.AddressSelectionAdapter;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.eventhelper.EventHelper;
import com.amcoder.pranku.eventhelper.EventListener;
import com.amcoder.pranku.views.EditDeletePopUP;

import java.util.ArrayList;
import java.util.List;

public class AddressSelectionFragment extends BaseFragment implements EventListener {

    public static final String TAG = AddressSelectionFragment.class.getName();
    private Context mContext;

    private RecyclerView mAddressListView;
//    private AddressController mAddressController;
    private AddressSelectionAdapter mAdapter;
    private List<AddressFields> mAddresses = new ArrayList<>();
    private Button mCancelButton;

    private boolean mIsAddressUpdateAfterDelivery;
    private String mJanRainEmail;

   // private DeliveryModes mDeliveryMode;
    private boolean mIsFromOnCreate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFromOnCreate = true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_selection, container, false);
        mAddressListView = (RecyclerView) view.findViewById(R.id.shipping_addresses);
        mCancelButton = (Button) view.findViewById(R.id.btn_cancel);
        bindCancelListener();

       // mAddressController = new AddressController(mContext, this);
        registerEvents();

        Bundle bundle = getArguments();
       // mDeliveryMode = bundle.getParcelable(Constant.SET_DELIVERY_MODE);
        List<AddressFields> addressList = PrankUtility.getAddressList();

        if (bundle.containsKey(Constant.ADDRESS_LIST)) {
            mAddresses = (List<AddressFields>) bundle.getSerializable(Constant.ADDRESS_LIST);
        }
        mAdapter = new AddressSelectionAdapter(mContext, addressList);
        mAddressListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAddressListView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleAndBackButtonVisibility(R.string.iap_address, true);

        if (mIsFromOnCreate) {
            mIsFromOnCreate = false;
        } else {
            mAdapter = new AddressSelectionAdapter(mContext, mAddresses);
            mAddressListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void registerEvents() {
        EventHelper.getInstance().registerEventNotification(EditDeletePopUP.EVENT_EDIT, this);
        EventHelper.getInstance().registerEventNotification(EditDeletePopUP.EVENT_DELETE, this);
        EventHelper.getInstance().registerEventNotification(Constant.ADD_NEW_ADDRESS, this);
        EventHelper.getInstance().registerEventNotification(Constant.DELIVER_TO_THIS_ADDRESS, this);
    }

    public void bindCancelListener() {
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //moveToShoppingCart();
            }
        });
    }

//    private void moveToShoppingCart() {
//        showFragment(ShoppingCartFragment.TAG);
//    }

//    @Override
//    public void onGetAddress(Message msg) {
//        if (mIsAddressUpdateAfterDelivery) {
//            mIsAddressUpdateAfterDelivery = false;
//            return;
//        }
//
//        dismissProgressDialog();
//        if (msg.obj instanceof IAPNetworkError) {
//            NetworkUtility.getInstance().showErrorMessage(msg, getFragmentManager(), mContext);
//            moveToShoppingCart();
//        } else {
//            if (msg.what == RequestCode.DELETE_ADDRESS) {
//                if (mAdapter.getOptionsClickPosition() != -1)
//                    mAddresses.remove(mAdapter.getOptionsClickPosition());
//                mAdapter.setAddresses(mAddresses);
//                mAdapter.notifyDataSetChanged();
//            } else if (isVisible()) {
//                if (msg.obj instanceof GetShippingAddressData) {
//                    GetShippingAddressData shippingAddresses = (GetShippingAddressData) msg.obj;
//                    mAddresses = shippingAddresses.getAddresses();
//                }
//            }
//            mAdapter = new AddressSelectionAdapter(mContext, mAddresses);
//            mAddressListView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void onCreateAddress(Message msg) {
//
//    }

//    @Override
//    public void onSetDeliveryAddress(final Message msg) {
//        if (msg.obj.equals(IAPConstant.IAP_SUCCESS)) {
//            Addresses selectedAddress = retrieveSelectedAddress();
//            mIsAddressUpdateAfterDelivery = true;
//            mAddressController.setDefaultAddress(selectedAddress);
//            if (mDeliveryMode == null)
//                mAddressController.getDeliveryModes();
//            else
//                checkPaymentDetails();
//        } else {
//            NetworkUtility.getInstance().showErrorMessage(msg, getFragmentManager(), mContext);
//            dismissProgressDialog();
//        }
//    }
//
//    @Override
//    public void onGetDeliveryModes(Message msg) {
//       handleDeliveryMode(msg, mAddressController);
//    }
//
//    @Override
//    public void onSetDeliveryMode(final Message msg) {
//        if (msg.obj.equals(IAPConstant.IAP_SUCCESS)) {
//            checkPaymentDetails();
//        } else {
//            NetworkUtility.getInstance().showErrorMessage(msg, getFragmentManager(), mContext);
//            dismissProgressDialog();
//        }
//    }
//
//    @Override
//    public void onGetPaymentDetails(Message msg) {
//        dismissProgressDialog();
//        if ((msg.obj).equals(NetworkConstants.EMPTY_RESPONSE)) {
//
//            Addresses address = retrieveSelectedAddress();
//            AddressFields selectedAddress = Utility.prepareAddressFields(address, mJanRainEmail);
//            CartModelContainer.getInstance().setShippingAddressFields(selectedAddress);
//            addFragment(BillingAddressFragment.createInstance(new Bundle(), AnimationType.NONE),
//                    BillingAddressFragment.TAG);
//        } else if ((msg.obj instanceof IAPNetworkError)) {
//            NetworkUtility.getInstance().showErrorMessage(msg, getFragmentManager(), mContext);
//        } else if ((msg.obj instanceof PaymentMethods)) {
//            AddressFields selectedAddress = Utility.prepareAddressFields(retrieveSelectedAddress(), mJanRainEmail);
//            CartModelContainer.getInstance().setShippingAddressFields(selectedAddress);
//            PaymentMethods mPaymentMethods = (PaymentMethods) msg.obj;
//            List<PaymentMethod> mPaymentMethodsList = mPaymentMethods.getPayments();
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(IAPConstant.PAYMENT_METHOD_LIST, (Serializable) mPaymentMethodsList);
//            addFragment(
//                    PaymentSelectionFragment.createInstance(bundle, AnimationType.NONE), PaymentSelectionFragment.TAG);
//        }
//    }

    @Override
    public void onEventReceived(final String event) {
        if (event.equalsIgnoreCase(Constant.DELIVER_TO_THIS_ADDRESS)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.DELIVER_TO_THIS_ADDRESS,mAdapter.getAddressForDelivery());
            bundle.putSerializable(Constant.DELIVER_PRODUCT, getArguments().getSerializable(Constant.DELIVER_PRODUCT));
            addFragment(OrderSummaryFragment.createInstance(bundle, AnimationType.NONE),
                    OrderSummaryFragment.TAG);
        }
//        if (!TextUtils.isEmpty(event)) {
//            if (EditDeletePopUP.EVENT_EDIT.equals(event)) {
//                HashMap<String, String> addressHashMap = updateShippingAddress();
//                moveToShippingAddressFragment(addressHashMap);
//            } else if (EditDeletePopUP.EVENT_DELETE.equals(event) && isNetworkConnected()) {
//                deleteShippingAddress();
//            }
//        }
//        if (event.equalsIgnoreCase(IAPConstant.ADD_NEW_ADDRESS)) {
//            Bundle args = new Bundle();
//            args.putBoolean(IAPConstant.IS_SECOND_USER, true);
//            if (mDeliveryMode != null)
//                args.putParcelable(IAPConstant.SET_DELIVERY_MODE, mDeliveryMode);
//            addFragment(ShippingAddressFragment.createInstance(args, AnimationType.NONE),
//                    ShippingAddressFragment.TAG);
//        } else if (event.equalsIgnoreCase(IAPConstant.DELIVER_TO_THIS_ADDRESS)) {
//            if (!isProgressDialogShowing()) {
//                showProgressDialog(mContext, getResources().getString(R.string.iap_please_wait));
//                mAddressController.setDeliveryAddress(retrieveSelectedAddress().getId());
//                CartModelContainer.getInstance().setAddressId(retrieveSelectedAddress().getId());
//            }
//        }
    }

//    public void checkPaymentDetails() {
//        PaymentController paymentController = new PaymentController(mContext, this);
//        paymentController.getPaymentDetails();
//    }

//    private void deleteShippingAddress() {
//        if (!isProgressDialogShowing()) {
//            showProgressDialog(mContext, getString(R.string.iap_please_wait));
//            int pos = mAdapter.getOptionsClickPosition();
//            mAddressController.deleteAddress(mAddresses.get(pos).getId());
//        }
//    }

//    private HashMap<String, String> updateShippingAddress() {
//        int pos = mAdapter.getOptionsClickPosition();
//        Addresses address = mAddresses.get(pos);
//        HashMap<String, String> addressHashMap = new HashMap<>();
//
//        String titleCode = address.getTitleCode();
//
//        if (titleCode.trim().length() > 0)
//            titleCode = titleCode.substring(0, 1).toUpperCase(Locale.getDefault()) + titleCode.substring(1);
//
//        addressHashMap.put(ModelConstants.FIRST_NAME, address.getFirstName());
//        addressHashMap.put(ModelConstants.LAST_NAME, address.getLastName());
//        addressHashMap.put(ModelConstants.TITLE_CODE, titleCode);
//        addressHashMap.put(ModelConstants.COUNTRY_ISOCODE, address.getCountry().getIsocode());
//        addressHashMap.put(ModelConstants.LINE_1, address.getLine1());
//        addressHashMap.put(ModelConstants.LINE_2, address.getLine2());
//        addressHashMap.put(ModelConstants.POSTAL_CODE, address.getPostalCode());
//        addressHashMap.put(ModelConstants.TOWN, address.getTown());
//        addressHashMap.put(ModelConstants.ADDRESS_ID, address.getId());
//        addressHashMap.put(ModelConstants.PHONE_1, address.getPhone1());
//
//        if (address.getRegion() != null) {
//            addressHashMap.put(ModelConstants.REGION_ISOCODE, address.getRegion().getName());
//            addressHashMap.put(ModelConstants.REGION_CODE, address.getRegion().getIsocode());
//        }
//
//        if (address.getEmail() != null)
//            addressHashMap.put(ModelConstants.EMAIL_ADDRESS, address.getEmail());
//        else
//            addressHashMap.put(ModelConstants.EMAIL_ADDRESS, mJanRainEmail);
//
//        return addressHashMap;
//    }
//
//    private void moveToShippingAddressFragment(final HashMap<String, String> payload) {
//        Bundle extras = new Bundle();
//        extras.putSerializable(IAPConstant.UPDATE_SHIPPING_ADDRESS_KEY, payload);
//        if (mDeliveryMode != null)
//            extras.putParcelable(IAPConstant.SET_DELIVERY_MODE, mDeliveryMode);
//        addFragment(ShippingAddressFragment.createInstance(extras, AnimationType.NONE),
//                ShippingAddressFragment.TAG);
//    }

    private AddressFields retrieveSelectedAddress() {
        int pos = mAdapter.getSelectedPosition();
        return mAddresses.get(pos);
    }

    public static AddressSelectionFragment createInstance(final Bundle args, final AnimationType animType) {
        AddressSelectionFragment fragment = new AddressSelectionFragment();
        args.putInt(Constant.EXTRA_ANIMATIONTYPE, animType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEvents();
    }

    public void unregisterEvents() {
        EventHelper.getInstance().unregisterEventNotification(EditDeletePopUP.EVENT_EDIT, this);
        EventHelper.getInstance().unregisterEventNotification(EditDeletePopUP.EVENT_DELETE, this);
        EventHelper.getInstance().unregisterEventNotification(Constant.ADD_NEW_ADDRESS, this);
        EventHelper.getInstance().unregisterEventNotification(Constant.DELIVER_TO_THIS_ADDRESS, this);
    }
}