package com.android.systemui.plugins;

import android.content.Context;

/* loaded from: classes2.dex */
public interface Plugin {
    @Deprecated
    default int getVersion() {
        return -1;
    }

    default void onDestroy() {
    }

    default void onCreate(Context context, Context context2) {
    }
}
