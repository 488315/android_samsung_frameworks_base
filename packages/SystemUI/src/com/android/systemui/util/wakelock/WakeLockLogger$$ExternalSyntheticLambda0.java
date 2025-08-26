package com.android.systemui.util.wakelock;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class WakeLockLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WakeLockLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return WakeLockLogger.logAcquire$lambda$1(logMessage);
            default:
                return WakeLockLogger.logRelease$lambda$3(logMessage);
        }
    }
}
