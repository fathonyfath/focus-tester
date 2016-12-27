package com.papbg.focustester.utils;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.papbg.focustester.R;

/**
 * Created by bradhawk on 12/27/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public void replaceFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(getFragmentContainer(), fragment);
        if(addToBackstack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @IdRes protected abstract int getFragmentContainer();
}
