package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    public KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2(KeyguardCoordinator keyguardCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2 keyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2 = new KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2(this.this$0, continuation);
        keyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2;
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
        return ((KeyguardCoordinator$trackSeenNotifications$isKeyguardPresent$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
