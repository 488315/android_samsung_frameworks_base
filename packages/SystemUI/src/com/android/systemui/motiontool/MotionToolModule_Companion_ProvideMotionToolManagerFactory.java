package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import com.android.app.motiontool.MotionToolManager;
import dagger.internal.Provider;

public final class MotionToolModule_Companion_ProvideMotionToolManagerFactory implements Provider {
    public final javax.inject.Provider windowManagerGlobalProvider;

    public MotionToolModule_Companion_ProvideMotionToolManagerFactory(javax.inject.Provider provider) {
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
