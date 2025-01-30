package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyboardShortcutAppItemLayout extends RelativeLayout {
    public KeyboardShortcutAppItemLayout(Context context) {
        super(context);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 1073741824) {
            ImageView imageView = (ImageView) findViewById(R.id.keyboard_shortcuts_icon);
            TextView textView = (TextView) findViewById(R.id.keyboard_shortcuts_keyword);
            int size = View.MeasureSpec.getSize(i) - (getPaddingRight() + getPaddingLeft());
            if (imageView.getVisibility() == 0) {
                size -= imageView.getMeasuredWidth();
            }
            textView.setMaxWidth((int) Math.round(size * 0.7d));
        }
        super.onMeasure(i, i2);
    }

    public KeyboardShortcutAppItemLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
