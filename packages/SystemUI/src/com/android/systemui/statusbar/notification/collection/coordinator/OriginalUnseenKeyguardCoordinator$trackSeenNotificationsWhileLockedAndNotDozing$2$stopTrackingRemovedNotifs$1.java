package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;

    public OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$stopTrackingRemovedNotifs$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$stopTrackingRemovedNotifs(null, null, this);
    }
}
