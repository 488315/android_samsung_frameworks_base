package com.android.systemui.globalactions;

import android.R;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GlobalActionsItem extends LinearLayout {
    public GlobalActionsItem(Context context) {
        super(context);
    }

    public final boolean isTruncated() {
        Layout layout;
        TextView textView = (TextView) findViewById(R.id.message);
        return textView != null && (layout = textView.getLayout()) != null && layout.getLineCount() > 0 && layout.getEllipsisCount(layout.getLineCount() - 1) > 0;
    }

    public GlobalActionsItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GlobalActionsItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
