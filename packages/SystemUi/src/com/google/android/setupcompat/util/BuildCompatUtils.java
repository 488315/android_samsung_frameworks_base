package com.google.android.setupcompat.util;

import android.os.Build;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BuildCompatUtils {
    private BuildCompatUtils() {
    }

    public static boolean isAtLeastU() {
        String str = Build.VERSION.CODENAME;
        if (str.equals("REL")) {
            return true;
        }
        return !str.equals("REL") && str.compareTo("UpsideDownCake") >= 0;
    }
}
