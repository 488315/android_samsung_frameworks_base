package com.android.systemui.log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LogBuffer$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LogBuffer$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = LogBuffer.$r8$clinit;
                String str1 = logMessage.getStr1();
                str1.getClass();
                return str1;
            case 1:
                int i2 = LogBuffer.$r8$clinit;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), " unfrozen");
            default:
                int i3 = LogBuffer.$r8$clinit;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), " frozen");
        }
    }
}
