package com.google.android.setupdesign.util;

import android.content.Context;
import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThemeHelper {
    public static final Logger LOG = new Logger("ThemeHelper");

    private ThemeHelper() {
    }

    public static String colorIntToHex(int i, Context context) {
        return String.format("#%06X", Integer.valueOf(context.getResources().getColor(i) & 16777215));
    }
}
