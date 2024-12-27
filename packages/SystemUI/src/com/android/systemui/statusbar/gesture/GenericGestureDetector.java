package com.android.systemui.statusbar.gesture;

import android.os.Looper;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final void addOnGestureDetectedCallback(Function1 function1, String str) {
        boolean isEmpty = this.callbacks.isEmpty();
        this.callbacks.put(str, function1);
        if (isEmpty) {
            startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        }
    }

    public final void onGestureDetected$frameworks__base__packages__SystemUI__android_common__SystemUI_core(MotionEvent motionEvent) {
        Iterator it = ((LinkedHashMap) this.callbacks).values().iterator();
        while (it.hasNext()) {
            ((Function1) it.next()).invoke(motionEvent);
        }
    }

    public abstract void onInputEvent(InputEvent inputEvent);

    public final void removeOnGestureDetectedCallback(String str) {
        this.callbacks.remove(str);
        if (this.callbacks.isEmpty()) {
            stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        }
    }

    public void startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat(this.tag, this.displayId);
        this.inputReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.statusbar.gesture.GenericGestureDetector$startGestureListening$1$1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                GenericGestureDetector.this.onInputEvent(inputEvent);
            }
        });
        this.inputMonitor = inputMonitorCompat;
    }

    public void stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        InputMonitorCompat inputMonitorCompat = this.inputMonitor;
        if (inputMonitorCompat != null) {
            this.inputMonitor = null;
            inputMonitorCompat.dispose();
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            this.inputReceiver = null;
            inputChannelCompat$InputEventReceiver.dispose();
        }
    }
}
