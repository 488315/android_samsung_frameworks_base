package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class NotificationLockscreenScrimViewModel$onActivated$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ NotificationLockscreenScrimViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationLockscreenScrimViewModel$onActivated$2$1(NotificationLockscreenScrimViewModel notificationLockscreenScrimViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationLockscreenScrimViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationLockscreenScrimViewModel$onActivated$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationLockscreenScrimViewModel$onActivated$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationLockscreenScrimViewModel notificationLockscreenScrimViewModel = this.this$0;
            this.label = 1;
            if (notificationLockscreenScrimViewModel.$$delegate_0.activateFlowDumper(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
