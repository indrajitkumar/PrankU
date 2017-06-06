package com.amcoder.pranku.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.Constant;
import com.amcoder.pranku.fragment.BaseFragment;
import com.philips.cdp.uikit.UiKitActivity;
import com.philips.cdp.uikit.drawable.VectorDrawable;

/**
 * Created by philips on 6/6/17.
 */

public class BaseActivity extends UiKitActivity implements ActionBarListener {
    private final int DEFAULT_THEME = R.style.Theme_Philips_DarkOrange_Gradient_WhiteBackground;
    private TextView mTitleTextView;
    private TextView mCountText;
    private ImageView mBackImage;
    private FrameLayout mCartContainer;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        addActionBar();
        setContentView(R.layout.prank_activity);
        setTitle(mTitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initTheme() {
        int themeIndex = getIntent().getIntExtra(Constant.KEY_ACTIVITY_THEME, DEFAULT_THEME);
        if (themeIndex <= 0) {
            themeIndex = DEFAULT_THEME;
        }
        setTheme(themeIndex);
    }

    public void addFragment(BaseFragment newFragment,
                            String newFragmentTag) {
        newFragment.setActionBarListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_mainFragmentContainer, newFragment, newFragmentTag);
        transaction.addToBackStack(newFragmentTag);
        transaction.commit();
    }

    private void addActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar == null) return;

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);

        View mCustomView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.action_bar, null);
        FrameLayout frameLayout = (FrameLayout) mCustomView.findViewById(R.id.iap_header_back_button);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        mBackImage = (ImageView) mCustomView.findViewById(R.id.iap_iv_header_back_button);
        Drawable mBackDrawable = VectorDrawable.create(getApplicationContext(), R.drawable.iap_back_arrow);
        mBackImage.setBackground(mBackDrawable);

        mTitleTextView = (TextView) mCustomView.findViewById(R.id.iap_actionBar_headerTitle_lebel);
        setTitle(getString(R.string.app_name));

        mCartContainer = (FrameLayout) mCustomView.findViewById(R.id.cart_container);
        ImageView mCartIcon = (ImageView) mCustomView.findViewById(R.id.cart_icon);
        Drawable mCartIconDrawable = VectorDrawable.create(getApplicationContext(), R.drawable.iap_shopping_cart);
        mCartIcon.setBackground(mCartIconDrawable);
//        mCartIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showFragment(ShoppingCartFragment.TAG);
//            }
//        });

        mCountText = (TextView) mCustomView.findViewById(R.id.item_count);

        mActionBar.setCustomView(mCustomView, params);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle = title.toString();
        mTitleTextView.setText(title);
    }

    @Override
    public void onBackPressed() {
        //Utility.hideKeypad(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFrag = fragmentManager.findFragmentById(R.id.fl_mainFragmentContainer);
        boolean backState = false;
        if (currentFrag != null && currentFrag instanceof BackEventListener) {
            backState = ((BackEventListener) currentFrag).handleBackEvent();
        }
        if (!backState) {
            super.onBackPressed();
        }
    }

    @Override
    public void updateActionBar(int resourceId, boolean visibility) {
        mTitleTextView.setText(getString(resourceId));
        if (visibility) {
            mBackImage.setVisibility(View.VISIBLE);
        } else {
            mBackImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateActionBar(String resourceString, boolean visibility) {
        mTitleTextView.setText(resourceString);
        if (visibility) {
            mBackImage.setVisibility(View.VISIBLE);
        } else {
            mBackImage.setVisibility(View.GONE);
        }
    }
}
