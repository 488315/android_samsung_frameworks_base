package com.android.server.wm;

import java.io.PrintWriter;

public interface IController {
    default void dumpLocked(PrintWriter printWriter) {}

    void initialize();

    default void setWindowManager(WindowManagerService windowManagerService) {}
}
