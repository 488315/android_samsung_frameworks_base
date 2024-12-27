package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;

import com.android.server.LocalServices;

/* loaded from: classes.dex */
public class DisplayStateListenerWrapper extends ExternalService {
    private final DisplayManagerInternal.DisplayStateListener displayStateListener;

    public DisplayStateListenerWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.displayStateListener =
                new DisplayManagerInternal
                        .DisplayStateListener() { // from class:
                                                  // com.samsung.android.hardware.secinputdev.external.DisplayStateListenerWrapper.1
                    public void onStart(int stateLogical, int statePhysical, int displayType) {
                        DisplayStateListenerWrapper.this.listener.onDisplayStateChanged(
                                true, stateLogical, statePhysical, displayType);
                    }

                    public void onFinish(int stateLogical, int statePhysical, int displayType) {
                        DisplayStateListenerWrapper.this.listener.onDisplayStateChanged(
                                false, stateLogical, statePhysical, displayType);
                    }
                };
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        try {
            DisplayManagerInternal displayManagerInternal =
                    (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
            if (displayManagerInternal == null) {
                throw new Exception("DisplayManagerInternal is null");
            }
            displayManagerInternal.registerDisplayStateListener(this.displayStateListener);
            return "";
        } catch (Exception e) {
            throw e;
        }
    }
}
