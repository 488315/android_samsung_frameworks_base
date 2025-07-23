package com.android.systemui.util.wakelock;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WakeLockLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WakeLockLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logAcquire$lambda$1;
        String logRelease$lambda$3;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                logAcquire$lambda$1 = WakeLockLogger.logAcquire$lambda$1(logMessage);
                return logAcquire$lambda$1;
            default:
                logRelease$lambda$3 = WakeLockLogger.logRelease$lambda$3(logMessage);
                return logRelease$lambda$3;
        }
    }
}
