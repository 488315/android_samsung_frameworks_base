package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory implements Provider {
    public final Provider motionToolManagerProvider;

    public MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory(Provider provider) {
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
