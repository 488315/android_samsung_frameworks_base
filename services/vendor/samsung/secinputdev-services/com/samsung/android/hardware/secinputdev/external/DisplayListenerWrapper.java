package com.samsung.android.hardware.secinputdev.external;

import android.R;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;
import android.view.DisplayInfo;

public class DisplayListenerWrapper extends ExternalService {
    private int currentRotation;
    private final DisplayInfo displayInfo;
    private DisplayManager.DisplayListener displayListener;
    private DisplayManager displayManager;
    private final boolean isReverseDefaultRotation;

    public DisplayListenerWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.displayInfo = new DisplayInfo();
        this.currentRotation = -1;
        this.displayListener = new AnonymousClass1();
        this.isReverseDefaultRotation =
                context.getResources().getBoolean(R.bool.config_shortPressEarlyOnStemPrimary);
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        this.displayManager = (DisplayManager) this.context.getSystemService("display");
        if (this.displayManager == null) {
            throw new Exception("DisplayManager is null");
        }
        this.displayManager.registerDisplayListener(this.displayListener, null);
        return "";
    }

    /* renamed from: com.samsung.android.hardware.secinputdev.external.DisplayListenerWrapper$1, reason: invalid class name */
    class AnonymousClass1 implements DisplayManager.DisplayListener {
        AnonymousClass1() {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            Display display;
            if (DisplayListenerWrapper.this.displayManager == null
                    || (display = DisplayListenerWrapper.this.displayManager.getDisplay(i))
                            == null) {
                return;
            }
            display.getDisplayInfo(DisplayListenerWrapper.this.displayInfo);
            final int displayRotation =
                    DisplayListenerWrapper.this.isReverseDefaultRotation
                            ? (DisplayListenerWrapper.this.displayInfo.rotation + 1) % 4
                            : DisplayListenerWrapper.this.displayInfo.rotation;
            if (DisplayListenerWrapper.this.currentRotation != displayRotation) {
                DisplayListenerWrapper.this.currentRotation = displayRotation;
                DisplayListenerWrapper.this.handler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.external.DisplayListenerWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayListenerWrapper.AnonymousClass1.this
                                        .lambda$onDisplayChanged$0(displayRotation);
                            }
                        });
            }
        }

        public /* synthetic */ void lambda$onDisplayChanged$0(int displayRotation) {
            DisplayListenerWrapper.this.listener.onDisplayChanged(displayRotation);
        }
    }
}
