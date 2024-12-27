package com.android.systemui.plugins.aod;

import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginAODBaseManager {
    default void onTrimMemory() {
    }

    default void dump(PrintWriter printWriter) {
    }
}
