package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.hardware.input.InputManagerGlobal;
import android.os.Handler;
import android.os.SemUEventObserver;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

public class SemUEventObserverWrapper extends ExternalService {
    private static final int GESTURE_DONE = 1;
    private static final int GESTURE_ERROR = 2;
    private static final int GESTURE_NOTHING = 0;
    private static final String TAG = "SemInput:UEvent";
    private InputManagerGlobal inputManagerGlobal;
    private final SemUEventObserver ueventObserver;

    public SemUEventObserverWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.ueventObserver = new AnonymousClass1();
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        this.ueventObserver.startObserving("DEVPATH=/devices/virtual/sec/tsp");
        this.ueventObserver.startObserving("DEVPATH=/devices/virtual/sec/sec_epen");
        this.ueventObserver.startObserving("DEVPATH=/devices/virtual/sec/digital_hall");
        this.inputManagerGlobal = InputManagerGlobal.getInstance();
        if (this.inputManagerGlobal == null) {
            Log.d(TAG, "can not get InputManagerGlobal");
            return "";
        }
        Log.d(TAG, "get InputManagerGlobal");
        return "";
    }

    public int processGestureEvent(SemUEventObserver.SemUEvent event) {
        String pos;
        String gesture = event.get("GESTURE");
        if (gesture == null) {
            return 0;
        }
        try {
            if (!gesture.isEmpty()
                    && !gesture.isBlank()
                    && (pos = event.get("POS")) != null
                    && !pos.isEmpty()
                    && !pos.isBlank()) {
                String[] xy = pos.split(",");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                int type = Integer.parseInt(gesture);
                Log.d(TAG, "!@[sec_input] gesture event type: " + type + "," + x + "," + y);
                if (this.inputManagerGlobal != null) {
                    this.inputManagerGlobal.notifyQuickAccess(type, x, y);
                }
                return 1;
            }
            return 2;
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "gesture uevent", e);
            return 2;
        }
    }

    /* renamed from: com.samsung.android.hardware.secinputdev.external.SemUEventObserverWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends SemUEventObserver {
        AnonymousClass1() {}

        public void onSemUEvent(SemUEventObserver.SemUEvent event) {
            final String result;
            Log.d(SemUEventObserverWrapper.TAG, "onSemUEvent: " + event);
            if (SemUEventObserverWrapper.this.processGestureEvent(event) == 0
                    && (result = event.get("RESULT")) != null
                    && !result.isEmpty()
                    && !result.isBlank()) {
                SemUEventObserverWrapper.this.handler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.external.SemUEventObserverWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemUEventObserverWrapper.AnonymousClass1.this.lambda$onSemUEvent$0(
                                        result);
                            }
                        });
            }
        }

        public /* synthetic */ void lambda$onSemUEvent$0(String result) {
            SemUEventObserverWrapper.this.listener.onSemUEvent(result);
        }
    }
}
