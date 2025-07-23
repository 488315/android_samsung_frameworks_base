package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import com.android.app.motiontool.MotionToolManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
