package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCallInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        String str = OngoingCallInteractor.TAG;
        return "Active call detected: uid=" + logMessage.getInt1() + " startTime=" + logMessage.getLong1() + " hasIcon=" + logMessage.getBool1() + " isAppVisible=" + logMessage.getBool2();
    }
}
