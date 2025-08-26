package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.view.View;
import android.widget.Space;

/* loaded from: classes4.dex */
public class LSOItemView {
    /* JADX WARN: Removed duplicated region for block: B:20:0x0042 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static View getView(Context context, LSOItemData lSOItemData) {
        View space;
        View widget;
        byte type = lSOItemData.getType();
        if (type == 1) {
            space = new Space(context);
        } else if (type == 2) {
            space = new LSOTextView(context, (LSOItemText) lSOItemData);
        } else if (type == 3) {
            space = new LSOImageView(context, (LSOItemImage) lSOItemData);
        } else {
            if (type != 4) {
                widget = type != 5 ? null : LSOWidgetView.getWidget(context, (LSOItemWidget) lSOItemData);
                if (widget != null) {
                    return null;
                }
                if (lSOItemData.isFieldUpdated(16)) {
                    widget.setBackgroundColor(lSOItemData.getBgColor());
                }
                if (lSOItemData.isFieldUpdated(64)) {
                    LSOAttributeSet attrs = lSOItemData.getAttrs();
                    if (attrs.size() > 0 && attrs.containsKey(LSOAttrConst.ATTR_ALPHA)) {
                        if (attrs.getAsFloat(LSOAttrConst.ATTR_ALPHA) != null) {
                            widget.setAlpha(attrs.getAsFloat(LSOAttrConst.ATTR_ALPHA).floatValue());
                            return widget;
                        }
                        widget.setAlpha(0.0f);
                    }
                }
                return widget;
            }
            space = new LSOContainerView(context, (LSOItemContainer) lSOItemData);
        }
        widget = space;
        if (widget != null) {
        }
    }
}
