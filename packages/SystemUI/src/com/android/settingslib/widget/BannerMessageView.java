package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class BannerMessageView extends LinearLayout {
    public Rect mTouchTargetForDismissButton;

    public BannerMessageView(Context context) {
        super(context);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mTouchTargetForDismissButton != null) {
            return;
        }
        View viewFindViewById = findViewById(R.id.top_row);
        View viewFindViewById2 = findViewById(R.id.banner_dismiss_btn);
        if (viewFindViewById == null || viewFindViewById2 == null || viewFindViewById2.getVisibility() != 0) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.settingslib_preferred_minimum_touch_target);
        int width = viewFindViewById2.getWidth();
        int height = viewFindViewById2.getHeight();
        int i5 = width < dimensionPixelSize ? dimensionPixelSize - width : 0;
        int i6 = height < dimensionPixelSize ? dimensionPixelSize - height : 0;
        Rect rect = new Rect();
        viewFindViewById2.getHitRect(rect);
        Rect rect2 = new Rect();
        viewFindViewById.getHitRect(rect2);
        Rect rect3 = new Rect();
        this.mTouchTargetForDismissButton = rect3;
        int i7 = rect2.left + rect.left;
        rect3.left = i7;
        int i8 = rect2.left + rect.right;
        rect3.right = i8;
        int i9 = rect2.top + rect.top;
        rect3.top = i9;
        int i10 = rect2.top + rect.bottom;
        rect3.bottom = i10;
        rect3.left = i7 - (i5 % 2 == 1 ? (i5 / 2) + 1 : i5 / 2);
        rect3.top = i9 - (i6 % 2 == 1 ? (i6 / 2) + 1 : i6 / 2);
        rect3.right = (i5 / 2) + i8;
        rect3.bottom = (i6 / 2) + i10;
        setTouchDelegate(new TouchDelegate(this.mTouchTargetForDismissButton, viewFindViewById2));
    }

    public BannerMessageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
