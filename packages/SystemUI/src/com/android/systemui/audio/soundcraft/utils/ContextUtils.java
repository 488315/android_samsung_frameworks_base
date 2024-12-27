package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ContextUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ContextUtils();
    }

    private ContextUtils() {
    }

    public static final int getDimenInt(int i, Context context) {
        return context.getResources().getDimensionPixelSize(i);
    }
}
