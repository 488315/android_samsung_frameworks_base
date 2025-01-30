package com.android.systemui;

import android.content.res.Configuration;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface CoreStartable extends Dumpable {
    void start();

    default void onConfigurationChanged(Configuration configuration) {
    }

    default void onTrimMemory(int i) {
    }

    default void onBootCompleted() {
    }

    @Override // com.android.systemui.Dumpable
    default void dump(PrintWriter printWriter, String[] strArr) {
    }
}
