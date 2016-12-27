package com.papbg.focustester.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.papbg.focustester.R;

/**
 * Created by bradhawk on 12/27/2016.
 */

public abstract class BaseFragment extends Fragment {

    private BaseActivity parentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            parentActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity that will holding this Fragment must extends BaseActivity.");
        }
    }

    protected BaseActivity getParentActivity() {
        return parentActivity;
    }

    @LayoutRes
    protected abstract int getLayoutId();
}
