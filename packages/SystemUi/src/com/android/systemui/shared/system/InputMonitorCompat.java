package com.android.systemui.shared.system;

import android.hardware.input.InputManagerGlobal;
import android.view.InputMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InputMonitorCompat {
    public final InputMonitor mInputMonitor;

    public InputMonitorCompat(String str, int i) {
        this.mInputMonitor = InputManagerGlobal.getInstance().monitorGestureInput(str, i);
    }
}
