package com.android.server.wm;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface IController {
    default void dumpLocked(PrintWriter printWriter) {}

    void initialize();

    default void setWindowManager(WindowManagerService windowManagerService) {}
}
