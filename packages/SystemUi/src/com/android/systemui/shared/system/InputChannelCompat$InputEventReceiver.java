package com.android.systemui.shared.system;

import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InputChannelCompat$InputEventReceiver {
    public final C24941 mReceiver;

    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver$1] */
    public InputChannelCompat$InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer, final InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        this.mReceiver = new BatchedInputEventReceiver(this, inputChannel, looper, choreographer) { // from class: com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver.1
            public final void onInputEvent(InputEvent inputEvent) {
                inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
                finishInputEvent(inputEvent, true);
            }
        };
    }

    public final void dispose() {
        dispose();
    }
}
