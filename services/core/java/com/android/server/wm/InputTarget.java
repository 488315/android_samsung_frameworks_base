package com.android.server.wm;

import android.os.IBinder;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
