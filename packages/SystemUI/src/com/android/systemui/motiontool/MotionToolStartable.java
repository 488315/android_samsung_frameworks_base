package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.systemui.CoreStartable;
import org.apache.harmony.dalvik.ddmc.DdmServer;

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
