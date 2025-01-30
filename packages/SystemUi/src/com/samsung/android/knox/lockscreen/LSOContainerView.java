package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import com.samsung.android.knox.lockscreen.LSOItemContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOContainerView extends LinearLayout {
    public final LSOItemContainer lsoContainer;
    public final Context mContext;

    public LSOContainerView(Context context, LSOItemContainer lSOItemContainer) {
        super(context);
        Drawable drawable;
        this.mContext = context;
        this.lsoContainer = lSOItemContainer;
        if (lSOItemContainer.isFieldUpdated(32)) {
            setGravity(lSOItemContainer.gravity);
        } else {
            setGravity(1);
        }
        if (lSOItemContainer.orientation == LSOItemContainer.ORIENTATION.VERTICAL) {
            setOrientation(1);
        }
        if (lSOItemContainer.isFieldUpdated(256) && (drawable = LSOUtils.getDrawable(lSOItemContainer.bgImagePath)) != null) {
            setBackgroundDrawable(drawable);
        }
        setClickable(false);
        setPadding(0, 0, 0, 0);
        addViews();
    }

    public final void addViews() {
        View view;
        int numItems = this.lsoContainer.getNumItems();
        for (int i = 0; i < numItems; i++) {
            LSOItemData item = this.lsoContainer.getItem(i);
            if (item != null && (view = LSOItemView.getView(this.mContext, item)) != null) {
                LinearLayout.LayoutParams layoutParams = getLayoutParams(item);
                if (layoutParams != null) {
                    addView(view, layoutParams);
                } else {
                    addView(view);
                }
            }
        }
    }

    public final LinearLayout.LayoutParams getLayoutParams(LSOItemData lSOItemData) {
        if (!lSOItemData.isFieldUpdated(2) && !lSOItemData.isFieldUpdated(4) && !lSOItemData.isFieldUpdated(8) && !lSOItemData.isFieldUpdated(32)) {
            return null;
        }
        float weight = lSOItemData.getWeight();
        int width = lSOItemData.isFieldUpdated(2) ? lSOItemData.getWidth() : -2;
        int height = lSOItemData.isFieldUpdated(4) ? lSOItemData.getHeight() : -2;
        LinearLayout.LayoutParams layoutParams = weight != 0.0f ? new LinearLayout.LayoutParams(width, height, weight) : new LinearLayout.LayoutParams(width, height);
        if (!lSOItemData.isFieldUpdated(32)) {
            return layoutParams;
        }
        layoutParams.gravity = lSOItemData.getGravity();
        return layoutParams;
    }
}
