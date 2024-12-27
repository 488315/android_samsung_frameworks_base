package com.android.systemui.decor;

import android.graphics.drawable.Drawable;
import android.util.Size;

public interface RoundedCornerResDelegate {
    Drawable getBottomRoundedDrawable();

    Size getBottomRoundedSize();

    boolean getHasBottom();

    boolean getHasTop();

    Drawable getTopRoundedDrawable();

    Size getTopRoundedSize();

    void updateDisplayUniqueId(String str, Integer num);
}
