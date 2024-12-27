package com.android.systemui;

import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
