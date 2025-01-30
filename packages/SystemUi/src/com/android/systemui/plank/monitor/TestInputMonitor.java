package com.android.systemui.plank.monitor;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TestInputMonitor {
    public static final String tag;
    public final Context mContext;
    public HandlerThread mHandlerThread;
    public TestInputHandler mInputHandler;
    public InputMonitor mInputMonitor;
    public TestInputEventReceiver mTestInputEventReceiver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface EventHandler {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TestInputEventReceiver extends InputEventReceiver {
        public final EventHandler eventHandler;

        public TestInputEventReceiver(TestInputMonitor testInputMonitor, EventHandler eventHandler, InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            this.eventHandler = eventHandler;
        }

        public final void onInputEvent(InputEvent inputEvent) {
            try {
                if (MotionEvent.class.isInstance(inputEvent)) {
                    ((TestInputHandler) this.eventHandler).onEventHandler((MotionEvent) inputEvent);
                }
            } finally {
                finishInputEvent(inputEvent, true);
            }
        }
    }

    static {
        new Companion(null);
        tag = "TestInputMonitor";
    }

    public TestInputMonitor(Context context) {
        this.mContext = context;
    }
}
