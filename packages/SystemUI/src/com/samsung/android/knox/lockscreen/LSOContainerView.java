package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import com.samsung.android.knox.lockscreen.LSOItemContainer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOContainerView extends LinearLayout {
    public final LSOItemContainer lsoContainer;
    public final Context mContext;

    public LSOContainerView(Context context, LSOItemContainer lSOItemContainer) {
        super(context);
        Drawable drawable;
        this.mContext = context;
        this.lsoContainer = lSOItemContainer;
        if (lSOItemContainer.isFieldUpdated(32)) {
            setGravity(lSOItemContainer.getGravity());
        } else {
            setGravity(1);
        }
        if (lSOItemContainer.getOrientation() == LSOItemContainer.ORIENTATION.VERTICAL) {
            setOrientation(1);
        }
        if (lSOItemContainer.isFieldUpdated(256) && (drawable = LSOUtils.getDrawable(lSOItemContainer.getBGImagePath())) != null) {
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
        if (lSOItemData.isFieldUpdated(32)) {
            layoutParams.gravity = lSOItemData.getGravity();
        }
        return layoutParams;
    }
}
