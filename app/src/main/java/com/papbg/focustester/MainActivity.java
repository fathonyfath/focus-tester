package com.papbg.focustester;

import android.os.Bundle;

import com.papbg.focustester.menu.MenuFragment;
import com.papbg.focustester.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new MenuFragment(), false);
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.main_container;
    }

}
