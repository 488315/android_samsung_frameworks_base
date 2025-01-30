package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOImageView extends ImageView {
    public LSOImageView(Context context, LSOItemImage lSOItemImage) {
        super(context);
        Bitmap maxBitmap;
        int maxImageSize = LSOUtils.getMaxImageSize(context);
        int maxImageSize2 = LSOUtils.getMaxImageSize(context);
        if (lSOItemImage.isFieldUpdated(64)) {
            LSOAttributeSet attrs = lSOItemImage.getAttrs();
            maxImageSize = attrs.containsKey(LSOAttrConst.ATTR_MAX_WIDTH) ? attrs.getAsInteger(LSOAttrConst.ATTR_MAX_WIDTH).intValue() : maxImageSize;
            if (attrs.containsKey(LSOAttrConst.ATTR_MAX_HEIGHT)) {
                maxImageSize2 = attrs.getAsInteger(LSOAttrConst.ATTR_MAX_HEIGHT).intValue();
            }
        }
        if (lSOItemImage.isFieldUpdated(128) && (maxBitmap = LSOUtils.getMaxBitmap(lSOItemImage.imagePath, maxImageSize, maxImageSize2)) != null) {
            setImageBitmap(maxBitmap);
        }
        if (lSOItemImage.isFieldUpdated(512)) {
            setScaleType(lSOItemImage.getScaleType());
        } else {
            setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}
