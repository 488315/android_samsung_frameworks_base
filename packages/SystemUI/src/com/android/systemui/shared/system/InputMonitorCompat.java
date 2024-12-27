package com.android.systemui.shared.system;

import android.hardware.input.InputManagerGlobal;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputMonitor;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InputMonitorCompat {
    public final InputMonitor mInputMonitor;
    public final String mName;

    public InputMonitorCompat(String str, int i) {
        String str2 = str + "-disp" + i;
        this.mName = str2;
        this.mInputMonitor = InputManagerGlobal.getInstance().monitorGestureInput(str, i);
        Trace.instant(4L, "InputMonitorCompat-" + str2 + " created");
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Input monitor (", str2, ") created", "InputMonitorCompat");
    }

    public final void dispose() {
        this.mInputMonitor.dispose();
        StringBuilder sb = new StringBuilder("InputMonitorCompat-");
        String str = this.mName;
        sb.append(str);
        sb.append(" disposed");
        Trace.instant(4L, sb.toString());
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Input monitor (", str, ") disposed", "InputMonitorCompat");
    }

    public final InputChannelCompat$InputEventReceiver getInputReceiver(Looper looper, Choreographer choreographer, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        StringBuilder sb = new StringBuilder("InputMonitorCompat-");
        String str = this.mName;
        sb.append(str);
        sb.append(" receiver created");
        Trace.instant(4L, sb.toString());
        Log.d("InputMonitorCompat", "Input event receiver for monitor (" + str + ") created");
        return new InputChannelCompat$InputEventReceiver(this.mName, this.mInputMonitor.getInputChannel(), looper, choreographer, inputChannelCompat$InputEventListener);
    }
}
