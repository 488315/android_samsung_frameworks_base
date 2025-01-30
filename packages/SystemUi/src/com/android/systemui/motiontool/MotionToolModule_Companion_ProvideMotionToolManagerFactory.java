package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import com.android.app.motiontool.MotionToolManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionToolModule_Companion_ProvideMotionToolManagerFactory implements Provider {
    public final Provider windowManagerGlobalProvider;

    public MotionToolModule_Companion_ProvideMotionToolManagerFactory(Provider provider) {
        this.windowManagerGlobalProvider = provider;
    }

    public static MotionToolManager provideMotionToolManager(WindowManagerGlobal windowManagerGlobal) {
        MotionToolManager motionToolManager;
        MotionToolModule.Companion.getClass();
        synchronized (MotionToolManager.Companion) {
            motionToolManager = MotionToolManager.INSTANCE;
            if (motionToolManager == null) {
                motionToolManager = new MotionToolManager(windowManagerGlobal, null);
                MotionToolManager.INSTANCE = motionToolManager;
            }
        }
        return motionToolManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMotionToolManager((WindowManagerGlobal) this.windowManagerGlobalProvider.get());
    }
}
