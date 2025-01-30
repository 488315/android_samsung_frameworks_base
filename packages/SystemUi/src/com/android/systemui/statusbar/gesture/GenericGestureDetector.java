package com.android.systemui.statusbar.gesture;

import android.os.Looper;
import android.view.Choreographer;
import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class GenericGestureDetector {
    public final Map callbacks = new LinkedHashMap();
    public final int displayId;
    public InputMonitorCompat inputMonitor;
    public InputChannelCompat$InputEventReceiver inputReceiver;
    public final String tag;

    public GenericGestureDetector(String str, int i) {
        this.tag = str;
        this.displayId = i;
    }

    public abstract void onInputEvent(InputEvent inputEvent);

    public final void removeOnGestureDetectedCallback(String str) {
        Map map = this.callbacks;
        map.remove(str);
        if (map.isEmpty()) {
            mo201xfadba4da();
        }
    }

    /* renamed from: startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public void mo200x8843713a() {
        mo201xfadba4da();
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat(this.tag, this.displayId);
        this.inputReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorCompat.mInputMonitor.getInputChannel(), Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.statusbar.gesture.GenericGestureDetector$startGestureListening$1$1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                GenericGestureDetector.this.onInputEvent(inputEvent);
            }
        });
        this.inputMonitor = inputMonitorCompat;
    }

    /* renamed from: stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public void mo201xfadba4da() {
        InputMonitorCompat inputMonitorCompat = this.inputMonitor;
        if (inputMonitorCompat != null) {
            this.inputMonitor = null;
            inputMonitorCompat.mInputMonitor.dispose();
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            this.inputReceiver = null;
            inputChannelCompat$InputEventReceiver.dispose();
        }
    }
}
