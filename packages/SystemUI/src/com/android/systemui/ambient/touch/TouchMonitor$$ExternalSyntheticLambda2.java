package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "stopMonitoring(): waiting for sessions to end: " + logMessage.getStr1();
            case 1:
                return "Session popped, hashCode: " + logMessage.getInt1();
            case 2:
                int i = TouchMonitor.AnonymousClass2.$r8$clinit;
                return "Exclusion rect updated to " + logMessage.getStr1();
            default:
                return "Session start, handler: " + logMessage.getStr1() + ", x: " + logMessage.getLong1() + ", y: " + logMessage.getLong2() + ", hashCode: " + logMessage.getInt1();
        }
    }
}
