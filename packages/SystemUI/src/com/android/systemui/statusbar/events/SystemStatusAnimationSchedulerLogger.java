package com.android.systemui.statusbar.events;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;

/* loaded from: classes3.dex */
public final class SystemStatusAnimationSchedulerLogger {
    public final LogBuffer logBuffer;

    public SystemStatusAnimationSchedulerLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logUpdateEvent(StatusEvent statusEvent, SystemEventAnimationState systemEventAnimationState) {
        LogLevel logLevel = LogLevel.DEBUG;
        SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 = new SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = statusEvent.getClass().getSimpleName();
        logMessageImpl.int1 = statusEvent.getPriority();
        logMessageImpl.bool1 = statusEvent.getForceVisible();
        logMessageImpl.bool2 = statusEvent.getShowAnimation();
        logMessageImpl.str2 = systemEventAnimationState.name();
        logBuffer.commit(logMessageObtain);
    }
}
