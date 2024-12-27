package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;

    public KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2$trackNewUnseenNotifs$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return KeyguardCoordinator$trackSeenNotificationsWhileLockedAndNotDozing$2.invokeSuspend$trackNewUnseenNotifs(null, null, null, this);
    }
}
