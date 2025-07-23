package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.systemui.CoreStartable;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
