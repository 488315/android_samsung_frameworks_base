package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.view.View;
import android.widget.Space;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOItemView {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static View getView(Context context, LSOItemData lSOItemData) {
        View space;
        View view;
        byte type = lSOItemData.getType();
        if (type == 1) {
            space = new Space(context);
        } else if (type == 2) {
            space = new LSOTextView(context, (LSOItemText) lSOItemData);
        } else if (type == 3) {
            space = new LSOImageView(context, (LSOItemImage) lSOItemData);
        } else {
            if (type != 4) {
                view = type != 5 ? null : LSOWidgetView.getWidget(context, (LSOItemWidget) lSOItemData);
                if (view != null) {
                    return null;
                }
                if (lSOItemData.isFieldUpdated(16)) {
                    view.setBackgroundColor(lSOItemData.getBgColor());
                }
                if (lSOItemData.isFieldUpdated(64)) {
                    LSOAttributeSet attrs = lSOItemData.getAttrs();
                    if (attrs.size() > 0 && attrs.containsKey(LSOAttrConst.ATTR_ALPHA)) {
                        if (attrs.getAsFloat(LSOAttrConst.ATTR_ALPHA) != null) {
                            view.setAlpha(attrs.getAsFloat(LSOAttrConst.ATTR_ALPHA).floatValue());
                        } else {
                            view.setAlpha(0.0f);
                        }
                    }
                }
                return view;
            }
            space = new LSOContainerView(context, (LSOItemContainer) lSOItemData);
        }
        view = space;
        if (view != null) {
        }
    }
}
