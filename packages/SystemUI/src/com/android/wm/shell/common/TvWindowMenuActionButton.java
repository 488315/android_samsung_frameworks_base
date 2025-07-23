package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TvWindowMenuActionButton extends RelativeLayout {
    public final View mButtonBackgroundView;

    public TvWindowMenuActionButton(Context context) {
        this(context, null, 0, 0);
    }

    @Override // android.view.View
    public final String toString() {
        return getContentDescription() == null ? "TvWindowMenuActionButton" : getContentDescription().toString();
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.tv_window_menu_action_button, this);
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        View findViewById = findViewById(R.id.background);
        this.mButtonBackgroundView = findViewById;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.src, android.R.attr.text}, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 != 0) {
            setContentDescription(getContext().getString(resourceId2));
        }
        obtainStyledAttributes.recycle();
        imageView.setImageTintList(getResources().getColorStateList(R.color.tv_window_menu_icon));
        findViewById.setBackgroundTintList(getResources().getColorStateList(R.color.tv_window_menu_icon_bg));
    }
}
