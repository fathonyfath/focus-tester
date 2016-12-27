package com.papbg.focustester.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.papbg.focustester.R;
import com.papbg.focustester.fiturColorClick.ColorClickFragment;
import com.papbg.focustester.utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * Created by bradhawk on 12/27/2016.
 */

public class MenuFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_menuList)
    ListView menuList;

    private MultiAdapter listViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        listViewAdapter = SmartAdapter.empty().map(MenuItem.class, MenuItemHolder.class).adapter();

        menuList.setAdapter(listViewAdapter);

        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(R.mipmap.ic_launcher, "Menu 1"));
        menuItemList.add(new MenuItem(R.mipmap.ic_launcher, "Menu 2"));
        menuItemList.add(new MenuItem(R.mipmap.ic_launcher, "Menu 3"));
        menuItemList.add(new MenuItem(R.mipmap.ic_launcher, "About"));
        menuItemList.add(new MenuItem(R.mipmap.ic_launcher, "Exit"));

        listViewAdapter.setItems(menuItemList);
        listViewAdapter.notifyDataSetChanged();

        listViewAdapter.setViewEventListener(new ViewEventListener() {
            @Override
            public void onViewEvent(int actionId, Object obj, int position, View view) {
                if (actionId == MenuItemHolder.ACTION_CONTAINER_CLICK) {
                    switch (position) {
                        case 0:
                            getParentActivity().replaceFragment(new ColorClickFragment(), true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}
