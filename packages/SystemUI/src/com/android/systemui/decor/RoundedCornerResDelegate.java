package com.android.systemui.decor;

import android.graphics.drawable.Drawable;
import android.util.Size;
import com.android.systemui.Dumpable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
