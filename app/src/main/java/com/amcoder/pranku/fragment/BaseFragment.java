package com.amcoder.pranku.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.amcoder.pranku.activities.ActionBarListener;

/**
 * Created by philips on 6/1/17.
 */

public abstract class BaseFragment extends Fragment {
    private Context mContext;
    private ActionBarListener mActionbarUpdateListener;
    String mTitle = "";

    public enum AnimationType {
        NONE
    }

    public void setActionBarListener(ActionBarListener actionBarListener) {
        mActionbarUpdateListener = actionBarListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void setTitleAndBackButtonVisibility(int resourceId, boolean isVisible) {
        mTitle = getString(resourceId);
        if (mActionbarUpdateListener != null)
            mActionbarUpdateListener.updateActionBar(resourceId, isVisible);
    }


    protected void setTitleAndBackButtonVisibility(String title, boolean isVisible) {
        mTitle = title;
        if (mActionbarUpdateListener != null)
            mActionbarUpdateListener.updateActionBar(title, isVisible);
    }

//    public void addFragment(BaseFragment newFragment,
//                            String newFragmentTag) {
//        if (getActivity() != null && !getActivity().isFinishing()) {
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//            transaction.replace(getId(), newFragment, newFragmentTag);
//            transaction.addToBackStack(newFragmentTag);
//            transaction.commitAllowingStateLoss();
//        }
//    }

    public void showFragment(String fragmentTag) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().getSupportFragmentManager().popBackStackImmediate(fragmentTag, 0);
        }
    }

    public boolean moveToPreviousFragment() {
        return getFragmentManager().popBackStackImmediate();
    }


}
