package com.samsung.android.displayaiqe;

import android.content.Context;
import android.util.Slog;

/* loaded from: classes.dex */
public final class DisplayAiqeHalInstance {
    private static final String TAG = DisplayAiqeHalInstance.class.getSimpleName();

    public static DisplayAiqeHal getInstance(
            DisplayAiqeManagerService aiqeManager, Context context) {
        Slog.d(TAG, "Querying Display AIQE HAL");
        if (DisplayAiqeAidl.isServicePresent()) {
            Slog.d(TAG, "Display AIQE AIDL present");
            return new DisplayAiqeAidl(aiqeManager, context);
        }
        Slog.d(TAG, "Display AIQE AIDL is not present");
        return null;
    }
}
