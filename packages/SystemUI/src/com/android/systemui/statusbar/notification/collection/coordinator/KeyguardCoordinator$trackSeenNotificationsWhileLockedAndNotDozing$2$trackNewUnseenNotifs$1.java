package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
