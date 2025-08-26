package com.android.wm.shell.splitscreen.tv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class TvSplitMenuView extends LinearLayout implements View.OnClickListener {
    public TvSplitMenuView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            keyEvent.getKeyCode();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.tv_split_main_menu_focus_button).setOnClickListener(this);
        findViewById(R.id.tv_split_main_menu_close_button).setOnClickListener(this);
        findViewById(R.id.tv_split_side_menu_focus_button).setOnClickListener(this);
        findViewById(R.id.tv_split_side_menu_close_button).setOnClickListener(this);
        findViewById(R.id.tv_split_menu_swap_stages).setOnClickListener(this);
    }

    public TvSplitMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TvSplitMenuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
    }
}
