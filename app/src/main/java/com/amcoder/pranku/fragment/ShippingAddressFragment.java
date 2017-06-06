/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */

package com.amcoder.pranku.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.address.Validator;
import com.amcoder.pranku.views.SalutationDropDown;
import com.amcoder.pranku.views.StateDropDown;
import com.philips.cdp.uikit.customviews.InlineForms;
import com.philips.cdp.uikit.drawable.VectorDrawable;

import java.util.HashMap;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class ShippingAddressFragment extends BaseFragment
        implements View.OnClickListener,
        InlineForms.Validator,
        AdapterView.OnItemSelectedListener, SalutationDropDown.SalutationListener,
        StateDropDown.StateListener {

    private Context mContext;
    public static final String TAG = ShippingAddressFragment.class.getName();

    protected LinearLayout mLlFirstName;
    protected LinearLayout mLlLastName;
    protected LinearLayout mLlSalutation;
    protected LinearLayout mLlAddressLineOne;
    protected LinearLayout mLlAddressLineTwo;
    protected LinearLayout mLlTown;
    protected LinearLayout mLlPostalCode;
    protected LinearLayout mLlCountry;
    protected LinearLayout mlLState;
    protected LinearLayout mLlEmail;
    protected LinearLayout mLlPhoneNumber;

    protected TextView mTvTitle;

    protected TextView mTvSalutation;
    protected TextView mTvFirstName;
    protected TextView mTvLastName;
    protected TextView mTvAddressLineOne;
    protected TextView mTvAddressLineTwo;
    protected TextView mTvTown;
    protected TextView mTvPostalCode;
    protected TextView mTvCountry;
    protected TextView mTvState;
    protected TextView mTvEmail;
    protected TextView mTvPhoneNumber;

    protected EditText mEtFirstName;
    protected EditText mEtLastName;
    protected EditText mEtSalutation;
    protected EditText mEtAddressLineOne;
    protected EditText mEtAddressLineTwo;
    protected EditText mEtTown;
    protected EditText mEtPostalCode;
    protected EditText mEtCountry;
    protected EditText mEtState;
    protected EditText mEtEmail;
    protected EditText mEtPhoneNumber;

    protected Button mBtnContinue;
    protected Button mBtnCancel;

//    private PaymentController mPaymentController;
//    private AddressController mAddressController;
    private AddressFields mShippingAddressFields;

    protected InlineForms mInlineFormsParent;
    private Validator mValidator = null;

    private SalutationDropDown mSalutationDropDown;
    private StateDropDown mStateDropDown;

    private HashMap<String, String> mAddressFieldsHashmap = null;
    private HashMap<String, String> addressHashMap = new HashMap<>();
    private Drawable imageArrow;
    protected boolean mIgnoreTextChangeListener = false;

    private String mRegionIsoCode = null;

    PhoneNumberUtil phoneNumberUtil;
    Phonenumber.PhoneNumber phoneNumber = null;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (!(this instanceof BillingAddressFragment))
//            CartModelContainer.getInstance().setAddressId(null);
//    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.iap_shipping_address_layout, container, false);
        phoneNumberUtil = PhoneNumberUtil.createInstance(mContext);
        mInlineFormsParent = (InlineForms) rootView.findViewById(R.id.inlineForms);

        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);

        mLlFirstName = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_first_name);
        mLlLastName = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_last_name);
        mLlSalutation = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_salutation);
        mLlAddressLineOne = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_address_line_one);
        mLlAddressLineTwo = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_address_line_two);
        mLlTown = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_town);
        mLlPostalCode = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_postal_code);
        mLlCountry = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_country);
        mlLState = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_state);
        mLlEmail = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_email);
        mLlPhoneNumber = (LinearLayout) mInlineFormsParent.findViewById(R.id.ll_phone_number);

        mTvSalutation = (TextView) mInlineFormsParent.findViewById(R.id.tv_salutation);
        mTvFirstName = (TextView) mInlineFormsParent.findViewById(R.id.tv_first_name);
        mTvLastName = (TextView) mInlineFormsParent.findViewById(R.id.tv_last_name);
        mTvAddressLineOne = (TextView) mInlineFormsParent.findViewById(R.id.tv_address_line_one);
        mTvAddressLineTwo = (TextView) mInlineFormsParent.findViewById(R.id.tv_address_line_two);
        mTvTown = (TextView) mInlineFormsParent.findViewById(R.id.tv_town);
        mTvPostalCode = (TextView) mInlineFormsParent.findViewById(R.id.tv_postal_code);
        mTvCountry = (TextView) mInlineFormsParent.findViewById(R.id.tv_country);
        mTvState = (TextView) mInlineFormsParent.findViewById(R.id.tv_state);
        mTvEmail = (TextView) mInlineFormsParent.findViewById(R.id.tv_email);
        mTvPhoneNumber = (TextView) mInlineFormsParent.findViewById(R.id.tv_phone_number);

        mEtFirstName = (EditText) mInlineFormsParent.findViewById(R.id.et_first_name);
        mEtLastName = (EditText) mInlineFormsParent.findViewById(R.id.et_last_name);
        mEtSalutation = (EditText) mInlineFormsParent.findViewById(R.id.et_salutation);
        mEtAddressLineOne = (EditText) mInlineFormsParent.findViewById(R.id.et_address_line_one);
        mEtAddressLineTwo = (EditText) mInlineFormsParent.findViewById(R.id.et_address_line_two);
        mEtTown = (EditText) mInlineFormsParent.findViewById(R.id.et_town);
        mEtPostalCode = (EditText) mInlineFormsParent.findViewById(R.id.et_postal_code);
        mEtCountry = (EditText) mInlineFormsParent.findViewById(R.id.et_country);
        mEtState = (EditText) mInlineFormsParent.findViewById(R.id.et_state);
        mEtEmail = (EditText) mInlineFormsParent.findViewById(R.id.et_email);
        mEtPhoneNumber = (EditText) mInlineFormsParent.findViewById(R.id.et_phone_number);

        mEtPostalCode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        mEtSalutation.setKeyListener(null);
        mEtState.setKeyListener(null);

        mBtnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        mBtnCancel = (Button) rootView.findViewById(R.id.btn_cancel);

        mBtnContinue.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mValidator = new Validator();
        mInlineFormsParent.setValidator(this);

//        mAddressController = new AddressController(mContext, this);
//        mPaymentController = new PaymentController(mContext, this);
        mShippingAddressFields = new AddressFields();

        //mEtEmail.setText(HybrisDelegate.getInstance(mContext).getStore().getJanRainEmail());
//        mEtEmail.setEnabled(false);

//        mEtCountry.setText(HybrisDelegate.getInstance(mContext).getStore().getCountry());
        showUSRegions();
        mEtCountry.setEnabled(false);

        mEtFirstName.addTextChangedListener(new IAPTextWatcher(mEtFirstName));
        mEtLastName.addTextChangedListener(new IAPTextWatcher(mEtLastName));
        mEtAddressLineOne.addTextChangedListener(new IAPTextWatcher(mEtAddressLineOne));
        mEtAddressLineTwo.addTextChangedListener(new IAPTextWatcher(mEtAddressLineTwo));
        mEtTown.addTextChangedListener(new IAPTextWatcher(mEtTown));
        mEtPostalCode.addTextChangedListener(new IAPTextWatcher(mEtPostalCode));
        mEtCountry.addTextChangedListener(new IAPTextWatcher(mEtCountry));
        mEtEmail.addTextChangedListener(new IAPTextWatcher(mEtEmail));
        mEtPhoneNumber.addTextChangedListener(new IAPTextWatcher(mEtPhoneNumber));

        mEtState.addTextChangedListener(new IAPTextWatcher(mEtState));
        mEtSalutation.addTextChangedListener(new IAPTextWatcher(mEtSalutation));

        Bundle bundle = getArguments();
        if (null != bundle && bundle.containsKey(Constant.UPDATE_SHIPPING_ADDRESS_KEY)) {
            updateFields();
        }

        setImageArrow();
        mEtSalutation.setCompoundDrawables(null, null, imageArrow, null);
        mSalutationDropDown = new SalutationDropDown(mContext, mEtSalutation, this);
        mEtState.setCompoundDrawables(null, null, imageArrow, null);
        mStateDropDown = new StateDropDown(mContext, mEtState, this);

        mEtSalutation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSalutationDropDown.show();
                return false;
            }
        });

        mEtState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PrankUtility.hideKeypad(mContext);
                mStateDropDown.show();
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setImageArrow() {
        imageArrow = VectorDrawable.create(mContext, R.drawable.count_drop_down);
        int width = (int) getResources().getDimension(R.dimen.count_drop_down_icon_width);
        int height = (int) getResources().getDimension(R.dimen.count_drop_down_icon_height);
        imageArrow.setBounds(0, 0, width, height);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onClick(View v) {

    }
//    @Override
//    public void onClick(final View v) {
//        PrankUtility.hideKeypad(mContext);
////        if (!isNetworkConnected()) return;
//        if (v == mBtnContinue) {
//            //Edit and save address
//            if (mBtnContinue.getText().toString().equalsIgnoreCase(getString(R.string.save))) {
//                if (!isProgressDialogShowing()) {
//                    showProgressDialog(mContext, getString(R.string.iap_please_wait));
//                    HashMap<String, String> addressHashMap = addressPayload();
//                    mAddressController.updateAddress(addressHashMap);
//                }
//            } else {//Add new address
//                if (!isProgressDialogShowing()) {
//                    showProgressDialog(mContext, getString(R.string.iap_please_wait));
//                    if (mlLState.getVisibility() == View.GONE)
//                        mShippingAddressFields.setRegionIsoCode(null);
//                    if (CartModelContainer.getInstance().getAddressId() != null) {
//                        HashMap<String, String> updateAddressPayload = addressPayload();
//                        if (mlLState.getVisibility() == View.VISIBLE && CartModelContainer.getInstance().getRegionIsoCode() != null)
//                            updateAddressPayload.put(ModelConstants.REGION_ISOCODE, CartModelContainer.getInstance().getRegionIsoCode());
//                        updateAddressPayload.put(ModelConstants.ADDRESS_ID, CartModelContainer.getInstance().getAddressId());
//                        mAddressController.updateAddress(updateAddressPayload);
//                    } else {
//                        mAddressController.createAddress(mShippingAddressFields);
//                    }
//                }
//            }
//        }
//        else if (v == mBtnCancel) {
////            Fragment fragment = getFragmentManager().findFragmentByTag(BuyDirectFragment.TAG);
////            if (fragment != null) {
////                moveToVerticalAppByClearingStack();
////            } else {
//                getFragmentManager().popBackStackImmediate();
////            }
//        }
//    }

//    private void showErrorFromServer(Error error) {
//
//        if (error != null) {
//            if (error.getSubject() != null) {
//                String errorMessage;
//                if (error.getSubject().equalsIgnoreCase(ModelConstants.COUNTRY_ISOCODE)) {
//                    errorMessage = getResources().getString(R.string.iap_country_error);
//                    mInlineFormsParent.setErrorMessage(errorMessage);
//                    mInlineFormsParent.showError(mEtCountry);
//                } else if (error.getSubject().equalsIgnoreCase(ModelConstants.POSTAL_CODE)) {
//                    errorMessage = getResources().getString(R.string.iap_postal_code_error);
//                    mInlineFormsParent.setErrorMessage(errorMessage);
//                    mInlineFormsParent.showError(mEtPostalCode);
//                } else if (error.getSubject().equalsIgnoreCase(ModelConstants.PHONE_1)) {
//                    errorMessage = getResources().getString(R.string.iap_phone_error);
//                    mInlineFormsParent.setErrorMessage(errorMessage);
//                    mInlineFormsParent.showError(mEtPhoneNumber);
//                } else if (error.getSubject().equalsIgnoreCase(ModelConstants.LINE_2)) {
//                    errorMessage = getResources().getString(R.string.iap_address_error);
//                    mInlineFormsParent.setErrorMessage(errorMessage);
//                    mInlineFormsParent.showError(mEtAddressLineTwo);
//                } else if (error.getSubject().equalsIgnoreCase(ModelConstants.LINE_1)) {
//                    errorMessage = getResources().getString(R.string.iap_address_error);
//                    mInlineFormsParent.setErrorMessage(errorMessage);
//                    mInlineFormsParent.showError(mEtAddressLineOne);
//                }
//                NetworkUtility.getInstance().showErrorDialog(mContext, getFragmentManager(),
//                        getString(R.string.iap_ok), getString(R.string.iap_server_error),
//                        error.getMessage());
//                mBtnContinue.setEnabled(false);
//            }
//        }
//    }

    public void checkFields() {
        String firstName = mEtFirstName.getText().toString();
        String lastName = mEtLastName.getText().toString();
        String addressLineOne = mEtAddressLineOne.getText().toString();
        String addressLineTwo = mEtAddressLineTwo.getText().toString();
        String postalCode = mEtPostalCode.getText().toString().replaceAll(" ", "");
        String phoneNumber = mEtPhoneNumber.getText().toString().replaceAll(" ", "");
        String town = mEtTown.getText().toString();
        String country = mEtCountry.getText().toString();
        String email = mEtEmail.getText().toString();

        if (mValidator.isValidName(firstName) && mValidator.isValidName(lastName)
                && mValidator.isValidAddress(addressLineOne) && (addressLineTwo.trim().equals("") || mValidator.isValidAddress(addressLineTwo))
                && mValidator.isValidPostalCode(postalCode)
                && mValidator.isValidEmail(email) && mValidator.isValidPhoneNumber(phoneNumber)
                && mValidator.isValidTown(town) && mValidator.isValidCountry(country)
                && (!mEtSalutation.getText().toString().trim().equalsIgnoreCase(""))
                && (mlLState.getVisibility() == View.GONE || (mlLState.getVisibility() == View.VISIBLE && !mEtState.getText().toString().trim().equalsIgnoreCase("")))) {

            mShippingAddressFields = setAddressFields(mShippingAddressFields);

            mBtnContinue.setEnabled(true);
        } else {
            mBtnContinue.setEnabled(false);
        }
    }

    @Override
    public void validate(View editText, boolean hasFocus) {
        boolean result = true;
        String errorMessage = null;

        if (editText.getId() == R.id.et_first_name && !hasFocus) {
            result = mValidator.isValidName(mEtFirstName.getText().toString());
            errorMessage = getResources().getString(R.string.iap_first_name_error);
        }
        if (editText.getId() == R.id.et_last_name && !hasFocus) {
            result = mValidator.isValidName(mEtLastName.getText().toString());
            errorMessage = getResources().getString(R.string.iap_last_name_error);
        }
        if (editText.getId() == R.id.et_town && !hasFocus) {
            result = mValidator.isValidTown(mEtTown.getText().toString());
            errorMessage = getResources().getString(R.string.iap_town_error);
        }
        if (editText.getId() == R.id.et_phone_number && !hasFocus) {
            result = validatePhoneNumber(mEtCountry.getText().toString()
                    , mEtPhoneNumber.getText().toString());
            errorMessage = getResources().getString(R.string.iap_phone_error);
        }
        if (editText.getId() == R.id.et_country && !hasFocus) {
            result = mValidator.isValidCountry(mEtCountry.getText().toString());
            errorMessage = getResources().getString(R.string.iap_country_error);
            showUSRegions();
        }
        if (editText.getId() == R.id.et_postal_code && !hasFocus) {
            result = mValidator.isValidPostalCode(mEtPostalCode.getText().toString());
            errorMessage = getResources().getString(R.string.iap_postal_code_error);
        }
        if (editText.getId() == R.id.et_email && !hasFocus) {
            result = mValidator.isValidEmail(mEtEmail.getText().toString());
            errorMessage = getResources().getString(R.string.iap_email_error);
        }
        if (editText.getId() == R.id.et_address_line_one && !hasFocus) {
            result = mValidator.isValidAddress(mEtAddressLineOne.getText().toString());
            errorMessage = getResources().getString(R.string.iap_address_error);
        }
        if (editText.getId() == R.id.et_address_line_two && !hasFocus) {
            if (mEtAddressLineTwo.getText().toString().trim().equals("")) {
                result = true;
            } else {
                result = mValidator.isValidAddress(mEtAddressLineTwo.getText().toString());
                errorMessage = getResources().getString(R.string.iap_address_error);
            }
        }
        if ((editText.getId() == R.id.et_salutation || editText.getId() == R.id.et_state) && !hasFocus) {
            checkFields();
        }

        if (!result) {
            mInlineFormsParent.setErrorMessage(errorMessage);
            mInlineFormsParent.showError((EditText) editText);
            mBtnContinue.setEnabled(false);
        } else {
            mInlineFormsParent.removeError(editText);
            checkFields();
        }
    }

    private void showUSRegions() {
        if (mEtCountry.getText().toString().equals("US")) {
            mlLState.setVisibility(View.VISIBLE);
        } else {
            mlLState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleAndBackButtonVisibility(R.string.iap_address, true);
    }

    public static ShippingAddressFragment createInstance(Bundle args, AnimationType animType) {
        ShippingAddressFragment fragment = new ShippingAddressFragment();
        args.putInt(Constant.EXTRA_ANIMATIONTYPE, animType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onSalutationSelect(String salutation) {
        mEtSalutation.setText(salutation);
    }

    @Override
    public void onStateSelect(String state) {
        mEtState.setText(state);
    }

    @Override
    public void stateRegionCode(String regionCode) {
        mRegionIsoCode = regionCode;
        mShippingAddressFields.setRegionIsoCode(regionCode);
        if (addressHashMap != null) {
            addressHashMap.put(Constant.REGION_ISOCODE, regionCode);
        }
    }

    private class IAPTextWatcher implements TextWatcher {
        private EditText mEditText;

        public IAPTextWatcher(EditText editText) {
            mEditText = editText;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mEditText != mEtPhoneNumber && !mIgnoreTextChangeListener) {
                validate(mEditText, false);
            }
        }

        private boolean isInAfterTextChanged;

        public synchronized void afterTextChanged(Editable text) {
            if (mEditText == mEtPhoneNumber && !isInAfterTextChanged && !mIgnoreTextChangeListener) {
                isInAfterTextChanged = true;
                validate(mEditText, false);
                isInAfterTextChanged = false;
            }
        }
    }

    private boolean validatePhoneNumber(String country, String number) {
        try {
            phoneNumber = phoneNumberUtil.parse(number, country);
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            String formattedPhoneNumber = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            mEtPhoneNumber.setText(formattedPhoneNumber);
            mEtPhoneNumber.setSelection(mEtPhoneNumber.getText().length());
            return isValid;
        } catch (Exception e) {
            Log.d("ShippingAddressFragment", "NumberParseException");
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void updateFields() {
        Bundle bundle = getArguments();
        mAddressFieldsHashmap = (HashMap<String, String>) bundle.getSerializable(Constant.UPDATE_SHIPPING_ADDRESS_KEY);
        if (null == mAddressFieldsHashmap) {
            return;
        }
        mBtnContinue.setText(getString(R.string.save));

        mEtFirstName.setText(mAddressFieldsHashmap.get(Constant.FIRST_NAME));
        mEtLastName.setText(mAddressFieldsHashmap.get(Constant.LAST_NAME));
        mEtSalutation.setText(mAddressFieldsHashmap.get(Constant.TITLE_CODE));
        mEtAddressLineOne.setText(mAddressFieldsHashmap.get(Constant.LINE_1));
        mEtAddressLineTwo.setText(mAddressFieldsHashmap.get(Constant.LINE_2));
        mEtTown.setText(mAddressFieldsHashmap.get(Constant.TOWN));
        mEtPostalCode.setText(mAddressFieldsHashmap.get(Constant.POSTAL_CODE));
        mEtCountry.setText(mAddressFieldsHashmap.get(Constant.COUNTRY_ISOCODE));
        mEtPhoneNumber.setText(mAddressFieldsHashmap.get(Constant.PHONE_1));
        mEtEmail.setText(mAddressFieldsHashmap.get(Constant.EMAIL_ADDRESS));

        if (mAddressFieldsHashmap.containsKey(Constant.REGION_ISOCODE) &&
                mAddressFieldsHashmap.get(Constant.REGION_ISOCODE) != null) {
            mEtState.setText(mAddressFieldsHashmap.get(Constant.REGION_ISOCODE));
            mlLState.setVisibility(View.VISIBLE);
        } else {
            mlLState.setVisibility(View.GONE);
        }

        if (mAddressFieldsHashmap.containsKey(Constant.REGION_CODE) &&
                mAddressFieldsHashmap.get(Constant.REGION_CODE) != null) {
            addressHashMap.put(Constant.REGION_ISOCODE,
                    mAddressFieldsHashmap.get(Constant.REGION_CODE));
        }
    }

    protected AddressFields setAddressFields(AddressFields addressFields) {
        addressFields.setFirstName(mEtFirstName.getText().toString());
        addressFields.setLastName(mEtLastName.getText().toString());
        addressFields.setTitleCode(mEtSalutation.getText().toString());
        addressFields.setCountryIsocode(mEtCountry.getText().toString());
        addressFields.setLine1(mEtAddressLineOne.getText().toString());
        addressFields.setLine2(mEtAddressLineTwo.getText().toString());
        addressFields.setPostalCode(mEtPostalCode.getText().toString().replaceAll(" ", ""));
        addressFields.setTown(mEtTown.getText().toString());
        addressFields.setPhoneNumber(mEtPhoneNumber.getText().toString().replaceAll(" ", ""));
        addressFields.setEmail(mEtEmail.getText().toString());
        return addressFields;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
