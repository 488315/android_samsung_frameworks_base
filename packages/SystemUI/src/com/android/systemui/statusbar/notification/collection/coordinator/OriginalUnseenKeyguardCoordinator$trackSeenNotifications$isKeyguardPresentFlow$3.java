package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = originalUnseenKeyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3 originalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3 = new OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3(this.this$0, continuation);
        originalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3.Z$0 = ((Boolean) obj).booleanValue();
        return originalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardCoordinatorLogger keyguardCoordinatorLogger;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        keyguardCoordinatorLogger = this.this$0.logger;
        keyguardCoordinatorLogger.logTrackingUnseen(z);
        return Unit.INSTANCE;
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((OriginalUnseenKeyguardCoordinator$trackSeenNotifications$isKeyguardPresentFlow$3) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
