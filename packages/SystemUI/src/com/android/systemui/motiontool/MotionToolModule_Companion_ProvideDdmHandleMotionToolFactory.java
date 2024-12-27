package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import dagger.internal.Provider;

public final class MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory implements Provider {
    public final javax.inject.Provider motionToolManagerProvider;

    public MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory(javax.inject.Provider provider) {
        this.motionToolManagerProvider = provider;
    }

    public static DdmHandleMotionTool provideDdmHandleMotionTool(MotionToolManager motionToolManager) {
        DdmHandleMotionTool ddmHandleMotionTool;
        MotionToolModule.Companion.getClass();
        synchronized (DdmHandleMotionTool.Companion) {
            ddmHandleMotionTool = DdmHandleMotionTool.INSTANCE;
            if (ddmHandleMotionTool == null) {
                ddmHandleMotionTool = new DdmHandleMotionTool(motionToolManager, null);
                DdmHandleMotionTool.INSTANCE = ddmHandleMotionTool;
            }
        }
        return ddmHandleMotionTool;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDdmHandleMotionTool((MotionToolManager) this.motionToolManagerProvider.get());
    }
}
