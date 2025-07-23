package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCallInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        String str = OngoingCallInteractor.TAG;
        return "Active call detected: uid=" + logMessage.getInt1() + " startTime=" + logMessage.getLong1() + " hasIcon=" + logMessage.getBool1() + " isAppVisible=" + logMessage.getBool2();
    }
}
