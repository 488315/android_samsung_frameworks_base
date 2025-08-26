package com.android.systemui.plugins.aod;

import java.io.PrintWriter;

/* loaded from: classes2.dex */
public interface PluginAODBaseManager {
    default void dump(PrintWriter printWriter) {
    }

    default void onTrimMemory() {
    }
}
