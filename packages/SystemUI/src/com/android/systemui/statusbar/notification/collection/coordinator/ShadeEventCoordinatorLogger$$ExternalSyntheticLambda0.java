package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeEventCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShadeEventCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logShadeEmptied$lambda$1;
        String logNotifRemovedByUser$lambda$3;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                logShadeEmptied$lambda$1 = ShadeEventCoordinatorLogger.logShadeEmptied$lambda$1(logMessage);
                return logShadeEmptied$lambda$1;
            default:
                logNotifRemovedByUser$lambda$3 = ShadeEventCoordinatorLogger.logNotifRemovedByUser$lambda$3(logMessage);
                return logNotifRemovedByUser$lambda$3;
        }
    }
}
