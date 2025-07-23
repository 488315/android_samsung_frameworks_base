package com.android.systemui.statusbar.chips.notification.domain.interactor;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SingleNotificationChipInteractor$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": Starting model has promotedContent=null, which shouldn't happen");
            case 1:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": Can't show chip because promotedContent=null, which shouldn't happen");
            default:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": Can't show chip because status bar chip icon view is null");
        }
    }
}
