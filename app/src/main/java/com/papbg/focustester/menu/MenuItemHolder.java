package com.papbg.focustester.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papbg.focustester.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * Created by bradhawk on 12/27/2016.
 */

public class MenuItemHolder extends BindableFrameLayout<MenuItem> {

    public static final int ACTION_CONTAINER_CLICK = 1;

    @BindView(R.id.menu_container)
    RelativeLayout container;

    @BindView(R.id.menu_text)
    TextView textView;

    public MenuItemHolder(Context context) {
        super(context);
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
        ButterKnife.bind(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_menu;
    }

    @Override
    public void bind(MenuItem menuItem) {
        textView.setText(menuItem.getMenu());

        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemAction(ACTION_CONTAINER_CLICK);
            }
        });
    }
}
