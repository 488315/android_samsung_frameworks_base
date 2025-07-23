package com.android.wm.shell.bubbles.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.shared.TypefaceUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleBarMenuItemView extends LinearLayout {
    public ImageView mImageView;
    public TextView mTextView;

    public BubbleBarMenuItemView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.bubble_bar_menu_item_icon);
        this.mTextView = (TextView) findViewById(R.id.bubble_bar_menu_item_title);
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        TypefaceUtils.setTypeface();
    }

    public BubbleBarMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleBarMenuItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubbleBarMenuItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
