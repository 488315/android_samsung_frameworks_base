package com.android.server.wm;

import android.os.IBinder;
import android.util.proto.ProtoOutputStream;

public interface InputTarget {
    boolean canScreenshotIme();

    void dumpProto(int i, ProtoOutputStream protoOutputStream);

    ActivityRecord getActivityRecord();

    DisplayContent getDisplayContent();

    int getDisplayId();

    InsetsControlTarget getImeControlTarget();

    int getPid();

    WindowState getWindowState();

    IBinder getWindowToken();

    void handleTapOutsideFocusInsideSelf();

    void handleTapOutsideFocusOutsideSelf();

    boolean isInputMethodClientFocus(int i, int i2);

    boolean receiveFocusFromTapOutside();

    boolean shouldControlIme();
}
