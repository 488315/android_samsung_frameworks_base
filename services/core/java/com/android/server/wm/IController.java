package com.android.server.wm;

import java.io.PrintWriter;

/* loaded from: classes3.dex */
public interface IController {
    default void dumpLocked(PrintWriter printWriter, String str) {
    }

    void initialize();

    default void setWindowManager(WindowManagerService windowManagerService) {
    }
}
