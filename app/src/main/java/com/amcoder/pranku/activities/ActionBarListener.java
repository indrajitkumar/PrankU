package com.amcoder.pranku.activities;

import android.support.annotation.StringRes;

/**
 * Created by philips on 6/6/17.
 */

public interface ActionBarListener {
    void updateActionBar(@StringRes int var1, boolean var2);

    void updateActionBar(String var1, boolean var2);
}
