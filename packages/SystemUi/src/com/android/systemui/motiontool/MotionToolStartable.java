package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.systemui.CoreStartable;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionToolStartable implements CoreStartable {
    public final DdmHandleMotionTool ddmHandleMotionTool;

    public MotionToolStartable(DdmHandleMotionTool ddmHandleMotionTool) {
        this.ddmHandleMotionTool = ddmHandleMotionTool;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        DdmHandleMotionTool ddmHandleMotionTool = this.ddmHandleMotionTool;
        ddmHandleMotionTool.getClass();
        DdmServer.registerHandler(DdmHandleMotionTool.CHUNK_MOTO, ddmHandleMotionTool);
    }
}
