package com.android.systemui.plugins.aod;

import java.io.PrintWriter;

public interface PluginAODBaseManager {
    default void onTrimMemory() {
    }

    default void dump(PrintWriter printWriter) {
    }
}
