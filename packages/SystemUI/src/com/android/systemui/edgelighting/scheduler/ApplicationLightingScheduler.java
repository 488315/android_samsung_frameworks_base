package com.android.systemui.edgelighting.scheduler;

import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import java.util.LinkedHashMap;

public final class ApplicationLightingScheduler {
    public LightingScheduleInfo mCurrentLightingScheduleInfo;
    public final LinkedHashMap mLinkedInfo = new LinkedHashMap();
    public EdgeLightingScheduler.AnonymousClass3 mListener;
}
