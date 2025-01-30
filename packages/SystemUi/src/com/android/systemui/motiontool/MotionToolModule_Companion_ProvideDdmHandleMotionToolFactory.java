package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
