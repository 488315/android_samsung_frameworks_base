package com.android.systemui.unfold.system;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceStateManagerFoldProvider implements FoldProvider {
    public final Map callbacks = new HashMap();
    public final Context context;
    public final DeviceStateManager deviceStateManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(DeviceStateManagerFoldProvider deviceStateManagerFoldProvider, Context context, final FoldProvider.FoldCallback foldCallback) {
            super(context, new Consumer() { // from class: com.android.systemui.unfold.system.DeviceStateManagerFoldProvider.FoldStateListener.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FoldProvider.FoldCallback.this.onFoldUpdated(((Boolean) obj).booleanValue());
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
        ((HashMap) this.callbacks).put(foldCallback, foldStateListener);
    }
}
