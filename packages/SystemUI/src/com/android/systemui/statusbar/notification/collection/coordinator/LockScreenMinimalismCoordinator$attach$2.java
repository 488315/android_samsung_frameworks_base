package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class LockScreenMinimalismCoordinator$attach$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LockScreenMinimalismCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenMinimalismCoordinator$attach$2(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenMinimalismCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LockScreenMinimalismCoordinator$attach$2(this.this$0, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object trackLockScreenNotificationMinimalismSettingChanges;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = this.this$0;
            this.label = 1;
            trackLockScreenNotificationMinimalismSettingChanges = lockScreenMinimalismCoordinator.trackLockScreenNotificationMinimalismSettingChanges(this);
            if (trackLockScreenNotificationMinimalismSettingChanges == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((LockScreenMinimalismCoordinator$attach$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
