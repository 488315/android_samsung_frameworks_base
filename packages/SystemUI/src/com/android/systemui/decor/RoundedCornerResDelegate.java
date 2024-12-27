package com.android.systemui.decor;

import android.graphics.drawable.Drawable;
import android.util.Size;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface RoundedCornerResDelegate {
    Drawable getBottomRoundedDrawable();

    Size getBottomRoundedSize();

    boolean getHasBottom();

    boolean getHasTop();

    Drawable getTopRoundedDrawable();

    Size getTopRoundedSize();

    void updateDisplayUniqueId(String str, Integer num);
}
