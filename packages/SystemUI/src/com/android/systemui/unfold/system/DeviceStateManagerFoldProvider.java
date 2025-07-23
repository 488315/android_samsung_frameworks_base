package com.android.systemui.unfold.system;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceStateManagerFoldProvider implements FoldProvider {
    public final ConcurrentHashMap callbacks = new ConcurrentHashMap();
    public final Context context;
    public final DeviceStateManager deviceStateManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(DeviceStateManagerFoldProvider deviceStateManagerFoldProvider, Context context, final FoldProvider.FoldCallback foldCallback) {
            super(context, new Consumer() { // from class: com.android.systemui.unfold.system.DeviceStateManagerFoldProvider.FoldStateListener.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    FoldProvider.FoldCallback foldCallback2 = FoldProvider.FoldCallback.this;
                    bool.getClass();
                    foldCallback2.onFoldUpdated(bool.booleanValue());
                }
            });
        }
    }

    public DeviceStateManagerFoldProvider(DeviceStateManager deviceStateManager, Context context) {
        this.deviceStateManager = deviceStateManager;
        this.context = context;
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void registerCallback(FoldProvider.FoldCallback foldCallback, Executor executor) {
        FoldStateListener foldStateListener = new FoldStateListener(this, this.context, foldCallback);
        this.deviceStateManager.registerCallback(executor, foldStateListener);
        this.callbacks.put(foldCallback, foldStateListener);
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void unregisterCallback(FoldProvider.FoldCallback foldCallback) {
        DeviceStateManager.DeviceStateCallback deviceStateCallback = (DeviceStateManager.DeviceStateCallback) this.callbacks.remove(foldCallback);
        if (deviceStateCallback != null) {
            this.deviceStateManager.unregisterCallback(deviceStateCallback);
        }
    }
}
