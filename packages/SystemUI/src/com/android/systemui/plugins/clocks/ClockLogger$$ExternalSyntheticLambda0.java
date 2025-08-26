package com.android.systemui.plugins.clocks;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ClockLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ClockLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ClockLogger.animateDoze$lambda$16(logMessage);
            case 1:
                return ClockLogger.onDraw$lambda$6(logMessage);
            case 2:
                return ClockLogger.onMeasure$lambda$0(logMessage);
            case 3:
                return ClockLogger.onViewAdded$lambda$14(logMessage);
            case 4:
                return ClockLogger.onLayout$lambda$2(logMessage);
            case 5:
                return ClockLogger.updateAxes$lambda$12(logMessage);
            case 6:
                return ClockLogger.setVisibility$lambda$8(logMessage);
            case 7:
                return ClockLogger.setAlpha$lambda$10(logMessage);
            case 8:
                return ClockLogger.animateFidget$lambda$18(logMessage);
            default:
                return ClockLogger.onDraw$lambda$4(logMessage);
        }
    }
}
