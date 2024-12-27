package com.android.systemui.shared.system;

import android.os.Looper;
import android.os.Trace;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;

public final class InputChannelCompat$InputEventReceiver {
    public final String mName;
    public final AnonymousClass1 mReceiver;

    @Deprecated
    public InputChannelCompat$InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        this("unknown", inputChannel, looper, choreographer, inputChannelCompat$InputEventListener);
    }

    public final void dispose() {
        dispose();
        StringBuilder sb = new StringBuilder("InputMonitorCompat-");
        String str = this.mName;
        sb.append(str);
        sb.append(" receiver disposed");
        Trace.instant(4L, sb.toString());
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Input event receiver for monitor (", str, ") disposed", "InputMonitorCompat");
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver$1] */
    public InputChannelCompat$InputEventReceiver(String str, InputChannel inputChannel, Looper looper, Choreographer choreographer, final InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        this.mName = str;
        this.mReceiver = new BatchedInputEventReceiver(this, inputChannel, looper, choreographer) { // from class: com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver.1
            public final void onInputEvent(InputEvent inputEvent) {
                inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
                finishInputEvent(inputEvent, true);
            }
        };
    }
}
