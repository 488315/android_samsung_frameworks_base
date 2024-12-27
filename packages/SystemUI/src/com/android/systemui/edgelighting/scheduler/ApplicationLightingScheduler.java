package com.android.systemui.edgelighting.scheduler;

import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import java.util.LinkedHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ApplicationLightingScheduler {
    public LightingScheduleInfo mCurrentLightingScheduleInfo;
    public final LinkedHashMap mLinkedInfo = new LinkedHashMap();
    public EdgeLightingScheduler.AnonymousClass3 mListener;
}
