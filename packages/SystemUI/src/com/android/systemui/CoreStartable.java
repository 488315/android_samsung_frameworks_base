package com.android.systemui;

import java.io.PrintWriter;

public interface CoreStartable extends Dumpable {
    default boolean isDumpCritical() {
        return true;
    }

    void start();

    default void onBootCompleted() {
    }

    default void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.Dumpable
    default void dump(PrintWriter printWriter, String[] strArr) {
    }
}
