package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.FrameLayout;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class DismissCircleView extends FrameLayout {
    public int mBackgroundResId;
    public int mIconSizeResId;
    public final ImageView mIconView;

    public DismissCircleView(Context context) {
        super(context);
        ImageView imageView = new ImageView(getContext());
        this.mIconView = imageView;
        addView(imageView);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        setBackground(getContext().getDrawable(this.mBackgroundResId));
        int dimensionPixelSize = getResources().getDimensionPixelSize(this.mIconSizeResId);
        this.mIconView.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 17));
    }

    public final void setup(int i, int i2, int i3) throws Resources.NotFoundException {
        this.mBackgroundResId = i;
        this.mIconSizeResId = i3;
        setBackground(getContext().getDrawable(i));
        this.mIconView.setImageDrawable(getContext().getDrawable(i2));
        int dimensionPixelSize = getResources().getDimensionPixelSize(this.mIconSizeResId);
        this.mIconView.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 17));
    }
}
