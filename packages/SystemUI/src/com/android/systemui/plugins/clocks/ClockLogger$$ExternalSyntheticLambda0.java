package com.android.systemui.plugins.clocks;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ClockLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ClockLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String animateDoze$lambda$16;
        String onDraw$lambda$6;
        String onMeasure$lambda$0;
        String onViewAdded$lambda$14;
        String onLayout$lambda$2;
        String updateAxes$lambda$12;
        String visibility$lambda$8;
        String alpha$lambda$10;
        String animateFidget$lambda$18;
        String onDraw$lambda$4;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                animateDoze$lambda$16 = ClockLogger.animateDoze$lambda$16(logMessage);
                return animateDoze$lambda$16;
            case 1:
                onDraw$lambda$6 = ClockLogger.onDraw$lambda$6(logMessage);
                return onDraw$lambda$6;
            case 2:
                onMeasure$lambda$0 = ClockLogger.onMeasure$lambda$0(logMessage);
                return onMeasure$lambda$0;
            case 3:
                onViewAdded$lambda$14 = ClockLogger.onViewAdded$lambda$14(logMessage);
                return onViewAdded$lambda$14;
            case 4:
                onLayout$lambda$2 = ClockLogger.onLayout$lambda$2(logMessage);
                return onLayout$lambda$2;
            case 5:
                updateAxes$lambda$12 = ClockLogger.updateAxes$lambda$12(logMessage);
                return updateAxes$lambda$12;
            case 6:
                visibility$lambda$8 = ClockLogger.setVisibility$lambda$8(logMessage);
                return visibility$lambda$8;
            case 7:
                alpha$lambda$10 = ClockLogger.setAlpha$lambda$10(logMessage);
                return alpha$lambda$10;
            case 8:
                animateFidget$lambda$18 = ClockLogger.animateFidget$lambda$18(logMessage);
                return animateFidget$lambda$18;
            default:
                onDraw$lambda$4 = ClockLogger.onDraw$lambda$4(logMessage);
                return onDraw$lambda$4;
        }
    }
}
