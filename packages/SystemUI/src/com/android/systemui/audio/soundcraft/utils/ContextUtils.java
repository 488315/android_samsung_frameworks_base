package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;

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
