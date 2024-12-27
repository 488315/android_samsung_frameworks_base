package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.Log;
import android.view.Display;

import java.util.function.Consumer;

/* loaded from: classes.dex */
public class FoldStateListenerWrapper extends ExternalService {
    private static final String DISPLAY_CATEGORY_BUILTIN =
            "com.samsung.android.hardware.display.category.BUILTIN";
    private static final String TAG = "SemInput:FoldState";
    private final DeviceStateManager.FoldStateListener foldStateListener;

    public FoldStateListenerWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.foldStateListener =
                new DeviceStateManager.FoldStateListener(
                        this.context,
                        new Consumer() { // from class:
                                         // com.samsung.android.hardware.secinputdev.external.FoldStateListenerWrapper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                FoldStateListenerWrapper.this.lambda$new$0((Boolean) obj);
                            }
                        });
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        DisplayManager displayManager = (DisplayManager) this.context.getSystemService("display");
        if (displayManager == null) {
            throw new Exception("DisplayManager is null");
        }
        Display[] displays = displayManager.getDisplays(DISPLAY_CATEGORY_BUILTIN);
        if (displays == null) {
            throw new Exception("Display is null");
        }
        Log.d(TAG, "Builtin displays: " + displays.length);
        if (displays.length < 2) {
            return "Builtin displays are under 2(" + displays.length + ")";
        }
        DeviceStateManager deviceStateManager =
                (DeviceStateManager) this.context.getSystemService(DeviceStateManager.class);
        if (deviceStateManager == null) {
            throw new Exception("DeviceStateManager is null");
        }
        deviceStateManager.registerCallback(
                new HandlerExecutor(this.handler), this.foldStateListener);
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Boolean folded) {
        this.listener.onFoldStateChanged(folded.booleanValue());
    }
}
