package com.android.systemui.decor;

import android.graphics.drawable.Drawable;
import android.util.Size;
import com.android.systemui.Dumpable;

/* loaded from: classes2.dex */
public interface RoundedCornerResDelegate extends Dumpable {
    Drawable getBottomRoundedDrawable();

    Size getBottomRoundedSize();

    boolean getHasBottom();

    boolean getHasTop();

    Drawable getTopRoundedDrawable();

    Size getTopRoundedSize();

    void setPhysicalPixelDisplaySizeRatio(float f);

    void updateDisplayUniqueId(String str, Integer num);
}
